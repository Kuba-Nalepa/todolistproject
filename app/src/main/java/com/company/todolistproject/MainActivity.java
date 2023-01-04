package com.company.todolistproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);
        Set<String> stringSet = sharedPreferences.getStringSet("data", new HashSet<>());
        ArrayList<String> itemList = new ArrayList<String>(stringSet);

        EditText item = findViewById(R.id.editText);
        Button add = findViewById(R.id.button);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter myAdapter = new MyAdapter(itemList, this);
        recyclerView.setAdapter(myAdapter);

        add.setOnClickListener(v -> {
            String itemName = item.getText().toString();
            item.setText("");
            myAdapter.addItem(itemName);

            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.putExtra("data", myAdapter.itemList);
            startService(intent);
        });
    }
}
