package com.example.tind3r;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.squareup.picasso.Picasso;


import java.io.InputStream;
import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity {

    public static ArrayList<Doctor> doctors;
    private ImageView picture;
    private Button callButton;
    private TextView bio, name;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE;
    int count = -1;
    Doctor d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        picture = findViewById(R.id.drPhoto);
        callButton = findViewById(R.id.callButton);
        bio = findViewById(R.id.drBio);
        name = findViewById(R.id.drName);

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
        name.setText(d.get_name());
        bio.setText(d.get_bio());
        Picasso.get().load(d.get_image_url()).into(picture);
    }

}
