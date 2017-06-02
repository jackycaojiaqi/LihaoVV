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
import com.fubang.lihaovv.entities.FollowListEnitty;
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
 * 项目名称：Youliao
 * 类描述：收藏房间列表
 * 创建人：zhuyunjian
 * 创建时间：2016-11-24 14:41
 * 修改人：zhuyunjian
 * 修改时间：2016-11-24 14:41
 * 修改备注：
 */
public class FollowAdapter extends ListBaseAdapter<FollowListEnitty> {
    private List<FollowListEnitty> list;

    public FollowAdapter(List<FollowListEnitty> list, Context context) {
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
            convertView = inflater.inflate(R.layout.item_follow_room, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
//        if (position == 0){
////            holder.simpleDraweeView.setVisibility(View.GONE);
////            holder.roomLayout.setVisibility(View.GONE);
//        }else {
        //显示gif动态图
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(AppConstant.HEAD_URL+list.get(position).getRoompic()))
                .setAutoPlayAnimations(true)
                .build();
        Log.d("123", AppConstant.HEAD_URL+list.get(position).getRoompic());
        holder.simpleDraweeView.setController(controller);
//            holder.simpleDraweeView.setImageURI(Uri.parse(AppConstant.HEAD_URL+list.get(position).getRoompic()));
        holder.roomText.setText(list.get(position).getRoomname());
        //点击直接进入房间
        holder.roomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("123",position+"-----------------");
                context.startActivity(NewRoomActivity_.intent(context)
                        .extra("roomIp",list.get(position).getGateway()).extra("roomId",list.get(position).getRoomid()).get());
            }
        });
//        }
        return convertView;
    }

    static class ViewHolder{
        //房间图片
        SimpleDraweeView simpleDraweeView;
        //房间名
        TextView roomText;
        LinearLayout roomLayout;

        public ViewHolder(View itemView) {
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.follow_room_pic);
            roomText = (TextView) itemView.findViewById(R.id.follow_room_name);
            roomLayout = (LinearLayout) itemView.findViewById(R.id.follow_room_layout);
        }
    }
}
