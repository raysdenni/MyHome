package shop.ptrowinda.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBooking extends AppCompatActivity {

    Button btn_mydashboard, btn_showhistory;
    Animation app_splash, bottomtotop, toptobottom;
    TextView app_title, app_subtitle;
    ImageView icon_success_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_booking);

        //mencari ID dari XML
        btn_showhistory = findViewById(R.id.btn_showhistory);
        btn_mydashboard = findViewById(R.id.btn_mydashboard);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        icon_success_booking = findViewById(R.id.icon_success_booking);

        //load animasi
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);

        //menjalankan animasi ke beberapa komponen
        app_title.startAnimation(toptobottom);
        app_subtitle.startAnimation(toptobottom);
        icon_success_booking.startAnimation(app_splash);
        btn_mydashboard.startAnimation(bottomtotop);
        btn_showhistory.startAnimation(bottomtotop);

        btn_showhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohistory = new Intent(SuccessBooking.this, BookingHistoryAct.class);
                startActivity(gotohistory);
                finish();
            }
        });

        //fungsi tombol back ke menu utama/dashboard
        btn_mydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessBooking.this, HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
    public void onBackPressed(){
        //disable back
    }
}
