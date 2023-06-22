/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.app.user.console;

import eapli.base.app.user.console.OrderServerCommunication.OrderServerClient;
import eapli.base.app.user.console.presentation.MainMenu;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.io.util.Console;
import org.apache.commons.cli.*;
import security.SpomsSecurityImpl;

import java.io.IOException;
import java.net.Socket;

/**
 * Base User App.
 */
@SuppressWarnings("squid:S106")
public final class BaseUserApp {
    public static OrderServerClient orderServerClient;
    public static long vatCode;

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private BaseUserApp() {
    }

    public static void main(final String[] args) throws IOException, ParseException {
        var commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        Options commandLineOptions = getCommandLineOptions();

        try{
            commandLine = commandLineParser.parse(commandLineOptions, args);

            int socketServerPort =  Integer.parseInt(commandLine.getOptionValue("p"));
            String address = commandLine.getOptionValue("a");
            String trustStoreBasePath = commandLine.getOptionValue("t");
            String trustedStoreSecret = commandLine.getOptionValue("s");

            var spomsSecurity = new SpomsSecurityImpl(trustStoreBasePath, trustedStoreSecret, "ClientApp");

            Socket socket = spomsSecurity.CreateSecureClientSocket(address, socketServerPort);

            orderServerClient = new OrderServerClient(socket);

            Thread orderServerClientThread = orderServerClient;
            orderServerClientThread.start();

            System.out.println("=====================================");
            System.out.println("Client Application");
            System.out.println("(C) 2021 - 2022");
            System.out.println("Connected to Order Server at address: " + address + ":" + socketServerPort);
            System.out.println("=====================================");

            AuthzRegistry.configure(PersistenceContext.repositories().users(),
                    new BasePasswordPolicy(), new PlainTextEncoder());

            vatCode = Console.readLong("Insert your VATCODE: ");

            final MainMenu menu = new MainMenu();
            menu.mainLoop();

        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "AGV Digital Twin", commandLineOptions );
            System.exit(1);
        } catch(Exception exception){
            System.out.println("An error has occurred: " + exception.getMessage());
        }

        // exiting the application, closing all threads
        System.exit(0);
    }

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
