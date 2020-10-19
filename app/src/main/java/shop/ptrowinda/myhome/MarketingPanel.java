package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MarketingPanel extends AppCompatActivity {

    CircleView btn_to_profile;
    ImageView foto_home_user;
    ProgressBar progressBar;
    TextView nama_lengkap, bio;
    LinearLayout btn_topupuser, btn_managegraha;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String LEVEL_KEY = "levelkey";
    String level_key = "";
    String level_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp_marketingpanel);

        getUsernameLocal();

        foto_home_user = findViewById(R.id.foto_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        btn_to_profile =findViewById(R.id.btn_to_profile);
        btn_topupuser =findViewById(R.id.btn_topupuser);
        btn_managegraha =findViewById(R.id.btn_managegraha);

        //mengambil referensi username pada database
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.with(MarketingPanel.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(foto_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(MarketingPanel.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_topupuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototopup = new Intent(MarketingPanel.this,MarketingPanelListUser.class);
                startActivity(gototopup);
            }
        });

        btn_managegraha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent gotomanagegraha = new Intent(MarketingPanel.this, MarketingPanelListGraha.class);
                startActivity(gotomanagegraha);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        SharedPreferences sharedPreferences2 = getSharedPreferences(LEVEL_KEY, MODE_PRIVATE);
        level_key_new = sharedPreferences2.getString(level_key, "");
    }

    public void onBackPressed(){
        //disable back
    }
}
