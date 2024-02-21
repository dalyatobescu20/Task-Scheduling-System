import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MyHost extends Host {

    private BlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>(10,Comparator.comparing(Task::getPriority).reversed().thenComparing(Task::getStart));
    private Task currentTask;
    private Task preemptedTask;
    private boolean isRunning = true;

    private boolean taskCompleted = false;



    @Override
    public void run() {
        while (isRunning) {
                if (!taskQueue.isEmpty()) {
                    Task nextTask = taskQueue.peek();
                    if (currentTask != null && currentTask.isPreemptible() && (nextTask != null && nextTask.getPriority() > currentTask.getPriority())) {
                        preemptedTask = currentTask;
                        currentTask = taskQueue.poll();
                        taskQueue.add(preemptedTask);
                    } else if (currentTask == null) {
                            currentTask = taskQueue.poll();
                    }
                    executeTask();
                } else if (currentTask != null) {
                    executeTask();
                }
        }
    }

    private void executeTask() {
            if (currentTask != null) {
                if (currentTask.getLeft() <= 0) {
                    currentTask.finish();
                    currentTask = null;
                } else {
                    long start = System.currentTimeMillis();
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    long end = System.currentTimeMillis();
                    currentTask.setLeft(currentTask.getLeft() - (end - start));
                }
            }
    }


    @Override
    public void addTask(Task task) {
        if (isRunning) {
            taskQueue.offer(task);
        } else {
            System.out.println("Host is shut down. Cannot add task.");
        }
    }

    @Override
    public int getQueueSize() {
        if(currentTask != null) {
            return taskQueue.size() + 1;
        }
        return taskQueue.size();
    }

    @Override
    public long getWorkLeft() {
        // For simplicity, assume equal work for each task
        long totalWork = 0;// Assuming each task takes 10 units of work
        if(currentTask != null) {
            totalWork += currentTask.getLeft();
        }
        for (Task task : taskQueue ) {
            totalWork += task.getLeft();
        }
        return totalWork;

    }

    @Override
    public void shutdown() {
        isRunning = false;
        System.out.println("Host is shutting down");
    }


}
