package com.example.tind3r;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity {

    public static ArrayList<Doctor> doctors;
    private ImageView picture;
    private Button callButton, viewButton, nextButton;
    private TextView bio, name;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE;
    int count = -1;
    Doctor d;
    CardContainer mCardContainer;
    SimpleCardStackAdapter adapter;
    Drawable draw;

    private class SetDrawable extends AsyncTask<Void, Void, Void> {
        URL url;
        Doctor d;
        public SetDrawable(URL u, Doctor doc) {
            url = u;
            d = doc;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap bmp = null;
            try
            {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e)
            {
                e.printStackTrace();
            }
            draw = new BitmapDrawable(getResources(),bmp);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            CardModel c = new CardModel(d.get_name(), d.get_name(), draw);
            c.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    Log.d("Swipeable Card", "I liked it");
                }

                @Override
                public void onDislike() {
                    Log.d("Swipeable Card", "I did not liked it");
                }
            });

            adapter.add(c);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Disordered);
        bio = findViewById(R.id.drBio);
        adapter = new SimpleCardStackAdapter(this);

        for (Doctor doctor : doctors) {
            URL url = null;
            try {
                url = new URL(doctor.get_image_url());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            SetDrawable sd = new SetDrawable(url, doctor);
            sd.execute();


        }

        mCardContainer.setAdapter(adapter);

        callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent phoneCall = new Intent(Intent.ACTION_CALL);
                    phoneCall.setData(Uri.parse("tel:" + d.get_phone()));
                    startActivity(phoneCall);
                }
            }
        });

        nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextDr();
            }
        });

        viewButton = findViewById(R.id.buttonView);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(d.get_contact_url()));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Sorry! Malformed link.",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SwipeActivity.this,
                    new String[] {Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }

        loadNextDr();
    }

    private void loadNextDr(){
        count++;
        d = doctors.get(count);
        bio.setText(d.get_bio());
    }



}
