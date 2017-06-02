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
import com.fubang.lihaovv.entities.HotAnchorEntity;
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
 * 创建时间：2016-07-04 10:19
 * 修改人：zhuyunjian
 * 修改时间：2016-07-04 10:19
 * 修改备注：
 */
public class HotRoomAdapter extends ListBaseAdapter<HotAnchorEntity> {

    public HotRoomAdapter(List<HotAnchorEntity> list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_hot_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
//        holder.simpleDraweeView.setImageURI(Uri.parse(AppConstant.BASE_URL+list.get(position).getCphoto()));
//        holder.roomNumber.setText(list.get(position).getRscount() + "/" + list.get(position).getRoomrs());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(AppConstant.BASE_URL+list.get(position).getCphoto()))
                .setAutoPlayAnimations(true)
                .build();
        Log.d("123", AppConstant.BASE_URL+list.get(position).getCphoto());
        holder.simpleDraweeView.setController(controller);
        holder.roomNumber.setText(list.get(position).getCalias());
        final String[] s = list.get(position).getRoomUrl().split(";");
        holder.roomText.setText(s[0]);
        holder.roomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("123",position+"-----------------");
                context.startActivity(NewRoomActivity_.intent(context)
                        .extra("roomIp",s[1]).extra("roomId",s[0]).get());
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
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.hot_room_pic);
            roomNumber = (TextView)itemView.findViewById(R.id.hot_room_number);
            roomText = (TextView) itemView.findViewById(R.id.hot_anchor_name);
            roomLayout = (LinearLayout) itemView.findViewById(R.id.hot_room_layout);
        }
    }

}
