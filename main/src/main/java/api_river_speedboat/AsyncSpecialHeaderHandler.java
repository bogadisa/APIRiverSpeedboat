package api_river_speedboat;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;
import org.jetbrains.annotations.Nullable;

import io.netty.handler.codec.http.HttpHeaders;

public class AsyncSpecialHeaderHandler extends AsyncCompletionHandler<Response>{
    private APIListener listener;
    private APIListenerManager manager;

    public AsyncSpecialHeaderHandler(APIListener listener) {
        super();
        this.listener = listener;
        this.manager = listener.getManager();


    }

    @Override
    public State onHeadersReceived(HttpHeaders headers) throws Exception {
        synchronized (manager){
            manager.setNextChangeId(headers.get("X-Next-Change-Id"));
            manager.notify();
        }
        return State.CONTINUE;
    }

    @Override
    public @Nullable Response onCompleted(@Nullable Response response) throws Exception {
        return response;
    }

}
