package example.wait;

import com.github.rholder.retry.*;
import example.Job;
import example.listener.MyRetryListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ExponentialWaitStrategy {

    public static void main(String[] args) {

        WaitStrategy exponentialWait = WaitStrategies.exponentialWait(3, 3, TimeUnit.SECONDS);

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(a -> a == false)
                .withWaitStrategy(exponentialWait)
                .withRetryListener(new MyRetryListener())
                .build();

        try {
            retryer.call(Job.JOB);
        } catch (RetryException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
