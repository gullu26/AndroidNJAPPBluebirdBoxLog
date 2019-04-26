package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.LogEntriesActivityNoEdit;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.AllUserLogData;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.R;

public class RecyclerViewAdapterMainNav extends RecyclerView.Adapter<RecyclerViewAdapterMainNav.ViewHolder>{


    private static final String TAG = "RecyclerViewAdapter";
    private AllUserLogData allUserLogData;


    private Context mContext;

    public RecyclerViewAdapterMainNav(AllUserLogData allUserLogData, Context mContext) {
        this.allUserLogData=allUserLogData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card_complete_entries, viewGroup, false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }


    public int getCloudImage(String s)
    {

        switch (s)
        {
            case "Clear":
            {
                return R.drawable.clear;
            }

            case "Cloudy":
            {
                return R.drawable.cloudy;
            }

            case "Overcast":
            {
                return R.drawable.overcast;
            }


            case "Rainy":
            {
                return R.drawable.rainy;
            }

            case "Snow":
            {
                return R.drawable.snow;
            }

        }
        return R.drawable.cloudy;

    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final String date=allUserLogData.getCompleteLogEntryArrayList().get(i).getDate();
        viewHolder.date.setText(date);
        viewHolder.boxes.setText(allUserLogData.getCompleteLogEntryArrayList().get(i).getBoxEntries().size() + " Boxes");

        String condition = allUserLogData.getCompleteLogEntryArrayList().get(i).getSunConditions();


        viewHolder.image.setImageResource(getCloudImage(condition));
        viewHolder.itemView.setBackgroundColor(Color.parseColor(getMonthColor(date)));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();

                //Adding key value pairs to this bundle
                //there are quite a lot data types you can store in a bundle
                extras.putInt("LOGNUM", i);



                //create and initialize an intent
                Intent intent = new Intent(v.getContext(), LogEntriesActivityNoEdit.class);

                //attach the bundle to the Intent object
                intent.putExtras(extras);

                //finally start the activity
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(allUserLogData==null)
        {
            allUserLogData = new AllUserLogData();
        }
        return allUserLogData.getCompleteLogEntryArrayList().size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView date, boxes;
        ImageView image;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.id_imageView_cloudy);
            date = itemView.findViewById(R.id.id_textView_log_date);
            boxes = itemView.findViewById(R.id.id_textView_number_of_boxes);
            parentLayout = itemView.findViewById(R.id.id_card_layout);



        }
    }

    public String getMonthColor(String date)
    {
        String month=date.split(" ")[0];
        String color="#ffffff";
        switch (month) {
            case "January":
            {
                color="#e5fcff";
                break;
            }

            case "February":
            {
                color="#ffe8e8";
                break;
            }

            case "March":
            {
                color="#d7ffd6";
                break;
            }

            case "April":
            {
                color="#f9eaff";
                break;
            }

            case "May":
            {
                color="#bbd1af";
                break;
            }

            case "June":
            {
                color="#fffec6";
                break;
            }

            case "July":
            {
                color="#c1d6f4";
                break;
            }

            case "August":
            {
                color="#d89595";
                break;
            }

            case "September":
            {
                color="#8c8c8c";
                break;
            }
            case "October":
            {
                color="#ffc47c";
                break;
            }

            case "November":
            {
                color="#d6c1a2";
                break;
            }

            case "December":
            {
                color="#fcfcfc";
                break;
            }



        }
        return color;
    }

}
