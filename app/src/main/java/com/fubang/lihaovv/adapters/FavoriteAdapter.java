package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.ui.NewRoomActivity_;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.entities.ActorListEntity;
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
 * 创建人：zhuyunjian
 * 创建时间：2016-07-11 15:56
 * 修改人：zhuyunjian
 * 修改时间：2016-07-11 15:56
 * 修改备注：
 */
public class FavoriteAdapter extends ListBaseAdapter<ActorListEntity> {

    public FavoriteAdapter(List<ActorListEntity> list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_follow_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.headImage.setImageURI(Uri.parse(AppConstant.HEAD_URL+list.get(position).getCphoto()));
        holder.roomNumber.setText(list.get(position).getNvcbid());
        holder.actorNumber.setText(list.get(position).getNuserid());
        holder.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getNvcbid().equals("0")){
                    Toast.makeText(context, "主播在赶来的路上", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("123",list.get(position).getMedia());
                    context.startActivity(NewRoomActivity_.intent(context).extra("roomIp", list.get(position).getMedia()).extra("roomId", list.get(position).getNvcbid()).get());
                }
            }
        });
        return convertView;
    }
    static class ViewHolder{
        TextView roomNumber,actorNumber;
        SimpleDraweeView headImage;

        public ViewHolder(View itemView) {
            headImage = (SimpleDraweeView)itemView.findViewById(R.id.follow_actor_head);
            roomNumber = (TextView)itemView.findViewById(R.id.follow_room_id);
            actorNumber = (TextView)itemView.findViewById(R.id.follow_actor_id);
        }
    }
}
