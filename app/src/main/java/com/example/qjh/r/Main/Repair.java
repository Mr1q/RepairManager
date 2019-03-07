package com.example.qjh.r.Main;

import android.app.DatePickerDialog;
import android.app.Person;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.qjh.r.Adapter.First_Adapter;
import com.example.qjh.r.Fragment.Fragment2;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.R;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Control.BaseActivity;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
    private Uri destinationUri;
    private Uri croppedUri;
    private  static   Intent intent;//接受列表传值
    public static User user= BmobUser.getCurrentUser(User.class);;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_in);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c1e38ff");
        intent=getIntent();
        inti();


    }

    public void inti() {
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
        textView2 = (TextView) findViewById(R.id.three_text);
        textView3 = (TextView) findViewById(R.id.two_text);
        textView = (TextView) findViewById(R.id.one_text);
        spinner = (Spinner) findViewById(R.id.select);

        spinner3 = (Spinner) findViewById(R.id.select3);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, spinnerItem);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, spinnerItem3);


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
                    textView2.setText("请选择项目");
                } else {
                    textView2.setText(adapter3.getItem(position));
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
                    textView3.setText("请选择维修地区");
                } else {
                    textView.setText(adapter.getItem(position));
                    textView3.setText(adapter.getItem(position));
//                    spinnerItem2.clear();
//                    spinnerItem2.add(adapter.getItem(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, spinnerItem2);
//        spinner2.setAdapter(adapter2);
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
        String msg = intent.getStringExtra("data");
        Msg.setText(msg);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speak:
                Voice voice = new Voice();
                voice.initSpeech(this);
            case R.id.put:
              ArrayList<Message_Bomb> message_bombs=new ArrayList<>();
                final Message_Bomb message_bomb=new Message_Bomb();
                message_bomb.setTitle(Msg.getText().toString());
                message_bomb.setMsg(write.getText().toString());
                message_bomb.setObj_Name(textView3.getText().toString());
                message_bomb.setName(username.getText().toString());
                message_bomb.setNumber(usernumber.getText().toString());
                message_bomb.setPhone(phone_number.getText().toString());
                message_bomb.setTime(gettime.getText().toString());
                message_bomb.setIdd(user.getObjectId());
                message_bomb.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null)
                        {
                            Message_Bomb message_bomb1=new Message_Bomb(Msg.getText().toString(),textView3.getText().toString(),write.getText().toString(),username.getText().toString(),usernumber.getText().toString(),phone_number.getText().toString(),gettime.getText().toString());
                            Fragment2.fruitList.add(message_bomb);
                            Fragment2.fruit_adapter.notifyDataSetChanged();
                            Toast.makeText(Repair.this,"提交成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });


                break;

            case R.id.total_Hand:
                Intent intent = new Intent(Repair.this, Hand_in_Msg.class);
                startActivity(intent);
                break;

            //获取时间
            case R.id.time:
                Gettime();
                break;

            case R.id.photo:
                CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
                        .setContext(this) //设置 context
                        .setContentView(R.layout.head_image) //设置布局文件
                        .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
                        .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
                        .setFouse(true)  //设置popupwindow 是否可以获取焦点
                        .setOutSideCancel(true) //设置点击外部取消
                        //  .setBackGroudAlpha(mActivity,0.7f) //是否设置背景色，原理为调节 alpha
                        .builder() //
                        .showAtLocation(R.layout.hand_in, Gravity.BOTTOM, 0, 0); //设置popupwindow居中显示
                popupWindow.setOnClickListener(R.id.pop_pic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photo = new Intent(Intent.ACTION_PICK, null);
                        photo.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(photo, 1);
                    }
                });
                popupWindow.setOnClickListener(R.id.pop_camera, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            String mTempPhotoPath;
                            Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mTempPhotoPath = getExternalCacheDir()+ "photo.png";
                            //封装照片路径
                        imageUri = FileProvider.getUriForFile(Repair.this,
                                    "com.example.qjh.r.fileprovider",
                                    new File(mTempPhotoPath));
                            //下面这句指定调用相机拍照后的照片存储的路径
                            intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intentToTakePhoto, 2);
                    }

                });
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: //从相册图片后返回的uri
                    //启动裁剪
                    try {
                        //该uri是上一个Activity返回的
                        imageUri = data.getData();
                        startUCrop();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        pane.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Toast.makeText(Repair.this,"132",Toast.LENGTH_SHORT).show();
                    try {
//                        imageUri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        pane.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                case UCrop.REQUEST_CROP: {
//                    croppedUri = UCrop.getOutput(data);
//                    try {
//                        if (croppedUri != null) {
//                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(croppedUri));
//                            pane.setImageBitmap(bit);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
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

    private void startUCrop() {
        //裁剪后保存到文件中

        Uri destionUri = Uri.fromFile(new File(getCacheDir(), "myCroppedImage.jpg"));
        UCrop uCrop = UCrop.of(imageUri, destionUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        options.setMaxBitmapSize(100);
        options.setShowCropFrame(false);
        options.setFreeStyleCropEnabled(true);  //是否能调整裁剪框
        uCrop.withOptions(options);
        uCrop.start(Repair.this);
    }


}
