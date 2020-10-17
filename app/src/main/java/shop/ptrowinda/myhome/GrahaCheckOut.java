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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class GrahaCheckOut extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_paynow, btn_minus, btn_plus;
    TextView textjumlahbooking, texttotalharga, textsaldo, nama_graha, type_rumah, ketentuan;
    Integer valueJumlahBooking = 1;
    Integer saldo = 0;
    Integer valuetotalharga = 0;
    Integer valuehargarumah = 0;
    int sisa_saldo = 0;
    ImageView notice_saldo;
    String lokasi, informasi;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    //Mengambil tanggal dan waktu
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    String date = dateFormat.format(c.getTime());
    String time = timeFormat.format(c.getTime());


    //generate nomor integer secara random untuk membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graha_check_out);

        //mengambil username yang ada di lokal
        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_graha_baru = bundle.getString("jenis_graha");

        //mengambil id dari XML
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);
        textjumlahbooking = findViewById(R.id.textjumlahbooking);
        textsaldo = findViewById(R.id.saldo);
        texttotalharga = findViewById(R.id.totalharga);
        btn_paynow = findViewById(R.id.btn_paynow);
        notice_saldo = findViewById(R.id.notice_saldo);
        nama_graha = findViewById(R.id.nama_graha);
        type_rumah = findViewById(R.id.type_rumah);
        ketentuan = findViewById(R.id.ketentuan);

        //setting nilai baru untuk beberapa komponen
        textjumlahbooking.setText(valueJumlahBooking.toString());

        notice_saldo.setVisibility(View.GONE);

        //inisialisasi nilai awal dengan angka 1, button minus defaultnya hilang
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);

        //mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saldo = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textsaldo.setText("Rp " + NumberFormat.getNumberInstance().format(saldo)+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Graha").child(jenis_graha_baru);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru dari firebase
                nama_graha.setText(dataSnapshot.child("nama_graha").getValue().toString());
                type_rumah.setText(dataSnapshot.child("type_rumah").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                lokasi = dataSnapshot.child("lokasi").getValue().toString();
                informasi = dataSnapshot.child("informasi").getValue().toString();
                //proses perhitungan harga
                valuehargarumah = Integer.valueOf(dataSnapshot.child("harga_graha").getValue().toString());
                valuetotalharga = valuehargarumah * valueJumlahBooking;

                if (valuetotalharga > saldo){
                    btn_paynow.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_paynow.setEnabled(false);
                    textsaldo.setTextColor(Color.parseColor("#D1206B"));
                    notice_saldo.setVisibility(View.VISIBLE);
                }
                texttotalharga.setText("Rp "+ NumberFormat.getNumberInstance().format(valuetotalharga)+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //fungsi button plus ketika ditekan
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahBooking+=1;
                textjumlahbooking.setText(valueJumlahBooking.toString());
                if (valueJumlahBooking > 1 ){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valuetotalharga = valuehargarumah * valueJumlahBooking;
                texttotalharga.setText("Rp "+ NumberFormat.getNumberInstance().format(valuetotalharga)+"");
                if (valuetotalharga > saldo){
                    btn_paynow.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_paynow.setEnabled(false);
                    textsaldo.setTextColor(Color.parseColor("#D1206B"));
                    notice_saldo.setVisibility(View.VISIBLE);
                }
            }
        });

        //fungsi button minus ketika ditekan akan berkurang
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahBooking-=1;
                textjumlahbooking.setText(valueJumlahBooking.toString());
                if (valueJumlahBooking <2 ){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valuetotalharga = valuehargarumah * valueJumlahBooking;
                texttotalharga.setText("Rp "+ NumberFormat.getNumberInstance().format(valuetotalharga)+"");
                if (valuetotalharga < saldo){
                    btn_paynow.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_paynow.setEnabled(true);
                    textsaldo.setTextColor(Color.parseColor("#FFBA5A"));
                    notice_saldo.setVisibility(View.GONE);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data user ke firebase dan membuat tabel baru "MyBooking"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyBooking").child(username_key_new).child(nama_graha.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_booking").setValue(nama_graha.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_graha").setValue(nama_graha.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi).toString();
                        reference3.getRef().child("type_rumah").setValue(type_rumah.getText().toString());
                        reference3.getRef().child("informasi").setValue(informasi).toString();
                        reference3.getRef().child("jumlah_booking").setValue(valueJumlahBooking.toString());
                        reference3.getRef().child("tanggal").setValue(date);
                        reference3.getRef().child("jam").setValue(time);

                        Intent gosuccesbooking = new Intent(GrahaCheckOut.this,SuccessBooking.class);
                        startActivity(gosuccesbooking);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //memperbaharui saldo kepada user (saat ini login)
                //mengambil data user dari firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_saldo = saldo - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_saldo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    //fungsi mengambil username lokal
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
