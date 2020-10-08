package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class GrahaDetail extends AppCompatActivity {

    Button btn_booking;
    LinearLayout btn_back;
    TextView title_graha, type_rumah, jenis_sertifikat, lokasi, detail_graha;
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
        detail_graha = findViewById(R.id.detail_graha);
        header_graha_detail = findViewById(R.id.header_graha_detail);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_graha_baru = bundle.getString("jenis_graha");

        //mengambil data dari firebase berdasarkan data intent
        reference = FirebaseDatabase.getInstance().getReference().child("Graha").child(jenis_graha_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru berdasarkan firebase
                title_graha.setText(dataSnapshot.child("nama_graha").getValue().toString());
                type_rumah.setText(dataSnapshot.child("type_rumah").getValue().toString());
                jenis_sertifikat.setText(dataSnapshot.child("jenis_sertifikat").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                detail_graha.setText(dataSnapshot.child("detail_graha").getValue().toString());
                Picasso.with(GrahaDetail.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_graha_detail);
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
