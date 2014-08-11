package com.zhy.listen.bean;

import java.sql.Timestamp;
import java.util.Date;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 *
 * @author zhanghongyan
 *
 */
public class Person {
    private String name;
    private Timestamp birthday;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public static void main(String[] args) {
        Person personData = new Person();
        personData.setName("tom");
        personData.setBirthday(new Timestamp(System.currentTimeMillis()));
        JSONObject jsonPerson = JSONObject.fromObject(personData);  
        String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss"};  
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));  
        Person person = (Person)JSONObject.toBean(jsonPerson, Person.class);
        System.out.println(person);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", birthday=" + birthday + "]";
    }

    
    
    
    
}
