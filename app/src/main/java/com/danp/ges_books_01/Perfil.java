package com.danp.ges_books_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Perfil extends AppCompatActivity {

    private Button btnSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSession();
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    public void cerrarSession(){
        SharedPreferences.Editor sessionPref = this.getSharedPreferences("session", Context.MODE_PRIVATE).edit();
        sessionPref.clear();
        sessionPref.apply();
        finish();
    }
}