package com.ezfun.guess.multithread.case003;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author SoySauce
 * @date 2019/9/11
 */
public class ExampleMain {
    public static void main(String[] args) throws IOException {
        Employee employee = new Employee();
        employee.setName("Trish");
        employee.setDept("Admin");

        //convert to json
        String jsonString = toJson(employee);
        System.out.println(jsonString);
        //convert to object
//        Employee e = toEmployee(jsonString);
//        System.out.println(e);
    }

    private static Employee toEmployee(String jsonData) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonData, Employee.class);
    }

    private static String toJson(Employee employee) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(employee);
    }






    private static class Employee {
        @JsonProperty("employee-name2")
        private String name;
        @JsonProperty("employee-dept2")
        private String dept;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", dept='" + dept + '\'' +
                    '}';
        }
    }
}
