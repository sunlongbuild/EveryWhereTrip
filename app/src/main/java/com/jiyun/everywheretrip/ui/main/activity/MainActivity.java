package com.jiyun.everywheretrip.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.version.VersionApiService;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.MyInfoBean;
import com.jiyun.everywheretrip.bean.version.VersionInfoBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.RxUtils;
import com.jiyun.everywheretrip.presenter.personal.MyInfoPresenter;
import com.jiyun.everywheretrip.ui.country.activity.CityActivity;
import com.jiyun.everywheretrip.ui.main.adapter.MyPagerAdapter;
import com.jiyun.everywheretrip.ui.main.fragment.BanMiFragment;
import com.jiyun.everywheretrip.ui.main.fragment.CoverFragment;
import com.jiyun.everywheretrip.ui.main.fragment.HomeFragment;
import com.jiyun.everywheretrip.ui.main.fragment.MyFragment;
import com.jiyun.everywheretrip.utils.ImageLoader;
import com.jiyun.everywheretrip.utils.InstallUtil;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.utils.Tools;
import com.jiyun.everywheretrip.view.personal.MyInfoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity<MyInfoView, MyInfoPresenter> implements MyInfoView, View.OnClickListener {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nv)
    NavigationView mNv;
    @BindView(R.id.dl)
    DrawerLayout mDl;
    @BindView(R.id.iv_location)
    ImageView mIvLocation;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_down)
    ImageView mIvDown;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    private ArrayList<Fragment> mList;
    private TextView mName;
    private ImageView mImg;
    private TextView mWrite;
    private String mToken;//sp中获取的token
    private String mPhoto;//sp中获取的头像
    private static final String TAG = "MainActivity----";
    private File sd;
    ;
    private int mNowVersionCode;
    private String mNewVersionCode;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected MyInfoPresenter initPresenter() {
        return new MyInfoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");

        mToolbar.setTitle("");
        //mToolbar.setNavigationIcon(R.drawable.ab);
        setSupportActionBar(mToolbar);


        initNavigationView();

        StatusBarUtil.setLightMode(this);

        int a = 10;
        int b = 20;
        String str2 = "我是远端的用户!";

        String str = "我是本地的用户";

        mList = new ArrayList<>();
        mList.add(new CoverFragment());
        mList.add(new HomeFragment());
        mList.add(new BanMiFragment());
        mList.add(new MyFragment());

        mTab.addTab(mTab.newTab().setText("发现").setIcon(R.drawable.main_activity_cover));
        mTab.addTab(mTab.newTab().setText("线路").setIcon(R.drawable.main_activity_home));
        mTab.addTab(mTab.newTab().setText("伴米").setIcon(R.drawable.main_activity_banmi));
        mTab.addTab(mTab.newTab().setText("我的").setIcon(R.drawable.main_activity_my));

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mList);
        mVp.setAdapter(adapter);

        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVp.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    mIvSetting.setVisibility(View.GONE);
                    mIvLocation.setVisibility(View.VISIBLE);
                    mTvAddress.setVisibility(View.VISIBLE);
                    mIvDown.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 1) {
                    mIvLocation.setVisibility(View.GONE);
                    mTvAddress.setVisibility(View.GONE);
                    mIvDown.setVisibility(View.GONE);
                    mIvSetting.setVisibility(View.GONE);
                } else if (tab.getPosition() == 2) {
                    mIvLocation.setVisibility(View.GONE);
                    mTvAddress.setVisibility(View.GONE);
                    mIvDown.setVisibility(View.GONE);
                    mIvSetting.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 3) {
                    mIvSetting.setVisibility(View.VISIBLE);
                    mIvLocation.setVisibility(View.GONE);
                    mTvAddress.setVisibility(View.GONE);
                    mIvDown.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMyInfo(mToken);
    }

    private void initNavigationView() {
        View headerView = mNv.getHeaderView(0);
        // 头像
        mPhoto = (String) SpUtil.getParam(Constants.PHOTO, "");
        mImg = headerView.findViewById(R.id.iv_headPhoto);
        Glide.with(this).load(mPhoto).into(mImg);

        // 用户名
        String name = (String) SpUtil.getParam(Constants.USER_NAME, "");
        mName = headerView.findViewById(R.id.tv_name);
        mName.setText(name);

        // 签名
        String signature = (String) SpUtil.getParam(Constants.SINGNATURE, "");
        mWrite = headerView.findViewById(R.id.tv_write);
        mWrite.setText(signature);

        //收藏和个人信息页面
        TextView mTvMyCollcet = headerView.findViewById(R.id.tv_myCollect);
        RelativeLayout mRlPersonalInfo = headerView.findViewById(R.id.rl_personalInfo);
        TextView mTvFollowing = headerView.findViewById(R.id.tv_following);
        //版本检测
        TextView mTvVerson = headerView.findViewById(R.id.tv_verson);

        //收藏和个人信息页面监听
        mTvMyCollcet.setOnClickListener(this);
        mRlPersonalInfo.setOnClickListener(this);
        mTvFollowing.setOnClickListener(this);
        mTvVerson.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_personalInfo://头像
                PersonalInfoActivity.startAct(MainActivity.this);
                break;
            case R.id.tv_myCollect://收藏
                Intent intent = new Intent(MainActivity.this, BanMiCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_following:
                Intent intent1 = new Intent(MainActivity.this, BanMiFollowingActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_verson:
                check();
                initSD();
                break;
        }
    }

    private void initSD() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            openSD();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openSD();
        }
    }

    private void openSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sd = Environment.getExternalStorageDirectory();
        }
    }

    private void check() {
        VersionApiService apiserver = HttpUtils.getInstance().getApiserver(VersionApiService.baseUrl, VersionApiService.class);
        Observable<VersionInfoBean> observable = apiserver.getVersionInfo(mToken);
        observable.compose(RxUtils.<VersionInfoBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VersionInfoBean>() {
                    @Override
                    public void onNext(VersionInfoBean versionInfoBean) {
                        mNowVersionCode = Tools.getVersionCode();
                        mNewVersionCode = versionInfoBean.getResult().getInfo().getVersion();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("版本提示")
                                .setMessage("当前版本为" + mNowVersionCode + "最新版本为" + mNewVersionCode)
                                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        okhttp();
                                        toastShort("正在升级...");
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }

                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }
                });
    }

    private void okhttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://cdn.banmi.com/banmiapp/apk/banmi_330.apk")
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                saveFile(inputStream, sd + "/" + "abc123.apk", body.contentLength());
            }
        });
    }

    private void saveFile(InputStream inputStream, final String string, long max) {

        //读写的进度
        long count = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(string));

            int length = -1;
            byte[] bys = new byte[1024 * 10];

            while ((length = inputStream.read(bys)) != -1) {
                outputStream.write(bys, 0, length);

                count += length;

                Log.d(TAG, "progress: " + count + "    max:" + max);
            }

            inputStream.close();
            outputStream.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();

                    InstallUtil.installApk(MainActivity.this, string);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initListener() {
        //点击toolbar中的图片开启侧滑菜单
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDl.openDrawer(Gravity.LEFT);
            }
        });

        mIvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CityActivity.class));
            }
        });
        mTvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CityActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getMyInfo(mToken);
    }

    @Override
    public void setData(MyInfoBean infoBean) {
        MyInfoBean.ResultBean result = infoBean.getResult();

        Glide.with(this).load(result.getPhoto()).into(mImg);
        ImageLoader.setImage(this,result.getPhoto(),mImg,R.mipmap.zhanweitu_touxiang_mdpi);
        mName.setText(result.getUserName());
        mWrite.setText(result.getDescription());

        SpUtil.setParam(Constants.USER_NAME, result.getUserName());
        SpUtil.setParam(Constants.SINGNATURE, result.getDescription());
        SpUtil.setParam(Constants.GENDER, result.getGender());
        SpUtil.setParam(Constants.PHOTO, result.getPhoto());
    }
}
