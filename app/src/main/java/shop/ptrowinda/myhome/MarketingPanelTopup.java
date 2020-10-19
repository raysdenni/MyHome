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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class MarketingPanelTopup extends AppCompatActivity {

    ImageView foto_home_user;
    TextView xnama_lengkap, xsisa_saldo;
    EditText xjumlah_saldo;
    Button btn_topup;
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
        setContentView(R.layout.mp_topup);

        getUsernameLocal();

        //Mengambil data di XML berdasarkan ID
        xnama_lengkap = findViewById(R.id.xnama_lengkap);
        xjumlah_saldo = findViewById(R.id.xjumlah_saldo);
        xsisa_saldo = findViewById(R.id.xsisa_saldo);
        btn_topup = findViewById(R.id.btn_topup);
        btn_back = findViewById(R.id.btn_back);
        foto_home_user = findViewById(R.id.foto_home_user);
        progressBar = findViewById(R.id.progressBar);

        //kondisi awal
        progressBar.setVisibility(View.GONE);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String username_baru = bundle.getString("username");

        //mengambil referensi username pada database
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                xsisa_saldo.setText(NumberFormat.getNumberInstance().format(dataSnapshot.child("user_balance").getValue()));
                xjumlah_saldo.requestFocus();
                Picasso.with(MarketingPanelTopup.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(foto_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_topup.setEnabled(false);
                btn_topup.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                final String jumlah_saldo = xjumlah_saldo.getText().toString();

                if (jumlah_saldo.isEmpty()){
                    xjumlah_saldo.requestFocus();
                    xjumlah_saldo.setError("Masukkan jumlah saldo yang ingin ditambahkan!");
                    btn_topup.setEnabled(true);
                    btn_topup.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int sisa_saldo = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                            int tambah_saldo = Integer.parseInt(xjumlah_saldo.getText().toString());
                            int total_saldo = sisa_saldo + tambah_saldo;
                            reference.getRef().child("user_balance").setValue(total_saldo);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //berpindah activity
                    Intent gotoadminpanel = new Intent(MarketingPanelTopup.this,MarketingPanelListUser.class);
                    startActivity(gotoadminpanel);
                    progressBar.setVisibility(View.GONE);
                    btn_topup.setVisibility(View.VISIBLE);
                }
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
