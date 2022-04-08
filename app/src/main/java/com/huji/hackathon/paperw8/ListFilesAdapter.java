package com.huji.hackathon.paperw8;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class ListFilesAdapter extends RecyclerView.Adapter<ListFilesAdapter.ViewHolder> {

    Context context;
    File[] filesAndFolders;

    public ListFilesAdapter(Context context, File[] filesAndFolders) {
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_grid_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File selectedFile = filesAndFolders[position];
        holder.textView.setText(selectedFile.getName());
        if (selectedFile.isDirectory()) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }

        holder.btn.setOnClickListener(v -> {
            if (selectedFile.isDirectory()) {
                Intent intent = new Intent(v.getContext(), FileListActivity.class);
                String path = selectedFile.getAbsolutePath();
                intent.putExtra("path", path);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            } else {
                try {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                String type = "application/pdf";
                intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()), type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context.getApplicationContext(), "Cannot open the file.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btn.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenu().add("DELETE");
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("DELETE")) {
                    boolean deleted = selectedFile.delete();
                    if (deleted) {
                        Toast.makeText(context.getApplicationContext(), "File deleted.", Toast.LENGTH_SHORT).show();
                        v.setVisibility(View.GONE);
                    }
                }
                return true;
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.subjectText);
            imageView = itemView.findViewById(R.id.subjectImage);
            btn = itemView.findViewById(R.id.button);
        }
    }
}
