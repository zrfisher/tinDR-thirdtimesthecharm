package com.example.tind3r;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ConcernsActivity extends AppCompatActivity {

    private String gender, insurance, zip;
    private TextView tv;
    private CheckBox anger, anxiety, children, depression, eating, lgbt, relationship;
    Button next;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerns);

        gender = getIntent().getStringExtra("gender");
        insurance = getIntent().getStringExtra("insurance");
        zip = getIntent().getStringExtra("zip");

        tv = findViewById(R.id.textViewConcerns);

        anger = findViewById(R.id.checkBoxAnger);
        anxiety = findViewById(R.id.checkBoxAnxiety);
        children = findViewById(R.id.checkBoxChild);
        depression = findViewById(R.id.checkBoxDepression);
        eating = findViewById(R.id.checkBoxEating);
        lgbt = findViewById(R.id.checkBoxLGBTQ);
        relationship = findViewById(R.id.checkBoxRelationships);
        next = findViewById(R.id.button2);

        url = "https://www.psychologytoday.com/us/therapists/";
        if(insurance != null){
            url+=insurance+"/";
        }
        if(zip!=null){
            url+=zip+"/";
        }
        url+="?sid=5e366b5673f8a" + gender;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anger.isChecked()){
                    url += "&spec=166";
                }
                if(anxiety.isChecked()){
                    url += "&spec=3";
                }
                if(children.isChecked()){
                    url += "&spec=5";
                }
                if(depression.isChecked()){
                    url += "&spec=2";
                }
                if(eating.isChecked()){
                    url += "&spec=9";
                }
                if(lgbt.isChecked()){
                    url += "&spec=172";
                }
                if(relationship.isChecked()){
                    url += "&spec=1";
                }

                //tv.setText(url);
                FetchData fd = new FetchData(url, ConcernsActivity.this);
                fd.execute();
            }
        });

    }
}
