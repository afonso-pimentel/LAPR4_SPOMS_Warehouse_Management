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
 * Builder for the SPOMS Packet
 */
public class SpomsPacketBuilder {
    private MessageCode messageCode;
    private ProtocolVersion protocolVersion;
    private String content;

    /**
     * Defines the Message Code for the SPOMS Packet
     * @param messageCode MessageCode
     * @return SpomsPacketBuilder
     */
    public SpomsPacketBuilder messageCode(MessageCode messageCode){
        this.messageCode = messageCode;
        return this;
    }

    /**
     * Defines the Protocol Version for the SPOMS Packet
     * @param protocolVersion ProtocolVersion
     * @return SpomsPacketBuilder
     */
    public SpomsPacketBuilder protocolVersion(ProtocolVersion protocolVersion){
        this.protocolVersion = protocolVersion;
        return this;
    }

    /**
     * Defines the data for the SPOMS Packet
     * @param data Data
     * @return SpomsPacketBuilder
     */
    public SpomsPacketBuilder data(String data){
        this.content = data;
        return this;
    }

    /**
     * Build's the SPOMS Packet
     * @return SPOMS Packet
     */
    public SpomsPacket build(){
        int[] lengths = CalculateDataLength1AndDataLength2(this.content.length());
        return new SpomsPacket(messageCode, protocolVersion, lengths[0], lengths[1], content);
    }

    /**
     * Calculates the DataLength1 and DataLength2 fields according to the SPOMS Protocol
     * @param totalLength TotalLength of the data
     * @return Integer array containing the values of the DataLength1 and DataLength2 field
     */
    private static int[] CalculateDataLength1AndDataLength2(int totalLength){
        int[] result = new int[2];

        for(int i = 0; i<=255; i++){

            for(int j=0; j<=255;j++){

                if((i+(256 * j))==totalLength){
                    result[0] = i;
                    result[1] = j;
                    break;

                }

            }

        }
        return result;
    }
}
