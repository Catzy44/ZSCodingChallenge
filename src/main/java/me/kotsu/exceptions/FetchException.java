package me.kotsu.exceptions;

import java.io.IOException;

public class FetchException extends IOException {
    public FetchException(String msg, Throwable cause) { super(msg, cause); }
    public FetchException(String msg) { super(msg, null); }
}