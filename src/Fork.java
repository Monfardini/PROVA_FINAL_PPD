public class Fork {
    private boolean inUse;

    public Fork() {
        this.inUse = false;
    }

    public synchronized boolean acquire() {
        if (!inUse) {
            inUse = true;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        inUse = false;
    }
}