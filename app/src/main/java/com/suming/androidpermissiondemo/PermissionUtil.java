package com.suming.androidpermissiondemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/10/23 11:05
 * @类描述 ${TODO}权限工具类
 * 没有做小米，vivo手机权限处理
 */
public class PermissionUtil {
    /**
     * 检测权限
     *
     * @param activity
     * @param permissions //请求的权限组
     * @param requestCode //请求码
     */
    public static void checkPermission(final Activity activity, final String[] permissions,
                                       final int requestCode, permissionInterface permissionInterface) {
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
        if (deniedPermissions != null && deniedPermissions.size() > 0) {
            //大于0,表示有需要的权限但没有申请
            PermissionUtil.requestContactsPermissions(activity,
                    deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            //已经拥有权限
            permissionInterface.requestPermissionSuccess();
        }
    }

    /**
     * 请求权限
     */
    public static void requestContactsPermissions(final Activity activity, final String[] permissions,
                                                  final int requestCode) {
        //默认是false,但是只要请求过一次权限就会为true,除非点了不再询问才会重新变为false
        if (shouldShowPermissions(activity, permissions)) {//不在访问权限，直接跳转到设置权限页面
//            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            startApplicationDetailsSettings(activity, requestCode);
        } else {
            // 无需向用户界面提示，直接请求权限,如果用户点了不再询问,即使调用请求权限也不会出现请求权限的对话框
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    /**
     * 判断请求权限是否成功
     *
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在传入的权限中，判断是否授权
     *
     * @param activity
     * @param permission 权限
     * @return 没有授权的权限
     */
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        //存储没有授权的权限
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            //判断改权限是否已授权
            if (ContextCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                //没有权限 就添加
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 检测这些权限中是否有 没有授权需要提示的
     *
     * @param activity
     * @param permission
     * @return 是否需要提示
     */
    public static boolean shouldShowPermissions(Activity activity, String... permission) {
        for (String value : permission) {
            //请求前勾选不在提示，返回false
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限返回方法
     */
    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, permissionInterface permissionInterface) {
        if (PermissionUtil.verifyPermissions(grantResults)) {//允许权限，有权限
            if (permissionInterface != null) {
                permissionInterface.requestPermissionSuccess();
            }
        } else {
            //拒绝了权限，没有权限
            //当用户之前已经请求过该权限并且拒绝了授权这个方法返回true 表示没有勾选了不再提示，false表示禁止了权限并且勾选了不再提示
            if (PermissionUtil.shouldShowPermissions(activity, permissions)) {
                if (permissionInterface != null) {
                    permissionInterface.requestPermissionRefuse();
                }
            } else {
                //并且勾选了不再提示
                if (permissionInterface != null) {
                    permissionInterface.requestPermissionRefuseAndNoTips();
                }
            }
        }

        //单个权限
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////            showToast("权限已申请");
//        } else {
////            showToast("权限已拒绝");
//        }

    }

    /**
     * 打开app详细设置界面<br/>
     * <p>
     * 在 onActivityResult() 中没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，<br/>
     * 所以 resultCode 总是为 RESULT_CANCEL，所以不能根据返回码进行判断。<br/>
     * 在 onActivityResult() 中还需要对权限进行判断，因为用户有可能没有授权就返回了！<br/>
     */
    public static void startApplicationDetailsSettings(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    //回调接口
    public interface permissionInterface {
        void requestPermissionSuccess();//请求权限成功

        void requestPermissionRefuse();//拒绝权限

        void requestPermissionRefuseAndNoTips();//拒绝权限并不在提示
    }
}
