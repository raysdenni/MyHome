package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBookingDetailAct extends AppCompatActivity {

    DatabaseReference reference;
    TextView xnama_graha, xtype_rumah, xtanggal, xjam, xinformasi, xjumlah_booking;

    LinearLayout btn_back;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_detail);

        getUsernameLocal();

        // mendapatkan data berdasarkan id
        btn_back = findViewById(R.id.btn_back);
        xnama_graha = findViewById(R.id.xnama_graha);
        xtype_rumah = findViewById(R.id.xtype_rumah);
        xtanggal = findViewById(R.id.xtanggal);
        xjam = findViewById(R.id.xjam);
        xinformasi = findViewById(R.id.xinformasi);
        xjumlah_booking = findViewById(R.id.xjumlah_booking);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String id_booking_baru = bundle.getString("id_booking");

        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("MyBooking").child(username_key_new).child(id_booking_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_graha.setText(dataSnapshot.child("nama_graha").getValue().toString());
                xtype_rumah.setText("Perumahan "+dataSnapshot.child("type_rumah").getValue().toString());
                xinformasi.setText(dataSnapshot.child("informasi").getValue().toString());
                xtanggal.setText(dataSnapshot.child("tanggal").getValue().toString());
                xjam.setText(dataSnapshot.child("jam").getValue().toString());
                xjumlah_booking.setText("x"+dataSnapshot.child("jumlah_booking").getValue().toString()+" Booking");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    }
}
