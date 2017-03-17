package com.twolight.fetcher.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twolight on 16/5/4.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    public void addFragment(int containerViewId, Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(containerViewId,fragment).commit();
    }
    public void removeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
    public void replaceFragment(int containerViewId,Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(containerViewId,fragment).commit();
    }

    protected <E extends View> E getViewById(int resid) {
        try {
            return (E) findViewById(resid);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    public List<String> filterPermission(String[] permission){
        List<String> inDangerousPermissions = new ArrayList<>();

        List<String> dangerousPermissions =  new ArrayList<>();

        dangerousPermissions.add(Manifest.permission.WRITE_CONTACTS);
        dangerousPermissions.add(Manifest.permission.GET_ACCOUNTS);
        dangerousPermissions.add(Manifest.permission.READ_CONTACTS);

        dangerousPermissions.add(Manifest.permission.READ_CALL_LOG);
        dangerousPermissions.add(Manifest.permission.READ_PHONE_STATE);
        dangerousPermissions.add(Manifest.permission.READ_PHONE_STATE);
        dangerousPermissions.add(Manifest.permission.CALL_PHONE);
        dangerousPermissions.add(Manifest.permission.WRITE_CALL_LOG);
        dangerousPermissions.add(Manifest.permission.USE_SIP);
        dangerousPermissions.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        dangerousPermissions.add(Manifest.permission.ADD_VOICEMAIL);

        dangerousPermissions.add(Manifest.permission.READ_CALENDAR);
        dangerousPermissions.add(Manifest.permission.WRITE_CALENDAR);

        dangerousPermissions.add(Manifest.permission.CAMERA);

        dangerousPermissions.add(Manifest.permission.BODY_SENSORS);

        dangerousPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        dangerousPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        dangerousPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        dangerousPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        dangerousPermissions.add(Manifest.permission.RECORD_AUDIO);

        dangerousPermissions.add(Manifest.permission.READ_SMS);
        dangerousPermissions.add(Manifest.permission.RECEIVE_WAP_PUSH);
        dangerousPermissions.add(Manifest.permission.RECEIVE_MMS);
        dangerousPermissions.add(Manifest.permission.RECEIVE_SMS);
        dangerousPermissions.add(Manifest.permission.SEND_SMS);

        for (String p : permission){
            if(dangerousPermissions.contains(p)){
                inDangerousPermissions.add(p);
            }
        }
        return inDangerousPermissions;
    }

    public void appPermission(String[] permission, int requestCode){

        if(Build.VERSION.SDK_INT >= 23){
            List<String> inDangerousPermissions = filterPermission(permission);

            ArrayList<String> noGranted = new ArrayList<>();

            for(String item : inDangerousPermissions){
                int result = ContextCompat.checkSelfPermission(this, item);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    noGranted.add(item);
                }
            }
            if (!noGranted.isEmpty()) {
                String[] will = new String[noGranted.size()];
                noGranted.toArray(will);

                boolean needShowMessage = false;

                for (String p : will){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,p)){
                        needShowMessage = true;
                        break;
                    }
                }
                if(needShowMessage){
                    showToast("分享需要授权");
                }

                ActivityCompat.requestPermissions(this,will ,
                        requestCode);
            }else{
                requestPermissionSuccess(requestCode);
            }
        }else{
            requestPermissionSuccess(requestCode);
        }
    }

    public void requestPermission(String permission, int requestCode){
        if(Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{permission},
                        requestCode);
            }else{
                requestPermissionSuccess(requestCode);
            }
        }else{
            requestPermissionSuccess(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkPermission(requestCode,permissions,grantResults);
    }
    private void checkPermission(int requestCode,String[] permissions, int[] grantResults) {
        if(permissions.length > 0 && grantResults.length > 0){
            boolean allGranted = true;
            for(int item : grantResults){
                if(item != PackageManager.PERMISSION_GRANTED){
                    allGranted = false;
                    break;
                }
            }
            if(allGranted){
                requestPermissionSuccess(requestCode);
            }else{
                showToast("获取权限失败");
                requestPermissionFailed(requestCode);
            }

        }
    }

    /**
     * 如果不需需要请求权限，直接调用此方法
     * 用户同意权限后，也会调用此方法
     * 子类重写此方法，处理本来调用逻辑
     * @param requestCode
     */
    public void requestPermissionSuccess(int requestCode){

    }
    public void requestPermissionFailed(int requestCode){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //没有分享的地方不要回调umeng的onActivityResult,umeng会持有context造成内存泄露

    }

    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

}
