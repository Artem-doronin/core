package ComplexTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ComplexTaskExecutor {
    private ExecutorService executor;

    public ComplexTaskExecutor(int numThreads) {
        executor = Executors.newFixedThreadPool(numThreads);
    }
    public void executeTasks(int numberOfTasks) {

        List<ComplexTask> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(new ComplexTask(i));
        }

        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("\nВсе задачи достигли барьера. Начинаем объединение результатов...");
        });

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            final ComplexTask task = tasks.get(i);
            futures.add(executor.submit(() -> {
                try {
                    task.execute();
                    int arrivalIndex = barrier.await();
                    System.out.println("Задача " + task.getTaskId() + " прошла барьер (позиция: "
                            + arrivalIndex + ")");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Задача " + task.getTaskId() + " была прервана");
                } catch (BrokenBarrierException e) {
                    System.out.println("Барьер сломан для задачи " + task.getTaskId());
                }
            }));
        }

        try {
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            System.out.println("Ошибка при выполнении задач: " + e.getMessage());
        }
        shutdown();
    }

        private void shutdown () {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

        }
    }

