package vn.iotstar.bai10.VD2;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.bai10.API.APIService;
import vn.iotstar.bai10.R;
import vn.iotstar.bai10.adapter.VideosAdapter;
import vn.iotstar.bai10.model.MessageVideoModel;
import vn.iotstar.bai10.model.VideoModel;

public class VideoShortWithAPIServerViewPager2 extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private VideosAdapter adapter;
    private List<VideoModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_short_with_apiserver_view_pager2);
        viewPager2 = findViewById(R.id.vpager);
        list = new ArrayList<>();
        getVideos();
    }

    private void getVideos() {
        APIService.serviceApi.getVideo().enqueue(new Callback<MessageVideoModel>() {
            @Override
            public void onResponse(Call<MessageVideoModel> call, Response<MessageVideoModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = response.body().getResult();
                    if (list != null && !list.isEmpty()) {
                        adapter = new VideosAdapter(VideoShortWithAPIServerViewPager2.this, list);
                        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                        viewPager2.setAdapter(adapter);
                    } else {
                        Log.e("API_ERROR", "List is empty");
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<MessageVideoModel> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching videos: " + t.getMessage());
            }
        });
    }
}