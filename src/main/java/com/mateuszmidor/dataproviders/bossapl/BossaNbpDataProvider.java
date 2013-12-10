package com.mateuszmidor.dataproviders.bossapl;

import java.net.URI;

public class BossaNbpDataProvider extends BossaDataProvider {
	private static final String GPW_NBP_QUOTES_URL = "http://bossa.pl/pub/waluty/mstock/mstnbp.zip";
	private static final String GPW_NBP_SYMBOLS_URL = "http://bossa.pl/pub/waluty/mstock/mstnbp.lst";
	
	@Override
	protected URI getSymbolsFileURI() {
		return URI.create(GPW_NBP_SYMBOLS_URL);
	}

	@Override
	protected URI getQuotesFileURI() {
		return URI.create(GPW_NBP_QUOTES_URL);
	}

	@Override
	public String getGroupName() {
		return "GPW NBP";
	}

}
