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

package strategies.strategies;

import AGVDigitalTwinSettings.AGVSettings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.*;

/**
 * Order assignment strategy implementation for SpomsStrategy
 */
public class OrderAssignmentStrategy implements SpomsStrategy{
    private final SharedInformation sharedInformation;

    /**
     * Initializes a new instance of OrderAssignmentStrategy
     * @param sharedInformation SharedInformation
     */
    public OrderAssignmentStrategy(SharedInformation sharedInformation){
        if(sharedInformation == null)
            throw new IllegalArgumentException("SharedInformation cannot be null");

        this.sharedInformation = sharedInformation;
    }

    /**
     * @inheritDoc
     */
    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        var mapper = new ObjectMapper();

        try {
            var orderTaskAssignment = mapper.readValue(packetToHandle.data(), OrderTaskAssignment.class);

            if(orderTaskAssignment.AgvId() == AGVSettings.Settings.ID){
                sharedInformation.addNewOrder(orderTaskAssignment);
                Helper.raiseEvent(LockObjects.lockNewOrder);
            }

            var response = new OrderTaskAssignmentResponse(false, "");

            var packetData = mapper.writeValueAsString(response);

            return new SpomsPacketBuilder()
                    .messageCode(MessageCode.ORDER_ASSIGNMENT_RESPONSE)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(packetData)
                    .build();

        }catch (Exception e)
        {
            var response = new OrderTaskAssignmentResponse(true, e.getMessage());

            var packetData = mapper.writeValueAsString(response);

            return new SpomsPacketBuilder()
                    .messageCode(MessageCode.ORDER_ASSIGNMENT_RESPONSE)
                    .protocolVersion(ProtocolVersion.VERSION_1)
                    .data(packetData)
                    .build();
        }
    }
}
