package com.example.tind3r;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchData extends AsyncTask<Void, Void, Void> {

    String url;
    private ArrayList<Doctor> drs = new ArrayList<Doctor>();
    private Activity activity;

    public FetchData(String s, Activity a){
        url = s;
        activity = a;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        // get html from search results page
        // parse to get list of profile links
        drs = new ArrayList<Doctor>();
        Document resultsDoc;
        String profile_link;
        ArrayList<String> profileLinks = new ArrayList<String>();

        try {
            resultsDoc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            resultsDoc = null;
        }

        if (resultsDoc != null) {
            // <div class="result-row normal-result row"
            Elements profiles = resultsDoc.select(".result-row.normal-result.row");
            for (Element profile : profiles) {
                profile_link = profile.attr("data-profile-url");
                profileLinks.add(profile_link);
            }
        }

        // put this code in a loop, (for profileurl in list)
        // iterate through urls from results page
        // create a Doctor and add to ArrayList
        Document doc;
        for (String profile_url : profileLinks) {
            try {
                doc = Jsoup.connect(profile_url).get();
            } catch (IOException e) {
                e.printStackTrace();
                doc = null;
            }

            if (doc != null) {
                Element profileContainer = doc.select("#profileContainer").first();
                String doctorName = profileContainer.attr("data-prof-name");
                String image_url = profileContainer.attr("data-profile-image");

                //currently gets link to profile since the email/contact url listed doesn't work
                String contact_url = profileContainer.attr("data-profile-url");

                String phone = profileContainer.attr("data-phone");

                // bio is in a meta tag so extract this separately
                String bio = "";
                Elements metaTags = doc.getElementsByTag("meta");
                for (Element metaTag : metaTags) {
                    String content = metaTag.attr("content");
                    String metaTagName = metaTag.attr("name");

                    if ("description".equals(metaTagName)) {
                        bio = content;
                    }
                }

                Log.d("test", "test2");

                Doctor d = new Doctor(doctorName, image_url, contact_url, phone, bio);
                drs.add(d);
            }
        }

        SwipeActivity.doctors = drs;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        //SwipeActivity.doctors = drs;
        activity.startActivity(new Intent(activity, SwipeActivity.class));

    }

}
