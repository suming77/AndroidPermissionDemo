package com.suming.androidpermissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/10/23 16:05
 * @类描述 ${TODO}权限工具类
 * 注意：没有做小米，vivo手机等权限处理
 * 我的博客地址：https://blog.csdn.net/m0_37796683
 */
public class MorePermissionActivity extends AppCompatActivity implements View.OnClickListener, PermissionUtil.permissionInterface {

    private int REQUEST_CODE_ONE = 10001;
    private int REQUEST_CODE_MORE = 10002;

    private String[] permission = new String[]{Manifest.permission.CALL_PHONE};
    private String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        findViewById(R.id.btn_single).setOnClickListener(this);
        findViewById(R.id.btn_more).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_single://封装后-单个权限
                requestOnePermission();
                break;
            case R.id.btn_more://封装后-多个权限
                requestMorePermission();
                break;
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void requestOnePermission() {
        PermissionUtil.checkPermission(this, permission, REQUEST_CODE_ONE, this);
    }

    private void requestMorePermission() {
        PermissionUtil.checkPermission(this, permissions, REQUEST_CODE_MORE, this);
    }


    //4.重写权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }

    @Override
    public void requestPermissionSuccess() {
        System.out.println("做需要使用到权限的事情");
    }

    @Override
    public void requestPermissionRefuse() {
        System.out.println("提示用户，禁止了权限，没有勾选不再提示框");
    }

    @Override
    public void requestPermissionRefuseAndNoTips() {
        System.out.println("提示用户，禁止了权限，并且勾选了不在提示框");
    }
}
