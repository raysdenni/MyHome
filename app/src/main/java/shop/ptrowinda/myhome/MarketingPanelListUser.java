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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarketingPanelListUser extends AppCompatActivity {

    ProgressBar progressBar;
    Button btn_back;

    LinearLayout mp_item_listuser;
    RecyclerView userlist_place;
    ArrayList<MPListUser> list;
    MPListUserAdapter mpListUserAdapter;

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
        setContentView(R.layout.activity_marketing_panel_list_user);

        getUsernameLocal();


        progressBar = findViewById(R.id.progressBar);
        mp_item_listuser = findViewById(R.id.mp_item_listuser);
        userlist_place = findViewById(R.id.userlist_place);
        btn_back = findViewById(R.id.btn_back);

        // state awal progressbar
        progressBar.setVisibility(View.VISIBLE);

        userlist_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MPListUser>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MPListUser p = dataSnapshot1.getValue(MPListUser.class);
                    list.add(p);
                }
                mpListUserAdapter = new MPListUserAdapter(MarketingPanelListUser.this, list);
                userlist_place.setAdapter(mpListUserAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMarketingPanel = new Intent(MarketingPanelListUser.this,MarketingPanel.class);
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
