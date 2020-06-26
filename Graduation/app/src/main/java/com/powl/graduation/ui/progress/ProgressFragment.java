package com.powl.graduation.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.powl.graduation.R;
import com.skydoves.progressview.ProgressView;

public class ProgressFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_progress, container, false);

        ProgressView progressView1=root.findViewById(R.id.progressView1);
        ProgressView progressView2=root.findViewById(R.id.progressView2);


        return root;
    }
}