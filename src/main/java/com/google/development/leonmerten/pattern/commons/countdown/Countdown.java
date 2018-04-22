package com.google.development.leonmerten.pattern.commons.countdown;

import com.google.development.leonmerten.pattern.commons.concurrent.FunctionValidator;
import com.google.development.leonmerten.pattern.exceptions.IllegalMethodUsageException;

import java.util.concurrent.*;

/**
 * <h1>Start a Countdown</h1>
 * Using the Countdown class you can run the countdown specified in the upper class:
 * {@link CountdownBuilder}
 * <p>
 *     It allows you to start, stop and reset the countdown.
 * </p>
 */
public class Countdown {
    /**
     * This class is not usable for the public. It just is available to save the state of tickSet equals null or not
     */
    public enum Type {
        NO_TICK_SET,
        TICK_SET;
    }

    private Countdown instance;
    private CountdownBuilder countdownBuilder;
    private Type type;
    private boolean running;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public Countdown(CountdownBuilder countdownBuilder, Future future) throws ExecutionException {
        this.countdownBuilder = countdownBuilder;
        try {
            this.type = (Type) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.instance = this;
    }

    /**
     * <h1>Start the Countdown</h1>
     * Using this method you can start the countdown {@link #instance}.
     * If its already running an exception is being thrown.
     * <span class="strong">The method will process inside of a new thread. It is prohibited to access this thread.</span>
     * The thread will die if number t is larger than the remaining time.
     * If there is a Delegate that will trigger every second declared in {@link #countdownBuilder} it will be called.
     * After that it checks if theres a tick set. Using the tick set you can call a Delegate only in specific times.
     * @throws IllegalMethodUsageException this exception is being thrown if the countdown {@link #instance} is already running
     */
    public void run() throws IllegalMethodUsageException {
        if(isRunning())
            throw new IllegalMethodUsageException("Using same Countdown twice");
        running = true;
        executorService.scheduleAtFixedRate(new Runnable() {
            int t = 0;
            public void run() {
                int time = (countdownBuilder.getLength() - t);
                t++;
                if(t > countdownBuilder.getLength()) {
                    if(FunctionValidator.notNullOrEmpty(countdownBuilder.getFinishedDelegate()))
                        countdownBuilder.getFinishedDelegate().invoke(instance);
                    executorService.shutdownNow();
                    return;
                }
                if(FunctionValidator.notNullOrEmpty(countdownBuilder.getSecondDelegate()))
                    countdownBuilder.getSecondDelegate().invoke(time);
                if(type == Type.NO_TICK_SET) {
                    if(countdownBuilder.isDelegateNoTickActive())
                        countdownBuilder.getTickDelegate().invoke(time);
                } else {
                    if(countdownBuilder.getTicks().contains(time))
                        countdownBuilder.getTickDelegate().invoke(time);
                }
            }
        }, 0 , 1, TimeUnit.SECONDS);
    }

    /**
     * Stops the countdown.  Parameters are still the same.
     */
    public void stop() {
        executorService.shutdown();
        running = false;
    }

    /**
     * Basicly just calls {@link #stop()} and dosn't do much more
     * TODO: ADD MORE LOGIC (kappa)
     */
    public void reset() {
        stop();
    }

    /**
     * This method is being used to check if the countdown of this class is running
     * @return local variable running
     */
    public boolean isRunning() {
        return running;
    }
}
