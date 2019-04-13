package com.example.classman.view.InfoMoView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.classman.R;
import com.example.classman.service.UserService;

public class UserInfoChange extends AppCompatActivity {
    int[] textIds = {
            R.id.editText1,
            R.id.editText2,
            R.id.editText3
    };
    Spinner spinner,spinner1;
    UserService userService;
    EditText[] textArray;
    Button saveButton;
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
                    Toast.makeText(UserInfoChange.this, "对不起，请将信息完整!", Toast.LENGTH_LONG).show();
                } else {
                    insertContact(strArray);
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
}
