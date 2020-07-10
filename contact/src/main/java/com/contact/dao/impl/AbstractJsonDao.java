package com.contact.dao.impl;

import com.contact.dao.IDao;
import com.contact.model.Contact;
import com.contact.observer.MySubjectObserver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    AbstractJsonDao(Class<T> myClass, MySubjectObserver subject, String jsonPathFile) {
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
            JSONArray data = (JSONArray) jsonObject.get("contacts");

            for (Object o: data) {
                JSONObject obj = (JSONObject) o;
                this.obj.add(constructor.newInstance(
                        Integer.parseInt(obj.get("id").toString()),
                        obj.get("prenom").toString(),
                        obj.get("nom").toString(),
                        obj.get("email").toString(),
                        obj.get("telephone").toString(),
                        obj.get("rue").toString(),
                        obj.get("ville").toString(), obj.get("codePostal").toString()));
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
        HashMap<String,Object> object = new HashMap<>();
        FileWriter file = null;

        try {
            FileReader reader = new FileReader(jsonPathFile);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) jsonObject.get("contacts");

            if (t != null && !this.obj.contains(t)) {
                if(this.sendNotification()) {
                    this.mySubject.setContact((Contact) t);
                    this.mySubject.setState(1);
                }
                Method prenom = t.getClass().getMethod("getPrenom");
                Method nom = t.getClass().getMethod("getNom");
                Method email = t.getClass().getMethod("getEmail");
                Method telephone = t.getClass().getMethod("getTelephone");
                Method rue = t.getClass().getMethod("getRue");
                Method ville = t.getClass().getMethod("getVille");
                Method codePostal = t.getClass().getMethod("getCodePostal");

                object.put("id", generateIdNewPersonne());
                object.put("nom", nom.invoke(t));
                object.put("prenom", prenom.invoke(t));
                object.put("email", email.invoke(t));
                object.put("telephone", telephone.invoke(t));
                object.put("rue", rue.invoke(t));
                object.put("ville", ville.invoke(t));
                object.put("codePostal", codePostal.invoke(t));
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
        Iterator <T> iter = this.obj.iterator();
        JSONParser parser = new JSONParser();
        FileReader reader;
        FileWriter file = null;
        while (iter.hasNext()) {
            T p = iter.next();
            if (p.equals(t)) {
                try {
                    // Remove contact from ArrayList and JSONObject
                    iter.remove();
                    reader = new FileReader(jsonPathFile);
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);
                    JSONArray data = (JSONArray) jsonObject.get("contacts");

                    Method id = t.getClass().getMethod("getId");

                    if (Integer.parseInt(id.invoke(t).toString()) <= data.size())
                        data.remove(data.get(Integer.parseInt(id.invoke(t).toString()) - 1));
                    this.obj.remove(t);

                    // Update file after removing object
                    file = new FileWriter(this.jsonPathFile);
                    file.write(jsonObject.toJSONString());
                    file.flush();

                    if(this.sendNotification()) {
                        this.mySubject.setContact((Contact) t);
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
    public Integer generateIdNewPersonne(){
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
        HashMap<String,Object> object = new HashMap<>();
        FileWriter file = null;

        try {
            FileReader reader = new FileReader(jsonPathFile);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) jsonObject.get("contacts");

            tmp = t;

            if (t != null) {
                this.delete(t);
                if(this.sendNotification()) {
                    this.mySubject.setContact((Contact) tmp);
                    this.mySubject.setState(1);
                }
                Method getId = tmp.getClass().getMethod("getId");
                Method getPrenom = tmp.getClass().getMethod("getPrenom");
                Method getNom = tmp.getClass().getMethod("getNom");
                Method getEmail = tmp.getClass().getMethod("getEmail");
                Method getTelephone = tmp.getClass().getMethod("getTelephone");
                Method getRue = tmp.getClass().getMethod("getRue");
                Method getVille = tmp.getClass().getMethod("getVille");
                Method getCodePostal = tmp.getClass().getMethod("getCodePostal");


                object.put("id", getId.invoke(tmp));
                object.put("nom", getNom.invoke(tmp));
                object.put("prenom", getPrenom.invoke(tmp));
                object.put("email", getEmail.invoke(tmp));
                object.put("telephone", getTelephone.invoke(tmp));
                object.put("rue", getRue.invoke(tmp));
                object.put("ville", getVille.invoke(tmp));
                object.put("codePostal", getCodePostal.invoke(tmp));
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
