package com.example.classman.view.InfoMoView;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.classman.Adapter.CAdapter;
import com.example.classman.R;
import com.example.classman.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends AppCompatActivity {
    private CAdapter cAdapter;
    private ListView dbListView;
    private Cursor cursor;
    Button bu;
    final int MENU_ADD = Menu.FIRST;
    final int MENU_SELECT=Menu.FIRST+1;
    private List<String> ids;
    private List<String> names;
    private List<String> tel;
    private List<String> cla;
    private List<String> power;
    Spinner spinner,spinner1;
    AlertDialog dialog;
    UserService userService;
    int position=-1;
    int index=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        userService=new UserService(this);
        dbListView = (ListView)findViewById(R.id.db_listview);
        spinner=(Spinner)findViewById(R.id.spinner);//班级
        spinner1=(Spinner)findViewById(R.id.spinner2);//职位
        bu=(Button)findViewById(R.id.button) ;
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=1;
                refreshDBOpenHelper();
            }
        });
        refreshDBOpenHelper();
        dbListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Intent intent= new Intent();
                intent.putExtra("cmd", 0);
                String contants_id = ids.get(position);
                intent.putExtra("id", contants_id);
                intent.setClass(UserInfo.this, Register.class);
                startActivity(intent);
            }
        });
        dbListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {
                dialog = new AlertDialog.Builder(UserInfo.this)
                        .setTitle("提示!!")
                        .setMessage("确定要删除这条记录？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String contants_id = ids.get(position);
                                        userService.delete(contants_id);
                                        Toast.makeText(UserInfo.this, "正在删除数据库，请稍后...", Toast.LENGTH_LONG).show();
                                        refreshDBOpenHelper();
                                        cAdapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        refreshDBOpenHelper();
        cAdapter.notifyDataSetChanged();
        super.onResume();

    }

    public void refreshDBOpenHelper(){
        if(index==0)
        {
            cursor = userService.select();
        }
           else
        {
            String u = spinner.getSelectedItem().toString();
            String p =spinner1.getSelectedItem().toString();
            if(u.equals("全部")&&p.equals("全部"))
            {
                cursor = userService.select();
            }
            else  if(u.equals("全部"))
            {
                cursor=userService.slectBypower(p);
            }
            else  if(p.equals("全部"))
            {
                cursor=userService.slectBycla(u);
            }
            else  {
                cursor = userService.slectBy(u, p);
            }
        }
        ids = new ArrayList<String>();
        names = new ArrayList<String>();
        tel = new ArrayList<String>();
        cla  = new ArrayList<String>();
        power = new ArrayList<String>();

        int count = cursor.getCount();
        if(count>0){
            for(int i=0;i<count;i++){
                cursor.moveToPosition(i);
                ids.add(cursor.getString(0));
                names.add(cursor.getString(1));
                tel.add(cursor.getString(3));
                cla.add(cursor.getString(4));
                power.add(cursor.getString(5));
            }
        }else{
            Toast.makeText(this,"不存在", Toast.LENGTH_SHORT).show();
        }
        cAdapter = new CAdapter(this,names,cla,power,tel);
        dbListView.setAdapter(cAdapter);
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ADD, 0,"用户录入");
        menu.add(0,MENU_SELECT,0,"查询全部");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){		//
            case MENU_ADD:			//press change add button
                Intent add_intent= new Intent(this, Register.class);
                add_intent.putExtra("cmd", 1);
                startActivity(add_intent);
                break;
            case MENU_SELECT:
                index =0;
                refreshDBOpenHelper();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
