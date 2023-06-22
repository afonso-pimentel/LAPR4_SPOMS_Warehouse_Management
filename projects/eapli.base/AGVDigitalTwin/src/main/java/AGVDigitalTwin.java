/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import AGVDigitalTwinSettings.AGVSettings;
import communication.SpomsCommunication;
import eapli.base.warehousemanagement.domain.AgvStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.framework.presentation.console.SelectWidget;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeoutException;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.SerializationUtils;
import strategies.factory.SpomsStrategyFactoryImpl;
import system.*;
import security.SpomsSecurityImpl;
import strategies.strategies.Simulation;

public class AGVDigitalTwin {
    final static Iterable<String> options = new ArrayList<> (
            List.of("COMMTEST", "DISCONNECT")
    );

    private static int socketServerPort = 0;
    private static String address = null;
    private static BigDecimal chargeRate;
    private static BigDecimal lossRate;
    private static Long chargedPercentage;
    private static Long lowPercentage;
    private static String trustStoreBasePath;
    private static String trustedStoreSecret;

    //All Threads
    private static AGVDigitalTwinClient agvDigitalTwinClient;
    private static SimulationEngine engine;
    private static Sensor sensor;
    private static RoutePlanner routePlanner;
    private static Positioning positioning;
    private static ControlSystem controlSystem;
    private static BatteryManagementSystem bms;
    private static Communication coms;

    public static SharedInformation sharedPlace;

    public static void main(String[] args) throws TimeoutException {
        try{
            loadCommands(args);

            ObjectMapper objectMapper = new ObjectMapper();

            var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "AGVDigitalTwin" + AGVSettings.Settings.ID);

            Socket socket = spomsSecurity.CreateSecureClientSocket(address, socketServerPort);

            printLogo();

            System.out.println("AGV DIGITAL TWIN [" + AGVSettings.Settings.ID + "] CONNECTED TO " + address + ":" + socketServerPort);

            System.out.println("========= Preparing All Systems  ===========");

            //Create communication with server
            agvDigitalTwinClient = new AGVDigitalTwinClient(socket, new SpomsStrategyFactoryImpl());
            agvDigitalTwinClient.start();

            //Map world
            SpomsPacket worldResponse = agvDigitalTwinClient.getSpomsCommunication().getResponse(new SpomsPacketBuilder()
                    .messageCode(MessageCode.WORLD_POSITIONS_REQUEST)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data("")
                    .build(), MessageCode.WORLD_POSITIONS_RESPONSE);


            sharedPlace = new SharedInformation(objectMapper.readValue(worldResponse.data(), WarehouseDTO.class));

            agvDigitalTwinClient.getStrategyFactory().addSharedInformation(sharedPlace);

            //Set AGV info
            loadAgvInfo(agvDigitalTwinClient.getSpomsCommunication(), sharedPlace);
            sensor = new Sensor(sharedPlace);
            sensor.start();

            routePlanner = new RoutePlanner(sharedPlace);
            routePlanner.start();

            positioning = new Positioning(sharedPlace);
            positioning.start();

            controlSystem = new ControlSystem(sharedPlace, agvDigitalTwinClient.getSpomsCommunication());
            controlSystem.start();

            bms = new BatteryManagementSystem(sharedPlace);
            bms.start();

            coms = new Communication(sharedPlace, agvDigitalTwinClient.getSpomsCommunication());
            coms.start();

            engine = new SimulationEngine(sharedPlace, agvDigitalTwinClient.getSpomsCommunication());
            engine.start();

            int selectedOption = -1;
            while (selectedOption != 0) {

                final SelectWidget<String> menuSelector = new SelectWidget<>("Select an option:", options,
                        visitee -> System.out.print(visitee));
                menuSelector.show();
                selectedOption = menuSelector.selectedOption();

                SpomsPacket request;
                SpomsPacket response;

                switch(selectedOption){
                    case 1:
                        request =  new SpomsPacketBuilder()
                                .messageCode(MessageCode.COMMTEST)
                                .protocolVersion(ProtocolVersion.VERSION_1)
                                .data("")
                                .build();
                        System.out.println("Sending COMMTEST!");
                        response = agvDigitalTwinClient.getSpomsCommunication().getResponse(request, MessageCode.ACK);
                        System.out.println("Response received!");
                        System.out.println(response.toString());
                        break;
                    case 2:
                        request =  new SpomsPacketBuilder()
                                .messageCode(MessageCode.DISCONN)
                                .protocolVersion(ProtocolVersion.VERSION_1)
                                .data("")
                                .build();
                        System.out.println("Sending DISCONNECT request!");
                        response = agvDigitalTwinClient.getSpomsCommunication().getResponse(request, MessageCode.ACK);
                        System.out.println("Response received!");
                        System.out.println(response.toString());

                        if(response != null){
                            System.out.println("AGV Digital Twin disconnected!");
                        }
                        break;
                }
            }
            shutdownDigitalTwin();

        }catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assign shared data to agv and initialize comunication protocol
     * @param spomsCommunication SpomsCommunication 
     * @param sharedPlace SharedInformation
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    static void loadAgvInfo(SpomsCommunication spomsCommunication, SharedInformation sharedPlace) throws IOException, TimeoutException, InterruptedException {

        SpomsPacket infoResponse = spomsCommunication.getResponse(new SpomsPacketBuilder()
                .messageCode(MessageCode.AGV_INFO_REQUEST)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(String.valueOf(AGVSettings.Settings.ID))
                .build(), MessageCode.AGV_INFO_RESPONSE);

        if(infoResponse == null) {
            System.out.println("Something went wrong!");
            shutdownDigitalTwin();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        AgvDTO responseObject = objectMapper.readValue(infoResponse.data(), AgvDTO.class);
        if(responseObject.HasErrors()) {
            System.out.println(responseObject.ErrorMessage());
            shutdownDigitalTwin();
        }

        AGVSettings.Settings.battery = new Battery(
                new BigDecimal(100),//responseObject.batteryCapacity,
                new BigDecimal(100),//responseObject.batteryCapacity,
                lossRate,
                chargeRate,
                lowPercentage,
                chargedPercentage);
        AGVSettings.Settings.dock = SerializationUtils.clone(sharedPlace.worldMap().docks.stream().filter(d -> d.id.equals(responseObject.dock)).findFirst().get());
        AGVSettings.Settings.currentPosition = SerializationUtils.clone(AGVSettings.Settings.dock.position);
        AGVSettings.Settings.currentStatus = AgvStatus.IDLE;
    }

    /**
     * Load args from cli
     * -p socketServerPort
     * -a address
     * -i AGV id
     * -t trustStoreBasePath
     * -s trustStoreSecret
     * -lp lowPercentage
     * -cp change percentage
     * -lr loss rate
     * -cr charge rate
     * -fs full speed
     * -hs half speed
     * @param args 1D String array
     */
    static void loadCommands(String[] args){
        var commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        Options commandLineOptions = getCommandLineOptions();

        try {
            commandLine = commandLineParser.parse(commandLineOptions, args);
        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "AGV Digital Twin", commandLineOptions );
            System.exit(1);
        }

        if (commandLine.hasOption("p")) {
            socketServerPort = Integer.parseInt(commandLine.getOptionValue("p"));
        }
        if(commandLine.hasOption("a")){
            address = commandLine.getOptionValue("a");
        }
        if(commandLine.hasOption("i")){
            AGVSettings.Settings.ID = Long.parseLong(commandLine.getOptionValue("i"));
        }
        if(commandLine.hasOption("t")){
            trustStoreBasePath = commandLine.getOptionValue("t");
        }
        if(commandLine.hasOption("s")){
            trustedStoreSecret  = commandLine.getOptionValue("s");
        }

        if(commandLine.hasOption("lp"))
            lowPercentage = Long.parseLong(commandLine.getOptionValue("lp"));
        else
            lowPercentage = Constants.lowPercentage;

        if(commandLine.hasOption("cp"))
            chargedPercentage = Long.parseLong(commandLine.getOptionValue("cp"));
        else
            chargedPercentage = Constants.chargedPercentage;

        if(commandLine.hasOption("lr"))
            lossRate = new BigDecimal(commandLine.getOptionValue("lr"));
        else
            lossRate = Constants.lossRate;

        if(commandLine.hasOption("cr"))
            chargeRate = new BigDecimal(commandLine.getOptionValue("cr"));
        else
            chargeRate = Constants.chargeRate;

        if(commandLine.hasOption("fs"))
            AGVSettings.Settings.fullSpeed = Integer.parseInt(commandLine.getOptionValue("fs"));
        else
            AGVSettings.Settings.fullSpeed = Constants.fullSpeed;

        if(commandLine.hasOption("hs"))
            AGVSettings.Settings.halfSpeed = Integer.parseInt(commandLine.getOptionValue("hs"));
        else
            AGVSettings.Settings.halfSpeed = Constants.halfSpeed;
    }

    /**
     * Shutdonw AGVDigitalTwin routines (raise lock events and joins threads to terminate)
     * @throws InterruptedException
     * @throws IOException
     * @throws TimeoutException
     */
    static void shutdownDigitalTwin() throws InterruptedException, IOException, TimeoutException {
        sharedPlace.currentStatus(AgvStatus.SHUTTING_DOWN);

        Helper.raiseEvent(LockObjects.lockPositioningComplete);
        Helper.raiseEvent(LockObjects.lockStartSensor);
        Helper.raiseEvent(LockObjects.lockThrottle);
        Helper.raiseEvent(LockObjects.lockNewPath);
        Helper.raiseEvent(LockObjects.lockNewOrder);
        Helper.raiseEvent(LockObjects.lockBatteryCharged);
        Helper.raiseEvent(LockObjects.lockCalculateRoute);
        bms.join();
        engine.join();
        sensor.join();
        routePlanner.join();
        positioning.join();
        controlSystem.join();
        coms.join();

        agvDigitalTwinClient.getSpomsCommunication().getResponse(new SpomsPacketBuilder()
                .messageCode(MessageCode.DISCONN)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data("")
                .build(), MessageCode.ACK);

        System.exit(0);
    }

    /**
     * print agv digital twin logo in a fancy way
     */
    static void printLogo(){
        String[] data = { "\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                "\u2502AGV Digital Twin\u2502",
                "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
        for (String s : data) {
            System.out.println(s);
        }
        for (String s : data) {
            if(System.console() != null)
                System.console().writer().println(s);
        }
    }

    /**
     * Get arguments from cli and add to Options object
     * @return Options object
     */
    static Options getCommandLineOptions(){
        Options options = new Options();

        options.addOption(Option.builder("a")
                .longOpt("address")
                .hasArg(true)
                .required(true)
                .build());

        options.addOption(Option.builder("p")
                .longOpt("port")
                .hasArg(true)
                .required(true)
                .type(Integer.class)
                .build());

        options.addOption(Option.builder("i")
                .longOpt("id")
                .hasArg(true)
                .required(true)
                .type(Long.class)
                .build());

        options.addOption(Option.builder("lp")
                .longOpt("lowPercentage")
                .hasArg(true)
                .required(false)
                .type(Long.class)
                .build());

        options.addOption(Option.builder("cp")
                .longOpt("chargedPercentage")
                .hasArg(true)
                .required(false)
                .type(Long.class)
                .build());

        options.addOption(Option.builder("lr")
                .longOpt("lossRate")
                .hasArg(true)
                .required(false)
                .type(BigDecimal.class)
                .build());

        options.addOption(Option.builder("cr")
                .longOpt("chargeRate")
                .hasArg(true)
                .required(false)
                .type(BigDecimal.class)
                .build());

        options.addOption(Option.builder("fs")
                .longOpt("fullSpeed")
                .hasArg(true)
                .required(false)
                .type(Integer.class)
                .build());

        options.addOption(Option.builder("hs")
                .longOpt("halfSpeed")
                .hasArg(true)
                .required(false)
                .type(Integer.class)
                .build());

        options.addOption(Option.builder("t")
                .longOpt("storePath")
                .hasArg(true)
                .required(true)
                .build());

        options.addOption(Option.builder("s")
                .longOpt("storeSecret")
                .hasArg(true)
                .required(true)
                .build());

        options.addOption(Option.builder("h")
                .longOpt("help")
                .hasArg(true)
                .required(false)
                .build());

        return options;
    }

}
