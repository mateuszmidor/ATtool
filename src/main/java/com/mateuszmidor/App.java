package com.mateuszmidor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application root class with main() func, here all the fun begins!
 * 
 * @author Mateusz Midor
 */
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	private App() {
	}
	
	public static void main(String[] args) {
		LOGGER.debug("Hello World!");
	}
}
