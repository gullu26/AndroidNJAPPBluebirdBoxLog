package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.LogBoxEntry;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.R;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.ViewBoxEntryActivity;

import java.util.ArrayList;

public class RecyclerViewAdapterBoxEntriesNoEdit extends RecyclerView.Adapter<RecyclerViewAdapterBoxEntriesNoEdit.ViewHolder>{


    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<LogBoxEntry> mBoxEntries = new ArrayList<>();

    private Context mContext;


    public RecyclerViewAdapterBoxEntriesNoEdit(ArrayList<LogBoxEntry> mBoxEntries, Context mContext) {
        this.mBoxEntries = mBoxEntries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //change layout
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card_box_entries_no_edit, viewGroup, false);
        ViewHolder holder=new ViewHolder(view);



        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.boxNum.setText("Box " + new Integer(mBoxEntries.get(i).getBoxNumber()).toString());
        viewHolder.birdName.setText(mBoxEntries.get(i).getSpecies());



        BirdList birdList=new BirdList();
        ArrayList<Bird> birds=birdList.getBirdArrayList();
        int imageSearch=R.drawable.generic_bird;
        for (Bird bird : birds)
        {
            if (bird.getName().equals  (mBoxEntries.get(i).getSpecies())    )
            {
                imageSearch=bird.getImageId();
            }
        }

        viewHolder.birdImage.setImageResource(imageSearch);

        //viewHolder.image.setImageResource();



        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();

                //Adding key value pairs to this bundle
                //there are quite a lot data types you can store in a bundle
                extras.putInt("POS", viewHolder.getAdapterPosition());
                extras.putInt("BOXNUM", mBoxEntries.get(viewHolder.getAdapterPosition()).getBoxNumber());
                extras.putString("SPECIES", mBoxEntries.get(viewHolder.getAdapterPosition()).getSpecies());
                extras.putString("ACTIVITY", mBoxEntries.get(viewHolder.getAdapterPosition()).getActivity());
                extras.putString("NEST", mBoxEntries.get(viewHolder.getAdapterPosition()).getNest());
                extras.putInt("EGGS", mBoxEntries.get(viewHolder.getAdapterPosition()).getEggs());
                extras.putInt("NESTLINGS", mBoxEntries.get(viewHolder.getAdapterPosition()).getNestlings());




                //create and initialize an intent
                Intent intent = new Intent(v.getContext(), ViewBoxEntryActivity.class);

                //attach the bundle to the Intent object
                intent.putExtras(extras);

                //finally start the activity
                ((Activity)v.getContext()).startActivity(intent);




            }
        });


    }

    @Override
    public int getItemCount() {
        return mBoxEntries.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView boxNum, birdName;
        ImageView birdImage;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            birdImage = itemView.findViewById(R.id.id_imageView_birdImage_noEdit);
            boxNum = itemView.findViewById(R.id.id_textView_boxNumber_noEdit);
            birdName = itemView.findViewById(R.id.id_textView_birdSpecies_noEdit);
            parentLayout = itemView.findViewById(R.id.id_card_layout_box_entries_no_edit);

        }
    }



}
