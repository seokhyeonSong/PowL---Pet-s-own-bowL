package com.powl.graduation.ui.gallery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.powl.graduation.R;

public class Confirm extends Dialog implements View.OnClickListener {

    private Button yes, no;
    private Context context;


    public Confirm(Context context) {
        super(context);
        this.context = context;
    }

    private Confirm.confirmListener confirmListener;

    public interface confirmListener {
        void onYes();
        void onNo();
    }

    public void setDialogListener(Confirm.confirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_confirm);

        yes = findViewById(R.id.yes);
        yes.setOnClickListener(this);

        no = findViewById(R.id.no);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.yes)
        {
            confirmListener.onYes();
            dismiss();
        }
        else if(v.getId()==R.id.no)
        {
            confirmListener.onNo();
            dismiss();
        }
    }
}
