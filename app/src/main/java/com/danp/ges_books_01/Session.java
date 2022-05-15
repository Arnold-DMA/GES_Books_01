package com.danp.ges_books_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.danp.ges_books_01.ui.main.SessionFragment;

public class Session extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SessionFragment.newInstance())
                    .commitNow();
        }
    }
}