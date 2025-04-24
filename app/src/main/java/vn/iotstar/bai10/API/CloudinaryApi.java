package vn.iotstar.bai10.API;

import org.checkerframework.checker.fenum.qual.PolyFenum;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CloudinaryApi {
   /* @Multipart
    @POST("https://api.cloudinary.com/v1_1/<ten_cloud>/video/upload")
    Call<CloudinaryResponse> uploadVideo(
            @Part MultipartBody.Part file,
            @Part("upload_preset") RequestBody uploadPreset
    );*/
}
