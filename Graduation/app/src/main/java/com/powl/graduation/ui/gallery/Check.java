package com.powl.graduation.ui.gallery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;

import com.powl.graduation.R;

public class Check extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Button cancel_button,ok_button;
    private RadioButton rad1,rad2,rad3,rad4,rad5;
    private Context context;
    private RadioGroup radGroup;
    private String s1="";
    private String s2="";
    private String s3="";
    private String s4="";
    private String s5="";
    private String answer="";


    public Check(Context context,String a,String b, String c, String d, String e) {
        super(context);
        this.context = context;
        this.s1=a;
        this.s2=b;
        this.s3=c;
        this.s4=d;
        this.s5=e;
    }

    private Check.checkListener checkListener;
    private Check.checkRadioListener checkRadioListener;

    public interface checkListener {
        void onOK();
        void onCancel();
    }

    public interface checkRadioListener {
        void onRad1();
        void onRad2();
        void onRad3();
        void onRad4();
        void onRad5();
    }

    public void setDialogListener(Check.checkListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setDialogRadioListener(Check.checkRadioListener checkRadioListener){
        this.checkRadioListener=checkRadioListener;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check);

        ok_button = findViewById(R.id.ok_button);
        ok_button.setOnClickListener(this);
        cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(this);

        rad1 = findViewById(R.id.rad1);
        rad1.setText(s1);
        rad2 = findViewById(R.id.rad2);
        rad2.setText(s2);
        rad3 = findViewById(R.id.rad3);
        rad3.setText(s3);
        rad4 = findViewById(R.id.rad4);
        rad4.setText(s4);
        rad5 = findViewById(R.id.rad5);
        rad5.setText(s5);

        radGroup=findViewById(R.id.radGroup);
        radGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cancel_button)
        {
            checkListener.onCancel();
            dismiss();
        }else if(v.getId()==R.id.ok_button){
            checkListener.onOK();
            dismiss();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radGroup, @IdRes int i){
        if(i==R.id.rad1){
            checkRadioListener.onRad1();
        }else if(i==R.id.rad2){
            checkRadioListener.onRad2();
        }else if(i==R.id.rad3){
            checkRadioListener.onRad3();
        }else if(i==R.id.rad4){
            checkRadioListener.onRad4();
        }else if(i==R.id.rad5){
            checkRadioListener.onRad5();
        }
    }

    public String getAnswer(){
        int id=radGroup.getCheckedRadioButtonId();
        RadioButton ans=(RadioButton)findViewById(id);
        answer=ans.getText().toString();
        return answer;
    }
}
