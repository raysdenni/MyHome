package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_grahawinda,btn_graharobeto,btn_history,btn_accsettings;
    CircleView btn_to_profile;
    ImageView foto_home_user;
    TextView user_balance, nama_lengkap, bio;

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
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_grahawinda = findViewById(R.id.btn_grahawinda);
        btn_graharobeto = findViewById(R.id.btn_graharobeto);
        btn_history = findViewById(R.id.btn_history);
        btn_accsettings = findViewById(R.id.btn_accsettings);
        foto_home_user = findViewById(R.id.foto_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        btn_to_profile =findViewById(R.id.btn_to_profile);

        //mengambil referensi username pada database
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
//                user_balance.setText(dataSnapshot.child("user_balance").getValue().toString());
                user_balance.setText(NumberFormat.getNumberInstance().format(dataSnapshot.child("user_balance").getValue()));
                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(foto_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_grahawinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotograhawinda = new Intent(HomeAct.this,GrahaDetail.class);
                //meletakkan data pada intent
                gotograhawinda.putExtra("jenis_graha", "Graha Winda");
                startActivity(gotograhawinda);
            }
        });
        btn_graharobeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotograhawinda = new Intent(HomeAct.this,GrahaDetail.class);
                //meletakkan data pada intent
                gotograhawinda.putExtra("jenis_graha", "Graha Robeto");
                startActivity(gotograhawinda);
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohistory = new Intent(HomeAct.this,BookingHistoryAct.class);
                startActivity(gotohistory);
            }
        });

        btn_accsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
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
