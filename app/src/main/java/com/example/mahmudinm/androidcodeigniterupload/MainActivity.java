package com.example.mahmudinm.androidcodeigniterupload;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button selectCamera, selectGallery, upload;
    ImageView imageView;
    private static final String folder_foto = "AplikasiKameraku";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectCamera = (Button) findViewById(R.id.selectCamera);
        selectGallery = (Button) findViewById(R.id.selectGallery);
        upload = (Button) findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.imageView);

        selectGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 11);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode != 0) {

            uri = data.getData();
            imageView.setImageURI(uri);

        }
    }
}
