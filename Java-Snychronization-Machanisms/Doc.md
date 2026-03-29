Coordinate interactions among threads in concurrent programs.

**Java volatile keyword**

volatile ensures that changes to variable are always consistent and visible to other threads atomically.
An atomic action is one that effectively happens all at once.
An atomic cannot stop in the middle.

Volatile is not needed in sequential programs:
-> Reads and writes of (most) java primitive variables are atomic.

Volatile is needed in concurrent programs.