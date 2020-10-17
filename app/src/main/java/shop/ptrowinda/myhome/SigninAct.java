package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;
    ProgressBar progressBar;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String LEVEL_KEY = "levelkey";
    String level_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //mencari ID pada XML
        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        progressBar = findViewById(R.id.progressBar);

        //kondisi awal
        progressBar.setVisibility(View.GONE);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister1 = new Intent(SigninAct.this,Register1Act.class);
                startActivity(gotoregister1);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ubah state menjadi loading pada button lanjutkan
                progressBar.setVisibility(View.VISIBLE);

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Gagal! Isikan Nama Pengguna anda!", Toast.LENGTH_SHORT).show();
                    xusername.requestFocus();
                    xusername.setError("Masukkan nama pengguna Anda!");
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Isikan kata sandi anda!", Toast.LENGTH_SHORT).show();
                        xpassword.requestFocus();
                        xpassword.setError("Masukkan kata sandi anda!");
                        progressBar.setVisibility(View.GONE);
                    }else {
                        //mengambil referensi username pada database
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){

                                    //Mengambil data password dari firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                                    // Mengambil data level dari firebase
                                    String levelFromFirebase = dataSnapshot.child("level").getValue().toString();
                                    //Validasi password dengan password yang ada di firebase
                                    if (password.equals(passwordFromFirebase)){

                                        //Simpan username (key) kepada lokal
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //Simpan level (key) kepada lokal
                                        SharedPreferences sharedPreferences2 = getSharedPreferences(LEVEL_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                        editor2.putString(level_key, levelFromFirebase);
                                        editor2.apply();

                                        if (levelFromFirebase.equals("1")){
                                            //berpindah aktivity
                                            Toast.makeText(getApplicationContext(), "Masuk sebagai Admin berhasil!", Toast.LENGTH_SHORT).show();
                                            Intent gotoadminpanel = new Intent(SigninAct.this,AdminPanel.class);
                                            startActivity(gotoadminpanel);
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                        }else if (levelFromFirebase.equals("2")){
                                            //berpindah aktivity
                                            Toast.makeText(getApplicationContext(), "Masuk sebagai Marketing berhasil!", Toast.LENGTH_SHORT).show();
                                            Intent gotomarketingpanel = new Intent(SigninAct.this,MarketingPanel.class);
                                            startActivity(gotomarketingpanel);
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                        }else{
                                            //berpindah aktivity
                                            Toast.makeText(getApplicationContext(), "Masuk sebagai Konsumen berhasil!", Toast.LENGTH_SHORT).show();
                                            Intent gotohome = new Intent(SigninAct.this,HomeAct.class);
                                            startActivity(gotohome);
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                        }
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Password Salah!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(), "Nama Pengguna Tidak Tersedia!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }
}
