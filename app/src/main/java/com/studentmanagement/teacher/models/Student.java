package com.studentmanagement.teacher.models;

public class Student {
    public String student_id,first_name,last_name,date_of_birth,roll_no,email_id,phone,father_name,mother_name,cgpa
    ,gender,address,city,state,pincode,profile_pic,qualification,institution,year_of_passing,current_year,standing_arrear,history_of_arrear,created_by;


    public Student() {

    }

    public Student(String student_id, String first_name, String last_name, String date_of_birth, String roll_no, String email_id, String phone, String father_name, String mother_name, String cgpa, String gender, String address, String city, String state, String pincode, String profile_pic, String qualification, String institution, String year_of_passing, String current_year, String standing_arrear, String history_of_arrear, String created_by) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.roll_no = roll_no;
        this.email_id = email_id;
        this.phone = phone;
        this.father_name = father_name;
        this.mother_name = mother_name;
        this.cgpa = cgpa;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.profile_pic = profile_pic;
        this.qualification = qualification;
        this.institution = institution;
        this.year_of_passing = year_of_passing;
        this.current_year = current_year;
        this.standing_arrear = standing_arrear;
        this.history_of_arrear = history_of_arrear;
        this.created_by = created_by;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getYear_of_passing() {
        return year_of_passing;
    }

    public void setYear_of_passing(String year_of_passing) {
        this.year_of_passing = year_of_passing;
    }

    public String getCurrent_year() {
        return current_year;
    }

    public void setCurrent_year(String current_year) {
        this.current_year = current_year;
    }

    public String getStanding_arrear() {
        return standing_arrear;
    }

    public void setStanding_arrear(String standing_arrears) {
        this.standing_arrear = standing_arrears;
    }

    public String getHistory_of_arrear() {
        return history_of_arrear;
    }

    public void setHistory_of_arrear(String history_of_arrear) {
        this.history_of_arrear = history_of_arrear;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
