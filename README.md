# Task Scheduling System

The purpose of this project is to implement a task scheduling system in a datacenter using Java Threads.

# Implementation Steps
## MyDispatcher Class:

1. Created a priority queue of type Task for each Host.
2. Depending on the scheduling algorithm:
    - For RoundRobin, tasks were added to the task vector of each host in the order they were read, using the formula (lastAssignedIndex + 1) % hosts.size().
    - For ShortestQueue, tasks were added to the host with the smallest task queue, using the getQueueSize() function.
    - For SizeIntervalTaskAssigment, tasks were added to the corresponding host type (small, medium, long).
    - For LeastWorkLeft, tasks were added to the host with the least remaining work to execute, using the getWorkLeft() function.

## MyHost Class:

1. Created a PriorityBlockingQueue of type Task to store the tasks to be executed.
2. Method run():
    - As long as there are tasks to execute and tasks in the task queue, a task is extracted from the queue and executed. If there are more tasks in the queue, check if we find a task with higher priority than the current task and if the current task is preemptable, and if found, execute that task and add the current task back to the queue.
3. Method addTask():
    - Adds a task to the host's task queue.
4. Method getQueueSize():
    - Returns the size of the host's task queue (adds +1 for the current task).
5. Method getWorkLeft():
    - Returns the remaining work to execute for the host by calling the getWorkLeft() method for each task (adds +1 for the current task).
6. Method executeTask():
    - Executes a task and decreases the remaining work to execute for the host.
    - Simulates the execution of a task by calling the sleep() method for 1000 milliseconds.
    - If the remaining work to execute for the host is 0, then calls the finish() method.
7. Method shutDown():
    - Sets the boolean variable isRunning to false to stop the execution of the host.

