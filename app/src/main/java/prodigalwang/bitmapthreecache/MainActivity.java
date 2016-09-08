package prodigalwang.bitmapthreecache;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import prodigalwang.bitmapthreecache.bitmap.MyBitmapUtils;

public class MainActivity extends AppCompatActivity {


    private ListView lv_bitmap;
    private ArrayList<String> list;
    private BitmapAdapter bitmapAdapter;
    private MyBitmapUtils myBitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lv_bitmap = (ListView) findViewById(R.id.lv_bitmap);

        initData();
    }

    private void initData() {
        String url;
        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            url = "http://10.0.2.2:8080/images/" + i + ".jpg";//本地Tomcat模拟下载十张图片
            list.add(url);
        }
        bitmapAdapter = new BitmapAdapter(MainActivity.this, list);
        lv_bitmap.setAdapter(bitmapAdapter);
        myBitmapUtils = new MyBitmapUtils();
    }

    private class BitmapAdapter extends BaseAdapter {

        private ViewHolder viewHolder;

        private Context context;
        private ArrayList<String> list;

        public BitmapAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
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

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_bitmap, null);
                viewHolder = new ViewHolder();
                viewHolder.image = (ImageView) convertView.findViewById(R.id.bitmap);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            myBitmapUtils.loadImage(viewHolder.image, (String) getItem(position));

            return convertView;
        }

        class ViewHolder {
            private ImageView image;
        }
    }
}
