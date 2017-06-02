package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.GiftTopEntity;
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
 * 创建时间：2016-06-27 14:09
 * 修改人：dell
 * 修改时间：2016-06-27 14:09
 * 修改备注：
 */
public class GiftTopAdapter extends ListBaseAdapter<GiftTopEntity> {
    public GiftTopAdapter(List<GiftTopEntity> list, Context context) {
        super(list, context);
    }

    @Override

    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_gifttop_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (position==0){
            holder.numberIv.setImageResource(R.mipmap.gold_icon);
        }else if (position == 1){
            holder.numberIv.setImageResource(R.mipmap.silver_icon);
        }else if (position == 2){
            holder.numberIv.setImageResource(R.mipmap.tongpai_icon);
        }else {
            holder.numberIv.setVisibility(View.GONE);
            holder.numberTv.setVisibility(View.VISIBLE);
            holder.numberTv.setText(position + 1 + "");
        }
//        if (list.get(position).getCphoto()!=null){
//            holder.headImage.setImageURI(Uri.parse(AppConstant.HEAD_URL+list.get(position).getCphoto()));
//        }
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(Uri.parse(AppConstant.STAR_URL+list.get(position).getSrc()))
//                .setAutoPlayAnimations(true)
//                .build();
//        Log.d("123", AppConstant.STAR_URL+list.get(position).getSrc());
//        holder.giftIv.setController(controller);
//        holder.nameTv.setText(list.get(position).getCalias());
//        holder.ValueTv.setText(list.get(position).getNcount());
        return convertView;
    }
    static class ViewHolder {
        TextView numberTv, nameTv, ValueTv;
        ImageView numberIv;
        SimpleDraweeView headImage,giftIv;
        public ViewHolder(View itemView) {
            numberTv = (TextView) itemView.findViewById(R.id.billboard_gifttop_number);
            nameTv = (TextView) itemView.findViewById(R.id.billboard_gifttop_name);
            ValueTv = (TextView) itemView.findViewById(R.id.billboard_gifttop_value);
            numberIv = (ImageView) itemView.findViewById(R.id.gifttop_number_image);
            giftIv = (SimpleDraweeView) itemView.findViewById(R.id.billboard_gifttop_gift);
            headImage = (SimpleDraweeView) itemView.findViewById(R.id.gifttop_list_headicon);
        }
    }
}
