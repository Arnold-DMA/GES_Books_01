package com.danp.ges_books_01.ui.session;

import static com.danp.ges_books_01.ui.home.HomeFragment.MESSAGE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danp.ges_books_01.R;
import com.danp.ges_books_01.databinding.FragmentLoginBinding;
import com.danp.ges_books_01.databinding.FragmentSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment {

    private FragmentSignupBinding binding;
    //private FirebaseAuth mAuth;
    private EditText etUsername, etEmail, etName, etPassword, etRepeatPassword;
    private Button btnRegistro;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        etUsername = binding.etUsername;
        etEmail = binding.etEmail;
        etName = binding.etName;
        etPassword = binding.etPassword;
        etRepeatPassword = binding.etRepeatPassword;
        btnRegistro = binding.btnRegistro;
        //mAuth = FirebaseAuth.getInstance();
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAlert(2);
                if(!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.getText().toString(),
                            etPassword.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        showHome(task.getResult().getUser().getEmail(), etName.getText().toString());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        showAlert(1);
                                    }
                                }
                            }
                    );
                }
                else {
                    showAlert(1);
                }
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    public void showAlert(int opcion){
        if (opcion == 1){
            Toast.makeText(getContext(), "Usuario registrado previamente", Toast.LENGTH_SHORT).show();
        }
        else if (opcion == 2){
            Toast.makeText(getContext(), "Primera parte", Toast.LENGTH_SHORT).show();
        }
    }

    public void showHome(String email, String name){
        Intent intent = new Intent();
        intent.putExtra("email", email);
        intent.putExtra(MESSAGE, name);
        //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    public Signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}