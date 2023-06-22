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

package communication;

import enums.MessageCode;
import models.SpomsPacket;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

/**
 * @inheritDoc
 *
 */
public class SpomsCommunicationImpl extends Thread implements SpomsCommunication{
    private final SpomsReceiver spomsReceiver;
    private final SpomsSender spomsSender;
    private final PacketListener packetListener;
    private ConcurrentLinkedQueue<MessageCode> requestQueue;
    private ConcurrentLinkedQueue<SpomsPacket> responseQueue;

    public SpomsCommunicationImpl(SpomsReceiver spomsReceiver, SpomsSender spomsSender) {
        this.spomsReceiver = spomsReceiver;
        this.spomsSender = spomsSender;
        packetListener = new PacketListener();
        spomsReceiver.subscribe(packetListener);

        requestQueue = new ConcurrentLinkedQueue<>();
        responseQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public SpomsPacket getResponse(SpomsPacket request, MessageCode expectedResponse) throws IOException, TimeoutException {
        requestQueue.add(expectedResponse);

        spomsSender.send(request);

        long start = System.currentTimeMillis();

        while (responseQueue.peek() == null || responseQueue.peek().messageCode() != expectedResponse){
            if (System.currentTimeMillis() - start > 30000 ){
                requestQueue.poll();
                throw new TimeoutException(String.format("Condition not met within %s ms", 30000));
            }
        }

        return responseQueue.poll();
    }

    /**
     * @inheritDoc
     *
     */
    public void run() {
        spomsReceiver.run();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public void publish(SpomsPacket packet) throws IOException {
        spomsSender.send(packet);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public void subscribe(SpomsPacketListener packetListener) {
        spomsReceiver.subscribe(packetListener);
    }

    protected class PacketListener implements SpomsPacketListener{
        @Override
        public void handlePacket(SpomsPacket newPacket) throws IOException {
            if(newPacket.messageCode() == requestQueue.peek()) {
                requestQueue.poll();
                responseQueue.add(newPacket);
            }
        }
    }
}
