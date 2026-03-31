Every single object in Java—from a simple Object to a complex HashMap—has an associated monitor.

When a thread enters a synchronized block, it "acquires" the monitor of that object. While it holds the monitor, no other thread can enter any synchronized block protected by that same monitor.