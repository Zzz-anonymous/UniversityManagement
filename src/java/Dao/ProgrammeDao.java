/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Programme;
import static Utility.Tools.initializeProgrammes;
import adt.ListInterface;
import adt.ListInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */

public class ProgrammeDao {
    public static Programme findProgrammeByName(String name) {
        ListInterface<Programme> programme = initializeProgrammes();

        for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
            Programme p = programme.getData(i);
            if (p.getName().equals(name)) {
                return p;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }

    public static Programme findProgrammeById(String id) {
        ListInterface<Programme> programme = initializeProgrammes();

        for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
            Programme p = programme.getData(i);
            if (p.getName().equals(id)) {
                return p;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }
    
        public static Programme getProgrammeById(String id) {
        ListInterface<Programme> programme = initializeProgrammes();

        for (int i = 1; i <= programme.getTotalNumberOfData(); i++) {
            Programme p = programme.getData(i);
            if (p.getId().equals(id)) {
                return p;
            }
        }

        // If no matching programme is found, return null or throw an exception
        return null;
    }
    
    
    // check the index number based on id
    public static int getIndex(String id, ListInterface<Programme> list) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        // Get an iterator for the LinkedList
        Iterator<Programme> iterator = list.getIterator();

        // Initialize index counter
        int index = 1; // Adjusted to zero-based indexing

        // Iterate over the list
        while (iterator.hasNext()) {
            Programme p = iterator.next();
            if (p != null) {
                if (p.getId().equals(trimmedId)) {
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

}
