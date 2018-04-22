package com.google.development.leonmerten.pattern.commons.concurrent;

import com.sun.istack.internal.NotNull;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SetValidator {
    private static ExecutorService service = Executors.newCachedThreadPool();

    @NotNull
    public static Future notNullOrEmpty(Set set, Result result, Result fail) {
            if (set != null && !set.isEmpty())
                return service.submit(result);
            return service.submit(fail);
    }
}
