package com.suming.androidpermissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.suming.androidpermissiondemo.base.BaseActivity;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/10/23 16:05
 * @类描述 ${TODO}权限工具类
 * 注意：没有做小米，vivo手机等权限处理
 * 我的博客地址：https://blog.csdn.net/m0_37796683
 */
public class UseBaseActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_base);

        findViewById(R.id.btn_single).setOnClickListener(this);
        findViewById(R.id.btn_multi).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_single://单个权限使用
                requestSinglePermission();
                break;
            case R.id.btn_multi://多个权限使用
                requestMultiPermission();
                break;
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }

    public void requestSinglePermission() {
        requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                Toast.makeText(UseBaseActivity.this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
            }

            @Override
            public void denied() {
                Toast.makeText(UseBaseActivity.this, "获取权限失败，正常功能受到影响", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void requestMultiPermission() {
        requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CALL_PHONE},
                new RequestPermissionCallBack() {
                    @Override
                    public void granted() {
                        Toast.makeText(UseBaseActivity.this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void denied() {
                        Toast.makeText(UseBaseActivity.this, "获取权限失败，正常功能受到影响", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
