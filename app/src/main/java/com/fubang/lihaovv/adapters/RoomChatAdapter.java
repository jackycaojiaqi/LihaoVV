package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.xlg.android.protocol.RoomChatMsg;
import com.zhuyunjian.library.ListBaseAdapter;

import java.lang.reflect.Field;
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
 * 项目名称：fubangzhibo
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-18 09:04
 * 修改人：dell
 * 修改时间：2016-05-18 09:04
 * 修改备注：聊天消息的适配器
 */
public class RoomChatAdapter extends ListBaseAdapter<RoomChatMsg> {
    private List<RoomChatMsg> list;

    public RoomChatAdapter(List<RoomChatMsg> list, Context context) {
        super(list, context);
        this.list = list;
    }

    public void addData(RoomChatMsg msg) {
        if (list.size() >= 100) {
            list.remove(0);
        }
        list.add(msg);
        notifyDataSetChanged();

    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_room_message, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (list.get(position).getIsprivate() == 1) {
            holder.userTv.setText(list.get(position).getSrcalias() + ":悄悄说");
        } else
            holder.userTv.setText(list.get(position).getSrcalias() + ":");
//        holder.messageTv.setText(Html.fromHtml(list.get(position).getContent()));
        if (list.get(position).getToid() == -1) {//普通礼物
            holder.simpleDraweeView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + getResourceId(list.get(position).getContent()));
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            holder.simpleDraweeView.setController(controller);
            holder.messageTv.setText("   X" + list.get(position).getDstvcbid());
            holder.messageTv.setTextColor(context.getResources().getColor(R.color.gray));
        } else if (list.get(position).getToid() == 8) {//中奖礼物
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.messageTv.setText(list.get(position).getContent());
            holder.messageTv.setTextColor(context.getResources().getColor(R.color.red));
            holder.userTv.setText("系统消息" + ":");
        } else {//普通聊天
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.messageTv.setText(list.get(position).getContent());
            holder.messageTv.setTextColor(context.getResources().getColor(R.color.gray));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView userTv;
        TextView messageTv;
        SimpleDraweeView simpleDraweeView;

        public ViewHolder(View itemView) {
            //显示聊天室消息发送人和消息
            userTv = (TextView) itemView.findViewById(R.id.item_chat_user);
            messageTv = (TextView) itemView.findViewById(R.id.item_chat_message);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_chat_gift);
        }
    }

    public int getResourceId(String name) {
        try {
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
