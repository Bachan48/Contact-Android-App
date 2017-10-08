package com.example.bacha.employees_contact_project.employee;

/**
 * Created by bachan on 3/24/2017.
 */

public class EmployeeDTO {
    private String employeeName;
    private String phoneHome;
    private String phoneOffice;
    private String phoneMobile;
    private String email;


    public EmployeeDTO(){

    }
    public EmployeeDTO(String employeeName, String phoneMobile, String email) {
        this(employeeName, " ", phoneMobile, email);
    }

    public EmployeeDTO(String employeeName, String phoneOffice, String phoneMobile, String email) {
        this(employeeName, " ", " ", phoneMobile, email);

    }

    public EmployeeDTO(String employeeName, String phoneHome, String phoneOffice, String phoneMobile, String email) {
        this.employeeName = employeeName;
        this.phoneHome = phoneHome;
        this.phoneOffice = phoneOffice;
        this.phoneMobile = phoneMobile;
        this.email = email;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public String getEmail() {
        return email;
    }
}
