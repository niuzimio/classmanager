package com.example.classman.service;

import android.content.Context;
import android.database.Cursor;

import com.example.classman.dao.UserDao;

public class UserService {
    UserDao userDao;
    public UserService(Context context)
    {
        userDao = new UserDao(context);
    }
    public Cursor login(String username, String password) { return  userDao.login(username,password); }
    public Cursor slectBy(String cla, String power) { return  userDao.selectBy(cla,power); }
    public Cursor slectBycla(String cla) { return  userDao.selectBycla(cla); }
    public Cursor slectBypower(String power) { return  userDao.selectBypower(power); }
    public Cursor slectById(String id) { return  userDao.selectById(id); }
    public long register(String[] strArray)
    {
        return  userDao.insert(strArray);
    }
    public Cursor select()
    {
        return  userDao.select();
    }
    public void delete(String id){ userDao.delete(id); }
    public int update(String id,String[] strArray){return  userDao.update(id,strArray);}
}
