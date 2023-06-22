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

public class AGVDigitalTwinClient extends Thread implements SpomsPacketListener {

    private final SpomsCommunication spomsCommunication;
    private final SpomsStrategyFactory strategyFactory;

    private final SpomsPacketTracer spomsPacketTracer;

    public AGVDigitalTwinClient(Socket socket, SpomsStrategyFactory strategyFactory) throws IOException {
        spomsCommunication = new SpomsCommunicationImpl(new SpomsReceiverImpl(socket), new SpomsSenderImpl(socket));
        spomsCommunication.subscribe(this);
        spomsPacketTracer = new SpomsPacketTracerImpl(AGVDigitalTwin.class.getName(), AGVDigitalTwin.class);
        this.strategyFactory = strategyFactory;
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

    @Override
    public void run(){
        ((SpomsCommunicationImpl)spomsCommunication).run();
    }

    /**
     * Returns the spoms communication for the client
     * @return SpomsCommunication
     */
    public SpomsCommunication getSpomsCommunication(){
        return spomsCommunication;
    }

    /**
     * Returns the spoms strategy factory for the client
     * @return SpomsStrategyFactory
     */
    public SpomsStrategyFactory getStrategyFactory(){
        return strategyFactory;
    }
}