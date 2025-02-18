package api_river_speedboat;

public class APIProcessor extends Thread{
    private String name;
    private Thread processorThread;
    
    private APIListenerManager manager;



    public APIProcessor() {
        manager = new APIListenerManager(this);
    }

    public boolean addListener(APIListener listener) {
        return manager.addListener(listener);
    }

    

    public void startProcessorThread() {
        processorThread = new Thread(this);
        processorThread.start();
    }
    
    public Thread getProcessorThread() {
        return processorThread;
    }


    @Override
    public void run() {
        this.name = Thread.currentThread().getName();

        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(name + " has finished waiting");
        }
    }
}
