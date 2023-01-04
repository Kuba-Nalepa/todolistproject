package com.company.todolistproject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyService extends Service {

    ArrayList<String> itemList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        itemList = intent.getStringArrayListExtra("data");
        Set<String> set = new HashSet<>(itemList);

        sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putStringSet("data",set);
        editor.apply();

        return super.onStartCommand(intent, flags, startId);
    }
}
