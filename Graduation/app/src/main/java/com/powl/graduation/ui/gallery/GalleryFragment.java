package com.powl.graduation.ui.gallery;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.powl.graduation.Basic;
import com.powl.graduation.Enroll;
import com.powl.graduation.R;
import com.powl.graduation.SQLite.Contact;
import com.powl.graduation.SQLite.DataAdapter;
import com.powl.graduation.SQLite.DatabaseHandler;
import com.powl.graduation.util.ImageResizeUtils;
import com.skydoves.elasticviews.ElasticButton;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class GalleryFragment extends Fragment {

    Socket socket = null;
    String ip = "192.168.1.179";
    String port = "8888";
    String message = "socket";
    String answer;

    private EditText fname;
    private ImageView pic;
    private DatabaseHandler db;
    private String f_name;
    private ListView lv;
    private DataAdapter data;
    private Contact dataModel;
    private Bitmap bp;
    private byte[] photo;
    private ElasticButton sv, display;
    private ScrollView scrollView;

    private Boolean isPermission = true;
    private Boolean isCamera = false;
    private File tempFile;

    private File imageFile;

    private MyClientTask myClientTask;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //Instantiate database handler
        db = new DatabaseHandler(getContext());

        scrollView = root.findViewById(R.id.scrollView);
        lv = root.findViewById(R.id.list1);
        pic = root.findViewById(R.id.pic);
        fname = root.findViewById(R.id.txt1);
        sv = root.findViewById(R.id.save);
        display = root.findViewById(R.id.display);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tedPermission();

                Choose choose = new Choose(getContext());
                choose.setDialogListener(new Choose.ChooseListener() {
                    @Override
                    public void onCamera() {
                        if (isPermission)
                            takePhoto();
                    }

                    public void onAlbum() {
                        if (isPermission)
                            selectImage();
                    }
                });
                choose.show();
            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "이름을 지어주세요~", Toast.LENGTH_LONG).show();
                } else {
                    addContact();
                    fname.setText("");
                }
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRecords();
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (myClientTask.getStatus() == AsyncTask.Status.RUNNING) {
                myClientTask.cancel(true);
            } else {
            }
        } catch (Exception e) {
        }

    }
    /**
     * 카메라에서 이미지 가져오기
     */
    private void takePhoto() {

        isCamera = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(getContext(), "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        "com.powl.graduation.fileprovider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 1);

            } else {

                Uri photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 1);

            }
        }
    }


    /**
     * 폴더 및 파일 만들기
     */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "photo_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + imageFile.getAbsolutePath());

        return imageFile;
    }

    private Uri cropImage(Uri photoUri) {

        Log.d(TAG, "tempFile : " + tempFile);

        /**
         *  갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
         */
        if (tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(getContext(), "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                e.printStackTrace();
            }
        }

        //크롭 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);

        return savingUri;
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    public void selectImage() {

        isCamera = true;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri photoUri = Uri.fromFile(tempFile);
                    Log.d(TAG, "takePhoto photoUri : " + photoUri);

                    cropImage(photoUri);
                    bp = setImage();
                    pic.setImageBitmap(bp);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri choosenImage = data.getData();

                    if (choosenImage != null) {
                        cropImage(choosenImage);
                        bp = decodeUri(choosenImage, 400);
                        pic.setImageBitmap(bp);
                    }
                }
        }
    }


    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }


    // function to get values from the Edittext and image
    private void getValues() {
        f_name = fname.getText().toString();
        photo = profileImage(bp);
    }

    //Insert data to the database
    private void addContact() {
        getValues();

        db.addContacts(new Contact(f_name, photo));
        Toast.makeText(getContext().getApplicationContext(), "사진 저장 완료", Toast.LENGTH_LONG).show();
        Log.i("contact", f_name + "이 저장되었습니다");
        pic.setImageResource(R.drawable.camera);
    }

    //Retrieve data from the database and set to the list view
    private void ShowRecords() {

        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data = new DataAdapter(getContext(), contacts);

        lv.setAdapter(data);

        if (lv.getAdapter() == null) return;
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < lv.getAdapter().getCount(); i++) {
            View listItem = lv.getAdapter().getView(i, null, lv);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        Log.i("lv.getAdapter", lv.getAdapter().getCount() + "개");
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getAdapter().getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);


                Confirm Confirm = new Confirm(getContext());
                Confirm.setDialogListener(new Confirm.confirmListener() {
                    @Override
                    public void onYes() {
                        myClientTask = new MyClientTask(ip, Integer.parseInt(port.toString()), message, dataModel);
                        Log.i("socket", "생성");
                        myClientTask.execute();
                        Log.i("socket", "실행");
                    }

                    @Override
                    public void onNo() {

                    }
                });
                Confirm.show();
                return false;
            }
        });
    }

    /**
     * tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
     */
    private Bitmap setImage() {


        ImageResizeUtils.resizeFile(tempFile, tempFile, 1280, isCamera);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());


        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

        return originalBm;
    }

    /**
     * 권한 설정
     */
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }


    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String myMessage = "";
        Contact image;
        String[] survey = new String[]{"1", "2", "3", "4", "5"};

        //constructor
        public MyClientTask(String addr, int port, String message) {
            dstAddress = addr;
            dstPort = port;
            myMessage = message;
        }

        //constructor
        public MyClientTask(String addr, int port, String message, Contact contact) {
            dstAddress = addr;
            dstPort = port;
            myMessage = message;
            image = contact;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            sendPhoto();
            recMessage();

            return null;
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();

            getActivity().finish();
        }

        @Override
        protected void onPostExecute(Void result) {

            final Check check = new Check(getContext(), survey[0], survey[1], survey[2], survey[3], survey[4]);


            check.setDialogListener(new Check.checkListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onOK() {
                    answer = check.getAnswer();
                    Log.i("@@@@@@answer", answer);

                    Intent intent = new Intent(getActivity(), Enroll.class);
                    intent.putExtra("answer", answer);
                    startActivity(intent);
                }


            });
            check.setDialogRadioListener(new Check.checkRadioListener() {
                @Override
                public void onRad1() {
                    Log.i("answer", "1");
                }

                @Override
                public void onRad2() {
                    Log.i("answer", "2");
                }

                @Override
                public void onRad3() {
                    Log.i("answer", "3");
                }

                @Override
                public void onRad4() {
                    Log.i("answer", "4");
                }

                @Override
                public void onRad5() {
                    Log.i("answer", "5");
                }
            });
            check.show();


            //sendCheck(answer);

            super.onPostExecute(result);


        }


        public void sendPhoto() {
            Socket socket = null;
            Log.i("socket", "소켓 생성 1");

            try {
                socket = new Socket(dstAddress, dstPort);
                //송신


                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                /*
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    Log.i("send", "수신 중");
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

                String[] survey2=response.split("/");
                for(int i=0;i<5;i++){
                    survey[i]=survey2[i];
                    Log.i("send mess", survey[i]);
                }
*/
                DataInputStream dis = new DataInputStream(new FileInputStream(imageFile));
                Log.i("send", "파일보내기 간다");
                DataOutputStream dos = new DataOutputStream(out);
                Log.i("send", "파일보내기 성공");

                byte[] buf = new byte[1024];
                while (dis.read(buf) > 0) {
                    dos.write(buf);
                    dos.flush();
                }
                Log.i("send", "파일보내기 완료");

                dos.close();

                Log.i("send", "송신 완료");


            } catch (SocketException s) {
                s.printStackTrace();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                        Log.i("socket", "소켓종료");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }


        public void recMessage() {
            Socket socket2 = null;

            Log.i("socket", "소켓 생성 2");

            try {
                socket2 = new Socket(dstAddress, dstPort + 1);
                //송신
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;
                InputStream in = socket2.getInputStream();
                /*
                 * notice:
                 * inputStream.read() will block if no data return
                 */
                while ((bytesRead = in.read(buffer)) != -1) {
                    Log.i("send", "수신 중");
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

                String[] survey2 = response.split("/");
                for (int i = 0; i < 5; i++) {
                    survey[i] = survey2[i];
                    Log.i("send mess", survey[i]);
                }

            } catch (SocketException s) {
                s.printStackTrace();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket2 != null) {
                    try {
                        socket2.close();
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