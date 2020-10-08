package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class MyProfileAct extends AppCompatActivity {

    Button btn_edit_profile, btn_sign_out, btn_back_home;
    TextView nama_lengkap, user_balance, bio, email_address, username;
    ImageView photo_profile;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Integer saldo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        //mendapatkan variable berdasdarkan id
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_sign_out = findViewById(R.id.btn_sign_out);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);
        bio = findViewById(R.id.bio);
        email_address = findViewById(R.id.email_address);
        username = findViewById(R.id.username);
        photo_profile = findViewById(R.id.photo_profile);
        btn_back_home = findViewById(R.id.btn_back_home);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
//                saldo = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
//                user_balance.setText("Rp. " + saldo+"");
                user_balance.setText("Rp "+NumberFormat.getNumberInstance().format(dataSnapshot.child("user_balance").getValue()));
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                email_address.setText(dataSnapshot.child("email_address").getValue().toString());
                username.setText(dataSnapshot.child("username").getValue().toString());
                Picasso.with(MyProfileAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoeditprofile = new Intent(MyProfileAct.this, EditProfileAct.class);
                startActivity(gotoeditprofile);
            }
        });

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent gotohome = new Intent(MyProfileAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fungsi untuk menyimpan username ke lokal
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                //berpindah activity
                Intent gotomulaiact = new Intent(MyProfileAct.this, MulaiAct.class);
                startActivity(gotomulaiact);
                finish();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    public void onBackPressed(){
        //disable back
    }
}
