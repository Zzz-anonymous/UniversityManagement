/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import adt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import adt.StackInterface;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
public class HistoryDao {

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
