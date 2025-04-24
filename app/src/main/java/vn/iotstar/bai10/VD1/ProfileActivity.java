package vn.iotstar.bai10.VD1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.iotstar.bai10.R;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView avatarImageView;
    private TextView tvEmail, tvVideoCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        avatarImageView = findViewById(R.id.avatarImageView);
        tvEmail = findViewById(R.id.tvEmail);
        tvVideoCount = findViewById(R.id.tvVideoCount);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            tvEmail.setText(currentUser.getEmail());

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
            userRef.child("avatarUrl").get().addOnSuccessListener(snapshot -> {
                String avatarUrl = snapshot.getValue(String.class);
                if (avatarUrl != null) {
                    Glide.with(this).load(avatarUrl).into(avatarImageView);
                }
            });

            DatabaseReference videosRef = FirebaseDatabase.getInstance().getReference("videos");
            videosRef.orderByChild("uid").equalTo(uid).get().addOnSuccessListener(snapshot -> {
                int count = 0;
                for (DataSnapshot videoSnapshot : snapshot.getChildren()) {
                    count++;
                }
                tvVideoCount.setText("Số video đã đăng: " + count);
            });
        }
    }
}