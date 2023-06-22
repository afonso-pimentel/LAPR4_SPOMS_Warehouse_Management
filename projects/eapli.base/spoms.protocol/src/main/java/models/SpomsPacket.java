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

package models;

import enums.MessageCode;
import enums.ProtocolVersion;

/**
 * SPOMS Packet
 */
public class SpomsPacket{
    public static final int TOTAL_PACKET_HEADER_SIZE = 4;
    private final MessageCode messageCode;
    private final ProtocolVersion protocolVersion;
    private final int dataLength1;
    private final int dataLength2;
    private final String data;

    /**
     * Initializes a new instance of a SPOMS Packet
     * @param messageCode MessageCode
     * @param protocolVersion ProtocolVersion
     * @param dataLength1 DataLength1
     * @param dataLength2 DataLength2
     * @param data Data
     */
    public SpomsPacket(MessageCode messageCode, ProtocolVersion protocolVersion, int dataLength1, int dataLength2, String data) {
        this.messageCode = messageCode;
        this.protocolVersion = protocolVersion;
        this.dataLength1 = dataLength1;
        this.dataLength2 = dataLength2;
        this.data = data;
    }

    /**
     * Message Code
     * @return MessageCode
     */
    public MessageCode messageCode() {
        return messageCode;
    }

    /**
     * Protocol Version
     * @return ProtocolVersion
     */
    public ProtocolVersion protocolVersion() {
        return protocolVersion;
    }

    /**
     * SPOMS Packet Data
     * @return Data
     */
    public String data() {
        return data;
    }

    /**
     * Data Length field 1
     * @return Data Length 1
     */
    public int dataLength1() {
        return dataLength1;
    }

    /**
     * Data Length field 2
     * @return Data Length 2
     */
    public int dataLength2() {
        return dataLength2;
    }

    @Override
    public String toString(){
        var stringRepresentationOfSpomsPacket = new StringBuilder();
        stringRepresentationOfSpomsPacket.append("_________________________SPOMS PACKET_________________________\n");
        stringRepresentationOfSpomsPacket.append("Protocol version: " + protocolVersion.name() + "\n");
        stringRepresentationOfSpomsPacket.append("Message code: " + messageCode.name() + "\n");
        stringRepresentationOfSpomsPacket.append("Message: " + data + "\n");
        stringRepresentationOfSpomsPacket.append("_________________________SPOMS PACKET_________________________\n");

        return stringRepresentationOfSpomsPacket.toString();
    }
}

