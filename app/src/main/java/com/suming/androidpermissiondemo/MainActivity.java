package com.suming.androidpermissiondemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

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
        findViewById(R.id.btn_utils).setOnClickListener(this);
        findViewById(R.id.btn_base).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal://封装前
                showAfterPermission();
                break;
            case R.id.btn_utils://工具类使用
                startActivity(new Intent(this, MorePermissionActivity.class));
                break;
            case R.id.btn_base://base类使用
                startActivity(new Intent(this, UseBaseActivity.class));
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
            //没有权限,去申请权限，申请权限前再判断之前是否禁止过权限

            //3.)判断之前是否决绝过权限
            //注意：第一次默认返回false,如果点击了禁止授权(没勾选不在提示)则返回true,可以直接提示重新获取权限框
            // 如果点击了禁止授权，并且勾选了不再提示框则返回false，这种情况可以留到回调再处理
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】\r\n" +
                                "您好，需要如下权限：\r\n" +
                                Manifest.permission.CAMERA +
                                " 请允许，否则将影响部分功能的正常使用。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //4.)申请相机权限
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                            }
                        }).show();
            } else {
                //4.)申请相机权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            }
        } else {
            //已有权限
            //do something
            System.out.println("做需要使用到权限的事情");
            Toast.makeText(this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
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
                Toast.makeText(this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
            } else {
                //权限申请失败
                //5.)用户点了拒绝权限，判断是否选择了不在提示
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    Toast.makeText(this, "提示用户，禁止了权限，没有勾选不再提示框", Toast.LENGTH_LONG).show();
                } else {
                    //可以在这里提示用户，根据需求跳转到权限设置页面让用户手动授权或者取消授权
                    Toast.makeText(this, "提示用户，禁止了权限，并且勾选了不在提示框", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
