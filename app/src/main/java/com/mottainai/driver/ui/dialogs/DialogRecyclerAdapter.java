package com.mottainai.driver.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class DialogRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<T> mArrayList;

    public abstract int getLayoutResId();
    public abstract RecyclerView.ViewHolder setViewHolder(View view);
    public abstract void onBindData(T dataModel, int position, RecyclerView.ViewHolder viewHolder);
    public abstract void onItemClick(T dataModel, int position);

    protected DialogRecyclerAdapter(ArrayList<T> arrayList) {
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view =  LayoutInflater.from(parent.getContext()).inflate(getLayoutResId(), parent, false);
        return setViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(view -> onItemClick(mArrayList.get(position), position));
        onBindData(mArrayList.get(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public void addItems(ArrayList<T> arrayList) {
        mArrayList = arrayList;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mArrayList.get(position);
    }
}
