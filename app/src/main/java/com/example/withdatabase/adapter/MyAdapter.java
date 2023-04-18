package com.example.withdatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.withdatabase.model.Note;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Note> notes;
    private LayoutInflater inflater;

    public MyAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
