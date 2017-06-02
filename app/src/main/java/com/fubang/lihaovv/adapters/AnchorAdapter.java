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
import com.fubang.lihaovv.entities.AnchorEntity;
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
 * 创建时间：2016-06-27 14:31
 * 修改人：dell
 * 修改时间：2016-06-27 14:31
 * 修改备注：
 */
public class AnchorAdapter extends ListBaseAdapter<AnchorEntity> {
    public AnchorAdapter(List<AnchorEntity> list, Context context) {
        super(list, context);
    }

    @Override

    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_anchor_list,parent,false);
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
        holder.numberTv.setText(position+1+"");
        holder.nameTv.setText(list.get(position).getCalias());
        holder.valueTv.setText(list.get(position).getNcount());
        return convertView;
    }
    static class ViewHolder {
        TextView numberTv, nameTv,valueTv;
        ImageView imageView;
        SimpleDraweeView headView;

        public ViewHolder(View itemView) {
            valueTv = (TextView) itemView.findViewById(R.id.billboard_lijin_value);
            numberTv = (TextView) itemView.findViewById(R.id.billboard_anchor_number);
            nameTv = (TextView) itemView.findViewById(R.id.billboard_lijin_name);
            imageView = (ImageView) itemView.findViewById(R.id.lijin_number_image);
            headView = (SimpleDraweeView) itemView.findViewById(R.id.lijin_list_headicon);
        }
    }
}
