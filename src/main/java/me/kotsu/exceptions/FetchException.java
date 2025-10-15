package me.kotsu.exceptions;

import java.io.IOException;

public class FetchException extends IOException {
	private static final long serialVersionUID = 2810162872873162616L;
	
	public FetchException(String msg, Throwable cause) { super(msg, cause); }
    public FetchException(String msg) { super(msg, null); }
}