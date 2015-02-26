package com.example.dropdownmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	// 工具栏
	private RelativeLayout rlTopBar;

	// 左中右三个控件（工具栏里）
	private TextView tvLeft;
	private TextView tvRight;
	private TextView tvMiddle;

	// 左中右三个弹出窗口
	private PopupWindow popLeft;
	private PopupWindow popRight;
	private PopupWindow popMiddle;

	// 左中右三个layout
	private View layoutLeft;
	private View layoutRight;
	private View layoutMiddle;

	// 左中右三个ListView控件（弹出窗口里）
	private ListView menulistLeft;
	private ListView menulistRight;
	private ListView menulistMiddle;

	// 菜单数据项
	private List<Map<String, String>> listLeft;
	private List<Map<String, String>> listRight;
	private List<Map<String, String>> listMiddle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initParam();
	}

	private void initParam() {
		rlTopBar = (RelativeLayout) this.findViewById(R.id.rl_topbar);

		tvLeft = (TextView) this.findViewById(R.id.tv_left);
		tvLeft.setOnClickListener(myListener);
		// 初始化数据项
		listLeft = new ArrayList<Map<String, String>>();
		for (int i = 1; i < 10; i++) {
			HashMap<String, String> mapTemp = new HashMap<String, String>();
			mapTemp.put("item", "left " + i);
			listLeft.add(mapTemp);
		}

		tvRight = (TextView) this.findViewById(R.id.tv_right);
		tvRight.setOnClickListener(myListener);
		// 初始化数据项
		listRight = new ArrayList<Map<String, String>>();
		for (int i = 1; i < 10; i++) {
			HashMap<String, String> mapTemp = new HashMap<String, String>();
			mapTemp.put("item", "right " + i);
			listRight.add(mapTemp);
		}

		tvMiddle = (TextView) this.findViewById(R.id.tv_middle);
		tvMiddle.setOnClickListener(myListener);
		// 初始化数据项
		listMiddle = new ArrayList<Map<String, String>>();
		for (int i = 1; i < 10; i++) {
			HashMap<String, String> mapTemp = new HashMap<String, String>();
			mapTemp.put("item", "mid " + i);
			listMiddle.add(mapTemp);
		}
	}

	private View.OnClickListener myListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_left:
				if (popLeft != null && popLeft.isShowing()) {
					popLeft.dismiss();
				} else {
					layoutLeft = getLayoutInflater().inflate(
							R.layout.pop_menulist, null);
					menulistLeft = (ListView) layoutLeft
							.findViewById(R.id.menulist);
					SimpleAdapter listAdapter = new SimpleAdapter(
							MainActivity.this, listLeft, R.layout.pop_menuitem,
							new String[] { "item" },
							new int[] { R.id.menuitem });
					menulistLeft.setAdapter(listAdapter);

					// 点击listview中item的处理
					menulistLeft
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// 改变顶部对应TextView值
									String strItem = listLeft.get(arg2).get(
										"item");
									tvLeft.setText(strItem);
									
									// 隐藏弹出窗口
									if (popLeft != null && popLeft.isShowing()) {
										popLeft.dismiss();
									}
								}
							});

					// 创建弹出窗口
					// 窗口内容为layoutLeft，里面包含一个ListView
					// 窗口宽度跟tvLeft一样
					popLeft = new PopupWindow(layoutLeft, tvLeft.getWidth(),
							LayoutParams.WRAP_CONTENT);

					ColorDrawable cd = new ColorDrawable(-0000);
					popLeft.setBackgroundDrawable(cd);
					popLeft.setAnimationStyle(R.style.PopupAnimation);
					popLeft.update();
					popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
					popLeft.setTouchable(true); // 设置popupwindow可点击
					popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
					popLeft.setFocusable(true); // 获取焦点

					// 设置popupwindow的位置（相对tvLeft的位置）
					int topBarHeight = rlTopBar.getBottom();
					popLeft.showAsDropDown(tvLeft, 0,
							(topBarHeight - tvLeft.getHeight()) / 2);

					popLeft.setTouchInterceptor(new View.OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// 如果点击了popupwindow的外部，popupwindow也会消失
							if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
								popLeft.dismiss();
								return true;
							}
							return false;
						}
					});
				}
				break;
			case R.id.tv_right:
				if (popRight != null && popRight.isShowing()) {
					popRight.dismiss();
				} else {
					layoutRight = getLayoutInflater().inflate(
							R.layout.pop_menulist, null);
					menulistRight = (ListView) layoutRight
							.findViewById(R.id.menulist);
					SimpleAdapter listAdapter = new SimpleAdapter(
							MainActivity.this, listRight, R.layout.pop_menuitem,
							new String[] { "item" },
							new int[] { R.id.menuitem });
					menulistRight.setAdapter(listAdapter);

					// 点击listview中item的处理
					menulistRight
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									String strItem = listRight.get(arg2).get(
										"item");
									tvRight.setText(strItem);
									
									if (popRight != null && popRight.isShowing()) {
										popRight.dismiss();
									}
								}
							});

					popRight = new PopupWindow(layoutRight, tvRight.getWidth(),
							LayoutParams.WRAP_CONTENT);

					ColorDrawable cd = new ColorDrawable(-0000);
					popRight.setBackgroundDrawable(cd);
					popRight.setAnimationStyle(R.style.PopupAnimation);
					popRight.update();
					popRight.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
					popRight.setTouchable(true); // 设置popupwindow可点击
					popRight.setOutsideTouchable(true); // 设置popupwindow外部可点击
					popRight.setFocusable(true); // 获取焦点

					// 设置popupwindow的位置
					int topBarHeight = rlTopBar.getBottom();
					popRight.showAsDropDown(tvRight, 0,
							(topBarHeight - tvRight.getHeight()) / 2);

					popRight.setTouchInterceptor(new View.OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// 如果点击了popupwindow的外部，popupwindow也会消失
							if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
								popRight.dismiss();
								return true;
							}
							return false;
						}
					});
				}
				break;
			case R.id.tv_middle:
				if (popMiddle != null && popMiddle.isShowing()) {
					popMiddle.dismiss();
				} else {
					layoutMiddle = getLayoutInflater().inflate(
							R.layout.pop_menulist, null);
					menulistMiddle = (ListView) layoutMiddle
							.findViewById(R.id.menulist);
					SimpleAdapter listAdapter = new SimpleAdapter(
							MainActivity.this, listMiddle, R.layout.pop_menuitem,
							new String[] { "item" },
							new int[] { R.id.menuitem });
					menulistMiddle.setAdapter(listAdapter);

					// 点击listview中item的处理
					menulistMiddle
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									String strItem = listMiddle.get(arg2).get(
										"item");
									tvMiddle.setText(strItem);
									
									if (popMiddle != null && popMiddle.isShowing()) {
										popMiddle.dismiss();
									}
								}
							});

					popMiddle = new PopupWindow(layoutMiddle, tvMiddle.getWidth(),
							LayoutParams.WRAP_CONTENT);

					ColorDrawable cd = new ColorDrawable(-0000);
					popMiddle.setBackgroundDrawable(cd);
					popMiddle.setAnimationStyle(R.style.PopupAnimation);
					popMiddle.update();
					popMiddle.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
					popMiddle.setTouchable(true); // 设置popupwindow可点击
					popMiddle.setOutsideTouchable(true); // 设置popupwindow外部可点击
					popMiddle.setFocusable(true); // 获取焦点

					// 设置popupwindow的位置
					int topBarHeight = rlTopBar.getBottom();
					popMiddle.showAsDropDown(tvMiddle, 0,
							(topBarHeight - tvMiddle.getHeight()) / 2);

					popMiddle.setTouchInterceptor(new View.OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// 如果点击了popupwindow的外部，popupwindow也会消失
							if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
								popMiddle.dismiss();
								return true;
							}
							return false;
						}
					});
				}
				break;
			default:
				break;
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
