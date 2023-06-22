import enums.MessageCode;
import enums.ProtocolVersion;
import models.SpomsPacketBuilder;
import security.SpomsSecurityImpl;
import org.apache.commons.cli.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocket;


class HttpConsumer {
    private static String serverAddress;
    private static int serverPort;
    public static boolean userExit;
    private static SSLSocket socket;
    private static String trustStoreBasePath = null;
    private static String trustedStoreSecret = null;

    public static void main(String args[]) throws Exception {

        var commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        Options commandLineOptions = getCommandLineOptions();

        try {
            commandLine = commandLineParser.parse(commandLineOptions, args);

            int agvManagerSocketPort = Integer.parseInt(commandLine.getOptionValue("p"));
            String agvManagerAddress = commandLine.getOptionValue("a");

            trustStoreBasePath = commandLine.getOptionValue("t");
            trustedStoreSecret = commandLine.getOptionValue("s");

            serverPort = Integer.parseInt(commandLine.getOptionValue("u"));
            serverAddress = commandLine.getOptionValue("k");

            var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "HTTPConsumer");

            socket = spomsSecurity.CreateSecureClientSocket(agvManagerAddress, agvManagerSocketPort);

            var httpAgvManagerClient = new HttpAgvManagerClient(socket);

            Thread agvDigitalTwinClientThread = httpAgvManagerClient;
            agvDigitalTwinClientThread.start();

            while (true) {

                TimeUnit.MILLISECONDS.sleep(500);

                // String message = new ObjectMapper().writeValueAsString(yourObjectHere);;

                var request = new SpomsPacketBuilder()
                        .messageCode(MessageCode.ALL_AGV_STATUS_REPORT_REQUEST)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data("")
                        .build();

                var response = httpAgvManagerClient.getSpomsCommunication().getResponse(request, MessageCode.ALL_AGV_STATUS_REPORT_RESPONSE);

                if (!postMessage(response.data())) {
                    System.out.println("Sorry, server not responding, message not posted");
                }

//			System.out.println(response.toString());


//			String message = "[{\"id\": \"01\", \"x\": \""+nn+"\", \"y\": \"03\", \"status\": \"idle\"}, {\"id\": \"01\", \"x\": \"02\", \"y\": \"09\", \"status\": \"idle\"}]";

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("AGV Digital Twin", commandLineOptions);
            System.exit(1);
        }
    }



    private static boolean postMessage(String msg) {
        Socket TCPconn;
        DataOutputStream sOut;
        DataInputStream sIn;

        var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "HttpConsumer");

        try {
            TCPconn = spomsSecurity.CreateSecureClientSocket(serverAddress, serverPort);
        } catch (IOException ex) {
            return false;
        }

        try {
            sOut = new DataOutputStream(TCPconn.getOutputStream());
            sIn = new DataInputStream(TCPconn.getInputStream());
            HTTPmessage request = new HTTPmessage();
            request.setRequestMethod("POST");
            request.setURI("/messages");
            request.setContentFromString(msg, "text/plain");
            request.send(sOut);
            HTTPmessage response = new HTTPmessage(sIn);
            if (!response.getStatus().startsWith("200")) {
                throw new IOException();
            }

        } catch (IOException ex) {
            try {
                TCPconn.close();
            } catch (IOException ex2) {
            }
            return false;
        }
        try {
            TCPconn.close();
        } catch (IOException ex2) {
        }
        return true;
    }

    static Options getCommandLineOptions() {
        Options options = new Options();

        options.addOption(Option.builder("a")
                .longOpt("address-manager")
                .hasArg(true)
                .required(true)
                .build());

        options.addOption(Option.builder("p")
                .longOpt("port-manager")
                .hasArg(true)
                .required(true)
                .type(Integer.class)
                .build());

        options.addOption(Option.builder("k")
                .longOpt("address-httpserver")
                .hasArg(true)
                .required(true)
                .build());

        options.addOption(Option.builder("u")
                .longOpt("port-httpserver")
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



