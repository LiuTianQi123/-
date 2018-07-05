package com.example.yuekao;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bw.bean.User;
import com.bw.util.Httputil;
import com.bw.util.Httputil2;
import com.bw.util.Mybase;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment{
    private Httputil httputil=Httputil.getinstance();
    private List<User.DataBeanX.DataBean> lists=new ArrayList<>();
    private Handler handler=new Handler();
    private Mybase mybase;
    private String urls="http://www.wanandroid.com/tools/mockapi/6523/hotnews_1?page=1";
    private int page=1;
    private PullToRefreshListView lv;
    public static Fragment1 getinstance(String title){
     Fragment1 fragment1=new Fragment1();
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String title = arguments.getString("title");
        TextView textView=new TextView(getActivity());
        textView.setText(title);
        if (title.equals("热评")){
            getdatas();
           View view=inflater.inflate(R.layout.fragment1,container,false);
           lv=view.findViewById(R.id.list);
           lv.setMode(PullToRefreshBase.Mode.BOTH);
            ILoadingLayout iLoadingLayout=lv.getLoadingLayoutProxy(true,false);
            iLoadingLayout.setPullLabel("上拉刷新");
            iLoadingLayout.setReleaseLabel("正在刷新");
            iLoadingLayout.setReleaseLabel("松开刷新");
            ILoadingLayout iLoadingLayout2=lv.getLoadingLayoutProxy(true,false);
            iLoadingLayout2.setPullLabel("下拉刷新");
            iLoadingLayout2.setReleaseLabel("正在刷新");
            iLoadingLayout2.setReleaseLabel("松开刷新");
            lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                  page=1;
                  getdatas();
                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          lv.onRefreshComplete();
                      }
                  }, 2000);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                    page+=1;
                    getdatas();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv.onRefreshComplete();
                        }
                    }, 2000);
                }
            });
            mybase=new Mybase(getActivity(),lists);
            lv.setAdapter(mybase);
            return view;
        }else {
            return textView;
        }
    }
    /**
     * 请求数据方法
     */
    public void getdatas() {
        String url = urls + page;
        Httputil2 httputil2=Httputil2.getinstance();
        httputil2.getdata(url);
        httputil2.setHttplistener(new Httputil2.Httplistener() {
            @Override
            public void jsondatas(String s) {
                Gson gson=new Gson();
                User user = gson.fromJson(s, User.class);
                List<User.DataBeanX.DataBean> data = user.getData().getData();
                Log.i("TAG",data.size()+"");
                if (page==1){
                    lists.clear();
                }
                lists.addAll(data);
                mybase.notifyDataSetChanged();
            }
        });
    }
}
