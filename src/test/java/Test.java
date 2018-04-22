import com.google.common.collect.Sets;
import com.google.development.leonmerten.pattern.Main;
import com.google.development.leonmerten.pattern.commons.countdown.Countdown;
import com.google.development.leonmerten.pattern.commons.countdown.CountdownBuilder;
import com.google.development.leonmerten.pattern.exceptions.IllegalMethodUsageException;

import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String... args) {
        System.out.println(Main.getBuildNumber());
        try {
            Countdown countdown = CountdownBuilder
                    .newBuilder()
                    .lenght(60)
                    .tick(Sets.newHashSet(60, 50, 40, 30, 20, 10 ,5,4,3,2,1))
                    .delegate(arg -> System.out.println("Number: " + arg))
                    .delegateNoTickActive(true)
                    .build();
            countdown.run();
        } catch (ExecutionException | IllegalMethodUsageException e) {
            e.printStackTrace();
        }
    }
}
