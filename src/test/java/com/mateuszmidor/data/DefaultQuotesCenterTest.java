package com.mateuszmidor.data;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultQuotesCenterTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Adding single provider should not entail any exceptions
     * 
     */
    @Test
    public void testAddDataProvider() throws DataProviderException {
        DataProvider provider = createMock(DataProvider.class);
        expect(provider.getGroupName()).andReturn("TFI");
        replay(provider);

        QuotesCenter center = new DefaultQuotesCenter();
        center.addDataProvider(provider);

        verify(provider);
    }

    /**
     * Adding more than one provider for the same group is not allowed
     * 
     */
    @Test(expected = DataProviderException.class)
    public void testAddDuplicatedDataProvider() throws DataProviderException {
        DataProvider provider = createMock(DataProvider.class);
        expect(provider.getGroupName()).andReturn("TFI").atLeastOnce();
        replay(provider);

        QuotesCenter center = new DefaultQuotesCenter();
        center.addDataProvider(provider);
        center.addDataProvider(provider);

        verify(provider);
    }

    @Test
    public void testGetGroups() throws DataProviderException {
        DataProvider provider1 = createMock(DataProvider.class);
        expect(provider1.getGroupName()).andReturn("TFI");
        replay(provider1);

        DataProvider provider2 = createMock(DataProvider.class);
        expect(provider2.getGroupName()).andReturn("GPW");
        replay(provider2);

        DataProvider provider3 = createMock(DataProvider.class);
        expect(provider3.getGroupName()).andReturn("NBP");
        replay(provider3);

        QuotesCenter center = new DefaultQuotesCenter();
        center.addDataProvider(provider1);
        center.addDataProvider(provider2);
        center.addDataProvider(provider3);
        Groups groups = center.getGroups();

        Assert.assertTrue(groups.contains("NBP"));
        Assert.assertTrue(groups.contains("TFI"));
        Assert.assertTrue(groups.contains("GPW"));

        verify(provider1);
        verify(provider2);
        verify(provider3);
    }

    @Test
    public void testGetSymbols() throws DataProviderException {
        Symbols inputSymbols = new Symbols();
        inputSymbols.add("PLN");
        inputSymbols.add("EUR");
        inputSymbols.add("USD");

        DataProvider provider = createMock(DataProvider.class);
        expect(provider.getGroupName()).andReturn("NBP");
        expect(provider.getAvailableSymbols()).andReturn(inputSymbols);
        replay(provider);

        QuotesCenter center = new DefaultQuotesCenter();
        center.addDataProvider(provider);
        Symbols outputSymbols = center.getSymbols("NBP");

        Assert.assertTrue(outputSymbols.contains("USD"));
        Assert.assertTrue(outputSymbols.contains("PLN"));
        Assert.assertTrue(outputSymbols.contains("EUR"));

        verify(provider);
    }

    @Test
    public void testGetQuotes() throws DataProviderException {
        Quotes inputQuotes = new Quotes("PLN", "polski zloty");

        DataProvider provider = createMock(DataProvider.class);
        expect(provider.getGroupName()).andReturn("NBP");
        expect(provider.getQuotesForSymbol("PLN")).andReturn(inputQuotes);
        replay(provider);

        QuotesCenter center = new DefaultQuotesCenter();
        center.addDataProvider(provider);
        Quotes outputQuotes = center.getQuotes("PLN", "NBP");

        Assert.assertEquals(inputQuotes, outputQuotes);

        verify(provider);
    }

}
