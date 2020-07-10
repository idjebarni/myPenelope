package com.user.dao.impl;

import com.user.dao.IDao;
import com.user.observer.UserObserver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.user.model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractJsonDao<T> extends AbstractDao<T> implements IDao<T> {

    private String jsonPathFile;
    List<T> obj = new ArrayList<>();

    AbstractJsonDao(Class<T> myClass, UserObserver subject, String jsonPathFile) {
        this.myClass = myClass;
        this.mySubject = subject;
        this.jsonPathFile = jsonPathFile;
        this.findAll();
    }

    @Override
    public List<T> findAll() {
        this.obj.clear();
        JSONParser parser = new JSONParser();
        Reader reader = null;
        try {
            reader = new FileReader(jsonPathFile);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) jsonObject.get("users");

            for (Object o : data) {
                JSONObject obj = (JSONObject) o;
                this.obj.add(constructor.newInstance(
                        Integer.parseInt(obj.get("id").toString()),
                        obj.get("username").toString(),
                        obj.get("password").toString(),
                        obj.get("firstname").toString(),
                        obj.get("lastname").toString()));
            }
        } catch (IOException | ParseException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return this.obj;
    }

    @Override
    public T findById(int id) {
        return super.findById(id, this.obj);
    }

    @Override
    public List<T> findByCriteria(String criteria, Object valueCriteria) {
        return null;
    }

    @Override
    public T create(T t) {
        JSONParser parser = new JSONParser();
        HashMap<String, Object> object = new HashMap<>();
        FileWriter file = null;

        try {
            FileReader reader = new FileReader(jsonPathFile);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) jsonObject.get("users");

            if (t != null && !this.obj.contains(t)) {
                if (this.sendNotification()) {
                    this.mySubject.setUser((User) t);
                    this.mySubject.setState(1);
                }
                Method username = t.getClass().getMethod("getUsername");
                Method password = t.getClass().getMethod("getPassword");
                Method firstname = t.getClass().getMethod("getFirstname");
                Method lastname = t.getClass().getMethod("getLastname");


                object.put("id", generateIdNewPersonne());
                object.put("username", username.invoke(t));
                object.put("password", password.invoke(t));
                object.put("firstname", firstname.invoke(t));
                object.put("lastname", lastname.invoke(t));
                // add object into JsonArray
                data.add(object);
                // add into arraylist
                this.obj.add(t);
            } else {
                System.out.println("Exist!");
            }

            //Write into the file
            file = new FileWriter(jsonPathFile);
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert file != null;
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    @Override
    public boolean delete(T t) {
        boolean flag = false;
        Iterator<T> iter = this.obj.iterator();
        JSONParser parser = new JSONParser();
        FileReader reader;
        FileWriter file = null;
        while (iter.hasNext()) {
            T p = iter.next();
            if (p.equals(t)) {
                try {
                    // Remove user from ArrayList and JSONObject
                    iter.remove();
                    reader = new FileReader(jsonPathFile);
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);

                    JSONArray data = (JSONArray) jsonObject.get("users");
                    Method id = t.getClass().getMethod("getId");

                    if (Integer.parseInt(id.invoke(t).toString()) <= data.size())
                        data.remove(data.get(Integer.parseInt(id.invoke(t).toString()) - 1));
                    this.obj.remove(t);
                    // Update file after removing object
                    file = new FileWriter(this.jsonPathFile);
                    file.write(jsonObject.toJSONString());
                    file.flush();

                    if (this.sendNotification()) {
                        this.mySubject.setUser((User) t);
                        this.mySubject.setState(2);
                    }

                } catch (IOException | ParseException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        assert file != null;
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Integer generateIdNewPersonne() {
        return super.generateIdNewPersonne(this.obj);
    }

    @Override
    public boolean sendNotification() {
        return sendNotification;
    }

    @Override
    public Integer getPersonnesSize() {
        return this.obj.size();
    }

    @Override
    public T update(T t) {

        T tmp = null;
        JSONParser parser = new JSONParser();
        HashMap<String, Object> object = new HashMap<>();
        FileWriter file = null;

        try {
            FileReader reader = new FileReader(jsonPathFile);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) jsonObject.get("users");

            tmp = t;

            if (t != null) {
                this.delete(t);
                if (this.sendNotification()) {
                    this.mySubject.setUser((User) tmp);
                    this.mySubject.setState(1);
                }

                Method getId = tmp.getClass().getMethod("getId");
                Method username = tmp.getClass().getMethod("getUsername");
                Method password = tmp.getClass().getMethod("getPassword");
                Method firstname = tmp.getClass().getMethod("getFirstname");
                Method lastname = tmp.getClass().getMethod("getLastname");


                object.put("id", getId.invoke(tmp));
                object.put("username", username.invoke(tmp));
                object.put("password", password.invoke(tmp));
                object.put("firstname", firstname.invoke(tmp));
                object.put("lastname", lastname.invoke(tmp));

                // add object into JsonArray
                data.add(object);

                // add into arraylist
                this.obj.add(tmp);

                this.delete(tmp);

            } else {
                System.out.println("Exist!");
            }

            //Write into the file
            file = new FileWriter(jsonPathFile);
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert file != null;
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
