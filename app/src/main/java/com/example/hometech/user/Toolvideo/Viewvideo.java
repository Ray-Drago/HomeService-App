package com.example.hometech.user.Toolvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.hometech.Config;
import com.example.hometech.R;

public class Viewvideo extends AppCompatActivity {
VideoView videoView;
String videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewvideo);
        videoView=findViewById(R.id.tvurl);

        Intent i=getIntent();
        videos=i.getStringExtra("videos");

//       / Toast.makeText(getApplicationContext(), ""+videos, Toast.LENGTH_SHORT).show();
        //specify the location of media file
        // Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse(Config.imgURL + videos);

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }
}