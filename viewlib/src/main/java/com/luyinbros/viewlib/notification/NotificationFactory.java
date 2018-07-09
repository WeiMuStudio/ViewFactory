package com.luyinbros.viewlib.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * 注意
 * 以下是必填项：
 * {@link android.support.v4.app.NotificationCompat.Builder#setContentTitle(CharSequence)}
 * {@link android.support.v4.app.NotificationCompat.Builder#setContentText(CharSequence)}
 * {@link android.support.v4.app.NotificationCompat.Builder#setSmallIcon(int)}/
 * https://www.cnblogs.com/travellife/p/Android-Notification-xiang-jie.html
 * https://www.jianshu.com/p/33e979ba6be1
 */
public final class NotificationFactory {

    public static void showMessage(@Nullable Context context) {
        if (context != null) {
            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                Notification notification = new NotificationCompat.Builder(context, "default")
                        .setContentTitle("测试notification")
                        .setContentText("This is content text")
                        .setSmallIcon(getAppIconResouce(context))
                        .setContentIntent(PendingIntent.getActivity(context,
                                1,
                                new Intent()
                                        .setClassName(context, "com.luyinbros.viewfactory.ViewTestActivity")
                                        .putExtra("ddd", 111)
                                , PendingIntent.FLAG_ONE_SHOT))
                        .setAutoCancel(true)
                        .build();
                if (Build.VERSION.SDK_INT>25){
                    manager.createNotificationChannel(new NotificationChannel("default","还想",NotificationManager.IMPORTANCE_DEFAULT));
                }
                manager.notify(1, notification);
            }
        }

    }

    public static int getAppIconResouce(@Nullable Context context) {
        if (context == null) {
            return 0;
        }
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getApplicationInfo(context.getApplicationContext().getPackageName(), 0).icon;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;

        }
    }

}
