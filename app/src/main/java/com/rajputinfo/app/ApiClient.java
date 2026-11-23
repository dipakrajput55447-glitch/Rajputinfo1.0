package com.rajputinfo.app;

import java.io.IOException;
import okhttp3.*;

public class ApiClient {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build();

    private static final String PHONE_API =
            "https://deeposint.felixrdx.xyz/api/developer/Cyb3rB4nn3r/?key=9c8c8ebe8355255bb2c4cd2e002476cf&query=";
    private static final String TG_TO_ID =
            "https://osint.invalidayushh.workers.dev/id?key=Rajput&q=";
    private static final String ID_TO_PHONE =
            "https://osint.invalidayushh.workers.dev/tgv2?key=Rajput&q=";

    public interface Callback {
        void onResult(String body, boolean ok);
    }

    private static void get(String url, Callback cb) {
        Request req = new Request.Builder().url(url).get().build();
        client.newCall(req).enqueue(new okhttp3.Callback() {
            @Override public void onFailure(Call call, IOException e) {
                cb.onResult("{\"error\":\"" + e.getMessage() + "\"}", false);
            }
            @Override public void onResponse(Call call, Response resp) throws IOException {
                String body = resp.body() != null ? resp.body().string() : "{}";
                cb.onResult(body, resp.isSuccessful());
            }
        });
    }

    public static void phoneToInfo(String phone, Callback cb) {
        get(PHONE_API + android.net.Uri.encode(phone), cb);
    }

    public static void usernameToId(String username, Callback cb) {
        get(TG_TO_ID + android.net.Uri.encode(username), cb);
    }

    public static void idToPhone(String id, Callback cb) {
        get(ID_TO_PHONE + android.net.Uri.encode(id), cb);
    }
}
