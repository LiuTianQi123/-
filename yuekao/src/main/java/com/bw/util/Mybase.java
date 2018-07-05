package com.bw.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.bean.User;
import com.example.yuekao.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class Mybase extends BaseAdapter{
    private Context context;
    private List<User.DataBeanX.DataBean> list;
    private final int ONE=0;
    private final int TWO=1;
    public Mybase(Context context, List<User.DataBeanX.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getPics()!=null){
            return ONE;
        }else {
            return TWO;
        }

    }
    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type==ONE) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = View.inflate(context, R.layout.mybase, null);
                holder.t1 = convertView.findViewById(R.id.textView);
                holder.t2 = convertView.findViewById(R.id.textView2);
                holder.imageView = convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            String url="http://img.365jia.cn/uploads/";
            String img=url+list.get(position).getPics();
            holder.t1.setText(list.get(position).getTitle());
            holder.t2.setText(list.get(position).getCatalog_name());
            ImageLoader.getInstance().displayImage(img, holder.imageView, Myapplication.getoptions());
            return convertView;
        }else if (type==TWO){
            Holder2 holder2;
            if (convertView==null){
                holder2=new Holder2();
                convertView=View.inflate(context,R.layout.mybase2,null);
                holder2.t1 = convertView.findViewById(R.id.textView3);
                holder2.t2 = convertView.findViewById(R.id.textView4);
                convertView.setTag(holder2);
            }else {
                holder2= (Holder2) convertView.getTag();
                holder2.t1.setText(list.get(position).getTitle());
                holder2.t2.setText(list.get(position).getCatalog_name());
            }
              return convertView;
        }
        return null;

    }
    class Holder{
        TextView t1,t2;
        ImageView imageView;
    }
    class Holder2{
        TextView t1,t2;
    }
}
