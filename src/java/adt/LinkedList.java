/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Zy
 */
import java.util.Iterator;
import java.util.function.Predicate;

public class LinkedList<T> implements LinkedListInterface<T> {

    private Node firstNode; // reference to first node
    private int totalNumberData;  	// number of data in list

    public LinkedList() {
        clear();
    }

    @Override
    public final void clear() {
        firstNode = null;
        totalNumberData = 0;
    }

    @Override
    public LinkedListInterface<T> search(Predicate<T> criteria) {
        LinkedListInterface<T> matchingData = new LinkedList<>();
        Node currentNode = firstNode;
        while (currentNode != null) {
            if (criteria.test(currentNode.data)) {
                matchingData.add(currentNode.data);
            }
            currentNode = currentNode.next;
        }
        return matchingData;
    }

    @Override
    public Iterator<T> getIterator() {
        return new LinkedListIterator();
    }

    // Iterator class to iterate over the linked list
    private class LinkedListIterator implements Iterator<T> {

        private Node currentNode = firstNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }

    @Override
    public boolean add(T newData) {
        Node newNode = new Node(newData);	// create the new node

        if (isEmpty()) {
            firstNode = newNode;
        } else {                        // add to end of nonempty list
            Node currentNode = firstNode;	// traverse linked list with p pointing to the current node
            while (currentNode.next != null) { // while have not reached the last node
                currentNode = currentNode.next;
            }
            currentNode.next = newNode; // make last node reference new node
        }

        totalNumberData++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newData) { // OutOfMemoryError possible
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= totalNumberData + 1)) {
            Node newNode = new Node(newData);

            if (isEmpty() || (newPosition == 1)) { // case 1: add to beginning of list
                newNode.next = firstNode;
                firstNode = newNode;
            } else {								// case 2: list is not empty and newPosition > 1
                Node nodeBefore = firstNode;
                for (int i = 1; i < newPosition - 1; ++i) {
                    nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
                }

                newNode.next = nodeBefore.next;	// make new node point to current node at newPosition
                nodeBefore.next = newNode;		// make the node before point to the new node
            }

            totalNumberData++;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T remove(int position) {
        T result = null;                 // return value

        if ((position >= 1) && (position <= totalNumberData)) {
            if (position == 1) {      // case 1: remove first entry
                result = firstNode.data;     // save entry to be removed
                firstNode = firstNode.next;
            } else {                         // case 2: givenPosition > 1
                Node nodeBefore = firstNode;
                for (int i = 1; i < position - 1; ++i) {
                    nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
                }
                result = nodeBefore.next.data;  // save entry to be removed
                nodeBefore.next = nodeBefore.next.next;	// make node before point to node after the
            } 																// one to be deleted (to disconnect node from chain)

            totalNumberData--;
        }

        return result; // return removed entry, or null if operation fails
    }

    @Override
    public boolean update(int position, T newData) {
        boolean isSuccessful = true;

        if ((position >= 1) && (position <= totalNumberData)) {
            Node currentNode = firstNode;
            for (int i = 0; i < position - 1; ++i) {
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            currentNode.data = newData;	// currentNode is pointing to the node at givenPosition
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getData(int position) {
        T result = null;

        if ((position >= 1) && (position <= totalNumberData)) {
            Node currentNode = firstNode;
            for (int i = 0; i < position - 1; ++i) {
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            result = currentNode.data;	// currentNode is pointing to the node at givenPosition
        }

        return result;
    }

    @Override
    public boolean contains(T data) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (data.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return found;
    }

    @Override
    public int getTotalNumberOfData() {
        return totalNumberData;
    }

    @Override
    public boolean isEmpty() {
        boolean result;

        result = totalNumberData == 0;

        return result;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.data + "\n";
            currentNode = currentNode.next;
        }
        return outputStr;
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
            this.next = null;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

}
