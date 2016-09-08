package prodigalwang.bitmapthreecache.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存
 * 在Android2.3以上,软引用和弱应用会被垃圾回收期回收的相当快,这时内存缓存就相当于没有存在,
 * 而大部分资源又从文件缓存中获取,这是没有意义的.所以采用谷歌推荐新的API:LruCache
 */
public class MemoryCacheUtils {


    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new
    // HashMap<String, SoftReference<Bitmap>>();
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        //给LruCache手动设置一个内存缓存上限,当大小超过时就回收一部分.底部采用了HashMap.
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;// 模拟器默认是16M
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存大小
                return byteCount;
            }
        };
    }

    /**
     * 从内存读
     *
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url) {
        // SoftReference<Bitmap> softReference = mMemoryCache.get(url);
        // if (softReference != null) {
        // Bitmap bitmap = softReference.get();
        // return bitmap;
        // }
        return mMemoryCache.get(url);
    }

    /**
     * 写内存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        // SoftReference<Bitmap> softReference = new
        // SoftReference<Bitmap>(bitmap);
        // mMemoryCache.put(url, softReference);
        mMemoryCache.put(url, bitmap);
    }
}
