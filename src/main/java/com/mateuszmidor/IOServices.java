package com.mateuszmidor;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;

/**
 * Facade providing input/output streams for files and URIs. This helps in unit
 * testing - you can exchange default IO provider with your own one and return
 * predefined streams instead of real file/network streams
 * 
 * @author Mateusz Midor
 * 
 */
public class IOServices {
    private static IOServicesProvider provider = new DefaultIOServicesProvider();

    private IOServices() {
    }

    public static IOServicesProvider getProvider() {
        return provider;
    }

    public static void setProvider(IOServicesProvider newProvider) {
        provider = newProvider;
    }

    public static InputStream getInputStreamForFile(Path path) throws ATtoolException {
        return provider.getInputStreamForFile(path);
    }

    public static OutputStream getOutputStreamForFile(Path path) throws ATtoolException {
        return provider.getOutputStreamForFile(path);
    }

    public static InputStream getInputStreamForURI(URI uri) throws ATtoolException {
        return provider.getInputStreamForURI(uri);
    }

    public static void closeStream(InputStream is) {
        provider.closeStream(is);
    }

    public static void closeStream(OutputStream os) {
        provider.closeStream(os);
    }
}
