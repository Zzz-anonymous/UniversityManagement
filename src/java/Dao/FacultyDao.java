/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Entity.Faculty;
import adt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import adt.StackInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class FacultyDao {

//    private final static StackInterface<String> stack = new LinkedStack<>();
//
//    public static void addHistory(String history) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        // Create a formatter with the desired format pattern
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        // Format the LocalDateTime object
//        String formattedDateTime = currentDateTime.format(formatter);
//        stack.push(formattedDateTime + ": " + history);
//    }
//
//    public static ListInterface<String> printHistory() {
//        ListInterface<String> copiedStack = new LinkedList<>();
//
//        // Iterate over the elements in the original stack and copy them to the copiedStack
//        Iterator<String> iterator = stack.getIterator();
//        while (iterator.hasNext()) {
//            copiedStack.add(iterator.next());
//        }
//
//        return copiedStack;
//    }

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
    
}
