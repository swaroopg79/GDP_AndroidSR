package com.example.s528802.studentregistartion;

/**
 * Created by S528802 on 2/6/2018.
 */

public class DataUploadInfo {

    private String firstName;
    private String lastName;
    private String emailId;
    private String Password;
    private String imageURL;

    public DataUploadInfo()
    {

    }

    public DataUploadInfo(String fname, String lname, String email, String pwd, String url )
    {
        this.firstName=fname;
        this.lastName=lname;
        this.emailId=email;
        this.Password=pwd;
        this.imageURL=url;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return Password;
    }

    public String getImageURL() {
        return imageURL;
    }
}
