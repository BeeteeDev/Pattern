package com.google.development.leonmerten.pattern.commons.countdown;

import com.google.development.leonmerten.pattern.commons.concurrent.Delegate;
import com.google.development.leonmerten.pattern.commons.concurrent.Result;
import com.google.development.leonmerten.pattern.commons.concurrent.SetValidator;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

public class CountdownBuilder {
    private final ReentrantLock lock = new ReentrantLock();
    private int length;
    private Delegate<Integer> tickDelegate;
    private Delegate<Integer> secondDelegate;
    private Delegate<Countdown> finishedDelegate;
    private Set<Integer> ticks;
    private boolean delegateNoTickActive = false;

    /**
     * Creates a new {@link CountdownBuilder} to build a new Countdown
     * @return created {@link CountdownBuilder}
     */
    public static CountdownBuilder newBuilder() {
        return new CountdownBuilder();
    }

    /**
     * Sets the length of the countdown
     * @param length length in seconds
     * @return this class
     */
    public CountdownBuilder lenght(int length) {
        this.length = length;
        return this;
    }

    public CountdownBuilder tick(Set<Integer> ticks) {
        this.ticks = ticks;
        return this;
    }

    public CountdownBuilder delegate(Delegate<Integer> delegate) {
        this.tickDelegate = delegate;
        return this;
    }


    public CountdownBuilder delegateSecond(Delegate<Integer> delegate) {
        this.secondDelegate = delegate;
        return this;
    }


    public CountdownBuilder delegateFinish(Delegate<Countdown> delegate) {
        this.finishedDelegate = delegate;
        return this;
    }

    public CountdownBuilder delegateNoTickActive(boolean delegateNoTickActive) {
        this.delegateNoTickActive = delegateNoTickActive;
        return this;
    }

    /**
     * Builds the countdown you can work with
     * @return the countdown
     * @throws ExecutionException is thrown if there is an error in the {@link Result} class.
     */
    public Countdown build() throws ExecutionException {
        return new Countdown(this, SetValidator
                    .notNullOrEmpty(ticks,
                            (Result<Countdown.Type>) () -> Countdown.Type.TICK_SET,
                            (Result<Countdown.Type>) () -> Countdown.Type.NO_TICK_SET));
    }

    public int getLength() {
        return length;
    }

    public Delegate<Integer> getTickDelegate() {
        return tickDelegate;
    }

    public Delegate<Integer> getSecondDelegate() {
        return secondDelegate;
    }

    public Delegate<Countdown> getFinishedDelegate() {
        return finishedDelegate;
    }

    public Set<Integer> getTicks() {
        return ticks;
    }

    public boolean isDelegateNoTickActive() {
        return delegateNoTickActive;
    }
}
