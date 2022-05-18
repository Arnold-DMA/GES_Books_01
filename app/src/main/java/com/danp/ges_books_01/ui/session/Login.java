package com.danp.ges_books_01.ui.session;

import static com.danp.ges_books_01.ui.sugerencias.SugerenciasFragment.MESSAGE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danp.ges_books_01.R;
import com.danp.ges_books_01.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    private FragmentLoginBinding binding;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin, btnGoogle;
    private static final int REQUEST_CODE_GOOGLE = 102;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String nombre;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View root = binding.getRoot();

        etEmail = binding.etUsername;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;
        btnGoogle = binding.btnGoogle;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.getText().toString(),
                            etPassword.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information


                                        db.collection("users").document(task.getResult().getUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                nombre = documentSnapshot.getString("name");
                                                showHome(task.getResult().getUser().getEmail(), nombre);
                                            }
                                        });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        showAlert(1);
                                    }
                                }
                            }
                    );
                } else {
                    showAlert(2);
                }
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(getActivity(), googleConf);
                startActivityForResult(googleClient.getSignInIntent(), REQUEST_CODE_GOOGLE);
            }
        });

        return root;
    }

    public void showAlert(int opcion){
        if (opcion == 1){
            Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
        }
        else if (opcion == 2){
            Toast.makeText(getContext(), "Debe llenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showHome(String email, String nombre){
        Intent intent = new Intent();
        intent.putExtra("email", email);
        intent.putExtra(MESSAGE, nombre);
        //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_GOOGLE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();

            if (account != null){
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                Toast.makeText(getContext(), "Google check", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    HashMap<String, String> datos = new HashMap<>();
                                    datos.put("email", account.getEmail());
                                    datos.put("name", account.getDisplayName());
                                    db.collection("users").document(account.getEmail()).set(datos);
                                    showHome(account.getEmail(), account.getDisplayName());
                                } else {
                                    // If sign in fails, display a message to the user.
                                    showAlert(1);
                                }
                            }
                        }
                );
            }
        }
    }


    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}