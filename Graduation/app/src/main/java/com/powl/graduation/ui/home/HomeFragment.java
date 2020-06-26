package com.powl.graduation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.powl.graduation.R;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.expandablelayout.ExpandableLayout;

public class HomeFragment extends Fragment {


    ExpandableLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4, expandableLayout5;
    ElasticButton button,button2,button3, button4, button5;
    ElasticButton semiButton, semiButton2, semiButton3, semiButton4, semiButton5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        expandableLayout1=root.findViewById(R.id.expandable);
        expandableLayout1.collapse();

        expandableLayout2=root.findViewById(R.id.expandable2);
        expandableLayout2.collapse();

        expandableLayout3=root.findViewById(R.id.expandable3);
        expandableLayout3.collapse();

        expandableLayout4=root.findViewById(R.id.expandable4);
        expandableLayout4.collapse();

        expandableLayout5=root.findViewById(R.id.expandable5);
        expandableLayout5.collapse();

        button = root.findViewById(R.id.ex1);
        button2 = root.findViewById(R.id.ex2);
        button3 = root.findViewById(R.id.ex3);
        button4 = root.findViewById(R.id.ex4);
        button5 = root.findViewById(R.id.ex5);

        semiButton = root.findViewById(R.id.showBox);
        semiButton2 = root.findViewById(R.id.showBox2);
        semiButton3 = root.findViewById(R.id.showBox3);
        semiButton4 = root.findViewById(R.id.showBox4);
        semiButton5 = root.findViewById(R.id.showBox5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout1.expand();
            }
        });
        semiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout1.collapse();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout2.expand();
            }
        });
        semiButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout2.collapse();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout3.expand();
            }
        });
        semiButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout3.collapse();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout4.expand();
            }
        });
        semiButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout4.collapse();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout5.expand();
            }
        });
        semiButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout5.collapse();
            }
        });
        return root;
    }
}