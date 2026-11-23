ackage com.rajputinfo.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactOwnerActivity extends AppCompatActivity {

    private static final String OWNER_URL = "https://t.me/AnonymouschatbotR";
    private static final String OWNER_NAME = "RAJPUT INFO OWNER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_owner);
        setTitle("Contact Owner");

        TextView tvName = findViewById(R.id.tvOwnerName);
        Button btn = findViewById(R.id.btnChat);

        tvName.setText(OWNER_NAME);

        btn.setOnClickListener(v -> {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(OWNER_URL));
                startActivity(i);
            } catch (Exception e) {
                // telegram not installed fallback
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(OWNER_URL));
                startActivity(i);
            }
        });
    }
}
