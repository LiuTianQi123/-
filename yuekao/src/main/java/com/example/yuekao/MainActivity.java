package com.example.yuekao;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.com.bw.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Dao dao;
    private HorizontalScrollView hs;
    private LinearLayout linearLayout;
    private ViewPager pager;
    private Button button;
    private List<ChannelBean> channelBeans;
    List<TextView> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getinstance();
    }
    public void getinstance(){
    hs=findViewById(R.id.hs);
    linearLayout=findViewById(R.id.line);
    button=findViewById(R.id.button);
    pager=findViewById(R.id.pager);
    dao=new Dao(this);
    list=new ArrayList<>();
    channelBeans=new ArrayList<>();
        channelBeans.add(new ChannelBean("头条",true));
        channelBeans.add(new ChannelBean("订阅",true));
        channelBeans.add(new ChannelBean("合肥",true));
        channelBeans.add(new ChannelBean("热评",true));
        channelBeans.add(new ChannelBean("图片",true));
        channelBeans.add(new ChannelBean("视频",true));
        channelBeans.add(new ChannelBean("世界杯",true));
        channelBeans.add(new ChannelBean("新闻",true));
        channelBeans.add(new ChannelBean("咨询",true));
     for(int i=0;i<channelBeans.size();i++){
        TextView textView=new TextView(this);
         String name = channelBeans.get(i).getName();
         dao.add(name);
         textView.setText(name);
         textView.setId(i+1000);
         textView.setTextSize(25);
         textView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int id = v.getId();
                 pager.setCurrentItem(id-1000);
             }
         });
         if (i==0){
             textView.setTextColor(Color.RED);
         }else {
             textView.setTextColor(Color.RED);
         }
         LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
         layoutParams.setMargins(30,10,30,10);
         linearLayout.addView(textView,layoutParams);
         list.add(textView);
     }
     pager.setAdapter(new Myadapter(getSupportFragmentManager()));
     pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           for (int i=0;i<list.size();i++){
              if (position==i){
                  list.get(i).setTextColor(Color.RED);
              }else {
                  list.get(i).setTextColor(Color.BLACK);
              }
           }
           TextView textView=list.get(position);
             int width = textView.getWidth()+10;
             hs.scrollTo(position*width,0);
         }

         @Override
         public void onPageSelected(int position) {

         }

         @Override
         public void onPageScrollStateChanged(int state) {

         }
     });
     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             ChannelActivity.startChannelActivity(MainActivity.this,channelBeans);
         }
     });
    }
  class Myadapter extends FragmentPagerAdapter{

      public Myadapter(FragmentManager fm) {
          super(fm);
      }

      @Override
      public Fragment getItem(int position) {
          return Fragment1.getinstance(list.get(position).getText().toString());
      }

      @Override
      public int getCount() {
          return list.size();
      }
  }
}
