package com.shahidrogers.rmxchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://api.fixer.io/latest?base=MYR";

    // JSON Node names
    private static final String TAG_RATES = "rates";
    private static final String TAG_AUD = "AUD";
    private static final String TAG_USD = "USD";
    private static final String TAG_EUR = "EUR";
    private static final String TAG_SGD = "SGD";
    private static final String TAG_GBP = "GBP";

    //constants
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";

    // rates JSONArray
    JSONObject rates = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> ratesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createCutomActionBarTitle();

        ratesList = new ArrayList<HashMap<String, String>>();

        // Calling async task to get json
        new GetRates().execute();

    }

    private void createCutomActionBarTitle(){
        //this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/oversteer.ttf");
        TextView frag1 = (TextView)v.findViewById(R.id.titleFragment1);
        frag1.setTypeface(tf);
        TextView frag2 = (TextView)v.findViewById(R.id.titleFragment2);
        frag2.setTypeface(tf);

        frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Made by Shahid Rogers\n    Data from Fixer.io", Toast.LENGTH_LONG).show();
            }
        });

        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetRates extends AsyncTask<Void, Void, Void> {

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            /*
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
             */

            //set progress bar to 50%
            pb.setProgress(50);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            //Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    rates = jsonObj.getJSONObject(TAG_RATES);

                    // AUD rate
                    String AUD = (String) rates.getString(TAG_AUD);
                    float audNewRate = Float.parseFloat(AUD);
                    audNewRate = 1 / audNewRate;
                    AUD = String.valueOf(audNewRate);
                    //tmp hashmap for single rate
                    HashMap<String, String> AUDrate = new HashMap<String, String>();
                    //adding node to hashmap key => value
                    AUDrate.put(FIRST_COLUMN, "AUD");
                    AUDrate.put(SECOND_COLUMN, "RM" + AUD);
                    ratesList.add(AUDrate);

                    // USD rate
                    String USD = (String) rates.getString(TAG_USD);
                    float usdNewRate = Float.parseFloat(USD);
                    usdNewRate = 1 / usdNewRate;
                    USD = String.valueOf(usdNewRate);
                    //tmp hashmap for single rate
                    HashMap<String, String> USDrate = new HashMap<String, String>();
                    //adding node to hashmap key => value
                    USDrate.put(FIRST_COLUMN, "USD");
                    USDrate.put(SECOND_COLUMN, "RM" + USD);
                    ratesList.add(USDrate);

                    // EUR rate
                    String EUR = (String) rates.getString(TAG_EUR);
                    float eurNewRate = Float.parseFloat(EUR);
                    eurNewRate = 1 / eurNewRate;
                    EUR = String.valueOf(eurNewRate);
                    //tmp hashmap for single rate
                    HashMap<String, String> EURrate = new HashMap<String, String>();
                    //adding node to hashmap key => value
                    EURrate.put(FIRST_COLUMN, "EUR");
                    EURrate.put(SECOND_COLUMN, "RM" + EUR);
                    ratesList.add(EURrate);

                    // SGD rate
                    String SGD = (String) rates.getString(TAG_SGD);
                    float sgdNewRate = Float.parseFloat(SGD);
                    sgdNewRate = 1 / sgdNewRate;
                    SGD = String.valueOf(sgdNewRate);
                    //tmp hashmap for single rate
                    HashMap<String, String> SGDrate = new HashMap<String, String>();
                    //adding node to hashmap key => value
                    SGDrate.put(FIRST_COLUMN, "SGD");
                    SGDrate.put(SECOND_COLUMN, "RM" + SGD);
                    ratesList.add(SGDrate);

                    // GBP rate
                    String GBP = (String) rates.getString(TAG_GBP);
                    Float gbpNewRate = Float.parseFloat(GBP);
                    gbpNewRate = 1 / gbpNewRate;
                    GBP = String.valueOf(gbpNewRate);
                    //tmp hashmap for single rate
                    HashMap<String, String> GBPrate = new HashMap<String, String>();
                    //adding node to hashmap key => value
                    GBPrate.put(FIRST_COLUMN, "GBP");
                    GBPrate.put(SECOND_COLUMN, "RM" + GBP);
                    ratesList.add(GBPrate);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /*
            // Dismiss the progress dialog

            if (pDialog.isShowing())
                pDialog.dismiss();
            */

            //log hashmap
            /*
            for (int a =0; a<ratesList.size();a++)
            {
                HashMap<String, String> tmpData = (HashMap<String, String>) ratesList.get(a);
                Set<String> key = tmpData.keySet();
                Iterator it = key.iterator();
                while (it.hasNext()) {
                    String hmKey = (String)it.next();
                    String hmData = (String) tmpData.get(hmKey);

                    Log.d("Hashmap", "Key: "+hmKey +" & Data: "+hmData +"\n");
                    it.remove(); // avoids a ConcurrentModificationException
                }

            }
            */

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, ratesList,
                    R.layout.list_item, new String[] { FIRST_COLUMN, SECOND_COLUMN }, new int[] { R.id.country_cell, R.id.rate_cell });

            ListView lv = (ListView) findViewById(R.id.listView);

            lv.setAdapter(adapter);

            //set progress bar to 100%
            pb.setProgress(100);
        }

    }
}
