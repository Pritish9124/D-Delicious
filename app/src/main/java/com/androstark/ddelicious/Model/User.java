package com.androstark.ddelicious.Model;

import java.lang.ref.SoftReference;

/**
 * Created by SALIM on 2/4/2018.
 */

public class User
{
    private String Name;
    private String Password;
    private String Phone;

    public User()
    {

    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {
        Password = password;
    }
}
