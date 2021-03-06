package com.mateuszmidor.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import com.mateuszmidor.data.Quotes;
import com.mateuszmidor.data.QuotesEntry;

public class QuotesTest {

	// Creates Date objects in a handy manner
	private Date createDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	@Test
	public void testGetSymbolName() {
		Quotes quotes = new Quotes("EUR", "");
		Assert.assertEquals("EUR", quotes.getSymbolName());
	}

	@Test
	public void testGetFullName() {
		Quotes quotes = new Quotes("", "Euro");
		Assert.assertEquals("Euro", quotes.getFullName());
	}

	@Test
	public void testIsEmpty() {
		Quotes quotes;

		// test for empty data
		quotes = new Quotes("", "");
		Assert.assertTrue(quotes.isEmpty());

		// test for nonempty data
		quotes.insertEntry(new QuotesEntry());
		Assert.assertFalse(quotes.isEmpty());
	}

	@Test
	public void testInsertEntry() {
		// prepare some QuotesEntry values
		final double OPEN = 10.0;
		final double CLOSE = 15.0;
		final double HIGH = 20.0;
		final double LOW = 5.0;
		final double VOLUME = 1000.0;
		final Date DATE = createDate(2000, Calendar.JANUARY, 1);

		// create quotes entry
		QuotesEntry input = new QuotesEntry(OPEN, HIGH, LOW, CLOSE, VOLUME,
				DATE);

		// insert quotes entry into quotes
		final Quotes quotes = new Quotes("", "");
		quotes.insertEntry(input);

		// get the inserted quotes entry back
		QuotesEntry output = quotes.iterator().next();

		// check the entry for proper values
		final double DELTA_VAL = 0.0001;
		Assert.assertEquals(CLOSE, output.getClose(), DELTA_VAL);
		Assert.assertEquals(OPEN, output.getOpen(), DELTA_VAL);
		Assert.assertEquals(HIGH, output.getHigh(), DELTA_VAL);
		Assert.assertEquals(LOW, output.getLow(), DELTA_VAL);
		Assert.assertEquals(VOLUME, output.getVolume(), DELTA_VAL);
		Assert.assertEquals(DATE, output.getDate());
	}

	@Test
	public void testSortAscending() {
		// create sample entries in subsequent years
		final QuotesEntry ENTRY1 = new QuotesEntry();
		ENTRY1.setDate(createDate(2001, Calendar.JANUARY, 1));
		final QuotesEntry ENTRY2 = new QuotesEntry();
		ENTRY2.setDate(createDate(2002, Calendar.JANUARY, 1));
		final QuotesEntry ENTRY3 = new QuotesEntry();
		ENTRY3.setDate(createDate(2003, Calendar.JANUARY, 1));

		// insert the entries in descenting order
		Quotes quotes = new Quotes("", "");
		quotes.insertEntry(ENTRY3);
		quotes.insertEntry(ENTRY2);
		quotes.insertEntry(ENTRY1);

		// sort the entries ascending
		quotes.sortAscending();

		// check quotes for ascending order
		Iterator<QuotesEntry> it = quotes.iterator();
		Assert.assertEquals(it.next(), ENTRY1);
		Assert.assertEquals(it.next(), ENTRY2);
		Assert.assertEquals(it.next(), ENTRY3);
	}

	@Test
	public void testIterator() {
		// create sample entries
		final QuotesEntry ENTRY1 = new QuotesEntry();
		final QuotesEntry ENTRY2 = new QuotesEntry();
		final QuotesEntry ENTRY3 = new QuotesEntry();

		// insert the entries
		Quotes quotes = new Quotes("", "");
		quotes.insertEntry(ENTRY1);
		quotes.insertEntry(ENTRY2);
		quotes.insertEntry(ENTRY3);

		// check quotes for proper order and existance
		Iterator<QuotesEntry> it = quotes.iterator();
		Assert.assertEquals(it.next(), ENTRY1);
		Assert.assertEquals(it.next(), ENTRY2);
		Assert.assertEquals(it.next(), ENTRY3);
	}

}
