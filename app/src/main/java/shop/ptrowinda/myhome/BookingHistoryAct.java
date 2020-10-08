package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingHistoryAct extends AppCompatActivity {

    LinearLayout item_my_booking;
    ProgressBar progressBar;
    Button btn_mydashboard;

    RecyclerView mybooking_place;
    ArrayList<MyBooking> list;
    BookingAdapter bookingAdapter;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        getUsernameLocal();

        // mendapatkan data berdasarkan ID
        btn_mydashboard = findViewById(R.id.btn_mydashboard);
        item_my_booking = findViewById(R.id.item_my_booking);
        mybooking_place = findViewById(R.id.mybooking_place);
        progressBar = findViewById(R.id.progressBar);

        //state awal progressbar
        progressBar.setVisibility(View.VISIBLE);

        mybooking_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyBooking>();

        reference = FirebaseDatabase.getInstance().getReference().child("MyBooking").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MyBooking p = dataSnapshot1.getValue(MyBooking.class);
                    list.add(p);
                }
                bookingAdapter = new BookingAdapter(BookingHistoryAct.this, list);
                mybooking_place.setAdapter(bookingAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //fungsi tombol back ke menu utama/dashboard
        btn_mydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(BookingHistoryAct.this, HomeAct.class);
                startActivity(gotohome);
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
