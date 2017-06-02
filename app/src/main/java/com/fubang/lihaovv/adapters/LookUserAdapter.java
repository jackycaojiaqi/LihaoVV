package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.utils.LookMessageUtil;
import com.xlg.android.protocol.RoomUserInfo;

import org.simple.eventbus.EventBus;

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
 * 项目名称：Wanghong
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-01-03 14:55
 * 修改人：zhuyunjian
 * 修改时间：2017-01-03 14:55
 * 修改备注：
 */
public class LookUserAdapter extends BaseExpandableListAdapter {
    private List<RoomUserInfo> list;
    private Context context;

    public LookUserAdapter(List<RoomUserInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return LookMessageUtil.getMessageEntity().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder ;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.look_user_list,parent,false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (GroupViewHolder) convertView.getTag();

        holder.nameTv.setText(list.get(groupPosition).getUseralias());
        holder.idTv.setText(list.get(groupPosition).getUserid()+"");

        String pichead = "head"+list.get(groupPosition).getHeadid() + "";
        holder.headImage.setImageResource(getResourceByReflect(pichead));
        return convertView;
    }

    /**
     * 获取图片名称获取图片的资源id的方法
     * @param imageName
     * @return
     */
    public int getResourceByReflect(String imageName){
        Class drawable  =  R.drawable.class;
        Field field = null;
        int r_id ;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id= R.drawable.head0;
//            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }
    static class GroupViewHolder{
        TextView nameTv,idTv;
        SimpleDraweeView headImage;
        GroupViewHolder(View item){
            nameTv = (TextView) item.findViewById(R.id.look_user_name);
            idTv = (TextView) item.findViewById(R.id.look_user_id);
            headImage = (SimpleDraweeView) item.findViewById(R.id.look_user_headicon);
        }
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.look_user_message,parent,false);
            holder = new ChildViewHolder();
            convertView.setTag(holder);
        }
        holder = (ChildViewHolder) convertView.getTag();
        holder.childGrid = (GridView) convertView.findViewById(R.id.adapter_look_explv_gridview);
        LookGridAdapter adapter = new LookGridAdapter(LookMessageUtil.getMessageEntity(),context);
        holder.childGrid.setAdapter(adapter);
        holder.childGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(context, "抱上1麦"+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(list.get(groupPosition),"FirstMic");
                }else if (position == 1){
                    Toast.makeText(context, "抱上2麦"+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(list.get(groupPosition),"SecondMic");
                }else if (position == 2){
                    Toast.makeText(context, "抱上3麦"+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(list.get(groupPosition),"ThirdMic");
                }else if (position == 3){
                    Toast.makeText(context, "私聊"+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(list.get(groupPosition),"PersonChat");
                }else {
                    Toast.makeText(context, "点击了-----------" + childPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
     static class ChildViewHolder{
        private GridView childGrid;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
