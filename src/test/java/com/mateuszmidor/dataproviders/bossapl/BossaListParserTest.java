package com.mateuszmidor.dataproviders.bossapl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

import com.mateuszmidor.dataproviders.DataProviderException;
import com.mateuszmidor.dataproviders.SymbolToNameMap;

public class BossaListParserTest {

	// well formed bossa.pl symbol list
	private static final String BOSSA_VALID_SYMBOL_LIST = 
			"-------------------------------------------------------------------------"
			+ '\n'
			+ "data          czas      rozmiar  plik              opis"
			+ '\n'
			+ "-------------------------------------------------------------------------"
			+ '\n'
			+ "2013-12-05    15:42      162 kB  EUR.mst           euro                  "
			+ '\n'
			+ "2013-12-05    15:42      227 kB  GBP.mst           funt szterling        "
			+ '\n'
			+ "2013-12-05    15:42      227 kB  SEK.mst           korona szwedzka       "
			+ '\n'
			+ "-------------------------------------------------------------------------"
			+ '\n'
			+ "                          ??? kB  3 plikow                               ";

	// lacking file and description for last entry
	private static final String BOSSA_INVALID_SYMBOL_LIST = 
			"-------------------------------------------------------------------------"
			+ '\n'
			+ "data          czas      rozmiar  plik              opis"
			+ '\n'
			+ "-------------------------------------------------------------------------"
			+ '\n'
			+ "2013-12-05    15:42      162 kB  EUR.mst           euro                  "
			+ '\n'
			+ "2013-12-05    15:42      227 kB  GBP.mst           funt szterling        "
			+ '\n'
			+ "2013-12-05    15:42      227 kB                                          "
			+ '\n'
			+ "-------------------------------------------------------------------------"
			+ '\n'
			+ "                          ??? kB  3 plikow                               ";

	// header only
	private static final String BOSSA_HEADERONLY_SYMBOL_LIST = 
			"-------------------------------------------------------------------------"
			+ '\n'
			+ "data          czas      rozmiar  plik              opis"
			+ '\n'
			+ "-------------------------------------------------------------------------";


	private static final InputStream BOSSA_VALID_LIST_STREAM = new ByteArrayInputStream(
			BOSSA_VALID_SYMBOL_LIST.getBytes());

	private static final InputStream BOSSA_INVALID_LIST_STREAM = new ByteArrayInputStream(
			BOSSA_INVALID_SYMBOL_LIST.getBytes());

	private static final InputStream BOSSA_HEADERONLY_LIST_STREAM = new ByteArrayInputStream(
			BOSSA_HEADERONLY_SYMBOL_LIST.getBytes());


	@Test
	public void testParseValidSymbolList() throws DataProviderException {
		SymbolToNameMap list = BossaListParser.parse(BOSSA_VALID_LIST_STREAM);

		Assert.assertTrue(list.containsKey("EUR"));
		Assert.assertEquals("euro", list.get("EUR"));

		Assert.assertTrue(list.containsKey("GBP"));
		Assert.assertEquals("funt szterling", list.get("GBP"));

		Assert.assertTrue(list.containsKey("SEK"));
		Assert.assertEquals("korona szwedzka", list.get("SEK"));
	}

	@Test(expected = DataProviderException.class)
	public void testParseInvalidSymbolList() throws DataProviderException {
		BossaListParser.parse(BOSSA_INVALID_LIST_STREAM);
		Assert.fail("DataProviderException should be thrown while parsing invalid bossa.pl symbol list");
	}

	@Test
	public void testParseHeaderOnlySymbolList() throws DataProviderException {
		// headeronly is OK - an empty symbol list
		BossaListParser.parse(BOSSA_HEADERONLY_LIST_STREAM);
	}


}
