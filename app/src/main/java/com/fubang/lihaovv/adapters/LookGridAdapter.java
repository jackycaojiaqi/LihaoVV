package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.LookMessageEntity;
import com.zhuyunjian.library.ListBaseAdapter;

import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：Wanghong
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-01-03 15:15
 * 修改人：zhuyunjian
 * 修改时间：2017-01-03 15:15
 * 修改备注：
 */
public class LookGridAdapter extends ListBaseAdapter<LookMessageEntity> {

    public LookGridAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_eplist_gridview,parent,false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.imageView = (ImageView) convertView.findViewById(R.id.strate_grid_image);
        holder.textView = (TextView) convertView.findViewById(R.id.strate_grid_text);
        holder.imageView.setImageResource(list.get(position).getLookMessageId());
        holder.textView.setText(list.get(position).getLookMessageName());
        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

}
