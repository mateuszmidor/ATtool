package com.mateuszmidor.data.providers.bossapl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.io.Files;
import com.mateuszmidor.ATtoolException;
import com.mateuszmidor.IOServices;
import com.mateuszmidor.data.DataProvider;
import com.mateuszmidor.data.DataProviderException;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.SymbolNameMap;

/**
 * Base bossa.pl quotes data provider. To be implemented by derived classes
 * specialized for stocks, currencies, mutual founds etc.
 * 
 * @author Mateusz Midor
 * 
 */
public abstract class BossaDataProvider extends DataProvider {

    // uri for symbols file (regular text file)
    protected abstract URI getSymbolsFileURI();

    // uri for quotes data file (file is ZIP archive)
    protected abstract URI getQuotesFileURI();

    @Override
    protected SymbolNameMap fetchSymbolToNameMap() throws DataProviderException {

        URI symbolsUri = getSymbolsFileURI();
        InputStream is = null;
        try {
            is = IOServices.getInputStreamForURI(symbolsUri);
            return BossaListParser.parse(is);

        } catch (ATtoolException e1) {
            throw new DataProviderException("Error during parsing bossa.pl symbol list: " + symbolsUri.toString(), e1);
        } finally {
            IOServices.closeStream(is);
        }

    }

    @Override
    protected Quotes fetchQuotesForSymbol(String symbol) throws DataProviderException {

        URI quotesUri = getQuotesFileURI();
        InputStream is = null;
        try {
            is = IOServices.getInputStreamForURI(quotesUri);
            ZipInputStream zipIn = new ZipInputStream(is);
            return loadQuotesFromZip(symbol, zipIn);

        } catch (ATtoolException e1) {
            throw new DataProviderException("Error during parsing bossa.pl quotes data: " + quotesUri.toString(), e1);
        } catch (IOException e) {
            throw new DataProviderException("Error during browsing bossa.pl ZIP archive: " + quotesUri.toString(), e);
        } finally {
            IOServices.closeStream(is);
        }
    }

    /**
     * Finds and fetches quotes data from a ZIP stream for a given symbol
     * 
     * @param symbol
     *            - what to look for
     * @param zipIn
     *            - where to look
     * @return Quotes - data fetched from ZIP
     * @throws IOException
     *             - from ZIP stream
     * @throws DataProviderException
     *             - from bossa.pl data parser
     */
    private Quotes loadQuotesFromZip(String symbol, ZipInputStream zipIn) throws IOException, DataProviderException {

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
                Quotes data = BossaQuotesParser.parse(symbolName, fullName, zipIn);

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
