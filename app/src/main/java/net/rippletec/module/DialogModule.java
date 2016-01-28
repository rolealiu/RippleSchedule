package net.rippletec.module;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

/**
 * Dialog对话框模块
 * 
 * @author rolealiu
 * @updateTime 2015/05/28
 */
public class DialogModule
{

	/**
	 * 对话框：纯文字
	 */
	public static void textDialog(Context context, String title, String content)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.create().show();
	}

	/**
	 * 对话框：文字、图标
	 */
	public static void textDialog(Context context, int iconDrawableResource,
			String title, String content)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.create().show();
	}

	/**
	 * 对话框：文字、图标、单一按钮
	 */
	public static void textDialog(Context context, int iconDrawableResource,
			String title, String content, String buttonText,
			OnClickListener buttonListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.setPositiveButton(buttonText, buttonListener);
		dialog.create().show();
	}

	/**
	 * 对话框：文字、图标、确认按钮、取消按钮
	 */
	public static void textDialog(Context context, int iconDrawableResource,
			String title, String content, String confirmText,
			String cancelText, OnClickListener confirmListener,
			OnClickListener cancelListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.setPositiveButton(confirmText, confirmListener);
		dialog.setNegativeButton(cancelText, cancelListener);
		dialog.create().show();
	}

	/**
	 * 对话框：纯文字
	 */
	public static void textDialog(Context context, int title, int content)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.create().show();
	}

	/**
	 * 对话框：文字、图标
	 */
	public static void textDialog(Context context, int iconDrawableResource,
			int title, int content)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.create().show();
	}

	/**
	 * 对话框：文字、图标、单一按钮
	 */
	public static void textDialog(Context context, int iconDrawableResource,
			int title, int content, int buttonText,
			OnClickListener buttonListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.setPositiveButton(buttonText, buttonListener);
		dialog.create().show();
	}

	/**
	 * 对话框：文字、图标、确认按钮、取消按钮
	 */
	public static void textDialog(Context context, int iconDrawableResource,
			int title, int content, int confirmText, int cancelText,
			OnClickListener confirmListener, OnClickListener cancelListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.setPositiveButton(confirmText, confirmListener);
		dialog.setNegativeButton(cancelText, cancelListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表
	 */
	public static void listDialog(Context context, String title,
			String[] items, OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标
	 */
	public static void listDialog(Context context, int iconDrawableResource,
			String title, String[] items, OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、单一按钮
	 */
	public static void listDialog(Context context, int iconDrawableResource,
			String title, String[] items, OnClickListener itemsListener,
			String buttonText, OnClickListener buttonListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.setPositiveButton(buttonText, buttonListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、确认按钮、取消按钮
	 */
	public static void listDialog(Context context, int iconDrawableResource,
			String title, String[] items, OnClickListener itemsListener,
			String confirmText, String cancelText,
			OnClickListener confirmListener, OnClickListener cancelListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.setPositiveButton(confirmText, confirmListener);
		dialog.setNegativeButton(cancelText, cancelListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表
	 */
	public static void listDialog(Context context, int title, String[] items,
			OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标
	 */
	public static void listDialog(Context context, int iconDrawableResource,
			int title, String[] items, OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、单一按钮
	 */
	public static void listDialog(Context context, int iconDrawableResource,
			int title, String[] items, OnClickListener itemsListener,
			int buttonText, OnClickListener buttonListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.setPositiveButton(buttonText, buttonListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、确认按钮、取消按钮
	 */
	public static void listDialog(Context context, int iconDrawableResource,
			int title, String[] items, OnClickListener itemsListener,
			int confirmText, int cancelText, OnClickListener confirmListener,
			OnClickListener cancelListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setItems(items, itemsListener);
		dialog.setPositiveButton(confirmText, confirmListener);
		dialog.setNegativeButton(cancelText, cancelListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表
	 */
	public static void singleChoiceListDialog(Context context, String title,
			String[] items, OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标
	 */
	public static void singleChoiceListDialog(Context context,
			int iconDrawableResource, String title, String[] items,
			OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、单一按钮
	 */
	public static void singleChoiceListDialog(Context context,
			int iconDrawableResource, String title, String[] items,
			OnClickListener itemsListener, String buttonText,
			OnClickListener buttonListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.setPositiveButton(buttonText, buttonListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、确认按钮、取消按钮
	 */
	public static void singleChoiceListDialog(Context context,
			int iconDrawableResource, String title, String[] items,
			OnClickListener itemsListener, String confirmText,
			String cancelText, OnClickListener confirmListener,
			OnClickListener cancelListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.setPositiveButton(confirmText, confirmListener);
		dialog.setNegativeButton(cancelText, cancelListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表
	 */
	public static void singleChoiceListDialog(Context context, int title,
			String[] items, OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标
	 */
	public static void singleChoiceListDialog(Context context,
			int iconDrawableResource, int title, String[] items,
			OnClickListener itemsListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、单一按钮
	 */
	public static void singleChoiceListDialog(Context context,
			int iconDrawableResource, int title, String[] items,
			OnClickListener itemsListener, int buttonText,
			OnClickListener buttonListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.setPositiveButton(buttonText, buttonListener);
		dialog.create().show();
	}

	/**
	 * 对话框：列表、图标、确认按钮、取消按钮
	 */
	public static void singleChoiceListDialog(Context context,
			int iconDrawableResource, int title, String[] items,
			OnClickListener itemsListener, int confirmText, int cancelText,
			OnClickListener confirmListener, OnClickListener cancelListener)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(iconDrawableResource);
		dialog.setTitle(title);
		dialog.setSingleChoiceItems(items, 0, itemsListener);
		dialog.setPositiveButton(confirmText, confirmListener);
		dialog.setNegativeButton(cancelText, cancelListener);
		dialog.create().show();
	}
}
