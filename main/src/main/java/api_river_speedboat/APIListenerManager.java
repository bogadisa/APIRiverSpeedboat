package api_river_speedboat;

public class APIListenerManager {
    private APIProcessor processor;

    private final int MAX_N_LISTENERS = 8;
    private APIListener[] listeners;
    private int nListeners;
    private boolean hasRoom;
    
    private String nextChangeId;

    public APIListenerManager(APIProcessor processor) {
        this.processor = processor;
    }

    public boolean addListener(APIListener listener) {
        if (hasRoom) {
            listeners[nListeners] = listener;
            listener.link(processor, this);

            nListeners++;

            if (listeners.length == MAX_N_LISTENERS) {
                hasRoom = false;
            } 
            return true;
        }

        return false;
    }

    
    public String getNextChangeId() {
        return nextChangeId;
    }

    public void setNextChangeId(String nextChangeId) {
        this.nextChangeId = nextChangeId;
    }
}
