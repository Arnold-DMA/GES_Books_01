package com.danp.ges_books_01.ui.sugerencias;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.danp.ges_books_01.Perfil;
import com.danp.ges_books_01.Session;
import com.danp.ges_books_01.databinding.FragmentSugerenciasBinding;
import com.danp.ges_books_01.databinding.FragmentLoginBinding;
import com.danp.ges_books_01.databinding.FragmentSugerenciasBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SugerenciasFragment extends Fragment {

    private FragmentSugerenciasBinding binding;
    private TextView session;
    private Button btnLogin;
    private static final int REQUEST_CODE = 101;
    public static final String MESSAGE = "nombre";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SugerenciasViewModel homeViewModel =
                new ViewModelProvider(this).get(SugerenciasViewModel.class);

        binding = FragmentSugerenciasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        session = binding.tvSesion;

        btnLogin = binding.btnLogin;
        boolean activo = comprobarSession();
        if (activo){
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Perfil.class);
                    startActivity(intent);
                }
            });
        }
        else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(getContext(), Session.class), REQUEST_CODE);
                /*Intent intent = new Intent (container.getContext(), Session.class);
                startActivityForResult(intent, 0);*/
                }
            });
        }



        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                activarSession(data.getStringExtra(MESSAGE), data.getStringExtra("email"));
                //session.setText(data.getStringExtra(MESSAGE));
            } else if (resultCode == RESULT_CANCELED){
                session.setText("Cancelado");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void activarSession(String nombre, String email){
        SharedPreferences.Editor sessionPref = getContext().getSharedPreferences("session", Context.MODE_PRIVATE).edit();
        sessionPref.putString("email", email);
        sessionPref.putString("nombre", nombre);
        sessionPref.apply();
        session.setText(nombre);
    }

    public boolean comprobarSession(){
        SharedPreferences sessionActiva = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        String nombreActivo = sessionActiva.getString("nombre", "");
        if(!nombreActivo.equals("")){
            session.setText(nombreActivo);
            return true;
        }
        else{
            session.setText("Sin sesión activa");
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Mensaje", "Mensaje: create");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Mensaje", "Mensaje: start");
        boolean activo = comprobarSession();
        if (activo){
            btnLogin.setText("PERFIL");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Perfil.class);
                    startActivity(intent);
                }
            });
        }
        else {
            btnLogin.setText("INICIAR SESIÓN");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(getContext(), Session.class), REQUEST_CODE);
                /*Intent intent = new Intent (container.getContext(), Session.class);
                startActivityForResult(intent, 0);*/
                }
            });
        }
    }
}