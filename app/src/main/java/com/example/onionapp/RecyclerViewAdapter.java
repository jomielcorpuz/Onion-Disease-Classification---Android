package com.example.onionapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RVviewHolder> {

    public static  int ItemCounter = 0;
    public static int imagePost;
    private static String ImagePATH;
    private static String ImAgE_NamE;
    private static String IMAGE_ID;
    private Context context;
    private ArrayList<ModelClass> modelClassArrayList;
    public static  int counter = 0;

    public RecyclerViewAdapter(Context context, ArrayList<ModelClass> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    @NonNull
    @Override
    public RVviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false);
        return new RVviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVviewHolder holder, int position) {
        ModelClass objectModel = modelClassArrayList.get(position);

        holder.diseaseName.setText(objectModel.getDiseaseName());
        holder.Image_ID_Holder.setText(objectModel.getId());
        ImagePATH = objectModel.getImagePath();
        holder.Date_Taken.setText(objectModel.getDateTaken());
        holder.objectImageView.setImageBitmap(objectModel.getImage());
        holder.ViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemCounter = 1;
                imagePost = holder.getAdapterPosition();
                IMAGE_ID = holder.Image_ID_Holder.getText().toString();
                counter = 1;

                Intent intent = new Intent(v.getContext(), ResultViewer.class);
                intent.putExtra("ID",objectModel.getId());
                intent.putExtra("Disease_Name",objectModel.getDiseaseName());
                intent.putExtra("Image",objectModel.getImage());
                intent.putExtra("Image_Path",objectModel.getImagePath());
                intent.putExtra("Date",objectModel.getDateTaken());


                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public static class RVviewHolder extends RecyclerView.ViewHolder{
        TextView diseaseName, Image_ID_Holder, Date_Taken;

        ImageView objectImageView;
        ImageButton ViewBtn;
        public RVviewHolder(@NonNull View itemView) {
            super(itemView);

            diseaseName = itemView.findViewById(R.id.Disease_Name);
            Image_ID_Holder = itemView.findViewById(R.id.ID_holder);
            Date_Taken = itemView.findViewById(R.id.DateTaken);
            objectImageView = itemView.findViewById(R.id.Image_Holder);
            ViewBtn = itemView.findViewById(R.id.ViewBtn);

        }
    }
}
