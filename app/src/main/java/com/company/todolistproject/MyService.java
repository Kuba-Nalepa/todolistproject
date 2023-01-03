package com.company.todolistproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class MyService extends Service {

    ArrayList<String> itemList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        itemList = intent.getStringArrayListExtra("data");
        FileHelper.writeData(itemList, getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }
}
