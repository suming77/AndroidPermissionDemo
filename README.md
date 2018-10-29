## AndroidPermissionDemo
6.0权限申请demo详解，包括单个和多个权限的申请，权限工具类的封装，BaseActivity的封装。
![image](https://github.com/FollowExcellence/AndroidPermissionDemo/blob/master/GIF.gif)

## Use
1.MorePermissionActivity.calss 在activity设置
```Java
 A.在你需要的地方设置：
 PermissionUtil.checkPermission(this, permissions, REQUEST_CODE_MORE, this);
 
 B.在回调的地放设置：
  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }
    
 C.实现三个方法：
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
 
```
2.UseBaseActivity.calss 在activity设置
```Java
 在你需要的地方请求权限：
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

 
```
## APK下载
"点击下载"[APK](https://github.com/FollowExcellence/AndroidPermissionDemo/blob/master/app/app-debug.apk) 
