package com.danp.ges_books_01.ui.buscar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.danp.ges_books_01.databinding.FragmentBuscarBinding;

public class BuscarFragment extends Fragment {

    private FragmentBuscarBinding binding;
    //private TextView texto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BuscarViewModel dashboardViewModel =
                new ViewModelProvider(this).get(BuscarViewModel.class);

        binding = FragmentBuscarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        textView.setText("Hola");
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //texto = (TextView) container.findViewById(R.id.text_dashboard);
        //texto.setText(dashboardViewModel.getText().toString());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}