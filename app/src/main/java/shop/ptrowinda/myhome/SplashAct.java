package shop.ptrowinda.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashAct extends AppCompatActivity {
    Animation app_splash, bottomtotop;
    ImageView app_logo;
    TextView app_subtitle;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String LEVEL_KEY = "levelkey";
    String level_key = "";
    String level_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load animasi
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);

        //load elemen
        app_logo = findViewById(R.id.app_logo);
        app_subtitle =findViewById(R.id.app_subtitle);

        //run animasi
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(bottomtotop);

        getUsernameLocal();

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        SharedPreferences sharedPreferences1 = getSharedPreferences(LEVEL_KEY, MODE_PRIVATE);
        level_key_new = sharedPreferences1.getString(level_key, "");
        if (username_key_new.isEmpty()) {
            //setting timer splash untuk 2 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //pindah activity ke activity lain
                    Intent gogetstarted = new Intent(SplashAct.this,MulaiAct.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000); //1000 milis = 1 detik
        }else {
            if (level_key_new.equals("1")){
                //setting timer splash untuk 2 detik
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //pindah activity ke activity lain
                        Toast.makeText(getApplicationContext(), "Masuk otomatis sebagai Admin", Toast.LENGTH_SHORT).show();
                        Intent gotoadminpanel = new Intent(SplashAct.this,AdminPanel.class);
                        startActivity(gotoadminpanel);
                        finish();
                    }
                }, 2000); //1000 milis = 1 detik
            }else if (level_key_new.equals("2")){
                //setting timer splash untuk 2 detik
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //pindah activity ke activity lain
                        Toast.makeText(getApplicationContext(), "Masuk otomatis sebagai Marketing", Toast.LENGTH_SHORT).show();
                        Intent gotomarketingpanel = new Intent(SplashAct.this,MarketingPanel.class);
                        startActivity(gotomarketingpanel);
                        finish();
                    }
                }, 2000); //1000 milis = 1 detik
            }else {
                //setting timer splash untuk 2 detik
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //pindah activity ke activity lain
                        Toast.makeText(getApplicationContext(), "Masuk otomatis sebagai Konsumen", Toast.LENGTH_SHORT).show();
                        Intent gotoHomeAct = new Intent(SplashAct.this,HomeAct.class);
                        startActivity(gotoHomeAct);
                        finish();
                    }
                }, 2000); //1000 milis = 1 detik
            }
        }
    }
}
