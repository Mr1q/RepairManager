package com.example.qjh.r.Main;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qjh.r.Adapter.First_Adapter;
import com.example.qjh.r.Fragment.Fragment2;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.R;
import com.example.qjh.r.Receiver.VPM;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Control.BaseActivity;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Repair extends BaseActivity implements View.OnClickListener {
    final String[] spinnerItem = {"请选择", "花江", "金鸡岭", "西区"};
    final String[] spinnerItem3 = {"请选择", "电", "水", "五金"};
    private List<String> spinnerItem2 = new ArrayList<String>();
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private Spinner spinner;//选择框
    private Spinner spinner2;//选择框
    private Spinner spinner3;//选择框
    private Button put;
    private ImageButton speak;
    private TextView textnumber;
    private EditText write;
    private Button gettime;//获取时间
    private EditText usernumber;//学号
    private EditText username; //姓名
    private EditText phone_number; //电话
    private Animation animation;
    private FrameLayout frameLayout;
    private TextView Msg;
    private Button photo; //拍照
    private ImageView pane;
    // 照片所在的Uri地址
    private Uri imageUri;
    private static Intent intent;//接受列表传值
    public static User user = BmobUser.getCurrentUser(User.class);
    ;
    private ImageButton back_msg;
    private EditText Location;
    public ArrayAdapter<String> adapter;
    public ArrayAdapter<String> adapter3;

    private Boolean Modify;//是否修改
    private String Id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_in);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c1e38ff");
        intent = getIntent();
        inti();


    }

    public void inti() {
        Location = (EditText) findViewById(R.id.Location);
        back_msg = (ImageButton) findViewById(R.id.back_msg);
        back_msg.setOnClickListener(this);
        pane = (ImageView) findViewById(R.id.pane);
        photo = (Button) findViewById(R.id.photo);
        photo.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username);
        phone_number = (EditText) findViewById(R.id.phone_number);
        Msg = (TextView) findViewById(R.id.msg);
        frameLayout = (FrameLayout) findViewById(R.id.total_Hand);
        frameLayout.setOnClickListener(this);
        usernumber = (EditText) findViewById(R.id.usernumber);
        gettime = (Button) findViewById(R.id.time);
        gettime.setOnClickListener(this);
        put = (Button) findViewById(R.id.put);
        put.setOnClickListener(this);
        write = (EditText) findViewById(R.id.write);
        textnumber = (TextView) findViewById(R.id.number_Text);

        put.setOnClickListener(this);
        speak = (ImageButton) findViewById(R.id.speak);
        speak.setOnClickListener(this);
        textView3 = (TextView) findViewById(R.id.three_text);
        textView2 = (TextView) findViewById(R.id.two_text);
        textView = (TextView) findViewById(R.id.one_text);
        spinner = (Spinner) findViewById(R.id.select);

        spinner3 = (Spinner) findViewById(R.id.select3);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, spinnerItem);
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, spinnerItem3);


        write.setText(intent.getStringExtra("4"));
        username.setText(intent.getStringExtra("2"));
        usernumber.setText(intent.getStringExtra("1"));
        gettime.setText(intent.getStringExtra("3"));
        Msg.setText(intent.getStringExtra("1"));
        spinner3.setAdapter(adapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter3.getItem(position).equals("请选择")) {
                    textView3.setText("请选择项目");
                } else {
                    textView3.setText(adapter3.getItem(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).equals("请选择")) {
                    textView.setText("请选择维修地区");
                    textView2.setText("请选择维修地区");
                } else {
                    textView.setText(adapter.getItem(position));
                    textView2.setText(adapter.getItem(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        animation = AnimationUtils.loadAnimation(this, R.anim.spinner);
        spinner.setOnTouchListener(new Spinner.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(animation);
                return false;
            }
        });
        change(); //文字变化监听
        //接受活动传回来的数据
        Receive();

    }

    private void Receive() {
        Intent intent = getIntent();
        if (intent != null) {
            Msg.setText(intent.getStringExtra("0"));
            for (int i = 0; i < spinnerItem.length; i++) {

                if (spinnerItem[i].equals(intent.getStringExtra("1"))) {
                    spinner.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < spinnerItem3.length; i++) {
                if (spinnerItem3[i].equals(intent.getStringExtra("4"))) {
                    spinner3.setSelection(i);
                    break;
                }
            }
            Location.setText(intent.getStringExtra("3"));
            write.setText(intent.getStringExtra("5"));
            usernumber.setText(intent.getStringExtra("6"));
            username.setText(intent.getStringExtra("7"));
            phone_number.setText(intent.getStringExtra("8"));
            gettime.setText(intent.getStringExtra("9"));
            Glide.with(this).load(intent.getStringExtra("10")).into(pane);
            Modify = intent.getBooleanExtra("Where", false);
            Id=intent.getStringExtra("id");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speak:
                Voice voice = new Voice();
                voice.initSpeech(this);
                break;
            case R.id.put:
                // ArrayList<Message_Bomb> message_bombs = new ArrayList<>();
                if (Modify == false) {
                    Hand_in();
                } else {
                    Modifys();
                }
                Intent intent1 = new Intent(this, VPM.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this).
                        setContentTitle("First")
                        .setContentText("第一条通知")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                notificationManager.notify(1, notification);
                break;
            case R.id.total_Hand:
                final Intent intent = new Intent(Repair.this, Hand_in_Msg.class);
                intent.putExtra("datas", Msg.getText());
                startActivityForResult(intent, 10);
                break;

            //获取时间
            case R.id.time:
                Gettime();
                break;

            case R.id.photo:
                final CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
                        .setContext(this) //设置 context
                        .setContentView(R.layout.head_image) //设置布局文件
                        .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
                        .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
                        .setFouse(true)  //设置popupwindow 是否可以获取焦点
                        .setOutSideCancel(true) //设置点击外部取消
                        .setBackGroudAlpha(Repair.this, 0.7f) //是否设置背景色，原理为调节 apha
                        .builder()
                        .showAtLocation(R.layout.hand_in, Gravity.BOTTOM, 0, 0); //设置popupwindow居中显示
                //添加点击事件
                popupWindow.setOnClickListener(R.id.pop_pic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent photo = new Intent(Intent.ACTION_PICK);
//                        photo.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // photo.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        Intent photo = new Intent();
                        if (Build.VERSION.SDK_INT < 19) {
                            photo.setAction(Intent.ACTION_GET_CONTENT);
                            photo.setType("image/*");
                        } else {
                            photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            photo.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        }


                        startActivityForResult(photo, 1);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setOnClickListener(R.id.pop_camera, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File image = new File(getExternalCacheDir(), "out_image.jpg");
                        try {
                            if (image.exists()) {
                                image.delete();
                            }
                            image.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (Build.VERSION.SDK_INT >= 24) {
                            imageUri = FileProvider.getUriForFile(Repair.this, "text", image);
                        } else {
                            imageUri = Uri.fromFile(image);
                        }
                        Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent2, 2);
                        popupWindow.dismiss();
                    }
                });


                popupWindow.setOnClickListener(R.id.pop_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;

            case R.id.back_msg:
                finish();
                break;

        }
    }

    private void Modifys() {
        if (imageUri == null) {
            final Message_Bomb message_bomb = new Message_Bomb();
            final ProgressDialog proess=new ProgressDialog(this);
            proess.setTitle("提示");
            proess.setMessage("上传中...");
            proess.setCancelable(false);
            proess.show();
           message_bomb.setValue("title",Msg.getText().toString());
            message_bomb.setValue("Msg",write.getText().toString());
            message_bomb.setValue("area1",textView.getText().toString());
            message_bomb.setValue("area2",textView2.getText().toString());
            message_bomb.setValue("obj_Name",textView3.getText().toString());
            message_bomb.setValue("Location",Location.getText().toString());
            message_bomb.setValue("name",username.getText().toString());
            message_bomb.setValue("number",usernumber.getText().toString());
            message_bomb.setValue("phone",phone_number.getText().toString());
            message_bomb.setValue("time",gettime.getText().toString());
            message_bomb.update(Id,new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        proess.dismiss();
                        Toast.makeText(Repair.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        } else {
            final BmobFile bmobFile = new BmobFile(uriToFile(imageUri, this));
            final ProgressDialog proess=new ProgressDialog(this);
            proess.setTitle("提示");
            proess.setMessage("上传中...");
            proess.setCancelable(false);
            proess.show();
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        final Message_Bomb message_bomb = new Message_Bomb();
                        message_bomb.setValue("picture",bmobFile);
                        message_bomb.setValue("title",Msg.getText().toString());
                        message_bomb.setValue("Msg",write.getText().toString());
                        message_bomb.setValue("area1",textView.getText().toString());
                        message_bomb.setValue("area2",textView2.getText().toString());
                        message_bomb.setValue("obj_Name",textView3.getText().toString());
                        message_bomb.setValue("Location",Location.getText().toString());
                        message_bomb.setValue("name",username.getText().toString());
                        message_bomb.setValue("number",usernumber.getText().toString());
                        message_bomb.setValue("phone",phone_number.getText().toString());
                        message_bomb.setValue("time",gettime.getText().toString());
                        message_bomb.update(Id,new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    proess.dismiss();
                                    Toast.makeText(Repair.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }

    }

    private void Hand_in() {
        if (imageUri == null) {
            Toast.makeText(Repair.this, "未上传图片", Toast.LENGTH_SHORT).show();
        } else {
            final BmobFile bmobFile = new BmobFile(uriToFile(imageUri, this));
            final ProgressDialog proess=new ProgressDialog(this);
            proess.setTitle("提示");
            proess.setMessage("上传中...");
            proess.setCancelable(false);
            proess.show();
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {

                        final Message_Bomb message_bomb = new Message_Bomb();
                        message_bomb.setPicture(bmobFile);
                        message_bomb.setTitle(Msg.getText().toString());
                        message_bomb.setMsg(write.getText().toString());
                        message_bomb.setArea1(textView.getText().toString());
                        message_bomb.setArea2(textView2.getText().toString());
                        message_bomb.setObj_Name(textView3.getText().toString());
                        message_bomb.setLocation(Location.getText().toString());
                        message_bomb.setName(username.getText().toString());
                        message_bomb.setNumber(usernumber.getText().toString());
                        message_bomb.setPhone(phone_number.getText().toString());
                        message_bomb.setTime(gettime.getText().toString());
                        message_bomb.setIdd(user.getObjectId());
                        message_bomb.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Message_Bomb message_bomb1 = new Message_Bomb(Msg.getText().toString(), write.getText().toString(), Location.getText().toString(), usernumber.getText().toString(), username.getText().toString(), gettime.getText().toString());
                                    Fragment2.message_bombs_list.add(message_bomb);
                                    Fragment2.fruit_adapter.notifyDataSetChanged();
                                    Toast.makeText(Repair.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    proess.dismiss();
                                    finish();
                                }
                            }
                        });

                    }
                }
            });
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    try {
                        //该uri是上一个Activity返回的
                        imageUri = data.getData();
                        Glide.with(this).load(imageUri).into(pane);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        pane.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    Msg.setText(data.getStringExtra("data"));
                    break;
            }
        }

    }

    public void Gettime() {
        final StringBuilder stringBuilder = new StringBuilder();
        final Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(Repair.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                stringBuilder.append(hourOfDay + ":" + minute);
                gettime.setText(stringBuilder);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show();

        new DatePickerDialog(Repair.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                Toast.makeText(Repair.this, text, Toast.LENGTH_SHORT).show();

                stringBuilder.append(year + "-" + (month + 1) + "-" + dayOfMonth + " ");
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    public void change() {
        usernumber.addTextChangedListener(new TextWatcher() {
            private int editstar;
            private int editend;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                editstar = usernumber.getSelectionStart();
                editend = usernumber.getSelectionEnd();
                if (temp.length() > 10) {
                    Toast.makeText(Repair.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    usernumber.setText(s);
                    usernumber.setSelection(tempSelection);
                }
            }
        });


        write.addTextChangedListener(new TextWatcher() {
            private int editstar;
            private int editend;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editstar = write.getSelectionStart();
                editend = write.getSelectionEnd();
                textnumber.setText(String.valueOf(200 - temp.length()));
                if (temp.length() > 200) {
                    Toast.makeText(Repair.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    write.setText(s);
                    write.setSelection(tempSelection);
                }

            }
        });


        username.addTextChangedListener(new TextWatcher() {
            private int editstar;
            private int editend;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editstar = username.getSelectionStart();
                editend = username.getSelectionEnd();
                if (temp.length() > 5) {
                    Toast.makeText(Repair.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    username.setText(s);
                    username.setSelection(tempSelection);
                }
            }
        });

        phone_number.addTextChangedListener(new TextWatcher() {
            private int editstar;
            private int editend;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editstar = phone_number.getSelectionStart();
                editend = phone_number.getSelectionEnd();
                if (temp.length() > 12) {
                    Toast.makeText(Repair.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    phone_number.setText(s);
                    phone_number.setSelection(tempSelection);
                }
            }
        });
    }

    public static File uriToFile(Uri uri, Context context) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            return GetFIlePath_From_Uri.GetFilePath_Min_sdkversion(uri, context);
        } else if ("content".equals(uri.getScheme())) {
            return new File(GetFIlePath_From_Uri.getFilePathFromURI(context, uri));//新的方式
        }
        return null;
    }


}
