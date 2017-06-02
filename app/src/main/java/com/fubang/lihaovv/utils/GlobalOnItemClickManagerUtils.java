package com.fubang.lihaovv.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.fubang.lihaovv.adapters.EmotionGridViewAdapter;


/**
 * Created by zejian
 * Time  16/1/8 下午5:05
 * Email shinezejian@163.com
 * Description:点击表情的全局监听管理类
 */
public class GlobalOnItemClickManagerUtils {

    private static GlobalOnItemClickManagerUtils instance;
    private EditText mEditText;//输入框
    private static Context mContext;

    public static GlobalOnItemClickManagerUtils getInstance(Context context) {
        mContext=context;
        if (instance == null) {
            synchronized (GlobalOnItemClickManagerUtils.class) {
                if(instance == null) {
                    instance = new GlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emotion_map_type) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAdapter = parent.getAdapter();

                if (itemAdapter instanceof EmotionGridViewAdapter) {
                    // 点击的是表情
                    EmotionGridViewAdapter emotionGvAdapter = (EmotionGridViewAdapter) itemAdapter;

                    if (position == emotionGvAdapter.getCount() - 1) {
                        // 如果点击了最后一个回退按钮,则调用删除键事件
                        mEditText.dispatchKeyEvent(new KeyEvent(
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    } else {
                        // 如果点击了表情,则添加到输入框中
                        String emotionName = emotionGvAdapter.getItem(position);
                        Log.d("123","emotionname"+emotionName);
                        String pageName = emotionName.substring(10,13);
                        int page = Integer.parseInt(pageName);
                        // 获取当前光标位置,在指定位置上添加表情图片文本
                        int curPosition = mEditText.getSelectionStart();
//                        StringBuilder sb = new StringBuilder(mEditText.getText().toString());
//                        sb.insert(curPosition, emotionName);
                        StringBuilder sb = new StringBuilder(emotionName);
                        // 得到随机表情图片的Bitmap
                        Log.d("123","face page-------"+page+"id-----"+id+"emotion typ====="+emotion_map_type);
                        Bitmap bitmap = BitmapFactory.decodeResource(view.getContext().getResources(), FaceUtil.getFaces().get(page-1).getFaceId());
                        // 得到SpannableString对象,主要用于拆分字符串
                        SpannableString spannableString = new SpannableString(sb);
                        // 得到ImageSpan对象
                        ImageSpan imageSpan = new ImageSpan(parent.getContext(), bitmap);
                        // 调用spannableString的setSpan()方法
                        spannableString.setSpan(imageSpan, 0, 13,
                                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        // 给EditText追加spannableString
                        mEditText.append(spannableString);
//                        // 特殊文字处理,将表情等转换一下
//                        mEditText.setText(SpanStringUtils.getEmotionContent(emotion_map_type,
//                                mContext, mEditText, sb.toString()));

                        // 将光标设置到新增完表情的右侧
                        mEditText.setSelection(curPosition + emotionName.length());
                    }

                }
            }
        };
    }

}
