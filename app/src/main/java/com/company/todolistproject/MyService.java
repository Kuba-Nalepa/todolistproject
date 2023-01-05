package com.company.todolistproject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import java.util.ArrayList;


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
        sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(itemList);

        editor.putString("data", json);
        editor.apply();

        return super.onStartCommand(intent, flags, startId);
    }
}
