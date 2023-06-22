import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.SSLServerSocket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import security.SpomsSecurityImpl;

/**
 *
 * @author ANDRE MOREIRA (asc@isep.ipp.pt)
 */
public class HttpServer {
    static private String BASE_FOLDER="HTTPServer/src/main/java/www";

    private static SSLServerSocket sock;

    public static void main(String args[]) throws Exception {

        Socket cliSock;

        var commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        Options commandLineOptions = getCommandLineOptions();
        int socketServerPort = 0;

        try {
            commandLine = commandLineParser.parse(commandLineOptions, args);

            socketServerPort = Integer.parseInt(commandLine.getOptionValue("p"));
            String trustStoreBasePath = commandLine.getOptionValue("t");
            String trustedStoreSecret = commandLine.getOptionValue("s");

            BASE_FOLDER = commandLine.getOptionValue("c");

            var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "HTTPServer");

            sock = spomsSecurity.CreateSecureServerSocket(socketServerPort, false);
        }
        catch(IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
            System.exit(1);
        }

        System.out.println("Server ready, listening on port number " + socketServerPort);

        while(true) { 

            cliSock=sock.accept();
            HttpRequest req=new HttpRequest(cliSock, BASE_FOLDER);
            req.start();
        }
    } 
	
    // MESSAGES ARE ACCESSED BY THREADS - LOCKING REQUIRED
    private static int nextMsgNum = 0;
    private static final ArrayList<String> MSG_LIST = new ArrayList<>();

    public static String getMsg(int msgNumber) {
        synchronized(MSG_LIST) {
            while(msgNumber>=nextMsgNum) {   
                try { MSG_LIST.wait(); }    // wait for a notification on MSG_LIST's monitor
                                            // while waiting MSG_LIST's intr lock is released
                catch(InterruptedException ex) {
                    System.out.println("Thread error: interrupted");
                    return null;
                }
            }
            return MSG_LIST.get(msgNumber);
        }
    }
    
    public static void addMsg(String msg) {
        synchronized(MSG_LIST) {
            MSG_LIST.add(nextMsgNum, msg);
            nextMsgNum++;
            MSG_LIST.notifyAll(); // notify all threads waiting on MSG_LIST's monitor
        }
    }

    static Options getCommandLineOptions(){
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

        options.addOption(Option.builder("c")
                .longOpt("content-folder")
                .hasArg(true)
                .required(true)
                .build());

        return options;
    }

}

