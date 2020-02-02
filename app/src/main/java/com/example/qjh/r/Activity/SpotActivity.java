package com.example.qjh.r.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.bumptech.glide.Glide;
import com.example.qjh.r.Control.BaseActivity;
import com.example.qjh.r.R;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class SpotActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_getIcon;
    private TextView tv_result_detail;
    private Button btn_toSpot;
    private ImageButton btn_back;
    private Uri imageUri;
    //设置APPID/AK/SK
    public static final String APP_ID = "17897100";
    public static final String API_KEY = "GtXSVM1L5w3pHfbWhXcaK8NL";
    public static final String SECRET_KEY = "1ODdlpq6w5UcQdxSHkFYdz85GuTPZ4y1";
    private LoadingPopupView loadingPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_layout);
        initData();
    }

    private void initData() {
        iv_getIcon = (ImageView) findViewById(R.id.iv_getIcon);
        tv_result_detail = (TextView) findViewById(R.id.tv_result_detail);
        btn_toSpot = (Button) findViewById(R.id.btn_toSpot);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        iv_getIcon.setOnClickListener(this);
        btn_toSpot.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_getIcon:
                takePhoto();
                break;
            case R.id.btn_toSpot:
                if (imageUri == null) {
                    Toast.makeText(this, "请上传图片", Toast.LENGTH_SHORT).show();
                } else {
                    loadingPopup = (LoadingPopupView) new XPopup.Builder(SpotActivity.this)
                            .dismissOnBackPressed(false)
                            .asLoading("正在识别中。。")
                            .show();
                    toSpot();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void takePhoto() {
        final CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
                .setContext(this) //设置 context
                .setContentView(R.layout.head_image) //设置布局文件
                .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
                .setFouse(true)  //设置popupwindow 是否可以获取焦点
                .setOutSideCancel(true) //设置点击外部取消
                .setBackGroudAlpha(SpotActivity.this, 0.7f) //是否设置背景色，原理为调节 apha
                .builder()
                .showAtLocation(R.layout.common_activity_repairMessage_, Gravity.BOTTOM, 0, 0); //设置popupwindow居中显示
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
                File image = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                try {
                    if (image.exists()) {
                        image.delete();
                    }
                    image.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(SpotActivity.this, "com.example.qjh.r.fileprovider", image);
                    Log.d("image_class", "onClick: " + imageUri);
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
                        Glide.with(this).load(imageUri).into(iv_getIcon);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        //    imageUri = data.getData();
                        Glide.with(this).load(imageUri).into(iv_getIcon);
                        //  Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        pane.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void toSpot() {

        final File file = uriToFile(imageUri, this);
        Log.d("onActivityResult_class", "onActivityResult: /" + file.getAbsolutePath());
        new Thread(new Runnable() {
            @Override
            public void run() {
                spotPhoto(file.getPath());
            }
        }).start();
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

    private void spotPhoto(String path) {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        //String path = "test.jpg";
        final JSONObject res = client.advancedGeneral(path, new HashMap<String, String>());


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("image_text", "Text: " + res.toString(2));
                    Log.d("image_text2", "Text: " + res.toString());
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(res.toString());
                    JSONArray state = jsonObject.optJSONArray("result");
                    JSONObject firstResult = state.optJSONObject(0);
                    String realName = firstResult.optString("keyword");
                    loadingPopup.dismiss();
                    tv_result_detail.setText(realName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
