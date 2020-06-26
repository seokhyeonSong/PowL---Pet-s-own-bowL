package com.powl.graduation.error;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.powl.graduation.R;


public class Alert extends Dialog implements View.OnClickListener {
    private TextView textView;
    private Button button;
    private Context context;
    String string;

    public Alert(Context context,String string) {
        super(context);
        this.context = context;
        this.string=string;
    }

    private Alert.ALERTListener alertListener;

    interface ALERTListener {
        void onButtonClicked();
    }

    public void setDialogListener(Alert.ALERTListener alertListener) {
        this.alertListener = alertListener;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alert);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textChange);
        textView.setText(string);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
        {
            alertListener.onButtonClicked();
            dismiss();
        }
    }
}
