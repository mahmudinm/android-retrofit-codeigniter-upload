package com.example.mahmudinm.androidcodeigniterupload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mahmudinm.androidcodeigniterupload.api.ApiRequest;
import com.example.mahmudinm.androidcodeigniterupload.api.ResponseStatus;
import com.example.mahmudinm.androidcodeigniterupload.api.Retroserver;
import com.example.mahmudinm.androidcodeigniterupload.utils.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button selectCamera, selectGallery, upload;
    ImageView imageView;
    ProgressDialog progressDialog;
    private static final String folder_foto = "AplikasiKameraku";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();

        selectCamera = (Button) findViewById(R.id.selectCamera);
        selectGallery = (Button) findViewById(R.id.selectGallery);
        upload = (Button) findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.imageView);
        progressDialog = new ProgressDialog(this);

        selectGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 11);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                File file = FileUtils.getFile(MainActivity.this, uri);
                RequestBody gambarBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part gambarPart = MultipartBody.Part.createFormData("gambar", file
                        .getName(), gambarBody);

                ApiRequest api = Retroserver.getRetrofit().create(ApiRequest.class);
                Call<ResponseStatus> postUpload = api.postUpload(gambarPart);
                postUpload.enqueue(new Callback<ResponseStatus>() {
                    @Override
                    public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                        progressDialog.dismiss();
                        String status = response.body().getStatus();
                        Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseStatus> call, Throwable t) {
                        progressDialog.dismiss();
                        t.printStackTrace();
                    }
                });

            }
        });

    }

    private void permission() {
        if (
                Build.VERSION.SDK_INT >= 23
                        && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{
                    Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA
            }, 0);
        } else {

        }
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
