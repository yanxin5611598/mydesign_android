package com.yx.aircheck.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.yx.aircheck.MainUI;

/*创建、显示、关闭等待对话框*/
public class DialogUtil {
    //等待对话框
    private static Dialog loadDialog;
    public static Dialog createProgressDialog(String message, Context context){
        //创建对话框(为对话框设置样式)
        ProgressDialog mDialog = ProgressDialog.show(context, "系统提示", message, false);//创建ProgressDialog
        //设置触屏不会取消
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        return mDialog;
    }
    //显示自定义等待对话框
    public static void showDialog(String message,Context context){
        //若不存在，则创建
        if (loadDialog == null) {
            loadDialog = createProgressDialog(message,context);
        }
        //若没有正在显示，则显示
        if (!loadDialog.isShowing()) {
            loadDialog.show();
        }
    }
    //关闭自定义等待对话框
    public static void closeDialog(){
        //若存在，且正在显示中，则关闭
        if (loadDialog!=null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        //释放内存
        loadDialog = null;
    }
}
