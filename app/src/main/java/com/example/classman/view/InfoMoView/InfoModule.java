package com.example.classman.view.InfoMoView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.classman.R;

import java.util.ArrayList;
import java.util.List;
public class InfoModule extends Activity {
    private GridView gv;
    private String[] title = { "用户信息管理", "学生信息管理", "团员信息管理" };
    private int[] image = { R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gv = (GridView) findViewById(R.id.gv);
        MyAdapter adapter = new MyAdapter(this);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                switch (position) {
                    case 0:
                    {
                        Intent intent1 = new Intent();
                        intent1.setClass(InfoModule.this, UserInfo.class);
                        startActivity(intent1);
                        break;
                    }
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.setClass(InfoModule.this, Login.class);
                        startActivity(intent1);
                        break;

                }
            }
        });
    }
    class MyAdapter extends BaseAdapter {// 创建适配器
        private List<PicEntity> list = new ArrayList<>();
        private Context context;
        public MyAdapter(Context context) {
            this.context = context;
            for (int i = 0; i < image.length; i++) {
                PicEntity picEntity = new PicEntity(title[i], image[i]);
                list.add(picEntity);
            }
        }
        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
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
            Viewholder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
                vh = new Viewholder();
                vh.iv_pic = (ImageView) convertView.findViewById(R.id.iv_image);
                vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(vh);
            } else {
                vh = (Viewholder) convertView.getTag();
            }
            vh.iv_pic.setImageResource(list.get(position).imageId);
            vh.tv_title.setText(list.get(position).title);
            return convertView;
        }
    }

    class Viewholder {
        TextView tv_title;
        ImageView iv_pic;
    }

    class PicEntity {// 创建实体类
        private String title;
        private int imageId;

        public PicEntity() {
            super();
        }

        public PicEntity(String title, int imageId) {
            super();
            this.title = title;
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }
}
