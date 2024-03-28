/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import adt.*;
import Entity.*;

/**
 *
 * @author Zy
 */
public class Tools {

    // Define cTypesList as a class-level variable
    private static LinkedListInterface<String> cTypesList = new LinkedList<>();
    // Create a list of courses for the programme
    private static LinkedListInterface<Course> courses = new LinkedList<>();

    // Method to initialize courseTypes with course types
    public static LinkedListInterface<String> initializeCourseTypes() {
        cTypesList.clear();
        // Populate cTypesList with some example course types
        cTypesList.add("Tutorial");
        cTypesList.add("Lecture");
        cTypesList.add("Practical");

        // Return a copy of the list to avoid direct modification of the original list
        return cTypesList;
    }
    
    
    //  Method to return a collection of with hard-coded programme values
    public static ListInterface<Faculty> initializeFaculties() {
        ListInterface<Faculty> fList = new ArrayList<>();
        fList.add(new Faculty( "FCCI","Faculty of Communication and Creative Industries"));

        fList.add(new Faculty( "FSSH","Faculty of Social Science and Humanities"));
       
        fList.add(new Faculty( "FOCS","Faculty of Computing and Information Technology"));

        fList.add(new Faculty( "FOAS","Faculty of Applied Sciences"));
 
        fList.add(new Faculty( "FOBE","Faculty of Built Environment"));

        fList.add(new Faculty( "FAFB","Faculty of Accountancy, Finance and Business"));
  
        return fList;
    }
    
    //  Method to return a collection of with hard-coded programme values
    public static ListInterface<Programme> initializeProgrammes() {
        ListInterface<Programme> pList = new ArrayList<>();
        pList.add(new Programme("RML", "Creative Multimedia"));
        pList.add(new Programme("RBC", "Broadcasting"));
        pList.add(new Programme("RPY", "Psychology"));
        pList.add(new Programme("REU", "English Studies"));
        pList.add(new Programme("RSD", "Sofware System Development"));
        pList.add(new Programme("RIT", "Internet Techonology"));
        pList.add(new Programme("RSP", "Sports Coaching and Performance"));
        pList.add(new Programme("RSE", "Sports and Exercise Science"));
        pList.add(new Programme("RES", "Real Estate Management"));
        pList.add(new Programme("RBD", "Construction Management and Economics"));
        pList.add(new Programme("RAC", "Accounting"));
        pList.add(new Programme("RFI", "Finance and Investment"));
        return pList;
    }

    //  Method to return a collection of with hard-coded tutor values
    public static ListInterface<Tutor> initializeTutors() {
        ListInterface<Tutor> tList = new ArrayList<>();
        tList.add(new Tutor("T1001", "Zhong Li", "Male", "abc@123.com", 1, "Doctoral"));
        tList.add(new Tutor("T1002", "Raiden Shogun", "Female", "abc@123.com", 1, "Master"));
        tList.add(new Tutor("T1003", "Yae Miko", "Female", "abc@123.com", 1, "Master"));
        tList.add(new Tutor("T1004", "Klee", "Female", "abc@123.com", 1, "Degree"));
        tList.add(new Tutor("T1005", "Ningguang", "Female", "abc@123.com", 1, "Master"));
        tList.add(new Tutor("T1006", "Xian Yun", "Female", "abc@123.com", 1, "Master"));
        tList.add(new Tutor("T1007", "Neuvillette", "Male", "abc@123.com", 1, "Master"));
        tList.add(new Tutor("T1008", "Alhaitham", "Male", "abc@123.com", 1, "Doctoral"));
        tList.add(new Tutor("T1009", "Beidou", "Female", "abc@123.com", 1, "Degree"));
        tList.add(new Tutor("T1010", "Nahida", "Female", "abc@123.com", 1, "Master"));
        tList.add(new Tutor("T1011", "Xiao", "Male", "abc@123.com", 1, "Degree"));
        tList.add(new Tutor("T1012", "Yelan", "Female", "abc@123.com", 1, "Degree"));
        return tList;
    }

    //  Method to return a collection of with hard-coded entity values
    public static ListInterface<Student> initializeStudents() {
        ListInterface<Student> sList = new ArrayList<>();
        sList.add(new Student("S0001", "Bennett", "Male", "abc@123.com", 1, 0, "RSE"));
        sList.add(new Student("S0002", "Amber", "Female", "abc@123.com", 1, 0, "RSP"));
        sList.add(new Student("S0003", "Dori", "Female", "abc@123.com", 1, 0, "RAC"));
        sList.add(new Student("S0004", "Kaveh", "Male", "abc@123.com", 1, 0, "RES"));
        sList.add(new Student("S0005", "Qiqi", "Female", "abc@123.com", 1, 0, "RPY"));
        sList.add(new Student("S0006", "Tighnari", "Male", "abc@123.com", 1, 0, "RML"));
        sList.add(new Student("S0007", "Xiangling", "Female", "abc@123.com", 1, 0, "RSD"));
        sList.add(new Student("S0008", "Xingqiu", "Male", "abc@123.com", 1, 0, "REU"));
        sList.add(new Student("S0009", "Chongyun", "Male", "abc@123.com", 1, 0, "RBD"));
        sList.add(new Student("S0010", "Collei", "Female", "abc@123.com", 1, 0, "RAC"));
        sList.add(new Student("S0011", "Furina", "Female", "abc@123.com", 1, 0, "RBC"));
        sList.add(new Student("S0012", "Kirara", "Female", "abc@123.com", 1, 0, "RIT"));
        return sList;
    }
}
