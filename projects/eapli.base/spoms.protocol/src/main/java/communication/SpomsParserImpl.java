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
import enums.ProtocolVersion;
import models.SpomsPacket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @inheritDoc
 *
 */
public class SpomsParserImpl implements SpomsParser{

    public int calculatePacketDataTotalLength(int dataLength1, int dataLength2) {
        return dataLength1 + (256 * dataLength2);
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public SpomsPacket parseFromRawPacket(byte[] rawPacket) {

        int protocolVersionNumber = Byte.toUnsignedInt(rawPacket[0]);
        int messageCodeNumber = Byte.toUnsignedInt(rawPacket[1]);
        int dataLength1 = Byte.toUnsignedInt(rawPacket[2]);
        int dataLength2 = Byte.toUnsignedInt(rawPacket[3]);

        int packetDataTotalLength = calculatePacketDataTotalLength(dataLength1, dataLength2);

        byte[] message = Arrays.copyOfRange(rawPacket, 4, 4 + packetDataTotalLength);

        var protocolVersion = ProtocolVersion.values()[protocolVersionNumber];
        var messageCode = MessageCode.values()[messageCodeNumber];

        return new SpomsPacket(messageCode, protocolVersion, dataLength1, dataLength2, new String(message));
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public byte[] parseToRawPacket(SpomsPacket packet){
        var protocolVersion = (byte)packet.protocolVersion().ordinal();
        var messageCode = (byte)packet.messageCode().ordinal();
        var dataLength1 = (byte) packet.dataLength1();
        var dataLength2 = (byte) packet.dataLength2();

        byte[] rawPacket = new byte[SpomsPacket.TOTAL_PACKET_HEADER_SIZE + calculatePacketDataTotalLength(packet.dataLength1(), packet.dataLength2())];

        rawPacket[0] = protocolVersion;
        rawPacket[1] = messageCode;
        rawPacket[2] = dataLength1;
        rawPacket[3] = dataLength2;

        byte[] message = packet.data().getBytes(StandardCharsets.UTF_8);

        int rawPacketIndex = 4;

        for(int i = 0; i < message.length; i++){
            rawPacket[rawPacketIndex] = message[i];
            rawPacketIndex++;
        }

        return rawPacket;
    }
}
