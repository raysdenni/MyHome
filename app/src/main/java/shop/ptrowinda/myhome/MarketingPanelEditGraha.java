package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class MarketingPanelEditGraha extends AppCompatActivity {

    ImageView thumbnail_graha;
    TextView xnama_graha, xtype_rumah, xharga_graha_now;
    EditText xinformasi, xsertifikat, xketentuan, xharga_graha, xslot;
    Button btn_update;
    LinearLayout btn_back;
    ProgressBar progressBar;

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
        setContentView(R.layout.mp_editgraha);

        getUsernameLocal();

        //Mengambil data di XML berdasarkan ID
        thumbnail_graha = findViewById(R.id.thumbnail_graha);
        xnama_graha = findViewById(R.id.xnama_graha);
        xtype_rumah = findViewById(R.id.xtype_rumah);
        xharga_graha_now = findViewById(R.id.xharga_graha_now);
        xinformasi = findViewById(R.id.xinformasi);
        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);
        xsertifikat = findViewById(R.id.xsertifikat);
        xketentuan = findViewById(R.id.xketentuan);
        xharga_graha = findViewById(R.id.xharga_graha);
        xslot = findViewById(R.id.xslot);
        progressBar = findViewById(R.id.progressBar);

        //kondisi awal
        progressBar.setVisibility(View.GONE);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_graha_baru = bundle.getString("nama_graha");

        //mengambil referensi username pada database
        reference = FirebaseDatabase.getInstance().getReference().child("Graha").child(nama_graha_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_graha.setText(dataSnapshot.child("nama_graha").getValue().toString());
                xtype_rumah.setText(dataSnapshot.child("type_rumah").getValue().toString());
                xharga_graha_now.setText("Rp. "+NumberFormat.getNumberInstance().format(dataSnapshot.child("harga_graha").getValue()));
                xinformasi.setText(dataSnapshot.child("informasi").getValue().toString());
                xsertifikat.setText(dataSnapshot.child("jenis_sertifikat").getValue().toString());
                xketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                xharga_graha.setText(dataSnapshot.child("harga_graha").getValue().toString());
                xslot.setText(dataSnapshot.child("slot").getValue().toString());
                Picasso.with(MarketingPanelEditGraha.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(thumbnail_graha);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_update.setEnabled(false);
                btn_update.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int update_harga_booking = Integer.parseInt(xharga_graha.getText().toString());
                        int update_slot = Integer.parseInt(xslot.getText().toString());
                        dataSnapshot.getRef().child("harga_graha").setValue(update_harga_booking);
                        dataSnapshot.getRef().child("slot").setValue(update_slot);
                        dataSnapshot.getRef().child("jenis_sertifikat").setValue(xsertifikat.getText().toString());
                        dataSnapshot.getRef().child("informasi").setValue(xinformasi.getText().toString());
                        dataSnapshot.getRef().child("ketentuan").setValue(xketentuan.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //berpindah activity
                Intent gotolistgraha = new Intent(MarketingPanelEditGraha.this,MarketingPanelListGraha.class);
                startActivity(gotolistgraha);
                progressBar.setVisibility(View.GONE);
                btn_update.setVisibility(View.VISIBLE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        SharedPreferences sharedPreferences2 = getSharedPreferences(LEVEL_KEY, MODE_PRIVATE);
        level_key_new = sharedPreferences2.getString(level_key, "");
    }
}
