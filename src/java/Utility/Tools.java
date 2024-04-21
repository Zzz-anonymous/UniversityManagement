/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import adt.*;
import Entity.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class Tools {

    // Define cTypesList as a class-level variable
    private static ListInterface<String> cTypesList = new LinkedList<>();

    // Method to initialize courseTypes with course types
    public static ListInterface<String> initializeCourseTypes() {
        cTypesList.reset();
        // Populate cTypesList with some example course types
        cTypesList.add("Tutorial");
        cTypesList.add("Lecture");
        cTypesList.add("Practical");

        // Return a copy of the list to avoid direct modification of the original list
        return cTypesList;
    }
    
    
    
    
    private final static StackInterface<String> stack = new LinkedStack<>();

    public static void addHistory(String history) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Create a formatter with the desired format pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // Format the LocalDateTime object
        String formattedDateTime = currentDateTime.format(formatter);
        stack.push(formattedDateTime + ": " + history);
    }

    public static ListInterface<String> printHistory() {
        ListInterface<String> copiedStack = new LinkedList<>();

        // Iterate over the elements in the original stack and copy them to the copiedStack
        Iterator<String> iterator = stack.getIterator();
        while (iterator.hasNext()) {
            copiedStack.add(iterator.next());
        }

        return copiedStack;
    }


    
}
