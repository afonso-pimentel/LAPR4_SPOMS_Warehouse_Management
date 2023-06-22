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

import enums.MessageCode;
import enums.ProtocolVersion;
import models.SpomsPacket;
import models.SpomsPacketBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * AGV Manager set of automated tasks
 */
public class OrderServerManagerAutomatedTasks extends Thread {
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(5);

                SpomsPacket request = new SpomsPacketBuilder()
                        .messageCode(MessageCode.STATUS_REQUEST)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data("Give me your status!")
                        .build();

                OrderServer.sendMessageToAllOrderServerClients(request);
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted error!");
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (TimeoutException e) {
            System.out.println("Time out error!");
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }
}
