/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Programme;
import static Utility.Tools.initializeProgrammes;
import adt.ListInterface;

/**
 *
 * @author Zy
 */


public class ProgrammeDao {
    public static Programme findProgrammeByName(String name) {
    ListInterface<Programme> programmes = initializeProgrammes();
    
    for (int i = 1; i <= programmes.getNumberOfEntries(); i++) {
        Programme programme = programmes.getEntry(i);
        if (programme.getName().equals(name)) {
            return programme;
        }
    }
    
    // If no matching programme is found, return null or throw an exception
    return null;
}

}
