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

import enums.AGVManagerClientTypes;
import enums.MessageCode;
import models.SpomsPacket;
import org.apache.commons.cli.Option;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.apache.commons.cli.*;
import security.SpomsSecurityImpl;
import javax.net.ssl.SSLServerSocket;

class AGVManager {
    private static SSLServerSocket sock;
    private static List<AGVManagerClient> clientsList = Collections.synchronizedList(new ArrayList());

    /**
     * Sends a message to all the AGV Manager clients
     * @param packet SPOMS Packet
     */
    public static synchronized void sendMessageToAllAGVManagerClients(SpomsPacket packet) throws Exception {
        for(AGVManagerClient client : clientsList){
            client.spomsCommunication.publish(packet);
        }
    }

    public static synchronized List<SpomsPacket> getResponseFromAllAGVManagerClients(SpomsPacket request, MessageCode expectedResponse) throws IOException, TimeoutException {
        var packets = new ArrayList<SpomsPacket>();

        for(AGVManagerClient client: clientsList){
            packets.add(client.spomsCommunication.getResponse(request, expectedResponse));
        }

        return packets;
    }

    /**
     * Add's a new AGV Manager client
     * @param newClient AGV Manager Client
     */
    public static synchronized void addAGVManagerClient(AGVManagerClient newClient) throws Exception {
        clientsList.add(newClient);

        System.out.println("New AGV Manager client added! Client number: " + clientsList.size());
    }

    /**
     * Removes the specified AGV Manager Client
     * @param client AGV Manager Client
     */
    public static synchronized void removeAGVManagerClient(AGVManagerClient client) throws Exception {
        clientsList.remove(client);
        System.out.println("AGV Manager client removed! Number of clients: " + clientsList.size());
    }

    /**
     *
     * @return
     */
    public static synchronized AGVManagerClient getDigitalTwin(Long id){
        Optional<AGVManagerClient> client =  clientsList.stream().filter(
                o -> o.clientType() == AGVManagerClientTypes.AGVDIGITALTWIN
        && o.id() == id).findFirst();

        if(client.isPresent())
            return client.get();

        return null;
    }

    public static void main(String[] args) throws Exception {
        var commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        Options commandLineOptions = GetCommandLineOptions();

        try {
            commandLine = commandLineParser.parse(commandLineOptions, args);

            int socketServerPort = Integer.parseInt(commandLine.getOptionValue("p"));
            String trustStoreBasePath = commandLine.getOptionValue("t");
            String trustedStoreSecret = commandLine.getOptionValue("s");
            long agvManagerId = Long.parseLong(commandLine.getOptionValue("i"));

            printLogo();

            var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "AGVManager" + agvManagerId);

            sock = spomsSecurity.CreateSecureServerSocket(socketServerPort, true);

            System.out.println("AGV MANAGER STARTED IN PORT " + socketServerPort);

            var agvAutomatedTasks = new AGVManagerAutomatedTasks();
            agvAutomatedTasks.start();

            while(true) {

                Socket clientSocket = sock.accept();

                var newClient = new AGVManagerClient(clientSocket);

                addAGVManagerClient(newClient);

                Thread clientThread = newClient;

                clientThread.start();
            }

        }catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "AGV Manager", commandLineOptions );
            System.exit(1);
        } catch(Exception exception){
            System.out.println("An error has occurred on AGV Manager: " + exception.getMessage());
        }
    }

    static void printLogo(){
        String[] data = { "\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                "\u2502AGV MANAGER\u2502",
                "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
        for (String s : data) {
            System.out.println(s);
        }
        for (String s : data) {
            if(System.console() != null)
                System.console().writer().println(s);
        }
    }

    static Options GetCommandLineOptions(){
        Options options = new Options();

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

        options.addOption(Option.builder("t")
                .longOpt("trusted-store")
                .hasArg(true)
                .required(true)
                .type(String.class)
                .build());

        options.addOption(Option.builder("s")
                .longOpt("trusted-store-secret")
                .hasArg(true)
                .required(true)
                .type(String.class)
                .build());

        options.addOption(Option.builder("h")
                .longOpt("help")
                .hasArg(true)
                .required(false)
                .build());

        return options;
    }
}