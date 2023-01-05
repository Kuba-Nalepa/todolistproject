package com.company.todolistproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText item = findViewById(R.id.editText);
        Button add = findViewById(R.id.button);
        Button sortByText = findViewById(R.id.btnSortByText);
        Button sortByDate = findViewById(R.id.btnSortByDate);
        CheckBox checkBox = findViewById(R.id.checkBox);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        SharedPreferences sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("data", "[]");
        Type listType = new TypeToken<ArrayList<ToDoItem>>() {}.getType();
        ArrayList<ToDoItem> toDoItems = gson.fromJson(json, listType);

        MyAdapter myAdapter = new MyAdapter(toDoItems, this);
        recyclerView.setAdapter(myAdapter);

        add.setOnClickListener(v -> {
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            String itemName = item.getText().toString();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            ToDoItem toDoItem = new ToDoItem(myAdapter.itemList.size(), simpleDateFormat.format(new Date()), itemName, false);

            item.setText("");
            myAdapter.addItem(toDoItem);

            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.putExtra("data", myAdapter.itemList);
            startService(intent);
        });

        sortByText.setOnClickListener(v -> {
            myAdapter.sortByTextLength();
        });

        sortByDate.setOnClickListener(v -> {
            myAdapter.sortByDate();
        });

        checkBox.setOnClickListener(v -> {
            myAdapter.setShowDeleted(checkBox.isChecked());
        });

    }
}
