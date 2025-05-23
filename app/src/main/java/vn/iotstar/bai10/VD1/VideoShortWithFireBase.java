package vn.iotstar.bai10.VD1;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.iotstar.bai10.R;
import vn.iotstar.bai10.adapter.VideosFireBaseAdapter;
import vn.iotstar.bai10.model.VideoModel1;

public class VideoShortWithFireBase extends AppCompatActivity {

    ViewPager2 viewPager2;
    private VideosFireBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_short_with_fire_base);
        viewPager2 = findViewById(R.id.vpager2);
        getVideos();
        CircleImageView avatarImageView = findViewById(R.id.avatarImageView);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.child("avatarUrl").get().addOnSuccessListener(snapshot -> {
            String avatarUrl = snapshot.getValue(String.class);
            if (avatarUrl != null) {
                Glide.with(this).load(avatarUrl).placeholder(R.drawable.ic_person_pin).into(avatarImageView);
            }
        });
        avatarImageView.setOnClickListener(view -> {
            Intent intent = new Intent(VideoShortWithFireBase.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
    private void getVideos() {
        // ** set database*/
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference( "videos");
        FirebaseRecyclerOptions<VideoModel1> options = new FirebaseRecyclerOptions.Builder<VideoModel1>()
                .setQuery(mDataBase, VideoModel1.class).build();
        adapter = new VideosFireBaseAdapter(options);
        viewPager2.setOrientation(viewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}