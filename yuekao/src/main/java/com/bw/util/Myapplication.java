package com.bw.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.yuekao.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class Myapplication extends Application {
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initimage();
    }
    public void initimage(){
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        builder.threadPoolSize(5); // 线程池大小
        builder.threadPriority(Thread.NORM_PRIORITY - 2); // 设置线程优先级
        builder.denyCacheImageMultipleSizesInMemory(); // 不允许在内存中缓存同一张图片的多个尺寸
        builder.tasksProcessingOrder(QueueProcessingType.LIFO); // 设置处理队列的类型，包括LIFO， FIFO
        builder.memoryCache(new LruMemoryCache(3 * 1024 * 1024)); // 内存缓存策略
        builder.memoryCacheSize(5 * 1024 * 1024);  // 内存缓存大小
        builder.memoryCacheExtraOptions(480, 800); // 内存缓存中每个图片的最大宽高
        builder.memoryCacheSizePercentage(50); // 内存缓存占总内存的百分比
        builder.diskCacheSize(50 * 1024 * 1024); // 设置磁盘缓存的大小
        builder.diskCacheFileCount(50); // 磁盘缓存文件数量
        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator()); // 磁盘缓存时图片名称加密方式
        builder.imageDownloader(new BaseImageDownloader(this)); // 图片下载器
        builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        builder.writeDebugLogs(); // 打印日志

        ImageLoader.getInstance().init(builder.build());
    }
    public static DisplayImageOptions getoptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher_background) // 加载过程中的显示图片
                .showImageForEmptyUri(R.drawable.ic_launcher_background) // 路径为空时显示的图片
                .showImageOnFail(R.drawable.ic_launcher_background) // 加载失败显示的图片
                .resetViewBeforeLoading(false) // 将要开始加载时是否需要替换成onLoading图片
                .delayBeforeLoading(1000) // 加载延迟时间
                .cacheInMemory(true) // 需要缓存在内存中
                .cacheOnDisk(true) // 0
                .considerExifParams(true) // 是否考虑Exif参数
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // 缩放类型
                .bitmapConfig(Bitmap.Config.RGB_565) // bitmap模式
                .displayer(new RoundedBitmapDisplayer(20)) // 设置图片显示形式(圆角 or 渐变等)
                .build();
        return options;
    }
}
