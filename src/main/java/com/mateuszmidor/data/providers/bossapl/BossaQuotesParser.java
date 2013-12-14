package com.mateuszmidor.data.providers.bossapl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Charsets;
import com.mateuszmidor.data.DataProviderException;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;

/**
 * Quotes parser for bossa.pl text file format
 * 
 * @author Mateusz Midor
 * 
 */
public class BossaQuotesParser {

	private static final String BOSSA_DATE_FORMAT = "yyyyMMdd";

	private BossaQuotesParser() {
	}

	public static Quotes parse(String symbol, String fullName, InputStream is)
			throws DataProviderException {

		// prepare string reader
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				Charsets.UTF_8));

		// skip the file header
		skipHeader(br);

		// prepare quotes buffer
		Quotes data = new Quotes(symbol, fullName);

		// parse entries and insert into buffer
		for (QuotesEntry e = getEntry(br); e != QuotesEntry.EMPTY_ENTRY; e = getEntry(br)) {
			data.insertEntry(e);
		}

		return data;
	}

	private static QuotesEntry getEntry(BufferedReader br)
			throws DataProviderException {

		// single entry is formatted as a single line
		// fetch one line
		String line;
		try {
			line = br.readLine();
		} catch (IOException e) {
			throw new DataProviderException(
					"Error during fetching single quotes entry from bossa.pl qutes data file",
					e);
		}

		// end of entries in the stream?
		if (null == line) {
			return QuotesEntry.EMPTY_ENTRY;
		}

		// parse entry from the string
		return parseEntry(line);
	}

	private static void skipHeader(BufferedReader br)
			throws DataProviderException {

		// skip one line header in quotes data stream
		try {
			br.readLine();
		} catch (IOException e) {
			throw new DataProviderException(
					"Error during skipping header in bossa.pl quotes data file",
					e);
		}
	}

	private static QuotesEntry parseEntry(String s)
			throws DataProviderException {
		String[] fields = s.split(",");

		// ticker (fields[0]) is not used
		String date = fields[1];
		String open = fields[2];
		String high = fields[3];
		String low = fields[4];
		String close = fields[5];
		String volume = fields[6];

		QuotesEntry entry = new QuotesEntry();
		try {
			entry.setDate(parseBossaPlDate(date));
			entry.setOpen(parseBossaPlFloat(open));
			entry.setHigh(parseBossaPlFloat(high));
			entry.setLow(parseBossaPlFloat(low));
			entry.setClose(parseBossaPlFloat(close));
			entry.setVolume(parseBossaPlFloat(volume));
		} catch (DataProviderException | NumberFormatException e) {
			throw new DataProviderException(
					"Error during parsing bossa.pl quotes entry: " + s, e);
		}

		return entry;
	}
	private static Date parseBossaPlDate(String bossaDate)
			throws DataProviderException {

		try {
			return new SimpleDateFormat(BOSSA_DATE_FORMAT).parse(bossaDate);
		} catch (ParseException e) {
			throw new DataProviderException(
					"Error during parsing bossa.pl date: " + bossaDate, e);
		}
	}

	private static float parseBossaPlFloat(String bossaFloat) {
		return Float.parseFloat(bossaFloat);
	}

}
