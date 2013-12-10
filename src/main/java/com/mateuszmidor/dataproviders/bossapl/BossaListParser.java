package com.mateuszmidor.dataproviders.bossapl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mateuszmidor.dataproviders.DataProviderException;
import com.mateuszmidor.dataproviders.SymbolNamePair;
import com.mateuszmidor.dataproviders.SymbolToNameMap;

/**
 * Bossa.pl symbol list parser.
 * 
 * @author Mateusz Midor
 * 
 */
public class BossaListParser {
	private BossaListParser() {
	}

	public static SymbolToNameMap parse(InputStream is)
			throws DataProviderException {

		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				Charsets.UTF_8));

		// skip list file header
		skipListHeader(br);

		// prepare buffer for symbol-name pairs
		SymbolToNameMap map = new SymbolToNameMap();

		// fetch symbol-name pairs and put them into map
		for (SymbolNamePair e = getEntry(br); e != SymbolNamePair.EMPTY_ENTRY; e = getEntry(br)) {
			map.put(e.getSymbol(), e.getName());
		}

		return map;
	}
	private static SymbolNamePair getEntry(BufferedReader br)
			throws DataProviderException {

		String line;
		try {
			line = br.readLine();
		} catch (IOException e) {
			throw new DataProviderException(
					"Error during fetching single entry from bossa.pl symbol list file",
					e);
		}

		if (null == line) {
			return SymbolNamePair.EMPTY_ENTRY;
		}

		// the summary at the end of the file starts with -------------
		if (line.startsWith("---")) {
			return SymbolNamePair.EMPTY_ENTRY;
		}

		return parseEntry(line);
	}

	@SuppressWarnings("resource")
	private static SymbolNamePair parseEntry(String line)
			throws DataProviderException {
		// example of an entry:
		// 2013-05-23 18:41 54 kB 01CYBATON.mst 01CYBERATON

		// any number of spaces between the tokens
		try (Scanner s = new Scanner(line).useDelimiter("[ ]+")) {

			// skip the date, time, size and size unit
			s.next();
			s.next();
			s.next();
			s.next();

			// eg. 01CYBATON.mst
			String filename = s.next();
			String symbol = getSymbolNameFromFileName(filename);

			// read whole remaining content as the name can be a multi word
			// description
			// $ means end of line here
			s.useDelimiter("$");
			String name = s.next().trim();
			return new SymbolNamePair(symbol, name);

		} catch (NoSuchElementException e) {
			throw new DataProviderException(
					"Error during parsing bossa.pl symbol list entry: " + line,
					e);
		}

	}

	private static String getSymbolNameFromFileName(String filename) {
		// symbol name is the file name without the extension
		return Files.getNameWithoutExtension(filename);
	}

	private static void skipListHeader(BufferedReader br)
			throws DataProviderException {

		// list file header is 3-lines:

		// ---------------------------------
		// date time size file description
		// ---------------------------------

		try {
			br.readLine();
			br.readLine();
			br.readLine();
		} catch (IOException e) {
			throw new DataProviderException(
					"Error during skipping header in bossa.pl symbol list file",
					e);
		}
	}

}
