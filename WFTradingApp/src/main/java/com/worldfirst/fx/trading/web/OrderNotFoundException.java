package com.worldfirst.fx.trading.web;

/**
 * Exception class for OrderNot found.
 */
public class OrderNotFoundException extends RuntimeException {

	OrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
}
