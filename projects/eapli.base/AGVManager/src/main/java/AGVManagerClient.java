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
import enums.AGVManagerClientTypes;
import enums.MessageCode;
import helpers.SpomsPacketTracer;
import helpers.SpomsPacketTracerImpl;
import models.SpomsPacket;
import strategies.factory.AgvManagerStrategyFactory;
import strategies.factory.AgvManagerStrategyFactoryImpl;
import strategies.strategies.AgvManagerStrategy;
import java.io.IOException;
import java.net.Socket;

public class AGVManagerClient extends Thread implements SpomsPacketListener {
    public final SpomsCommunication spomsCommunication;
    private final AgvManagerStrategyFactory strategyFactory;
    private final SpomsPacketTracer spomsPacketTracer;
    private AGVManagerClientTypes clientType;
    private long id;

    public AGVManagerClient(Socket socket) throws IOException {
        spomsCommunication = new SpomsCommunicationImpl(new SpomsReceiverImpl(socket), new SpomsSenderImpl(socket));
        spomsCommunication.subscribe(this);
        strategyFactory = new AgvManagerStrategyFactoryImpl();
        spomsPacketTracer = new SpomsPacketTracerImpl(AGVManager.class.getName(), AGVManager.class);

        clientType = AGVManagerClientTypes.DEFAULT;
        id = 0;
    }

    public void run() {
        ((SpomsCommunicationImpl)spomsCommunication).run();
        try {
            AGVManager.removeAGVManagerClient(this);
        } catch (Exception e) {
        }
    }
    @Override
    public void handlePacket(SpomsPacket newPacket) throws IOException {
        AgvManagerStrategy strategy = strategyFactory.GetStrategy(newPacket.messageCode());

        spomsPacketTracer.trace(newPacket);

        middleware(newPacket);

        if(strategy != null){
            SpomsPacket responsePacket = strategy.PerformStrategy(newPacket);
            spomsCommunication.publish(responsePacket);
            return;
        }
    }

    private void middleware(SpomsPacket packet){
       try{
           switch(packet.messageCode()){
               case AGV_INFO_REQUEST:
                   Long id = Long.parseLong(packet.data());
                   this.id = id;
                   this.clientType = AGVManagerClientTypes.AGVDIGITALTWIN;
                   break;
               default:
                   break;
           }
       } catch(Exception e){

       }
    }

    /**
     * Return client type
     * @return AGVManagerClientType
     */
    public AGVManagerClientTypes clientType(){
        return clientType;
    }

    /**
     * Returns the id
     * @return Long
     */
    public Long id(){
        return id;
    }
}
