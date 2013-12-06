package com.mateuszmidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * IO services provider giving access to file and URI input/output streams. Used
 * by IOServices
 * 
 * @author Mateusz Midor
 * 
 */
public class DefaultIOServicesProvider implements IOServicesProvider {

	@Override
	public InputStream getInputStreamForFile(Path path) throws ATtoolException {
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new ATtoolException("Error opening input stream for: "
					+ path.toString(), e);
		}
	}

	@Override
	public OutputStream getOutputStreamForFile(Path path)
			throws ATtoolException {
		try {
			return Files.newOutputStream(path);
		} catch (IOException e) {
			throw new ATtoolException("Error opening output stream for: "
					+ path.toString(), e);
		}
	}

	@Override
	public InputStream getInputStreamForURI(URI uri) throws ATtoolException {
		URL url;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e1) {
			throw new ATtoolException("Error converting URI to URL for: "
					+ uri.toString(), e1);
		}

		try {
			return url.openStream();
		} catch (IOException e2) {
			throw new ATtoolException("Error opening input stream for: "
					+ url.toString(), e2);
		}
	}

}
