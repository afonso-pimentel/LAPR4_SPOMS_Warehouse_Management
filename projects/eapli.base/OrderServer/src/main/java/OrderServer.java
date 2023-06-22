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

import enums.MessageCode;
import models.SpomsPacket;
import org.apache.commons.cli.Option;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.apache.commons.cli.*;
import security.SpomsSecurityImpl;

class OrderServer {
    private static ServerSocket sock;
    private static List<OrderServerClient> clientsList = Collections.synchronizedList(new ArrayList());

    /**
     * Sends a message to all the Order Server clients
     * @param packet SPOMS Packet
     */
    public static synchronized void sendMessageToAllOrderServerClients(SpomsPacket packet) throws Exception {
        for(OrderServerClient client : clientsList){
            client.spomsCommunication.publish(packet);
        }
    }

    public static synchronized List<SpomsPacket> getResponseFromAllOrderServerClients(SpomsPacket request, MessageCode expectedResponse) throws IOException, TimeoutException {
        var packets = new ArrayList<SpomsPacket>();

        for(OrderServerClient client: clientsList){
            packets.add(client.spomsCommunication.getResponse(request, expectedResponse));
        }

        return packets;
    }

    /**
     * Add's a new Order Server client
     * @param newClient Order Server Client
     */
    public static synchronized void addOrderServerClient(OrderServerClient newClient) throws Exception {
        clientsList.add(newClient);

        System.out.println("New Order Server client added! Client number: " + clientsList.size());
    }

    /**
     * Removes the specified Order Server Client
     * @param client Order Server Client
     */
    public static synchronized void removeOrderServerClient(OrderServerClient client) throws Exception {
        clientsList.remove(client);
        System.out.println("Order server client removed! Number of clients: " + clientsList.size());
    }

    public static void main(String[] args) throws Exception {
        var commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        Options commandLineOptions = GetCommandLineOptions();

        try {
            commandLine = commandLineParser.parse(commandLineOptions, args);

            int socketServerPort =  Integer.parseInt(commandLine.getOptionValue("p"));
            String trustStoreBasePath = commandLine.getOptionValue("t");
            String trustedStoreSecret = commandLine.getOptionValue("s");

            printLogo();

            var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "OrderServer");

            sock = spomsSecurity.CreateSecureServerSocket(socketServerPort, true);

            System.out.println("Order Server STARTED IN PORT " + socketServerPort);

            while(true) {

                Socket clientSocket = sock.accept();

                var newClient = new OrderServerClient(clientSocket);

                addOrderServerClient(newClient);

                Thread clientThread = newClient;

                clientThread.start();
            }

        }catch(IOException ex) {
            System.out.println("Local port number not available.");
            System.exit(1);

        }catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "Order Server", commandLineOptions );
            System.exit(1);
        }
    }

    static void printLogo(){
        String[] data = { "\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510",
                "\u2502Order Server\u2502",
                "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518"};
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