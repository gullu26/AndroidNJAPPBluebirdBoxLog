package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.Bird;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.BirdList;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.R;

import java.util.ArrayList;

public class RecyclerViewAdapterBirdSpecies extends RecyclerView.Adapter<RecyclerViewAdapterBirdSpecies.ViewHolder>{


    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Bird> mBirds = new BirdList().getBirdArrayList();

    private Context mContext;

    public RecyclerViewAdapterBirdSpecies(Context mContext) {


        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //change layout
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card_bird_search, viewGroup, false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.description.setText( mBirds.get(viewHolder.getAdapterPosition()).getDescription() );
        viewHolder.birdName.setText( mBirds.get(viewHolder.getAdapterPosition()).getName() );
        viewHolder.birdImage.setImageResource( mBirds.get(viewHolder.getAdapterPosition()).getImageId());


        //switch for the birds



        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //makes it return the bird you want


                Intent resultIntent = new Intent();
                resultIntent.putExtra("SPECIES", mBirds.get(viewHolder.getAdapterPosition()).getName());
                ((Activity)(mContext)).setResult(Activity.RESULT_OK, resultIntent);
                ((Activity)(mContext)).finish();

            }
        });



    }

    @Override
    public int getItemCount() {
        return mBirds.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView birdName, description;
        ImageView birdImage;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            birdImage = itemView.findViewById(R.id.id_imageView_birdImage_search);
            description = itemView.findViewById(R.id.id_textView_birdDescription);
            birdName = itemView.findViewById(R.id.id_textView_bird_name_search);
            parentLayout = itemView.findViewById(R.id.id_card_layout_bird_search);

        }
    }



}
