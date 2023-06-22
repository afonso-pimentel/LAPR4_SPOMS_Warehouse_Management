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

package security;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

/**
 * Contract for the implementation of security in the SPOMS Communication Protocol
 */
public interface SpomsSecurity {
    /**
     * Creates a secure communication channel by creating and initializing a secure client socket
     * @param serverAddress Server Address to connect to
     * @param serverPort Server Port to connect to
     * @return SSLSocket
     */
    SSLSocket CreateSecureClientSocket(String serverAddress, int serverPort) throws IOException;

    /**
     * Creates a secure communication channel by creating and initializing a secure server socket
     * @param serverPort Server Port to be used
     * @param withMutualAuthentication Identifies if mutual authentication should be used
     * @return SSLServerSocket
     */
    SSLServerSocket CreateSecureServerSocket(int serverPort, boolean withMutualAuthentication) throws IOException;
}
