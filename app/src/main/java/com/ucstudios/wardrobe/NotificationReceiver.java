package com.ucstudios.wardrobe;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    public static boolean cancelProgressBar() {
                return true;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String message = intent.getStringExtra("cancel");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(2);
                        cancelProgressBar();
    }
}
