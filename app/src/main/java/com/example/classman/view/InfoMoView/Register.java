package com.example.classman.view.InfoMoView;
import android.app.job.JobInfo;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.classman.R;
import com.example.classman.service.UserService;
public class Register extends AppCompatActivity {
    int[] textIds = {
            R.id.editText1,
            R.id.editText2,
            R.id.editText3
    };
    Cursor cursor;
    Spinner spinner,spinner1;
    UserService userService;
    EditText[] textArray;
    Button saveButton;
    int status = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        textArray = new EditText[textIds.length];
        for (int i = 0; i < textIds.length; i++) {
            textArray[i] = (EditText) findViewById(textIds[i]);
        }
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        initSaveImageButton();
        userService =new UserService(this);
        Intent intent = getIntent();
        status = intent.getExtras().getInt("cmd");
        switch (status) {
            case 0:
                String contants_id = intent.getExtras().getString("id");
                cursor =userService.slectById(contants_id);
                int count = cursor.getCount();
                if (count == 0) {
                    Toast.makeText(this, "对不起，没有找到！", Toast.LENGTH_LONG).show();
                } else {
                    cursor.moveToFirst();
                    textArray[0].setText(cursor.getString(1));
                    textArray[1].setText(cursor.getString(2));
                    textArray[2].setText(cursor.getString(3));
                }
                cursor.close();
                break;
            case 1:
                for (EditText et : textArray) {
                    et.getEditableText().clear();
                }
                break;
        }
    }

    public void initSaveImageButton() {
        saveButton = (Button) findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String[] strArray = new String[textArray.length + 2];
                for (int i = 0; i < textArray.length; i++) {
                    strArray[i] = textArray[i].getText().toString().trim(); //获得用户输入的信息数组
                }
                strArray[textArray.length+1] = spinner.getSelectedItem().toString();
                strArray[textArray.length] = spinner1.getSelectedItem().toString();

                if (strArray[0].equals("") || strArray[1].equals("")) {
                    Toast.makeText(Register.this, "对不起，请将信息完整!", Toast.LENGTH_LONG).show();
                } else {
                    switch (status) {  //判断当前的状态
                        case 0:    //查询联系人详细信息时按下保存
                            updateContact(strArray);  //更新联系人信息
                            break;
                        case 1:    //新建联系人时按下保存按钮
                            insertContact(strArray);  //插入联系人信息
                            break;

                    }
                }
            }
        });
    }

    public void insertContact(String[] strArray) {
        long count = userService.register(strArray);  //插入数据
        if (count == -1) {
            Toast.makeText(this, "添加用户失败！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "添加用户成功！", Toast.LENGTH_LONG).show();
        }
    }
    public void updateContact(String [] strArray){
            Intent intent = getIntent();
            String id= intent.getExtras().getString("id");
            int count =userService.update(id+"", strArray); //更新数据库
            if(count == 1){
                Toast.makeText(this, "修改信息成功！", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "修改信息失败！", Toast.LENGTH_LONG).show();
            }
        }

    }

