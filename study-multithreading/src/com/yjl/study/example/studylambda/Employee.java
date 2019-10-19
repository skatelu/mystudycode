package com.yjl.study.example.studylambda;

import jdk.nashorn.internal.objects.annotations.Getter;

public class Employee {
    public String name;
    public Integer age;
    private Double scalay;
    private Integer id;
    private Status status;

    public Employee() {
    }

    public Employee(String name, Integer age, Double scalay, Status status) {
        this.name = name;
        this.age = age;
        this.scalay = scalay;
        this.status = status;
    }

    public Employee(String name, Integer age, Double scalay) {
        this.name = name;
        this.age = age;
        this.scalay = scalay;
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

    public Double getScalay() {
        return scalay;
    }

    public void setScalay(Double scalay) {
        this.scalay = scalay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (age != employee.age) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (scalay != null ? !scalay.equals(employee.scalay) : employee.scalay != null) return false;
        return id != null ? id.equals(employee.id) : employee.id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (scalay != null ? scalay.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", scalay=" + scalay +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public enum Status{
        Free,
        Busy,
        VOCATION;
    }
}
