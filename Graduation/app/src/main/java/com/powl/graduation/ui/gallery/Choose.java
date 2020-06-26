package com.powl.graduation.ui.gallery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.powl.graduation.R;

public class Choose extends Dialog implements View.OnClickListener {

    private Button camera, album;
    private Context context;


    public Choose(Context context) {
        super(context);
        this.context = context;
    }

    private Choose.ChooseListener chooseListener;

    interface ChooseListener {
        void onCamera();
        void onAlbum();
    }

    public void setDialogListener(Choose.ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose);

        camera = findViewById(R.id.chooseCamera);
        camera.setOnClickListener(this);

        album = findViewById(R.id.chooseAlbum);
        album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.chooseCamera)
        {
            chooseListener.onCamera();
            dismiss();
        }
        else if(v.getId()==R.id.chooseAlbum)
        {
            chooseListener.onAlbum();
            dismiss();
        }
    }
}
