package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.RoomSumEntity;
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
 * 项目名称：newfubangzhibo
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-09-28 09:37
 * 修改人：zhuyunjian
 * 修改时间：2016-09-28 09:37
 * 修改备注：
 */
public class RoomsumAdapter extends ListBaseAdapter<RoomSumEntity> {
    public RoomsumAdapter(List<RoomSumEntity> list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_roomsum_list,parent,false);
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
        holder.idTv.setText(list.get(position).getNvcbid());
        holder.nameTv.setText(list.get(position).getCname());
        holder.ValueTv.setText(list.get(position).getRenqi());
        return convertView;
    }
    static class ViewHolder {
        TextView numberTv, nameTv, ValueTv,idTv;
        ImageView imageView;
        public ViewHolder(View itemView) {
            idTv = (TextView) itemView.findViewById(R.id.billboard_roomsum_id);
            imageView = (ImageView) itemView.findViewById(R.id.roomsum_number_image);
            numberTv = (TextView) itemView.findViewById(R.id.billboard_roomsum_number);
            nameTv = (TextView) itemView.findViewById(R.id.billboard_roomsum_name);
            ValueTv = (TextView) itemView.findViewById(R.id.billboard_roomsum_value);
        }
    }
}