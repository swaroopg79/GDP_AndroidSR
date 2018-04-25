package com.example.s528802.studentregistartion;

/**
 * Created by S528802 on 4/11/2018.
 */

public class ImageUpload {

    public String email;
    public String url;

    public ImageUpload(String email, String url) {
        this.email = email;
        this.url = url;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public String getEmail() {

        return email;
    }
}



