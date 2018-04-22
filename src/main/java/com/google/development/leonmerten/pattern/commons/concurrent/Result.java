package com.google.development.leonmerten.pattern.commons.concurrent;

import java.util.concurrent.Callable;

public interface Result<T> extends Callable<T> {
    T call() throws Exception;
}
