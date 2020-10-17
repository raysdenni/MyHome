package shop.ptrowinda.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register1Act extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username, password, email_address;
    ProgressBar progressBar;

    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        //mendapatkan id dari XML
        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.btn_back);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);
        progressBar = findViewById(R.id.progressBar);

        //kondisi awal
        progressBar.setVisibility(View.GONE);

        //fungsi button/tombol
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ubah state menjadi loading pada button lanjutkan
                btn_continue.setEnabled(false);
                btn_continue.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                final String xusername = username.getText().toString();
                final String xpassword = password.getText().toString();
                final String xemail_address = email_address.getText().toString();

                if (xusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Gagal! Isikan Nama Pengguna anda!", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                    username.setError("Masukkan nama pengguna Anda!");
                    btn_continue.setEnabled(true);
                    btn_continue.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if (xpassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Isikan kata sandi anda!", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    password.setError("Masukkan kata sandi anda!");
                    btn_continue.setEnabled(true);
                    btn_continue.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else if (xemail_address.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Isikan Email anda!", Toast.LENGTH_SHORT).show();
                    email_address.requestFocus();
                    email_address.setError("Masukkan Email anda!");
                    btn_continue.setEnabled(true);
                    btn_continue.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    //mengambil username pada database firebase
                    reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                    reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // jika username tersedia
                            if (dataSnapshot.exists()){
                                Toast.makeText(getApplicationContext(), "Nama pengguna sudah ada!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_continue.setVisibility(View.VISIBLE);
                                btn_continue.setEnabled(true);
                            }
                            else {
                                //fungsi untuk menyimpan username ke lokal
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, username.getText().toString());
                                editor.apply();

                                //simpan kedalam database
                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                        dataSnapshot.getRef().child("user_balance").setValue(5000000);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                progressBar.setVisibility(View.GONE);
                                btn_continue.setVisibility(View.VISIBLE);
                                btn_continue.setEnabled(true);

                                //berpindah aktivity
                                Intent gotonextregister = new Intent(Register1Act.this,Register2Act.class);
                                startActivity(gotonextregister);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
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
