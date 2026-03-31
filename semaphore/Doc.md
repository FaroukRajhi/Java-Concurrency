A Semaphore is a synchronization mechanism that controls access to a shared resource through a counter. It maintains a set of permits:

Acquire - Takes a permit (decreases counter)

Release - Returns a permit (increases counter)

If no permits are available, threads block until one becomes available

**Types of semaphores**

Counting Semaphore - Multiple permits (any positive number)

Binary Semaphore - Only 1 permit (acts like a mutex/lock)