# In android

Android implements manay standard java concurrency and sync classes.


# Conceptual view

A thread is a unit of computation that runs in the context of a process.

Threads running in a process can communicate with each other via shared objects or message passing.

 # Implementation view

Each java thread has a stack, a program counter, and other registers (unique state).

The heap and static sreas are shared across threads.

=> Heap is dynamic.

The java virtual machine recycles the resources associated with the Thread.

# Types of java threads

**User Threads**
Threads created by the application to perform specific tasks.

When a JVM starts it contains a single User thread.
Can outlive the main Thread

**Daemon Threads**
Background threads that provide supporting services.
All daemon threads terminate automatically when all user threads terminate.
