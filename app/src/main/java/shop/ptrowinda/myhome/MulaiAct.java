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

public class MulaiAct extends AppCompatActivity {

    Button btn_sign_in, btn_new_account_create;
    Animation toptobuttom, bottomtotop;
    ImageView emblem_app;
    TextView intro_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);

        //load animasi
        toptobuttom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);

        //load elemen yang akan di animasikan
        emblem_app = findViewById(R.id.emblem_app);
        intro_app = findViewById(R.id.intro_app);

        //jalankan animasi
        emblem_app.startAnimation(toptobuttom);
        intro_app.startAnimation(toptobuttom);
        btn_sign_in.startAnimation(bottomtotop);
        btn_new_account_create.startAnimation(bottomtotop);

        //fungsi tombol sign in ketika ditekan
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(MulaiAct.this,SigninAct.class);
                startActivity(gotosign);
            }
        });

        //fungsi tombol create akun ketika ditekan
        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister1 = new Intent(MulaiAct.this,Register1Act.class);
                startActivity(gotoregister1);
            }
        });
    }
    public void onBackPressed(){
        //disable back
    }
}
