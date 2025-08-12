package StreamApi.ForkJoinPoolExample;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {

    private final int n;

    public FactorialTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 5) {
            return computeSequentially();
        }
        FactorialTask subtask = new FactorialTask(n - 1);
        subtask.fork();
        return n * subtask.join();
    }

    private long computeSequentially() {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
