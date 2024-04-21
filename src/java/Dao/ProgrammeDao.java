/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Programme;

import adt.ArrayList;
import adt.ListInterface;
import adt.ListInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */

public class ProgrammeDao {
//    public static Programme findProgrammeByName(String name) {
//        ListInterface<Programme> programme = initializeProgrammes();
//
//        for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
//            Programme p = programme.getData(i);
//            if (p.getName().equals(name)) {
//                return p;
//            }
//        }
//
//        // If no matching programme is found, return null or throw an exception
//        return null;
//    }
//
//    public static Programme findProgrammeById(String id) {
//        ListInterface<Programme> programme = initializeProgrammes();
//
//        for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
//            Programme p = programme.getData(i);
//            if (p.getName().equals(id)) {
//                return p;
//            }
//        }
//
//        // If no matching programme is found, return null or throw an exception
//        return null;
//    }
//    
//        public static Programme getProgrammeById(String id) {
//        ListInterface<Programme> programme = initializeProgrammes();
//
//        for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
//            Programme p = programme.getData(i);
//            if (p.getId().equals(id)) {
//                return p;
//            }
//        }
//
//        // If no matching programme is found, return null or throw an exception
//        return null;
//    }
//    
//    
//    // check the index number based on id
//    public static int getIndex(String id, ListInterface<Programme> list) {
//        // Trim the provided ID to remove leading and trailing whitespace
//        String trimmedId = id.trim();
//
//        // Get an iterator for the LinkedList
//        Iterator<Programme> iterator = list.getIterator();
//
//        // Initialize index counter
//        int index = 1; // Adjusted to zero-based indexing
//
//        // Iterate over the list
//        while (iterator.hasNext()) {
//            Programme p = iterator.next();
//            if (p != null) {
//                if (p.getId().equals(trimmedId)) {
//                    // If student ID matches, return the index
//                    return index;
//                }
//            }
//            // Increment index counter
//            index++;
//        }
//
//        // If student ID is not found, return -1
//        return -1;
//    }
    
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


}
