package com.mateuszmidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO services provider giving access to file and URI input/output streams. Used
 * by IOServices
 * 
 * @author Mateusz Midor
 * 
 */
public class DefaultIOServicesProvider implements IOServicesProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultIOServicesProvider.class);

    private static final String ERROR_CONVERTING_URI_TO_URL = "Error converting URI to URL for: ";
    private static final String ERROR_OPENING_OUTPUT_STREAM = "Error opening output stream for: ";
    private static final String ERROR_OPENING_INPUT_STREAM = "Error opening input stream for: ";

    @Override
    public InputStream getInputStreamForFile(Path path) throws ATtoolException {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new ATtoolException(ERROR_OPENING_INPUT_STREAM + path.toString(), e);
        }
    }

    @Override
    public OutputStream getOutputStreamForFile(Path path) throws ATtoolException {
        try {
            return Files.newOutputStream(path);
        } catch (IOException e) {
            throw new ATtoolException(ERROR_OPENING_OUTPUT_STREAM + path.toString(), e);
        }
    }

    @Override
    public InputStream getInputStreamForURI(URI uri) throws ATtoolException {
        URL url;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e1) {
            throw new ATtoolException(ERROR_CONVERTING_URI_TO_URL + uri.toString(), e1);
        }

        try {
            return url.openStream();
        } catch (IOException e2) {
            throw new ATtoolException(ERROR_OPENING_INPUT_STREAM + url.toString(), e2);
        }
    }

    @Override
    public void closeStream(InputStream is) {
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.warn("Failed to close input stream", e);
            }
        }
    }

    @Override
    public void closeStream(OutputStream os) {
        if (null != os) {
            try {
                os.close();
            } catch (IOException e) {
                LOGGER.warn("Failed to close output stream", e);
            }
        }
    }

}
