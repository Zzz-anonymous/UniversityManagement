/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Tutor;
import static Utility.Tools.initializeTutors;
import adt.ListInterface;

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

}
