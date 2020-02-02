package com.example.tind3r;

public class Doctor {
    String name;
    String image_url;
    String contact_url;
    String phone;
    String bio;

    public Doctor(String name, String image_url,
                  String contact_url, String phone,
                  String bio)
    {
        this.name = name;
        this.image_url = image_url;
        this.contact_url = contact_url;
        this.phone = phone;
        this.bio = bio;
    }

    public String get_name()
    {
        return name;
    }

    public String get_image_url()
    {
        return image_url;
    }

    public String get_contact_url()
    {
        return contact_url;
    }

    public String get_phone()
    {
        return phone;
    }

    public String get_bio()
    {
        return bio;
    }
}
