package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.entities.RichEntity;
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
 * <p/>
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-06-24 16:10
 * 修改人：dell
 * 修改时间：2016-06-24 16:10
 * 修改备注：
 */
public class RichAdapter extends ListBaseAdapter<RichEntity> {
    public RichAdapter(List<RichEntity> list, Context context) {
        super(list, context);
    }

    @Override

    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_rich_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (position==0){
            holder.imageView.setImageResource(R.mipmap.gold_icon);
        }else if (position == 1){
            holder.imageView.setImageResource(R.mipmap.silver_icon);
        }else if (position == 2){
            holder.imageView.setImageResource(R.mipmap.tongpai_icon);
        }else {
            holder.imageView.setVisibility(View.GONE);
            holder.numberTv.setVisibility(View.VISIBLE);
            holder.numberTv.setText(position + 1 + "");
        }
        if (list.get(position).getCphoto()!=null){
            holder.headView.setImageURI(Uri.parse(AppConstant.HEAD_URL+list.get(position).getCphoto()));
        }
        holder.nameTv.setText(list.get(position).getCalias());
        holder.ValueTv.setText(list.get(position).getNcount());
        return convertView;
    }
    static class ViewHolder {
        TextView numberTv, nameTv, ValueTv;
        SimpleDraweeView headView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            numberTv = (TextView) itemView.findViewById(R.id.billboard_rich_number);
            nameTv = (TextView) itemView.findViewById(R.id.billboard_rich_name);
            ValueTv = (TextView) itemView.findViewById(R.id.billboard_rich_value);
            headView = (SimpleDraweeView) itemView.findViewById(R.id.rich_list_headicon);
            imageView = (ImageView) itemView.findViewById(R.id.rich_number_image);
        }
    }
}
