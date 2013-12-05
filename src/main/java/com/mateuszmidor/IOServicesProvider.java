package com.mateuszmidor;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;

/**
 * Interface for IO service providers. Used by IOServices.
 * 
 * @author Mateusz Midor
 * 
 */
public interface IOServicesProvider {
	InputStream getInputStreamForFile(Path filename) throws ATtoolException;
	OutputStream getOutputStreamForFile(Path filename) throws ATtoolException;

	InputStream getInputStreamForURI(URI uri) throws ATtoolException;
}
