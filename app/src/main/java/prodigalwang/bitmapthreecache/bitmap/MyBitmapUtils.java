package prodigalwang.bitmapthreecache.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import prodigalwang.bitmapthreecache.R;


/**
 * 自定义图片加载工具
 */
public class MyBitmapUtils {

    private static final String TAG = "bitmapCache";
    NetCacheUtils mNetCacheUtils;
    LocalCacheUtils mLocalCacheUtils;
    MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
    }

    /**
     * 加载图片,优先顺序:内存缓存>本地缓存>网络获取
     *
     * @param ivPic 图片控件对象
     * @param url   下载该图片的url
     */
    public void loadImage(ImageView ivPic, String url) {
        ivPic.setImageResource(R.drawable.nopic);// 设置默认加载图片

        Bitmap bitmap = null;
        // 从内存读
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            Log.e(TAG, "从内存读取图片啦...");

            return;
        }

        // 从本地读
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            Log.e(TAG, "从本地读取图片啦...");

            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);// 将图片保存在内存
            return;
        }

        // 从网络读
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
        Log.e(TAG, "从网络读取图片啦...");
    }

}
