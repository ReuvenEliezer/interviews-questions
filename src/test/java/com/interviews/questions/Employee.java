package com.interviews.questions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private Double salary;
    private LocalDateTime joiningDate;
    private List<String> list;

    public Employee(int id, String name, double salary, LocalDateTime joiningDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.joiningDate = joiningDate;
    }



    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDateTime getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDateTime joiningDate) {
        this.joiningDate = joiningDate;
    }

    // Compare Two Employees based on their ID

//    /**
//     * @param anotherEmployee - The Employee to be compared.
//     * @return A negative integer, zero, or a positive integer as this employee
//     * is less than, equal to, or greater than the supplied employee object.
//     */
//    @Override
//    public int compareTo(Employee anotherEmployee) {
//        return this.getId() - anotherEmployee.getId();
//    }

    // Two employees are equal if their IDs are equal


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
//        return Objects.hash(id, name, salary, joiningDate, list);
    }

    @Override
    public int compareTo(Employee o) {
        return this.getSalary().compareTo(o.getSalary());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", joiningDate=" + joiningDate +
                ", list=" + list +
                '}';
    }

}
