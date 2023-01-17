package com.example.hometech.FeedbackList;

public class fbModel {
    String id, userid, username, userphn, techid, techname, techphn, feedback, ratingvalue;

    public fbModel(String id, String userid, String username, String userphn, String techid, String techname, String techphn, String feedback, String ratingvalue) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.userphn = userphn;
        this.techid = techid;
        this.techname = techname;
        this.techphn = techphn;
        this.feedback = feedback;
        this.ratingvalue = ratingvalue;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphn() {
        return userphn;
    }

    public String getTechid() {
        return techid;
    }

    public String getTechname() {
        return techname;
    }

    public String getTechphn() {
        return techphn;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getRatingvalue() {
        return ratingvalue;
    }
}