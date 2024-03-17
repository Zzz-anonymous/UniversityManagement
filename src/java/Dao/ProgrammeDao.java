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

}
