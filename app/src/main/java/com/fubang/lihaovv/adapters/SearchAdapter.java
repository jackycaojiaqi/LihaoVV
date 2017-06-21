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
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.HistoryEnity;
import com.fubang.lihaovv.entities.RoomListEntity;
import com.fubang.lihaovv.ui.RoomActivity_;
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
 * 创建时间：2016-09-28 12:50
 * 修改人：zhuyunjian
 * 修改时间：2016-09-28 12:50
 * 修改备注：
 */
public class SearchAdapter extends ListBaseAdapter<RoomListEntity> {
    public SearchAdapter(List<RoomListEntity> list, Context context) {
        super(list, context);
    }

    public void notifylist(List<RoomListEntity> list_new) {
        list = list_new;
        notifyDataSetChanged();
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_history_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(AppConstant.HEAD_URL + list.get(position).getRoompic()))
                .setAutoPlayAnimations(true)
                .build();
        holder.headImage.setController(controller);
        holder.idTv.setText(list.get(position).getRoomid());
        holder.nameTv.setText(list.get(position).getRoomname());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(RoomActivity_.intent(context)
                        .extra("roomIp", list.get(position).getGateway()).
                                extra("roomId", list.get(position).getRoomid()).
                                extra("roomPwd", list.get(position).getRoompwd()).get());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        LinearLayout layout;
        TextView idTv, nameTv;
        SimpleDraweeView headImage;

        public ViewHolder(View itemView) {
            layout = (LinearLayout) itemView.findViewById(R.id.history_layout);
            idTv = (TextView) itemView.findViewById(R.id.history_room_id);
            nameTv = (TextView) itemView.findViewById(R.id.history_room_name);
            headImage = (SimpleDraweeView) itemView.findViewById(R.id.history_list_headicon);
        }
    }
}

