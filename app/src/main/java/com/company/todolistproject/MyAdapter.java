package com.company.todolistproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    public ArrayList<ToDoItem> itemList;
    private final Context _context;
    private final String[] _urlList = {
        "https://img.redro.pl/plakaty/countryside-road-in-mountains-on-a-sunny-day-beautiful-view-in-to-the-distant-foggy-valley-from-the-top-of-the-pass-trees-along-the-way-wonderful-rural-landscape-in-summer-400-243053348.jpg",
        "https://img.redro.pl/plakaty/mountain-lake-landscape-in-summer-beautiful-scenery-of-synevyr-national-park-ukraine-body-of-water-among-the-forest-great-view-and-amazing-attarction-of-carpathian-nature-travel-europe-concept-400-242983964.jpg",
        "https://bestsimilar.com/img/tag/thumb/c2/124014.jpg"
    };
    private Boolean showDeleted = false;

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
        holder.btnReturn.setVisibility(View.INVISIBLE);

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
                holder.btnReturn.setVisibility(View.VISIBLE);

                Intent intent = new Intent(_context, MyService.class);
                intent.putExtra("data", itemList);
                _context.startService(intent);
                notifyItemChanged(position);
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        if (itemList.get(position).getDeleted() && !showDeleted) {
            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.itemView.setLayoutParams(params);
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.btnDelete.setVisibility(View.GONE);

        } else if(itemList.get(position).getDeleted() && showDeleted) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.btnReturn.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.itemView.setLayoutParams(params);

            holder.btnReturn.setOnClickListener( v -> {
                String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                itemList.get(position).setDate(simpleDateFormat.format(new Date()));

                itemList.get(position).setDeleted(false);
                Intent intent = new Intent(_context, MyService.class);
                intent.putExtra("data", itemList);
                _context.startService(intent);
                notifyItemChanged(position);
            });

        } else {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnReturn.setVisibility(View.INVISIBLE);
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#0000FFFF"));
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.itemView.setLayoutParams(params);
        }

        holder.textViewDate.setText(itemList.get(position).getDate());

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

    @SuppressLint("NotifyDataSetChanged")
    public void sortByTextLength() {
        itemList.sort(Comparator.comparingInt(s -> s.getText().length()));
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortByDate() {
        itemList.sort((s1, s2) -> {
            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            try {
                Date date1 = formatter.parse(s1.getDate());
                Date date2 = formatter.parse(s2.getDate());
                notifyDataSetChanged();
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShowDeleted(Boolean showDeleted) {
        this.showDeleted = showDeleted;
        notifyDataSetChanged();
    }

    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewToDo;
        private final ImageButton btnDelete;
        private final ImageButton btnReturn;
        private final WebView webView;
        private final TextView textViewDate;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewToDo = itemView.findViewById(R.id.textViewToDo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnReturn = itemView.findViewById(R.id.btnReturn);
            webView = itemView.findViewById(R.id.webView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
