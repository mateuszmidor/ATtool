package com.mateuszmidor.data.providers.bossapl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import com.mateuszmidor.data.DataProviderException;
import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;
import com.mateuszmidor.data.providers.bossapl.BossaQuotesParser;

public class BossaQuotesParserTest {

	// for comparing floating points - open, high, low, close
	private static final double DELTA = 0.001;

	// well formatted quotes data
	private static final String KGHM_VALID_QUOTES = "<TICKER>,<DTYYYYMMDD>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>"
			+ '\n'
			+ "KGHM,19970101,10.00,20.00,5.00,15.00,100"
			+ '\n'
			+ "KGHM,19970102,100.00,200.00,50.00,150.00,200";

	// string instead of volume
	private static final String KGHM_INVALID_VOLUME_QUOTES = "<TICKER>,<DTYYYYMMDD>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>"
			+ '\n'
			+ "KGHM,19970101,10.00,20.00,5.00,15.00,ahundred"
			+ '\n'
			+ "KGHM,19970102,100.00,200.00,50.00,150.00,twohundreds";

	// invalid date format
	private static final String KGHM_VALID_DATE_QUOTES = "<TICKER>,<DTYYYYMMDD>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>"
			+ '\n'
			+ "KGHM,1997-JAN-01,10.00,20.00,5.00,15.00,100"
			+ '\n'
			+ "KGHM,1997-JAN-02,100.00,200.00,50.00,150.00,200";

	private static final InputStream KGHM_VALID_QUOTES_STREAM = new ByteArrayInputStream(
			KGHM_VALID_QUOTES.getBytes());

	private static final InputStream KGHM_INVALID_QUOTES_STREAM = new ByteArrayInputStream(
			KGHM_INVALID_VOLUME_QUOTES.getBytes());

	private static final InputStream KGHM_INVALID_DATE_STREAM = new ByteArrayInputStream(
			KGHM_VALID_DATE_QUOTES.getBytes());

	// Creates Date objects in a handy manner
	private Date createDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	@Test
	public void testParseValidQuotes() throws DataProviderException {
		Quotes quotes = BossaQuotesParser.parse("", "",
				KGHM_VALID_QUOTES_STREAM);

		QuotesEntry e;
		Iterator<QuotesEntry> it = quotes.iterator();

		e = it.next();
		Assert.assertEquals(createDate(1997, Calendar.JANUARY, 1), e.getDate());
		Assert.assertEquals(10.0, e.getOpen(), DELTA);
		Assert.assertEquals(20.0, e.getHigh(), DELTA);
		Assert.assertEquals(5.0, e.getLow(), DELTA);
		Assert.assertEquals(15.0, e.getClose(), DELTA);
		Assert.assertEquals(100.0, e.getVolume(), DELTA);

		e = it.next();
		Assert.assertEquals(createDate(1997, Calendar.JANUARY, 2), e.getDate());
		Assert.assertEquals(100.0, e.getOpen(), DELTA);
		Assert.assertEquals(200.0, e.getHigh(), DELTA);
		Assert.assertEquals(50.0, e.getLow(), DELTA);
		Assert.assertEquals(150.0, e.getClose(), DELTA);
		Assert.assertEquals(200.0, e.getVolume(), DELTA);
	}

	@Test(expected = DataProviderException.class)
	public void testParseInvalidVolumeQuotes() throws DataProviderException {
		BossaQuotesParser.parse("", "", KGHM_INVALID_QUOTES_STREAM);
	}

	@Test(expected = DataProviderException.class)
	public void testParseInvalidDateQuotes() throws DataProviderException {
		BossaQuotesParser.parse("", "", KGHM_INVALID_DATE_STREAM);
	}
}
