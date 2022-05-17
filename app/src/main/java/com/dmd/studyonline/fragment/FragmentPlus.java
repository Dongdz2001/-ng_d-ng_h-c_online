package com.dmd.studyonline.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dmd.studyonline.MainActivity;
import com.dmd.studyonline.R;
import com.dmd.studyonline.model.ClassLink;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class FragmentPlus extends Fragment {
    private EditText edtTitle;
    private EditText edtLink;
    private FloatingActionButton fabSubmitButton;

    public FragmentPlus() {
        super(R.layout.fragment_plus);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = Objects.requireNonNull(inflater).inflate(R.layout.fragment_plus, container, false);
        edtTitle = view.findViewById(R.id.edtTitle);
        edtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.scrollReyclerView();
                }
            }
        });
        edtLink = view.findViewById(R.id.edtLink);
        edtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.scrollReyclerView();
                }
            }
        });
        fabSubmitButton = (FloatingActionButton) view.findViewById(R.id.fab);
        fabSubmitButton.bringToFront();
        fabSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String link = edtLink.getText().toString();
                ClassLink classLink = new ClassLink(title, link);
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.addDataSubmit(classLink);
                }
            }
        });
        return view;
    }
}