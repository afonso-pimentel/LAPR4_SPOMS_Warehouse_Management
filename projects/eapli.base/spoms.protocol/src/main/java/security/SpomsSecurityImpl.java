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
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/**
 * @inheritDoc
 *
 */
public class SpomsSecurityImpl implements SpomsSecurity{
    private final String trustStoreBasePath;
    private final String trustStorePassword;
    private final String certificateName;

    private final String storePath;

    /**
     * Initializes a new instance of SpomsSecurityImpl
     * @param trustStoreBasePath Trust store base path
     * @param trustStorePassword Trust store password
     * @param certificateName Certificate name
     */
    public SpomsSecurityImpl(String trustStoreBasePath, String trustStorePassword, String certificateName){
        if(trustStoreBasePath == null || trustStoreBasePath.length() == 0)
            throw new IllegalArgumentException("Trust store base path cannot be null or empty");

        if(trustStorePassword == null || trustStorePassword.length() == 0)
            throw new IllegalArgumentException("Trust store password cannot be null or empty");

        if(certificateName == null || certificateName.length() == 0)
            throw new IllegalArgumentException("Certificate name cannot be null or empty");

        this.trustStoreBasePath = trustStoreBasePath;
        this.trustStorePassword = trustStorePassword;
        this.certificateName = certificateName;

        var storePathStringBuilder = new StringBuilder();
        storePathStringBuilder.append(trustStoreBasePath);
        storePathStringBuilder.append(certificateName);
        storePathStringBuilder.append(".jks");

        storePath = storePathStringBuilder.toString();

        initializeCertificateStore();
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public SSLSocket CreateSecureClientSocket(String serverAddress, int serverPort) throws IOException {
        if(serverAddress == null || serverAddress.length() == 0)
            throw new IllegalArgumentException("Server address cannot be null or empty");

        if(serverPort <= 0)
            throw new IllegalArgumentException("Server port cannot <= 0");

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = (SSLSocket) sslSocketFactory.createSocket(serverAddress,serverPort);

        sock.startHandshake();

        return sock;
    }

    /**
     * @inheritDoc
     *
     */
    @Override
    public SSLServerSocket CreateSecureServerSocket(int serverPort, boolean withMutualAuthentication) throws IOException {
        if(serverPort <= 0)
            throw new IllegalArgumentException("Server port cannot <= 0");

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        SSLServerSocket sock = (SSLServerSocket) sslServerSocketFactory.createServerSocket(serverPort);
        sock.setNeedClientAuth(withMutualAuthentication);

        return sock;
    }

    private void initializeCertificateStore(){
        // Trust these certificates provided by authorized clients
        System.setProperty("javax.net.ssl.trustStore", storePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

        // Use this certificate and private key as server certificate
        System.setProperty("javax.net.ssl.keyStore", storePath);
        System.setProperty("javax.net.ssl.keyStorePassword", trustStorePassword);
    }
}
