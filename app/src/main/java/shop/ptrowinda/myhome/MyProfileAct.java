package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
    TextView nama_lengkap, user_balance, bio, email_address, username, txtsaldo;
    ImageView photo_profile;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String LEVEL_KEY = "levelkey";
    String level_key = "";
    String level_key_new = "";

//    Integer saldo = 0;

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
        txtsaldo = findViewById(R.id.txtsaldo);
        photo_profile = findViewById(R.id.photo_profile);
        btn_back_home = findViewById(R.id.btn_back_home);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
//                saldo = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
//                user_balance.setText("Rp. " + saldo+"");

                //logika untuk menampilkan tulisan berdasarkan level user
                if (level_key_new.equals("1")){
                    txtsaldo.setText("Menu Profil Admin");
                    txtsaldo.setTextSize(24);
                    user_balance.setText("Berikut adalah detail profil anda.");
                    user_balance.setTextSize(18);
                    user_balance.setTextColor(Color.parseColor("#FF8D8D8D"));
                }else if (level_key_new.equals("2")){
                    txtsaldo.setText("Menu Profil Marketing");
                    txtsaldo.setTextSize(24);
                    user_balance.setText("Berikut adalah detail profil anda.");
                    user_balance.setTextSize(18);
                    user_balance.setTextColor(Color.parseColor("#FF8D8D8D"));
                }else {
                    user_balance.setText("Rp "+NumberFormat.getNumberInstance().format(dataSnapshot.child("user_balance").getValue()));
                }
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
                //logika back berdasarkan level user
                if (level_key_new.equals("1")){
                    Intent gotoadminpanel = new Intent(MyProfileAct.this, AdminPanel.class);
                    startActivity(gotoadminpanel);
                }else if (level_key_new.equals("2")){
                    Intent gotomarketingpanel = new Intent(MyProfileAct.this, MarketingPanel.class);
                    startActivity(gotomarketingpanel);
                }else {
                    Intent gotohome = new Intent(MyProfileAct.this, HomeAct.class);
                    startActivity(gotohome);
                }
            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fungsi untuk menghapus username di lokal
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();
                //fungsi untuk menghapus level di lokal
                SharedPreferences sharedPreferences2 = getSharedPreferences(LEVEL_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor.putString(level_key, null);
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
        SharedPreferences sharedPreferences2 = getSharedPreferences(LEVEL_KEY, MODE_PRIVATE);
        level_key_new = sharedPreferences2.getString(level_key, "");
    }

    public void onBackPressed(){
        //disable back
    }
}
