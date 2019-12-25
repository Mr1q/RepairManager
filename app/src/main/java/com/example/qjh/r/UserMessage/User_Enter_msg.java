package com.example.qjh.r.UserMessage;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qjh.r.Login.Login;
import com.example.qjh.r.Login.User;
import com.example.qjh.r.Main.CustomPopupWindow;
import com.example.qjh.r.Main.GetFIlePath_From_Uri;
import com.example.qjh.r.R;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import Control.ActivityCollector;
import Control.BaseActivity;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class User_Enter_msg extends BaseActivity implements View.OnClickListener {
    private Button save;
    private Button modify;
    private LinearLayout over;
    private Button riqi;
    private EditText username2;
    private EditText mail_1;
    private EditText address;
    private RadioGroup radioGroup;
    private RadioButton male;
    private RadioButton female;
    private ImageView Return_Msg;
    private Uri imageUri;
    private CircleImageView message_image;
    private User user = BmobUser.getCurrentUser(User.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_message);
        init();
        Init_Meg();

    }

    public void init() {
        message_image = (CircleImageView) findViewById(R.id.message_image);
        message_image.setOnClickListener(this);
        message_image.setEnabled(false);
        Return_Msg = (ImageView) findViewById(R.id.Return_Msg);
        Return_Msg.setOnClickListener(this);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.femle);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        username2 = (EditText) findViewById(R.id.username2);
        mail_1 = (EditText) findViewById(R.id.mail_1);
        address = (EditText) findViewById(R.id.address);
        riqi = (Button) findViewById(R.id.riqi);
        riqi.setOnClickListener(this);
        over = (LinearLayout) findViewById(R.id.over);
        over.setOnClickListener(this);
        save = (Button) findViewById(R.id.save);
        modify = (Button) findViewById(R.id.modify);
        modify.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    public void Init_Meg() {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", user.getObjectId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    for (User users : list) {
                        username2.setText(users.getName());
                        address.setText(users.getAddress());
                        riqi.setText(users.getRiqi());
                        mail_1.setText(users.getNumber());
                        Glide.with(User_Enter_msg.this).load(users.getImage().getFileUrl()).into(message_image);
                    }
                }
            }
        });


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
                        Glide.with(this).load(imageUri).into(message_image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        message_image.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify:
                message_image.setEnabled(true);
                save.setVisibility(View.VISIBLE);
                modify.setVisibility(View.INVISIBLE);
                username2.setEnabled(true);
                username2.setSelection(username2.getText().length());
                mail_1.setEnabled(true);
                mail_1.setSelection(mail_1.getText().length());
                address.setEnabled(true);
                address.setSelection(address.getText().length());
                male.setEnabled(true);
                male.setVisibility(View.VISIBLE);
                female.setVisibility(View.VISIBLE);
                female.setEnabled(true);
                riqi.setEnabled(true);
                break;
            case R.id.save:
                message_image.setEnabled(false);
                save.setVisibility(View.INVISIBLE);
                modify.setVisibility(View.VISIBLE);
                username2.setEnabled(false);
                mail_1.setEnabled(false);
                male.setEnabled(false);
                female.setEnabled(false);
                address.setEnabled(false);
                riqi.setEnabled(false);
                Save();
                break;
            case R.id.over:
                BmobUser.logOut();
                ActivityCollector.Finish_All();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);

                break;
            case R.id.riqi:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                        Toast.makeText(User_Enter_msg.this, text, Toast.LENGTH_SHORT).show();
                        riqi.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.Return_Msg:
                finish();
                break;
            case R.id.message_image:
                final CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
                        .setContext(this) //设置 context
                        .setContentView(R.layout.head_image) //设置布局文件
                        .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
                        .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
                        .setFouse(true)  //设置popupwindow 是否可以获取焦点
                        .setOutSideCancel(true) //设置点击外部取消
                        .setBackGroudAlpha(User_Enter_msg.this, 0.7f) //是否设置背景色，原理为调节 apha
                        .builder()
                        .showAtLocation(R.layout.hand_in, Gravity.BOTTOM, 0, 0); //设置popupwindow居中显示
                //添加点击事件
                popupWindow.setOnClickListener(R.id.pop_pic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                            imageUri = FileProvider.getUriForFile(User_Enter_msg.this, "text", image);
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


            default:
        }
    }

    private void Save() {
        if (imageUri != null) {
            final BmobFile bmobFile = new BmobFile(uriToFile(imageUri, this));
            final ProgressDialog proess = new ProgressDialog(this);
            proess.setTitle("提示");
            proess.setMessage("上传中...");
            proess.setCancelable(false);
            proess.show();
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        user.setImage(bmobFile);
                        user.setName(username2.getText().toString());
                        user.setAddress(address.getText().toString());
                        user.setRiqi(riqi.getText().toString());
                        user.setNumber(mail_1.getText().toString());
                        if (radioGroup.getCheckedRadioButtonId() == R.id.male) {
                            user.setSex("男");
                            female.setVisibility(View.INVISIBLE);
                        } else if (radioGroup.getCheckedRadioButtonId() == R.id.femle) {
                            user.setSex("女");
                            male.setVisibility(View.INVISIBLE);
                        }
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(User_Enter_msg.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    proess.dismiss();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            final ProgressDialog proess = new ProgressDialog(this);
            proess.setTitle("提示");
            proess.setMessage("上传中...");
            proess.setCancelable(false);
            proess.show();
            user.setName(username2.getText().toString());
            user.setAddress(address.getText().toString());
            user.setRiqi(riqi.getText().toString());
            user.setNumber(mail_1.getText().toString());
            if (radioGroup.getCheckedRadioButtonId() == R.id.male) {
                user.setSex("男");
                female.setVisibility(View.INVISIBLE);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.femle) {
                user.setSex("女");
                male.setVisibility(View.INVISIBLE);
            }
            user.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(User_Enter_msg.this, "保存成功", Toast.LENGTH_SHORT).show();
                        proess.dismiss();
                    }
                }
            });
        }

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
    public void check()
    {
        username2.addTextChangedListener(new TextWatcher() {
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
                editstar = username2.getSelectionStart();
                editend = username2.getSelectionEnd();
                if (temp.length() > 7) {
                    Toast.makeText(User_Enter_msg.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    username2.setText(s);
                    username2.setSelection(tempSelection);
                }
            }
        });
        mail_1.addTextChangedListener(new TextWatcher() {
            private int editstar;
            private int editend;
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp=s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editstar = mail_1.getSelectionStart();
                editend = mail_1.getSelectionEnd();
                if (temp.length() > 11) {
                    Toast.makeText(User_Enter_msg.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    mail_1.setText(s);
                    mail_1.setSelection(tempSelection);
                }
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            private int editstar;
            private int editend;
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp=s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editstar = address.getSelectionStart();
                editend = address.getSelectionEnd();
                if (temp.length() > 20) {
                    Toast.makeText(User_Enter_msg.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    s.delete(editstar - 1, editend);
                    int tempSelection = editstar;
                    address.setText(s);
                    address.setSelection(tempSelection);
                }
            }
        });




    }

}
