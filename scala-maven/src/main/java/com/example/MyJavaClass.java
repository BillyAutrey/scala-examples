package com.example;

class MyJavaClass{
    private MyScalaClass scalaClass;

    public MyJavaClass(){
        scalaClass = new MyScalaClass();
    }

    public String callScalaClass(String input){
        return "Java String, plus input from " + input + ", as well as a call to " + scalaClass.getLanguage();
    }
}