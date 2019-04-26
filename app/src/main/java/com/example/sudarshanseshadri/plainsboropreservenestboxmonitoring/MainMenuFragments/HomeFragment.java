package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MainMenuFragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.LogEntriesActivity;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeFragment extends Fragment {
    URL url;
    URLConnection urlConnection;
    InputStream inputStream;
    BufferedReader bufferedReader;


    TextView date;
    View rootView;
    String dateText;
    Button letsGo;
    Spinner windSpinner, cloudSpinner;
    EditText et;

    ImageView conditionImage;

    //user modified data/weather api stuff

    int temperature;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        letsGo = rootView.findViewById(R.id.id_button_start);
        et = rootView.findViewById(R.id.id_editText_temp);
        temperature = Integer.parseInt((et.getText()).toString());

        conditionImage = rootView.findViewById(R.id.id_imageView_condition);

        setDefaultDate();

        setUpWindSpinner();
        setUpCloudSpinner();


        AsyncThread getWeather = new AsyncThread();
        getWeather.execute();


        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a Bundle object
                Bundle extras = new Bundle();

                //Adding key value pairs to this bundle
                //there are quite a lot data types you can store in a bundle
                extras.putInt("TEMP", Integer.parseInt(et.getText().toString()));
                extras.putString("DATE", (String) date.getText());
                extras.putString("SUN", cloudSpinner.getSelectedItem().toString());
                extras.putString("WIND", (String) windSpinner.getSelectedItem().toString());


                //create and initialize an intent
                Intent intent = new Intent(getActivity(), LogEntriesActivity.class);

                //attach the bundle to the Intent object
                intent.putExtras(extras);

                //finally start the activity
                startActivity(intent);
            }
        });
        //get the weather automatically with OpenWeatherMap


        // Inflate the layout for this fragment
        return rootView;
    }


    public void setDefaultDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MMMM dd, YYYY");
        String strDate = mdformat.format(calendar.getTime());
        String dayNum = strDate.split(" ")[1];
        if (dayNum.startsWith("0")) {
            dayNum = dayNum.substring(1);
        }
        String formatStrDate = strDate.split(" ")[0] + " " + dayNum + " " + strDate.split(" ")[2];

        dateText = formatStrDate;
        date = rootView.findViewById(R.id.id_textView_date);
        date.setText(formatStrDate);

    }

    public void setDate(String s) {
        dateText = s;

    }

    public void setUpWindSpinner() {
        windSpinner = (Spinner) rootView.findViewById(R.id.id_spinner_wind);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.windConditions, R.layout.layout_main_menu_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        windSpinner.setAdapter(adapter);

        windSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        windSpinner.setSelection(0);

    }

    protected int getCloudImage(String s) {

        switch (s) {
            case "Clear": {
                return R.drawable.clear;
            }

            case "Cloudy": {
                return R.drawable.cloudy;
            }

            case "Overcast": {
                return R.drawable.overcast;
            }


            case "Rainy": {
                return R.drawable.rainy;
            }

            case "Snow": {
                return R.drawable.snow;
            }

        }
        return R.drawable.cloudy;

    }

    private void setUpCloudSpinner() {
        cloudSpinner = (Spinner) rootView.findViewById(R.id.id_spinner_cloud);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.cloudConditions, R.layout.layout_main_menu_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cloudSpinner.setAdapter(adapter);

        cloudSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conditionImage.setImageResource(getCloudImage(cloudSpinner.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //cloudSpinner.setSelection(0);
    }

    String info = "";

    public class AsyncThread extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... voids) {
            final String ID = "5102729"; //id of the plainsboro preserve

            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + ID + "&units=imperial,us&APPID=40e95f00517dc507947dce2b6f96626a");


                urlConnection = url.openConnection();


                inputStream = urlConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                info = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {
                JSONObject json = new JSONObject(info);


                int condition = json.getJSONArray("weather").getJSONObject(0).getInt("id");
                cloudSpinner.setSelection(getCloudSpinnerPosition(condition));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAGHOMEFRAG", "nay cond");
            }

            try {

                JSONObject json = new JSONObject(info);
                JSONObject main = json.getJSONObject("wind");
                double speed = main.getDouble("speed");
                windSpinner.setSelection(getWindSpinnerPosition(speed));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAGHOMEFRAG", "nay wind");
            }


            try {

                JSONObject json = new JSONObject(info);
                JSONObject main = json.getJSONObject("main");
                String temp = main.getString("temp");
                int kel = (int) ((Double.parseDouble(temp)));
                int far = (int) ((Double.parseDouble(temp) - 273.15) * 9 / 5) + 32;
                et.setText(far + "");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAGHOMEFRAG", "nay temp");
            }
        }


        protected int getCloudSpinnerPosition(int conditionId) {
            //Clear       0
            //Cloudy      1
            //Overcast    2
            //Rainy       3
            //Snow        4

            if (conditionId == 804)//overcast
            {
                return 2;
            }
            if (conditionId >= 801 && conditionId <= 803 || conditionId == 711) //cloudy
            {
                return 1;
            } else if (conditionId >= 200 && conditionId <= 531) //all types of rain
            {
                return 3;
            } else if (conditionId >= 600 && conditionId <= 622) //types of snow
            {
                return 4;
            }

            return 0;

        }


    }


    protected int getWindSpinnerPosition(double speed) {
        //No Wind     0
        //Some Wind   1
        //Very Windy  2

        if (speed < 2.5)//no wind
        {
            return 0;
        } else if (speed < 11) //some wind
        {
            return 1;
        } else if (speed >= 11) //very windy
        {
            return 3;
        }


        return 0;

    }


}



