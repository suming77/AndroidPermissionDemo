package com.suming.androidpermissiondemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/10/23 16:05
 * @类描述 ${TODO}权限工具类
 * 注意：没有做小米，vivo手机等权限处理
 * 我的博客地址：https://blog.csdn.net/m0_37796683
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_normal).setOnClickListener(this);
        findViewById(R.id.btn_more).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal://封装前
                showAfterPermission();
                break;
            case R.id.btn_more://封装后-多个权限
                startActivity(new Intent(this, MorePermissionActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 请求相机权限
     */
    private void showAfterPermission() {
        //1.)先在清单文件manifest中声明相机权限
        //不管是6.0之前还是之后，你所使用到的权限都需要在清单文件中声明

        //2.)判断权限是否已经授权
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //没有权限,去申请权限，申请权限前再判断之前是否决绝过权限

            //3.)判断之前是否决绝过权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                //4.)申请相机权限
                //用户之前拒绝了权限，没有勾选不再提示框，直接去申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else {
                //用户之前拒绝了权限，勾选不再提示框，直接去设置界面申请权限
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 100);
            }

        } else {
            //已有权限
            //do something
            System.out.println("做需要使用到权限的事情");
        }
    }

    //4.重写权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请成功
                //do something
                System.out.println("做需要使用到权限的事情");
            } else {
                //权限申请失败
                //5.)用户点了拒绝权限，判断是否选择了不在提示
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    System.out.println("提示用户，禁止了权限，没有勾选不再提示框");
                } else {
                    System.out.println("提示用户，禁止了权限，并且勾选了不在提示框");
                }
            }
        }
    }
}
