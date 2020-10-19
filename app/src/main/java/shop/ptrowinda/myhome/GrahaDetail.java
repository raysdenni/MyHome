package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class GrahaDetail extends AppCompatActivity {

    Button btn_booking;
    LinearLayout btn_back;
    TextView title_graha, type_rumah, jenis_sertifikat, lokasi, detail_graha,harga_booking, slot;
    ImageView header_graha_detail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graha_detail);

        //mengambil id dari layout
        btn_booking = findViewById(R.id.btn_booking);
        btn_back = findViewById(R.id.btn_back);
        title_graha = findViewById(R.id.title_graha);
        type_rumah = findViewById(R.id.type_rumah);
        jenis_sertifikat = findViewById(R.id.jenis_sertifikat);
        lokasi = findViewById(R.id.lokasi);
        slot = findViewById(R.id.slot);
        detail_graha = findViewById(R.id.detail_graha);
        harga_booking = findViewById(R.id.harga_booking);
        header_graha_detail = findViewById(R.id.header_graha_detail);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_graha_baru = bundle.getString("jenis_graha");

        //mengambil data dari firebase berdasarkan data intent
        reference = FirebaseDatabase.getInstance().getReference().child("Graha").child(jenis_graha_baru);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru berdasarkan firebase
                title_graha.setText(dataSnapshot.child("nama_graha").getValue().toString());
                type_rumah.setText(dataSnapshot.child("type_rumah").getValue().toString());
                jenis_sertifikat.setText(dataSnapshot.child("jenis_sertifikat").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                detail_graha.setText(dataSnapshot.child("detail_graha").getValue().toString());
                harga_booking.setText("Rp. "+ NumberFormat.getNumberInstance().format(dataSnapshot.child("harga_graha").getValue()));
                slot.setText(dataSnapshot.child("slot").getValue().toString()+" Unit");
                Picasso.with(GrahaDetail.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_graha_detail);
                int slot_unit = Integer.valueOf(dataSnapshot.child("slot").getValue().toString());
                //logika jika unit lebih kecil dari 1 atau = 0(habis)
                if (slot_unit<1){
                    //sembunyikan button booking
                    slot.setTextColor(getResources().getColor(R.color.red));
                    btn_booking.setEnabled(false);
                    btn_booking.setText("Stok Unit Habis");
                    btn_booking.setBackgroundResource(R.drawable.bg_buttonred);
                }else {
                    slot.setTextColor(getResources().getColor(R.color.GreenPrimary));
                    btn_booking.setEnabled(true);
                    btn_booking.setText("BOOKING");
                    btn_booking.setBackgroundResource(R.drawable.bg_buttonsignin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(GrahaDetail.this,GrahaCheckOut.class);
                gotocheckout.putExtra("jenis_graha",jenis_graha_baru);
                startActivity(gotocheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
