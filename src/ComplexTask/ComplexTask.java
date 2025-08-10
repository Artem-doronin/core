package ComplexTask;

import java.util.Random;

public class ComplexTask {
    private int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void execute() {
        System.out.println("Задача " + taskId + " выполняется...");
        try {
            Thread.sleep(new Random().nextInt(1000) + 500);  // имитация работы
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Задача " + taskId + " была прервана во время выполнения");
        }
        System.out.println("Задача " + taskId + " завершена.");
    }

}

