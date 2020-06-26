package com.powl.graduation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.powl.graduation.SQLite.Contact;
import com.skydoves.elasticviews.ElasticButton;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Enroll extends AppCompatActivity {

    String ip = "192.168.1.179";
    String port = "8888";
    MyClientTask a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        Intent intent = getIntent();
        String enroll = intent.getExtras().getString("answer");
        Log.i("vvvv", enroll);

        a = new MyClientTask(ip, Integer.parseInt(port.toString()), enroll);
        a.execute();


        ElasticButton elasticButton = (ElasticButton) findViewById(R.id.eb);
        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Basic.class);

                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (a.getStatus() == AsyncTask.Status.RUNNING) {
                a.cancel(true);
            } else {
            }
        } catch (Exception e) {
        }

    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String sendEnroll;

        //constructor
        public MyClientTask(String addr, int port, String enroll) {
            dstAddress = addr;
            dstPort = port;
            sendEnroll = enroll;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            sendCheck(sendEnroll, ip, Integer.parseInt(port.toString()));

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }


        public void sendCheck(String send, String dstAddress, int dstPort) {
            Socket socket3 = null;

            try {
                socket3 = new Socket(dstAddress, dstPort+2);
                //송신
                OutputStream out = socket3.getOutputStream();

                out.write(send.getBytes());

                Log.i("send", "송신 완료");

            } catch (SocketException s) {
                s.printStackTrace();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (socket3 != null) {
                    try {
                        socket3.close();
                        Log.i("socket", "소켓종료");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
