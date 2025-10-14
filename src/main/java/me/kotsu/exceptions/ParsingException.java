package me.kotsu.exceptions;

public class ParsingException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6549166051174124451L;
	public ParsingException(String msg, Throwable cause) { super(msg, cause); }
    public ParsingException(String msg) { super(msg, null); }
}