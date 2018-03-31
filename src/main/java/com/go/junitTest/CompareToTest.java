package com.go.junitTest;

public class CompareToTest implements Comparable<CompareToTest> {

    private int age;
    private float height;
    private String name;

    //compareTo 多个关键领域比较
    public int compareTo(CompareToTest o) {
        if (this.age > o.age) {
            return 1;
        }
        if (this.age < o.age) {
            return 0;
        }
        if (this.height > o.height) {
            return 1;
        }
        if (this.height < o.height) {
            return 0;
        }
        return this.name.compareTo(o.name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        CompareToTest compareToTest = new CompareToTest();
        compareToTest.setName("eriz");
//        compareToTest.setAge(14);
        CompareToTest compareToTest1 = new CompareToTest();
        compareToTest1.setName("rose");
//        compareToTest1.setAge(13);
        int result = compareToTest.compareTo(compareToTest1);
        System.out.println(result);
    }
}
