package com.company.todolistproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    public ArrayList<ToDoItem> itemList;
    private final Context _context;
    private final String[] _urlList = {
        "https://img.redro.pl/plakaty/countryside-road-in-mountains-on-a-sunny-day-beautiful-view-in-to-the-distant-foggy-valley-from-the-top-of-the-pass-trees-along-the-way-wonderful-rural-landscape-in-summer-400-243053348.jpg",
        "https://img.redro.pl/plakaty/mountain-lake-landscape-in-summer-beautiful-scenery-of-synevyr-national-park-ukraine-body-of-water-among-the-forest-great-view-and-amazing-attarction-of-carpathian-nature-travel-europe-concept-400-242983964.jpg",
        "https://bestsimilar.com/img/tag/thumb/c2/124014.jpg"
    };

    public MyAdapter(ArrayList<ToDoItem> itemList, Context context) {
        this.itemList = itemList;
        this._context = context;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyAdapterViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        holder.textViewToDo.setText(itemList.get(position).getText());

        holder.btnDelete.setOnClickListener( view -> {

            /* Postanowiłem że wróce do koncepcji zwykłego AlertDialogu ponieważ nie jestem w
            * stanie sprawić by ten komunikat wyświetlał się za pomocą AlertDialogFragmentu  */
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this item from list?");
            alert.setCancelable(false);
            alert.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            alert.setPositiveButton("Yes", (dialog, which) -> {
                itemList.get(position).setDeleted(true);
                notifyDataSetChanged();
                Intent intent = new Intent(_context, MyService.class);
                intent.putExtra("data", itemList);
                _context.startService(intent);
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        if (itemList.get(position).getDeleted()) {
            holder.itemView.setVisibility(View.GONE);
        }

        holder.webView.setWebViewClient(new WebViewClient());
        Random random = new Random();
        int randomNumber = random.nextInt(_urlList.length);
        holder.webView.loadUrl(_urlList[randomNumber]);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(ToDoItem toDoItem) {
        itemList.add(toDoItem);
        notifyItemInserted(getItemCount());
    }

    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewToDo;
        private final ImageButton btnDelete;
        private final WebView webView;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewToDo = itemView.findViewById(R.id.textViewToDo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            webView = itemView.findViewById(R.id.webView);
        }
    }
}
