package com.company.todolistproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    EditText item;
    Button add;
    ArrayList<String> itemList = new ArrayList<>();
    RecyclerView recyclerView;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);

        myAdapter = new MyAdapter(itemList, this);
        recyclerView.setAdapter(myAdapter);

        add.setOnClickListener(v -> {
            String itemName = item.getText().toString();
            item.setText("");
            FileHelper.writeData(itemList, getApplicationContext());
            myAdapter.addItem(itemName);
        });
    }
}
