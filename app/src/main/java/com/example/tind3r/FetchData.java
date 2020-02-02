package com.example.tind3r;

import android.os.AsyncTask;

import java.util.ArrayList;

public class FetchData extends AsyncTask<Void, Void, Void> {

    String url;
    private ArrayList<Doctor> drs;


    public FetchData(String s){
        url = s;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        //todo harshitha
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        SwipeActivity.doctors = drs;
    }

}
