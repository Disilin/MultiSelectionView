package com.example.disilin.selection.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import java.io.File;

/**
 * 设备工具类
 * @author dsl
 *
 */

public class DeviceUtils {

	/**
	 * 获取屏幕密度
	 * @param context
	 * @return
	 */
	public static float getScreenDensity(Context context)
	{
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager)context.getApplicationContext().
				getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}

	private static int sScreenWidth = 0;
	/**
	 * 获得屏幕宽度
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		if (sScreenWidth == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager wm = (WindowManager) context.getApplicationContext().
					getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
			sScreenWidth = dm.widthPixels;
		}
		return sScreenWidth;
	}

	private static int sScreenHeight = 0;
	/**
	 * 获得屏幕高度
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		if (sScreenHeight == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager wm = (WindowManager) context.getApplicationContext().
					getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
			sScreenHeight = dm.heightPixels;
		}
		return sScreenHeight;
	}
	
	/**
	 * 设备单位转像素单位
	 */
	public static int dp2px(Context context, float dip)
	{
		Resources resources = context.getApplicationContext().getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.getDisplayMetrics());
		return Math.round(px);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return Math.round(spValue * fontScale + 0.5f);
	}

	/**
	 * 像素单位转设备单位
	 * @param context
	 * @param px
	 * @return
	 */
	public static int px2dp(Context context, float px)
	{
		Resources resources = context.getApplicationContext().getResources();
		final float scale = resources.getDisplayMetrics().density;
		return Math.round(px / scale + 0.5f);
	}

	/**
	 * 检测当前手机是否有SD卡
	 * */
	public static boolean isSDCardExists()
	{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 返回指定子目录的完整路径（根据是否有sd卡判断存储路径）
	 * @param subDir    子目录
	 * @param createIfNotExists 不存在是否创建
	 * @return
	 */
	public static String getSavePath(String subDir, boolean createIfNotExists)
	{
		String result = "";
		if (isSDCardExists())
		{
			result = Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		else
		{
			result = Environment.getDataDirectory().getAbsolutePath();
		}

		if (!result.endsWith(File.separator))
		{
			result += File.separator;
		}

		if (!TextUtils.isEmpty(subDir))
		{
			if (subDir.startsWith(File.separator))
			{
				result += subDir.substring(1);
			}
			else
			{
				result += subDir;
			}

			if (!result.endsWith(File.separator))
			{
				result += File.separator;
			}
		}

		if (createIfNotExists) {
			File fPath = new File(result);    //文件目录
			if (!fPath.exists()) {    //判断目录是否存在，不存在创建
				fPath.mkdirs();    //创建目录
			}
		}

		return result;
	}

	private static int sStatusBarHeight = -1;
	public static int getStatusBarHeight(Context context)
	{
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			sStatusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
		}
		return sStatusBarHeight;
	}
}
