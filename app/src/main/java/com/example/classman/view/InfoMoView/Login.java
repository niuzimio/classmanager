package com.example.classman.view.InfoMoView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classman.R;
import com.example.classman.service.UserService;
import com.example.classman.view.Manager;


public class Login extends AppCompatActivity {
    TextView t1,t2,t3;
    EditText e1,e2;
    Button b1,b2,b3;
    RadioButton r1,r2;
    Cursor cursor;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText) findViewById(R.id.editText1);
        e2=(EditText) findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2) ;
        b3=(Button)findViewById(R.id.button3);
        b1.setOnClickListener(new Click());
        b2.setOnClickListener(new Click());
        b3.setOnClickListener(new Click());
        userService=new UserService(this);
    }
    protected  void onDestory()
    {
        super.onDestroy();
    }
    class Click implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    String u = e1.getText().toString();//获取EditText控件值
                    String p = e2.getText().toString();
                    cursor=userService.login(u,p);
                    int count=cursor.getCount();
                    if (count!=0) {
                        cursor.moveToFirst();
                        String power = cursor.getString(5);
                            if (power.equals("班主任")) {
                                Intent intent = new Intent();
                                intent.setClass(Login.this, Manager.class);
                                intent.putExtra("cmd", 0);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "无此人", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_LONG).show();
                        }
                        break;
                case R.id.button2:
                    Intent intent = new Intent();
                    intent.setClass(Login.this, Register.class);
                    intent.putExtra("cmd", 1);
                    startActivity(intent);
                    break;
                case R.id.button3:
                    e1.setText("");
                    e2.setText("");
                    break;
            }
        }
    }
}
