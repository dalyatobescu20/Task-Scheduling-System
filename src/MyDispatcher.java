/* Implement this class. */

import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyDispatcher extends Dispatcher {
    private int lastAssignedIndex = -1; // Initialize to -1 so that the first task goes to index 0
    private final Lock lock = new ReentrantLock();

    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }

    @Override
    public synchronized void addTask(Task task) {
        if (algorithm == SchedulingAlgorithm.ROUND_ROBIN) {
            try {
                lock.lock();
                lastAssignedIndex = (lastAssignedIndex + 1) % hosts.size();
                hosts.get(lastAssignedIndex).addTask(task);

            } finally {
                lock.unlock();
            }

        } else if (algorithm == SchedulingAlgorithm.SHORTEST_QUEUE) {
            try {
                lock.lock(); // Acquire the lock
                int minQueueSize = Integer.MAX_VALUE;
                int index = 0;
                for (int i = 0; i < hosts.size(); i++) {
                    if (hosts.get(i).getQueueSize() < minQueueSize) {
                        minQueueSize = hosts.get(i).getQueueSize();
                        index = i;
                    }
                }
                hosts.get(index).addTask(task);
            } finally {
                lock.unlock(); // Release the lock in a finally block
            }

        } else if (algorithm == SchedulingAlgorithm.SIZE_INTERVAL_TASK_ASSIGNMENT) {
            try {
                lock.lock();
                if (task.getType() == TaskType.SHORT) {
                    int index = 0;
                    hosts.get(index).addTask(task);
                } else if (task.getType() == TaskType.MEDIUM) {
                    int index = 1;
                    hosts.get(index).addTask(task);
                } else if (task.getType() == TaskType.LONG) {
                    int index = 2;
                    hosts.get(index).addTask(task);
                }
            } finally {
                lock.unlock();
            }
        } else if (algorithm == SchedulingAlgorithm.LEAST_WORK_LEFT) {
            try {
                lock.lock();
                long minWorkLeft = Long.MAX_VALUE;
                int index = -1;
                for (int i = 0; i < hosts.size(); i++) {
                    if (hosts.get(i).getWorkLeft() / 1000 < minWorkLeft) {
                        minWorkLeft = hosts.get(i).getWorkLeft() / 1000;
                        index = i;
                    }
                }
                if(index != -1) {
                    hosts.get(index).addTask(task);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
