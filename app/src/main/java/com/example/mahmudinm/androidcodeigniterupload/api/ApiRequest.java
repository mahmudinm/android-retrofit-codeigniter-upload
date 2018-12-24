package com.example.mahmudinm.androidcodeigniterupload.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Mahmudinm on 24/12/2018.
 */

public interface ApiRequest {

    @Multipart
    @POST("upload")
    Call<ResponseStatus> postUpload(@Part MultipartBody.Part gambar);

}
