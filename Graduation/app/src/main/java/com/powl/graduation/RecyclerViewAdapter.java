package com.powl.graduation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<Object> items = null;

    RecyclerViewAdapter(ArrayList<Object> items)
    {
        super();
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(parent);
    }

    @Override
    public int getItemCount() {
        if(items == null)
        {
            return 0;
        }
        else{
            return items.size();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // TODO()
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        RecyclerViewHolder(ViewGroup parent)
        {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.listcontacts, parent, false));

            // findViewById를 이용하시
        }
    }
}