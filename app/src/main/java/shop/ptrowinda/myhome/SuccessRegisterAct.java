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

public class SuccessRegisterAct extends AppCompatActivity {

    Button btn_mulai;
    Animation app_splash, bottomtotop, toptobottom;
    ImageView icon_success;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //mengambil id icon dan text yang akan di animasikan
        icon_success = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        btn_mulai = findViewById(R.id.btn_mulai);

        //load animasi
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);

        //jalankan animasi
        btn_mulai.startAnimation(bottomtotop);
        icon_success.startAnimation(app_splash);
        app_title.startAnimation(toptobottom);
        app_subtitle.startAnimation(toptobottom);

        btn_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegisterAct.this,HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });
    }
    public void onBackPressed(){
        //disable back
    }
}
