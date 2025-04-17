package vn.iotstar.bai10.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.bai10.Api.APIService;
import vn.iotstar.bai10.R;
import vn.iotstar.bai10.adapters.VideosAdapter;
import vn.iotstar.bai10.models.MessageVideoModel;
import vn.iotstar.bai10.models.VideoModel;

public class MainActivity3 extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosAdapter videosAdapter;
    private List<VideoModel> list; // Đổi "List" thành "list" để nhất quán với quy ước đặt tên biến.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);

        viewPager2 = findViewById(R.id.vpager);
        list = new ArrayList<>();
        getVideos();
    }

    private void getVideos() {
        APIService.servieapi.getVideos().enqueue(new Callback<MessageVideoModel>() {
            @Override
            public void onResponse(Call<MessageVideoModel> call, Response<MessageVideoModel> response) {
                if (response.body() != null && response.body().getResult() != null) {
                    list = response.body().getResult();
                    videosAdapter = new VideosAdapter(getApplicationContext(), list);
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                    viewPager2.setAdapter(videosAdapter);
                } else {
                    Log.d("TAG", "Response body or result is null");
                }
            }

            @Override
            public void onFailure(Call<MessageVideoModel> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}