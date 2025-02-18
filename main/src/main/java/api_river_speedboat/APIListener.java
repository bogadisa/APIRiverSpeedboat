package api_river_speedboat;

import static org.asynchttpclient.Dsl.asyncHttpClient;

import org.asynchttpclient.*;

public class APIListener extends Thread {
    private String name;

    private String uri;
    private RequestHeader header;
    private AsyncHttpClient asyncHttpClient;

    private APIProcessor processor;
    private APIListenerManager manager;

    private boolean linked = false;

    private AsyncSpecialHeaderHandler headerHandler;

    public APIListener(String uri) {
        this(uri, new RequestHeader(), asyncHttpClient());
    }

    public APIListener(String uri, AsyncHttpClient asyncHttpClient) {
        this(uri, new RequestHeader(), asyncHttpClient);
    }
    public APIListener(String uri, RequestHeader header) {
        this(uri, header, asyncHttpClient());
    }

    public APIListener(String uri, RequestHeader header, AsyncHttpClient asyncHttpClient) {
        this.uri = uri;
        this.header = header;
        this.asyncHttpClient = asyncHttpClient;

        this.name = Thread.currentThread().getName();
        this.headerHandler = new AsyncSpecialHeaderHandler(this);
    }

    public APIListenerManager getManager() {
        return manager;
    }
    

    public void link(APIProcessor processor, APIListenerManager manager) {
        this.processor = processor;
        this.manager = manager;
        this.linked = true;
    }


    @Override
    public void run() {
        if (!linked) {
            System.out.println(name + " has not been linked");
            return;
        }
        BoundRequestBuilder requestBase = asyncHttpClient.prepareGet(uri)
            .addHeader("User-Agent", header.getUserAgent())
            .addHeader("Authorization", header.getAuthorization());

        synchronized (processor) {
            while (processor.getProcessorThread() != null) {
                sendGetRequest(requestBase);
                try {
                    processor.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendGetRequest(BoundRequestBuilder requestBase) {
        String nextChangeId = manager.getNextChangeId();
        if (nextChangeId != null) {
            // TODO do I need to set the requestBase again?
            requestBase.addQueryParam("id", nextChangeId);
        }

        Request request = requestBase.build();

        ListenableFuture<Response> whenResponse = asyncHttpClient.executeRequest(request, headerHandler);
    }
    
}
