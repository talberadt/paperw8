package com.huji.hackathon.paperw8;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;

    public Adapter(Context context, List<String> titles, List<Integer> images) {
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(this.titles.get(position));
        holder.image.setImageResource(this.images.get(position));
        holder.btn.setOnClickListener(v -> {
            Intent brp = new Intent(v.getContext(), FileListActivity.class);
            String path = Environment.getExternalStorageDirectory().toString();
            brp.putExtra("path", path);
            v.getContext().startActivity(brp);
        });
    }

    @Override
    public int getItemCount() {
        return this.titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.subjectText);
            image = itemView.findViewById(R.id.subjectImage);
            btn = itemView.findViewById(R.id.button);
        }
    }
}
