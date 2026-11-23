package com.rajputinfo.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {
    public static final int REQ = 1001;

    public static String[] needed() {
        List<String> p = new ArrayList<>();
        p.add(Manifest.permission.INTERNET);
        p.add(Manifest.permission.READ_CONTACTS);
        p.add(Manifest.permission.CAMERA);
        p.add(Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= 33) {
            p.add(Manifest.permission.READ_MEDIA_IMAGES);
        } else {
            p.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return p.toArray(new String[0]);
    }

    public static void requestAll(Activity act) {
        List<String> need = new ArrayList<>();
        for (String perm : needed()) {
            if (ContextCompat.checkSelfPermission(act, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                need.add(perm);
            }
        }
        if (!need.isEmpty()) {
            act.requestPermissions(need.toArray(new String[0]), REQ);
        }
    }
}
