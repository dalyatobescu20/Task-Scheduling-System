# Task Scheduling System

The purpose of this project is to implement a task scheduling system in a datacenter using Java Threads.

We will simulate the architecture depicted in the image below. As can be seen, the system consists of two main elements. The Dispatcher (or load balancer, depicted in green in the image) has the role of fetching tasks arriving in the system (for example, from clients of the datacenter) and sending them to the nodes in the datacenter based on predefined policies. The nodes (marked in blue in the image, and whose number may vary) are responsible for executing tasks they receive in order of priority and preempting those running for more important tasks, etc. Each node in the system also has a queue in which it stores tasks to be executed later. The main goal of this project is to implement the logic for both the dispatcher and the compute nodes.

<img width="620" alt="Screenshot 2024-02-21 at 21 38 28" src="https://github.com/dalyatobescu20/Task-Scheduling-System/assets/94745479/b176da0c-ded1-4a25-bc02-6cd3e8426b3e">

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

