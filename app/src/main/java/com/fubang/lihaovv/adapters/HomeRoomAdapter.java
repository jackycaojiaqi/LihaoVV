package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.ui.NewRoomActivity_;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.entities.RoomListEntity;
import com.fubang.lihaovv.ui.RoomActivity_;
import com.zhuyunjian.library.ListBaseAdapter;

import java.util.List;

/**
 * 首页房间列表的适配器
 * Created by dell on 2016/4/7.
 */
public class HomeRoomAdapter extends ListBaseAdapter<RoomListEntity> {
    private List<RoomListEntity> list;

    public HomeRoomAdapter(List<RoomListEntity> list, Context context) {
        super(list, context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_home_room, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
//        if (position == 0){
////            holder.simpleDraweeView.setVisibility(View.GONE);
////            holder.roomLayout.setVisibility(View.GONE);
//        }else {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(AppConstant.HEAD_URL+list.get(position).getRoompic()))
                .setAutoPlayAnimations(true)
                .build();
        Log.d("123", AppConstant.HEAD_URL+list.get(position).getRoompic());
        holder.simpleDraweeView.setController(controller);
//            holder.simpleDraweeView.setImageURI(Uri.parse(AppConstant.HEAD_URL+list.get(position).getRoompic()));
            holder.roomNumber.setText(list.get(position).getRscount() + "/" + list.get(position).getRoomrs());
            holder.roomText.setText(list.get(position).getRoomname());
            holder.roomLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("123",position+"-----------------");
                    context.startActivity(RoomActivity_.intent(context)
                            .extra("roomIp",list.get(position).getGateway())
                            .extra("roomId",list.get(position).getRoomid())
                            .extra("roomPwd",list.get(position).getRoompwd()).get());
//                    context.startActivity(NewRoomActivity_.intent(context)
//                            .extra("roomIp","42.121.57.170:12529;42.121.57.170:12529")
//                            .extra("roomId","123")
//                            .extra("roomPwd","").get());
                }
            });
//        }
        return convertView;
    }

    static class ViewHolder{
        SimpleDraweeView simpleDraweeView;
        TextView roomNumber,roomText;
        LinearLayout roomLayout;

        public ViewHolder(View itemView) {
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.home_room_pic);
            roomNumber = (TextView)itemView.findViewById(R.id.home_room_number);
            roomText = (TextView) itemView.findViewById(R.id.home_room_name);
            roomLayout = (LinearLayout) itemView.findViewById(R.id.home_room_layout);
        }
    }
}
