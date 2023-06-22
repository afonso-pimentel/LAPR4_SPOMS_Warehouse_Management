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

import models.SpomsPacket;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @inheritDoc
 *
 */
public class SpomsReceiverImpl implements SpomsReceiver, Closeable{
    private final Socket clientSocket;
    private final List<SpomsPacketListener> listOfSpomsPacketListeners;

    private final SpomsParser spomsParser;
    private final DataInputStream socketReader;

    public SpomsReceiverImpl(Socket socket) throws IOException {
        clientSocket = socket;

        listOfSpomsPacketListeners = Collections.synchronizedList(new ArrayList());

        socketReader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        spomsParser = new SpomsParserImpl();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public void subscribe(SpomsPacketListener spomsPacketListener) {
        listOfSpomsPacketListeners.add(spomsPacketListener);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public void unsubscribe(SpomsPacketListener spomsPacketListener) {
        listOfSpomsPacketListeners.remove(spomsPacketListener);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public void run() {
        try {
            int dataLength1 = 0;
            int dataLength2 = 0;

            int endOfStream = 0;

            while (endOfStream != -1) {
                byte[] packetHeader = new byte[SpomsPacket.TOTAL_PACKET_HEADER_SIZE];

                // READ SPOMS PACKET HEADER => 4 BYTES IN TOTAL
                endOfStream = socketReader.read(packetHeader, 0, SpomsPacket.TOTAL_PACKET_HEADER_SIZE);

                // IF CLIENT DISCONNECTS 'endOfStream' will be -1
                if(endOfStream == -1)
                    break;

                dataLength1 = Byte.toUnsignedInt(packetHeader[2]);
                dataLength2 = Byte.toUnsignedInt(packetHeader[3]);

                // CALCULATE PACKET DATA LENGTH
                int packetDataLength = dataLength1 + (256 * dataLength2);

                byte[] packetData = new byte[packetDataLength];

                // READ THE TOTAL AMMOUNT OF THE PACKET DATA LENGTH
                endOfStream = socketReader.read(packetData, 0, packetDataLength);

                // CREATE FINAL RAW SPOMS PACKET
                byte[] rawPacket = new byte[SpomsPacket.TOTAL_PACKET_HEADER_SIZE + packetDataLength];

                // COPY THE SPOMS PACKET HEADER AND PACKET DATA INTO THE FINAL RAW PACKET
                System.arraycopy(packetHeader,0,rawPacket,0, packetHeader.length);
                System.arraycopy(packetData,0,rawPacket, packetHeader.length, packetData.length);

                SpomsPacket packet = spomsParser.parseFromRawPacket(rawPacket);

                NotifyListeners(packet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void NotifyListeners(SpomsPacket newPacket) throws IOException {
        if(this.listOfSpomsPacketListeners == null || newPacket == null)
            return;

        for(SpomsPacketListener spomsPacketListener : this.listOfSpomsPacketListeners){
            try{
                if(spomsPacketListener != null){
                    spomsPacketListener.handlePacket(newPacket);
                }
            } catch(Exception e){
                // SUPRESS ANY ERRORS WHEN NOTIFYING LISTENERS
            }
        }
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public void close() throws IOException {
        if (socketReader != null) {
            socketReader.close();
        }
    }
}
