package com.fl.my;

import java.util.*;
import java.util.stream.Collectors;

public class Student {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private String id;

    private String name;

    private Integer age;


    public static void main(String[] args) {
        List<Student> list = new ArrayList<> ();
        for (int i = 1; i < 5; i++) {
            Student stu = new Student ();
            stu.setId (UUID.randomUUID ().toString ());
            stu.setName ("NAME" + i);
            if(i==2||i==3){
                stu.setAge (20);
            }else{
                stu.setAge (20 + i);
            }
            list.add (stu);
        }
        System.out.println (list.size ());
        List<Student> unique = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<> (Comparator.comparing(Student::getAge))), ArrayList::new)
        );
        System.out.println (unique.size ());


    }

}
