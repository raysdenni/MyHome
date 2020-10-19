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

public class MarketingPanelListGraha extends AppCompatActivity {

    ProgressBar progressBar;
    Button btn_back;

    LinearLayout mp_item_listgraha;
    RecyclerView grahalist_place;
    ArrayList<MPListGraha> list;
    MPListGrahaAdapter mpListGrahaAdapter;

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
        setContentView(R.layout.mp_listgraha);

        getUsernameLocal();

        progressBar = findViewById(R.id.progressBar);
        mp_item_listgraha = findViewById(R.id.mp_item_listgraha);
        grahalist_place = findViewById(R.id.grahalist_place);
        btn_back = findViewById(R.id.btn_back);

        //state awal progressbar
        progressBar.setVisibility(View.VISIBLE);

        grahalist_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MPListGraha>();

        reference = FirebaseDatabase.getInstance().getReference().child("Graha");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MPListGraha p = dataSnapshot1.getValue(MPListGraha.class);
                    list.add(p);
                }
                mpListGrahaAdapter = new MPListGrahaAdapter(MarketingPanelListGraha.this, list);
                grahalist_place.setAdapter(mpListGrahaAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMarketingPanel = new Intent(MarketingPanelListGraha.this,MarketingPanel.class);
                startActivity(gotoMarketingPanel);
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
