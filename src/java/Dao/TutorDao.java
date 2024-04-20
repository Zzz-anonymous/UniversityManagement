/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Tutor;
import static Utility.Tools.initializeTutors;
import adt.ListInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class TutorDao {

    public static Tutor findTutorByName(String name) {
        ListInterface<Tutor> tutor = initializeTutors();

        for (int i = 1; i <= tutor.getTotalNumberOfData(); i++) {
            Tutor t = tutor.getData(i);
            if (t.getName().equals(name)) {
                return t;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }
    
    // check the index number based on id
    public static int getIndex(String id, ListInterface<Tutor> list) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<Tutor> iterator = list.getIterator();

        // Initialize index counter
        int index = 1;

        // Iterate over the list
        while (iterator.hasNext()) {
            Tutor t = iterator.next();
            if (t != null) {
                if (t.getId().equals(trimmedId)) {
                    // If student ID matches, return the index
                    return index;
                }
            }
            // Increment index counter
            index++;
        }

        // If student ID is not found, return -1
        return -1;
    }
    
    // get tutor object by id
    public static Tutor getTutorById(String id) {
        ListInterface<Tutor> tutor = initializeTutors();

        for (int i = 1; i <= tutor.getTotalNumberOfData(); i++) {
            Tutor t = tutor.getData(i);
            if (t.getName().equals(id)) {
                return t;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }

}
