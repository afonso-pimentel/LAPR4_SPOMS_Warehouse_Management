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

import communication.*;
import helpers.SpomsPacketTracer;
import helpers.SpomsPacketTracerImpl;
import models.SpomsPacket;
import strategies.factory.SpomsStrategyFactory;
import strategies.factory.SpomsStrategyFactoryImpl;
import strategies.strategies.SpomsStrategy;
import java.io.IOException;
import java.net.Socket;

public class OrderServerClient extends Thread implements SpomsPacketListener {
    public final SpomsCommunication spomsCommunication;
    private final SpomsStrategyFactory strategyFactory;

    private final SpomsPacketTracer spomsPacketTracer;

    public OrderServerClient(Socket socket) throws IOException {
        spomsCommunication = new SpomsCommunicationImpl(new SpomsReceiverImpl(socket), new SpomsSenderImpl(socket));
        spomsCommunication.subscribe(this);
        spomsPacketTracer = new SpomsPacketTracerImpl(OrderServer.class.getName(), OrderServer.class);
        strategyFactory = new SpomsStrategyFactoryImpl();
    }

    public void run() {
        ((SpomsCommunicationImpl)spomsCommunication).run();
        try {
            OrderServer.removeOrderServerClient(this);
        } catch (Exception e) {
        }
    }
    @Override
    public void handlePacket(SpomsPacket newPacket) throws IOException {
        SpomsStrategy strategy = strategyFactory.GetStrategy(newPacket.messageCode());

        spomsPacketTracer.trace(newPacket);

        if(strategy != null){
            SpomsPacket responsePacket = strategy.PerformStrategy(newPacket);
            spomsCommunication.publish(responsePacket);
            return;
        }
    }
}
