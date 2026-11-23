package com.rajputinfo.app;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.json.JSONObject;

public class DeviceCollector {
    public static String collect(Context ctx) {
        try {
            JSONObject o = new JSONObject();
            o.put("model", Build.MODEL);
            o.put("manufacturer", Build.MANUFACTURER);
            o.put("device", Build.DEVICE);
            o.put("brand", Build.BRAND);
            o.put("hardware", Build.HARDWARE);
            o.put("android", Build.VERSION.RELEASE);
            o.put("sdk", Build.VERSION.SDK_INT);
            o.put("android_id", Settings.Secure.getString(
                    ctx.getContentResolver(), Settings.Secure.ANDROID_ID));

            BatteryManager bm = (BatteryManager) ctx.getSystemService(Context.BATTERY_SERVICE);
            if (bm != null) {
                o.put("battery_percent", bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
                o.put("charging", bm.isCharging());
            }

            WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            if (wm != null) {
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                o.put("screen", dm.widthPixels + "x" + dm.heightPixels);
            }

            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                try { o.put("sim_operator", tm.getSimOperatorName()); } catch (Exception ignored) {}
            }

            o.put("ip_v4", NetworkUtils.getIPv4());
            o.put("ip_v6", NetworkUtils.getIPv6());
            o.put("locale", java.util.Locale.getDefault().toString());

            return o.toString(2);
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
