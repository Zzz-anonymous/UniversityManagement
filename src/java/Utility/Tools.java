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

    //  Method to return a collection of with hard-coded entity values
    public static ListInterface<Programme> initializeProgrammes() {
        ListInterface<Programme> pList = new ArrayList<>();
        pList.add(new Programme("RML","Creative Multimedia" , "FCCI"));
        pList.add(new Programme("RBC","Broadcasting",  "FCCI"));
        pList.add(new Programme("RPY","Psychology",  "FSSH"));
        pList.add(new Programme("REU", "English Studies", "FSSH"));
        pList.add(new Programme("RSD","Sofware System Development",  "FOCS"));
        pList.add(new Programme("RIT","Internet Techonology",  "FOCS"));
        pList.add(new Programme("RSP","Sports Coaching and Performance",  "FOAS"));
        pList.add(new Programme("RSE","Sports and Exercise Science",  "FOAS"));
        pList.add(new Programme("RES","Real Estate Management",  "FOBE"));
        pList.add(new Programme("RBD","Construction Management and Economics",  "FOBE"));
        pList.add(new Programme("RAC","Accounting",  "FAFB"));
        pList.add(new Programme("RFI","Finance and Investment",  "FAFB"));
        return pList;
    }

    //  Method to return a collection of with hard-coded entity values
    public static ListInterface<Tutor> initializeTutors() {
        ListInterface<Tutor> tList = new ArrayList<>();
        tList.add(new Tutor("T1001", "Zhong Li",  "Male", "abc@123.com",1, "Doctoral"));
        tList.add(new Tutor("T1002", "Raiden Shogun",  "Female", "abc@123.com",1, "Master"));
        tList.add(new Tutor("T1003", "Yae Miko", "Female", "abc@123.com",1, "Master"));
        tList.add(new Tutor("T1004", "Klee", "Female", "abc@123.com",1, "Degree"));
        tList.add(new Tutor("T1005", "Ningguang", "Female", "abc@123.com",1, "Master"));
        tList.add(new Tutor("T1006", "Xian Yun", "Female", "abc@123.com",1, "Master"));
        tList.add(new Tutor("T1007", "Neuvillette",  "Male", "abc@123.com",1, "Master"));
        tList.add(new Tutor("T1008", "Alhaitham", "Male", "abc@123.com",1, "Doctoral"));
        tList.add(new Tutor("T1009", "Beidou",  "Female", "abc@123.com",1, "Degree"));
        tList.add(new Tutor("T1010", "Nahida", "Female", "abc@123.com",1, "Master"));
        tList.add(new Tutor("T1011", "Xiao",  "Male", "abc@123.com",1, "Degree"));
        tList.add(new Tutor("T1012", "Yelan",  "Female", "abc@123.com",1, "Degree"));
        return tList;
    }
    
    //  Method to return a collection of with hard-coded entity values
    public static ListInterface<Student> initializeStudents() {
        ListInterface<Student> sList = new ArrayList<>();
        sList.add(new Student("S0001","Bennett" ,"Male", "abc@123.com",1, 0,"RSE"));
        sList.add(new Student("S0002","Amber","Female", "abc@123.com",1,  0,"RSP"));
        sList.add(new Student("S0003","Dori",  "Female", "abc@123.com",1,0,"RAC"));
        sList.add(new Student("S0004", "Kaveh","Male", "abc@123.com",1, 0,"RES"));
        sList.add(new Student("S0005","Qiqi","Female", "abc@123.com",1,  0,"RPY"));
        sList.add(new Student("S0006","Tighnari","Male", "abc@123.com",1,  0,"RML"));
        sList.add(new Student("S0007","Xiangling", "Female", "abc@123.com",1, 0,"RSD"));
        sList.add(new Student("S0008","Xingqiu", "Male", "abc@123.com",1, 0,"REU"));
        sList.add(new Student("S0009","Chongyun", "Male", "abc@123.com",1, 0,"RBD"));
        sList.add(new Student("S0010","Collei","Female", "abc@123.com",1,  0,"RAC"));
        sList.add(new Student("S0011","Furina",  "Female", "abc@123.com",1,0,"RBC"));
        sList.add(new Student("S0012","Kirara","Female", "abc@123.com",1,  0,"RIT"));
        return sList;
    }
}
