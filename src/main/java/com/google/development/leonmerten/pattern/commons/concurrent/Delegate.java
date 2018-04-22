package com.google.development.leonmerten.pattern.commons.concurrent;

public interface Delegate<T> {
    void invoke(T arg);
}
