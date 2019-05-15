package com.jiyun.everywheretrip.ui.main.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.UpLoadHeadBean;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HeadActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_options)
    ImageView mIvOptions;
    @BindView(R.id.img)
    ImageView mImg;
    private PopupWindow mPopupWindow;
    private TextView mTvCamera;
    private TextView mTvPhoto;
    private TextView mTVvCancel;

    private static final int CAMERA_CODE = 100;
    private static final int ALBUM_CODE = 200;
    private static final String TAG = MainActivity.class.getName();

    private File mFile;
    private Uri mImageUri;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_options, R.id.img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_options:
                initPop();
                break;
            case R.id.img:
                break;
        }
    }

    private void initPop() {
        mPopupWindow = new PopupWindow();
        View inflate = LayoutInflater.from(HeadActivity.this).inflate(R.layout.header_popupwindow, null);
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(mIvBack, Gravity.BOTTOM, 0, 0);

        popBackground();//popupWindow背景

        mTvCamera = inflate.findViewById(R.id.tv_camera);
        mTvPhoto = inflate.findViewById(R.id.tv_photo);
        mTVvCancel = inflate.findViewById(R.id.tv_cancel);

        mTvCamera.setOnClickListener(this);
        mTvPhoto.setOnClickListener(this);
        mTVvCancel.setOnClickListener(this);
    }

    private void popBackground() {
        /**
         * 点击popupWindow让背景变暗
         */
        final WindowManager.LayoutParams lp = HeadActivity.this.getWindow().getAttributes();
        lp.alpha = 0.7f;//代表透明程度，范围为0 - 1.0f
        HeadActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        HeadActivity.this.getWindow().setAttributes(lp);
        /**
         * 退出popupWindow时取消暗背景
         */
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                HeadActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                HeadActivity.this.getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                takeCamera();
                break;
            case R.id.tv_photo:
                takePhoto();
                break;
            case R.id.tv_cancel:
                mPopupWindow.dismiss();
                break;
        }
        mPopupWindow.dismiss();
    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }


    //打开相册
    private void openAlbum() {
        //启动相册
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ALBUM_CODE);
    }


    private void takeCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            openCamera();
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    //打开相机
    private void openCamera() {
        //创建文件用于保存图片
        mFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //适配7.0,  等到对应的mImageUri路径地址
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mImageUri = Uri.fromFile(mFile);
        } else {
            //第二个参数要和清单文件中的配置保持一致
            mImageUri = FileProvider.getUriForFile(this, "com.baidu.upload.provider", mFile);
        }

        //启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//将拍照图片存入mImageUri
        startActivityForResult(intent, CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {//判断回调成功

            if (requestCode == CAMERA_CODE) {//拍照

                //显示拍照后的图片
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                    mImg.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //拍照后的图片上传
                uploadFile(mFile);
            } else if (requestCode == ALBUM_CODE) {//相册

                //获取到相册选中后的图片URI路径
                Uri imageUri = data.getData();

                //显示相册选中后的图片
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    mImg.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //文件上传，将Uri路径转换为File对象
                //处理uri,7.0以后的fileProvider 把URI 以content provider 方式 对外提供的解析方法
                File file = getFileFromUri(imageUri, this);

                if (file.exists()) {
                    uploadFile(file);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                openCamera();
            } else if (requestCode == 200) {
                openAlbum();
            }
        }
    }

    public File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    /**
     * 通过内容解析中查询uri中的文件路径
     */
    private File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }

    private void uploadFile(File mFile) {

        String url = "http://yun918.cn/study/public/file_upload.php";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), mFile);

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "H1808A")
                .addFormDataPart("file", mFile.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final UpLoadHeadBean UpLoadHeadBean = gson.fromJson(string, UpLoadHeadBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (UpLoadHeadBean != null) {
                            if (UpLoadHeadBean.getCode() == 200) {
                                Toast.makeText(HeadActivity.this, UpLoadHeadBean.getRes(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent();
                                intent.putExtra(Constants.PHOTO,UpLoadHeadBean.getData().getUrl());
                                setResult(200,intent);

                                Glide.with(HeadActivity.this).load(UpLoadHeadBean.getData().getUrl()).into(mImg);
                                finish();
                                Log.e(TAG, "run: " + UpLoadHeadBean.getData().getUrl());
                            } else {
                                Toast.makeText(HeadActivity.this, UpLoadHeadBean.getRes(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
