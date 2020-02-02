package com.example.tind3r;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner selectInsurance, selectGender;
    TextView zip;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectInsurance = findViewById(R.id.insuranceSpinner);
        ArrayAdapter<CharSequence> adapterI = ArrayAdapter.createFromResource(this,R.array.insurances, android.R.layout.simple_spinner_dropdown_item);
        adapterI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectInsurance.setAdapter(adapterI);

        selectGender = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(this,R.array.genders, android.R.layout.simple_spinner_dropdown_item);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGender.setAdapter(adapterG);

        zip = findViewById(R.id.locationTextEdit);

        next = findViewById(R.id.toConcerns);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String z = "";
                String s = selectGender.getSelectedItem().toString();
                if(s.equals("Man")){
                    z = "&spec=1002";
                } else if(s.equals("Woman")){
                    z = "&spec=1001";
                } else if(s.equals("Non binary")){
                    z = "&spec=1712";
                }
                Intent intent = new Intent(getBaseContext(), ConcernsActivity.class);
                intent.putExtra("insurance", selectInsurance.getSelectedItem().toString());
                intent.putExtra("gender", z);
                intent.putExtra("zip", zip.getText().toString());
                startActivity(intent);
            }
        });

    }


}
