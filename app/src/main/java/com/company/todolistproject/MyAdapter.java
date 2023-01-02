package com.company.todolistproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    private final ArrayList<String> _itemList;
    private final Context _context;

    public MyAdapter(ArrayList<String> itemList, Context context) {
        this._itemList = itemList;
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
        holder.textViewToDo.setText(_itemList.get(position));
        holder.btnDelete.setOnClickListener( view -> {

            /* Postanowiłem że wróce do koncepcji zwykłego AlertDialogu ponieważ nie jestem w
            * stanie sprawić by ten komunikat wyświetlał się za pomocą AlertDialogFragmentu  */
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this item from list?");
            alert.setCancelable(false);
            alert.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            alert.setPositiveButton("Yes", (dialog, which) -> {
                _itemList.remove(position);
                notifyDataSetChanged();
                FileHelper.writeData(_itemList, _context);
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return _itemList.size();
    }

    public void addItem(String item) {
        _itemList.add(item);
        notifyItemInserted(getItemCount());
    }

    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewToDo;
        private final ImageButton btnDelete;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewToDo = itemView.findViewById(R.id.textViewToDo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
