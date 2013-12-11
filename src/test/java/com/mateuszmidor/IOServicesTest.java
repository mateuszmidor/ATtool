package com.mateuszmidor;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
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
    private static URI INPUT_URI = URI.create("http://www.mateuszmidor.com/CV.pdf");

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

        // return input stream for file
        expect(substitutedProvider.getInputStreamForFile(INPUT_FILE_PATH)).andReturn(inputFileStream);

        // return input stream for URI
        expect(substitutedProvider.getInputStreamForURI(INPUT_URI)).andReturn(inputUriStream);

        // return output stream for file
        expect(substitutedProvider.getOutputStreamForFile(OUTPUT_FILE_PATH)).andReturn(outputFileStream);

        // close 2 input streams
        substitutedProvider.closeStream(anyObject(InputStream.class));
        expectLastCall().times(2);

        // close 1 output stream
        substitutedProvider.closeStream(anyObject(OutputStream.class));
        expectLastCall().once();

        // mock ready
        replay(substitutedProvider);

        originalProvider = IOServices.getProvider();
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
        IOServices.closeStream(is);
    }

    @Test
    public void testGetOutputStreamForFile() throws ATtoolException {
        OutputStream os = IOServices.getOutputStreamForFile(OUTPUT_FILE_PATH);
        Assert.assertEquals(outputFileStream, os);
        IOServices.closeStream(os);
    }

    @Test
    public void testGetInputStreamForURI() throws ATtoolException {
        InputStream is = IOServices.getInputStreamForURI(INPUT_URI);
        Assert.assertEquals(inputUriStream, is);
        IOServices.closeStream(is);
    }

}
