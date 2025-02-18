package api_river_speedboat;

import com.google.gson.Gson;

import io.netty.handler.codec.http.HttpHeaders;

import org.asynchttpclient.*;
import org.jetbrains.annotations.Nullable;

import static org.asynchttpclient.Dsl.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        RequestHeader header = new RequestHeader();
        String POE_PUBLIC_STASHES_AUTH_TOKEN = "750d4f685cfa83d024d86508e7ede4ab55b5acc7";
        String OATH_ACC_TOKEN_CONTACT_EMAIL = "magnus.hoddevik@gmail.com";
        header.setAuthorization("Bearer " + POE_PUBLIC_STASHES_AUTH_TOKEN);
        header.setUserAgent("OAuth pathofmodifiers/0.1.0 (contact: " + OATH_ACC_TOKEN_CONTACT_EMAIL + ") StrictMode");
        Gson gson = new Gson();
        String jsonHeader = gson.toJson(header);

        String uri = "https://api.pathofexile.com/public-stash-tabs";
        
        AsyncHttpClient asyncHttpClient = asyncHttpClient();

        HeaderHandler headerHandler = new HeaderHandler();

        
        Request request = asyncHttpClient.prepareGet(uri)
            .addHeader("User-Agent", header.getUserAgent())
            .addHeader("Authorization", header.getAuthorization())
            .build();

        System.out.println(request.getHeaders());

        long start = System.currentTimeMillis();

        ListenableFuture<Response> whenResponse = asyncHttpClient.executeRequest(request, 
            new AsyncCompletionHandler<Response> () {
                @Override
                public State onHeadersReceived(HttpHeaders headers) throws Exception {
                    headerHandler.setHeader(headers.get("X-Next-Change-Id"));;
                    System.out.println("Header " + (System.currentTimeMillis() - start));
                    return State.CONTINUE;
                }

                @Override
                public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                    System.out.println("Status " + (System.currentTimeMillis() - start));
                    return State.CONTINUE;
                }

                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                    System.out.println("Body " + (System.currentTimeMillis() - start));
                    return State.ABORT;
                }

                @Override
                public void onThrowable(Throwable t) {
                    t.printStackTrace();
                }

                @Override
                public @Nullable Response onCompleted(@Nullable Response response) throws Exception {
                    System.out.println("complete " + (System.currentTimeMillis() - start));
                    return response;
                }

            });

            try {

                long startofstart = System.currentTimeMillis();
                System.out.println(startofstart - start);
                System.out.println("HEY!");
                Response response = whenResponse.get();
                System.out.println("BYE!");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                asyncHttpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}