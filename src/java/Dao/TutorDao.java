/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Tutor;
import adt.ArrayList;
import adt.ListInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class TutorDao {

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
}
