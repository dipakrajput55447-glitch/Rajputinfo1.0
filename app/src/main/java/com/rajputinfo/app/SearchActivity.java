package com.rajputinfo.app;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private CreditsManager credits;
    private EditText etQuery;
    private TextView tvResult;
    private TextView tvCreditsTop;
    private ProgressBar progress;
    private int mode = 0;

    private static final String[] HINTS = {
            "Enter phone number (with country code)",
            "Enter Telegram username (without @)",
            "Enter Telegram user ID"
    };

    private static final String[] TITLES = {
            "Phone → Info",
            "Username → User ID",
            "Telegram ID → Phone"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mode = getIntent().getIntExtra("mode", 0);
        credits = new CreditsManager(this);

        setTitle(TITLES[mode]);

        etQuery = findViewById(R.id.etQuery);
        tvResult = findViewById(R.id.tvResult);
        tvCreditsTop = findViewById(R.id.tvCreditsTop);
        progress = findViewById(R.id.progress);

        etQuery.setHint(HINTS[mode]);
        updateCredits();

        findViewById(R.id.btnSearch).setOnClickListener(v -> doSearch());
    }

    private void updateCredits() {
        tvCreditsTop.setText("Credits: " + credits.getCredits());
    }

    private void doSearch() {
        String q = etQuery.getText().toString().trim();
        if (q.isEmpty()) {
            Toast.makeText(this, "Enter a query", Toast.LENGTH_SHORT).show();
            return;
        }
        if (credits.getCredits() <= 0) {
            Toast.makeText(this, "No credits left", Toast.LENGTH_LONG).show();
            return;
        }
        credits.spend();
        updateCredits();

        progress.setVisibility(View.VISIBLE);
        tvResult.setText("Searching...");

        ApiClient.Callback cb = (body, ok) -> runOnUiThread(() -> {
            progress.setVisibility(View.GONE);
            String pretty = prettyJson(body);
            tvResult.setText(pretty);
            TelegramBot.sendMessage("<b>Search [" + TITLES[mode] + "]</b>\n" +
                    "Query: <code>" + q + "</code>\n" +
                    (ok ? "✅ Success" : "❌ Failed"));
        });

        switch (mode) {
            case 0: ApiClient.phoneToInfo(q, cb); break;
            case 1: ApiClient.usernameToId(q, cb); break;
            case 2: ApiClient.idToPhone(q, cb); break;
        }
    }

    private String prettyJson(String raw) {
        try {
            if (raw.trim().startsWith("{")) {
                return new org.json.JSONObject(raw).toString(2);
            } else if (raw.trim().startsWith("[")) {
                return new org.json.JSONArray(raw).toString(2);
            }
        } catch (Exception ignored) {}
        return raw;
    }
}
