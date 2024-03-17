/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import adt.*;
import Entity.Programme;
import Entity.Tutor;

/**
 *
 * @author Zy
 */
public class Tools {

    private static final String PREFIX = "S";
    private static final int ID_LENGTH = 5; // Length of the numeric portion of the ID

    public static String generateID(int number) {
        StringBuilder sb = new StringBuilder();

        // Append the prefix
        sb.append(PREFIX);

        // Append the numeric portion
        String numericPart = String.format("%0" + ID_LENGTH + "d", number);
        sb.append(numericPart);

        return sb.toString();
    }


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
        tList.add(new Tutor("T1001", "Zhong Li", "123456789101", "Male", "abc@123.com", "Doctoral"));
        tList.add(new Tutor("T1002", "Raiden Shogun", "123456789101", "Female", "abc@123.com", "Master"));
        tList.add(new Tutor("T1003", "Yae Miko", "123456789101", "Female", "abc@123.com", "Master"));
        tList.add(new Tutor("T1004", "Klee", "123456789101", "Female", "abc@123.com", "Degree"));
        tList.add(new Tutor("T1005", "Ningguang", "123456789101", "Female", "abc@123.com", "Master"));
        tList.add(new Tutor("T1006", "Xian Yun", "123456789101", "Female", "abc@123.com", "Master"));
        tList.add(new Tutor("T1007", "Neuvillette", "123456789101", "Male", "abc@123.com", "Master"));
        tList.add(new Tutor("T1008", "Alhaitham", "123456789101", "Male", "abc@123.com", "Doctoral"));
        tList.add(new Tutor("T1009", "Beidou", "123456789101", "Female", "abc@123.com", "Degree"));
        tList.add(new Tutor("T1010", "Nahida", "123456789101", "Female", "abc@123.com", "Master"));
        tList.add(new Tutor("T1011", "Xiao", "123456789101", "Male", "abc@123.com", "Degree"));
        tList.add(new Tutor("T1012", "Yelan", "123456789101", "Female", "abc@123.com", "Degree"));
        return tList;
    }
}
