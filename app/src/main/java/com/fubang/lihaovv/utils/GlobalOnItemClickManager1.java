package com.fubang.lihaovv.utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Created by dss886 on 15/9/22.
 */
public class GlobalOnItemClickManager1 {

    private static GlobalOnItemClickManager1 instance;
    private EditText mEditText;

    public static GlobalOnItemClickManager1 getInstance() {
        if (instance == null) {
            instance = new GlobalOnItemClickManager1();
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emojiType) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StringBuilder sb = new StringBuilder();
                switch (emojiType) {
                    case 0:
                        if (position<8) {
                            sb.append("/mr80").append(String.valueOf(position));
                        }else if (position == 8){
                            sb.append("/mr80").append(String.valueOf(position + 1));
                        }
                        else {
                            sb.append("/mr8").append(String.valueOf(position + 1));
                        }
                        break;
                    case 1:
                        if (position<8) {
                            sb.append("/mr80").append(String.valueOf(position));
                        }else if (position == 8){
                            sb.append("/mr80").append(String.valueOf(position + 1));
                        }
                        else {
                            sb.append("/mr8").append(String.valueOf(position + 1));
                        }
                        break;
                    case 2:
                        if (position<8) {
                            sb.append("/mr80").append(String.valueOf(position));
                        }else if (position == 8){
                            sb.append("/mr80").append(String.valueOf(position + 1));
                        }
                        else {
                            sb.append("/mr8").append(String.valueOf(position + 1));
                        }
                        break;
                    case 3:
                        if (position<8) {
                            sb.append("/mr80").append(String.valueOf(position));
                        }else if (position == 8){
                            sb.append("/mr80").append(String.valueOf(position + 1));
                        }
                        else {
                            sb.append("/mr8").append(String.valueOf(position + 1));
                        }
                        break;
                }
                mEditText.append(sb.toString());
            }
        };
    }
}
