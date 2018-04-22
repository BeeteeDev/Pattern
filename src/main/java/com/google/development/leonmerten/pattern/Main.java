package com.google.development.leonmerten.pattern;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>Main reference class.</h1>
 * This class contains all basic information's about the program.
 *
 * @author Leon Justin Merten
 * @since 0.0.4-SNAPSHOT
 * @version {@link BUILD_NUMBER}
 */
public class Main {
    private static final String BUILD_NUMBER = "45";
    private static final String ADDITION = "-SNAPSHOT";
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * This method is used to get the current Build number with separators.
     * <p>Example: 32842 - 328.4.2</p>
     * @return edited {@link #BUILD_NUMBER} string
     */
    public static String getBuildNumber() {
        lock.lock();
        try {
            if (Integer.valueOf(BUILD_NUMBER) < 10)
                return "0.0." + BUILD_NUMBER + ADDITION;
            else if (10 <= Integer.valueOf(BUILD_NUMBER) && Integer.valueOf(BUILD_NUMBER) < 100)
                return "0." + BUILD_NUMBER.substring(0, 1) + "." + BUILD_NUMBER.substring(1) + ADDITION;
            else if (100 <= Integer.valueOf(BUILD_NUMBER))
                return BUILD_NUMBER.substring(0, 1)
                        + "."
                        + BUILD_NUMBER.substring(BUILD_NUMBER.length() - 2, 2)
                        + "."
                        + BUILD_NUMBER.substring(BUILD_NUMBER.length() - 2, 2)
                        + ADDITION;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There was an error while trying to process the build number. :c");
        } finally {
            lock.unlock();
        }
        return BUILD_NUMBER;
    }
}
