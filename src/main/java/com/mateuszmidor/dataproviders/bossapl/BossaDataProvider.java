package com.mateuszmidor.dataproviders.bossapl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.io.Files;
import com.mateuszmidor.ATtoolException;
import com.mateuszmidor.IOServices;
import com.mateuszmidor.dataproviders.DataProvider;
import com.mateuszmidor.dataproviders.DataProviderException;
import com.mateuszmidor.dataproviders.Quotes;
import com.mateuszmidor.dataproviders.SymbolToNameMap;

/**
 * Base bossa.pl quotes data provider. To be implemented by derived classes
 * specialized for stocks, currencies, mutual founds etc.
 * 
 * @author Mateusz Midor
 * 
 */
public abstract class BossaDataProvider extends DataProvider {

	// uri for symbols file (regular text file)
	abstract protected URI getSymbolsFileURI();

	// uri for quotes data file (file is ZIP archive)
	abstract protected URI getQuotesFileURI();

	@Override
	protected SymbolToNameMap fetchSymbolToNameMap()
			throws DataProviderException {

		URI symbolsUri = getSymbolsFileURI();
		try (InputStream is = IOServices.getInputStreamForURI(symbolsUri)) {

			SymbolToNameMap map = BossaListParser.parse(is);
			return map;

		} catch (IOException e) {
			throw new DataProviderException(
					"Error during closing stream for bossa.pl symbol list:"
							+ symbolsUri.toString(), e);
		} catch (ATtoolException e1) {
			throw new DataProviderException(
					"Error during parsing bossa.pl symbol list: "
							+ symbolsUri.toString(), e1);
		}

	}

	@Override
	protected Quotes fetchQuotesForSymbol(String symbol)
			throws DataProviderException {

		URI quotesUri = getQuotesFileURI();
		try (InputStream is = IOServices.getInputStreamForURI(quotesUri)) {

			ZipInputStream zipIn = new ZipInputStream(is);
			Quotes quotes = loadQuotesFromZip(symbol, zipIn); 
			return quotes;
			
		} catch (IOException e) {
			throw new DataProviderException(
					"Error during browsing bossa.pl ZIP archive: "
							+ quotesUri.toString(), e);
		} catch (ATtoolException e1) {
			throw new DataProviderException(
					"Error during parsing bossa.pl quotes data: "
							+ quotesUri.toString(), e1);
		}
	}

	/**
	 * Finds and fetches quotes data from a ZIP stream for a given symbol
	 * @param symbol - what to look for 
	 * @param zipIn - where to look
	 * @return Quotes - data fetched from ZIP
	 * @throws IOException - from ZIP stream 
	 * @throws DataProviderException - from bossa.pl data parser
	 */
	private Quotes loadQuotesFromZip(String symbol, ZipInputStream zipIn)
			throws IOException, DataProviderException {
		
		// look for the right file in the archive
		ZipEntry entry = zipIn.getNextEntry();
		while (null != entry) {

			// file name to symbol name
			String symbolName = getSymbolNameFromFileName(entry.getName());

			// is this the entry we are looking for?
			if (symbolName.equalsIgnoreCase(symbol)) {

				// file found 
				// get the name for symbol
				String fullName = getFullNameForSymbol(symbol);
				
				// parse the data
				Quotes data = BossaQuotesParser.parse(symbolName, fullName,
						zipIn);

				zipIn.closeEntry();
				
				// return the data
				return data;
			}

			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		
		// file not found in ZIP stream
		return Quotes.EMPTY_QUOTES;
	}

	private String getSymbolNameFromFileName(String filename) {
		// symbol name is file name without extension
		return Files.getNameWithoutExtension(filename);
	}

}
