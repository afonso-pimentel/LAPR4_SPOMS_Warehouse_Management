package eapli.base.app.user.console.OrderServerCommunication;

import communication.*;
import eapli.base.app.user.console.BaseUserApp;
import helpers.SpomsPacketTracer;
import helpers.SpomsPacketTracerImpl;
import models.SpomsPacket;
import java.io.IOException;
import java.net.Socket;

public class OrderServerClient extends Thread implements SpomsPacketListener {
    private final SpomsCommunication spomsCommunication;
    private final SpomsPacketTracer spomsPacketTracer;

    public OrderServerClient(Socket socket) throws IOException {
        spomsCommunication = new SpomsCommunicationImpl(new SpomsReceiverImpl(socket), new SpomsSenderImpl(socket));
        spomsCommunication.subscribe(this);
        spomsPacketTracer = new SpomsPacketTracerImpl(BaseUserApp.class.getName(), BaseUserApp.class);
    }

    @Override
    public void handlePacket(SpomsPacket newPacket) throws IOException {
        spomsPacketTracer.trace(newPacket);
    }

    @Override
    public void run(){
        ((SpomsCommunicationImpl)spomsCommunication).run();
    }

    public SpomsCommunication SpomsCommunication(){
        return spomsCommunication;
    }
}