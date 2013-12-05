package com.mateuszmidor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IOServicesTest {

	// input for testing IOServices methods
	private static Path INPUT_FILE_PATH = Paths.get("fileIn.txt");
	private static Path OUTPUT_FILE_PATH = Paths.get("fileOut.txt");
	private static URI INPUT_URI = URI
			.create("http://www.mateuszmidor.com/CV.pdf");

	private static IOServicesProvider substitutedProvider;
	private static IOServicesProvider originalProvider;
	private static ByteArrayInputStream inputFileStream;
	private static ByteArrayInputStream inputUriStream;
	private static ByteArrayOutputStream outputFileStream;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// streams that IOServices provider will return
		inputFileStream = new ByteArrayInputStream(new byte[0]);
		inputUriStream = new ByteArrayInputStream(new byte[0]);
		outputFileStream = new ByteArrayOutputStream();

		// mock services provider to return aforementioned streams
		substitutedProvider = createMock(IOServicesProvider.class);
		expect(substitutedProvider.getInputStreamForFile(INPUT_FILE_PATH))
				.andReturn(inputFileStream);
		expect(substitutedProvider.getInputStreamForURI(INPUT_URI)).andReturn(
				inputUriStream);
		expect(substitutedProvider.getOutputStreamForFile(OUTPUT_FILE_PATH))
				.andReturn(outputFileStream);
		replay(substitutedProvider);

		// remember original provider
		originalProvider = IOServices.getProvider();
		
		// set our mock as current provier
		IOServices.setProvider(substitutedProvider);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// restore original provider
		IOServices.setProvider(originalProvider);
		verify(substitutedProvider);
	}

	@Test
	public void testGetProvider() {
		Assert.assertEquals(substitutedProvider, IOServices.getProvider());
	}

	@Test
	public void testGetInputStreamForFile() throws ATtoolException {
		InputStream is = IOServices.getInputStreamForFile(INPUT_FILE_PATH);
		Assert.assertEquals(inputFileStream, is);
	}

	@Test
	public void testGetOutputStreamForFile() throws ATtoolException {
		OutputStream os = IOServices.getOutputStreamForFile(OUTPUT_FILE_PATH);
		Assert.assertEquals(outputFileStream, os);
	}

	@Test
	public void testGetInputStreamForURI() throws ATtoolException {
		InputStream is = IOServices.getInputStreamForURI(INPUT_URI);
		Assert.assertEquals(inputUriStream, is);
	}

}
