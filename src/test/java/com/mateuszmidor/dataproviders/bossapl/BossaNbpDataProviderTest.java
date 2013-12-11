package com.mateuszmidor.dataproviders.bossapl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mateuszmidor.IOServices;
import com.mateuszmidor.IOServicesProvider;
import com.mateuszmidor.dataproviders.DataProviderException;
import com.mateuszmidor.dataproviders.Quotes;
import com.mateuszmidor.dataproviders.Symbols;

public class BossaNbpDataProviderTest {
    private static final Path SYMBOLS_FILE_PATH = Paths.get("src/test/java/testData/NbpSymbols.lst");
    private static final Path QUOTES_FILE_PATH = Paths.get("src/test/java/testData/NbpQuotes.zip");

    // same as in BossaNbpDataProvider
    private static final String GPW_NBP_SYMBOLS_URL = "http://bossa.pl/pub/waluty/mstock/mstnbp.lst";
    private static final String GPW_NBP_QUOTES_URL = "http://bossa.pl/pub/waluty/mstock/mstnbp.zip";

    private static BossaNbpDataProvider classUnderTest;

    private static IOServicesProvider originalProvider;
    private static IOServicesProvider substitutedProvider;

    // to check at the end, if all opened streams have been closed
    private static int remainingOpenStreamCount = 0;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // setup our class-under-test
        classUnderTest = new BossaNbpDataProvider();
        
        // prepare input data for mocking
        URI symbolsUri = URI.create(GPW_NBP_SYMBOLS_URL);
        URI quotesUri = URI.create(GPW_NBP_QUOTES_URL);

        // mock our streams provider
        substitutedProvider = createMock(IOServicesProvider.class);

        // should be asked one time for symbols input stream
        expect(substitutedProvider.getInputStreamForURI(symbolsUri)).andAnswer(new IAnswer<InputStream>() {

            @Override
            public InputStream answer() throws Throwable {
                remainingOpenStreamCount++;
                return getSymbolsStream();
            }
        }).once();
        
        // should be asked one or more times for quotes input stream
        // each time return a new stream, as the previous one should have been closed 
        expect(substitutedProvider.getInputStreamForURI(quotesUri)).andAnswer(new IAnswer<InputStream>() {
            @Override
            public InputStream answer() throws Throwable {
                remainingOpenStreamCount++;
                return getQuotesStream();
            }

        }).atLeastOnce();

        // count down when closing a stream. At least one close should be called
        substitutedProvider.closeStream(EasyMock.anyObject(InputStream.class));
        EasyMock.expectLastCall().andAnswer(new IAnswer<Void>() {

            @Override
            public Void answer() throws Throwable {
                remainingOpenStreamCount--;
                return null;
            }
        }).atLeastOnce();
        
        // mock ready
        replay(substitutedProvider);

        originalProvider = IOServices.getProvider();
        IOServices.setProvider(substitutedProvider);
    }

    private static InputStream getQuotesStream() throws IOException {
        return Files.newInputStream(QUOTES_FILE_PATH);
    }

    private static InputStream getSymbolsStream() throws IOException {
        return Files.newInputStream(SYMBOLS_FILE_PATH);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        IOServices.setProvider(originalProvider);
        verify(substitutedProvider);
        
        // all streams shoud be closed by now
        Assert.assertEquals(0, remainingOpenStreamCount);
    }

    @Test
    public void testGetGroupName() {
        Assert.assertEquals("GPW NBP", classUnderTest.getGroupName());
    }

    @Test
    public void testIsSymbolAvailable() throws DataProviderException {

        // check for available symbols
        Assert.assertTrue(classUnderTest.isSymbolAvailable("EUR"));
        Assert.assertTrue(classUnderTest.isSymbolAvailable("USD"));
        Assert.assertTrue(classUnderTest.isSymbolAvailable("GBP"));

        // try some made up symbol that does not exist
        Assert.assertFalse(classUnderTest.isSymbolAvailable("XXX"));
    }

    @Test
    public void testGetAvailableSymbols() throws DataProviderException {
        Symbols symbols = classUnderTest.getAvailableSymbols();
        Assert.assertTrue(symbols.contains("EUR"));
        Assert.assertTrue(symbols.contains("USD"));
        Assert.assertTrue(symbols.contains("GBP"));

        // check for some non existing symbol
        Assert.assertFalse(symbols.contains("XXX"));
    }

    @Test
    public void testGetFullNameForSymbol() throws DataProviderException {
        Assert.assertEquals("euro", classUnderTest.getFullNameForSymbol("EUR"));
        Assert.assertEquals("dolar amerykanski", classUnderTest.getFullNameForSymbol("USD"));
        Assert.assertEquals("funt szterling", classUnderTest.getFullNameForSymbol("GBP"));
    }
    
    @Test
    public void testGetQuotesForSymbol() throws DataProviderException {
        Quotes eur = classUnderTest.getQuotesForSymbol("EUR");
        Assert.assertTrue(!eur.isEmpty());
        Assert.assertEquals("EUR", eur.getSymbolName());

        Quotes usd = classUnderTest.getQuotesForSymbol("USD");
        Assert.assertTrue(!usd.isEmpty());
        Assert.assertEquals("USD", usd.getSymbolName());

        Quotes gbp = classUnderTest.getQuotesForSymbol("GBP");
        Assert.assertTrue(!gbp.isEmpty());
        Assert.assertEquals("GBP", gbp.getSymbolName());

        // also test for some non existing symbol
        Quotes xxx = classUnderTest.getQuotesForSymbol("XXX");
        Assert.assertTrue(xxx.isEmpty());
    }
}
