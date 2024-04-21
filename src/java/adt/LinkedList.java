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

public class LinkedList<T> implements ListInterface<T> {

    private Node firstNode;
    private Node lastNode;
    private int totalNumberData;

    public LinkedList() {
        reset();
    }

    @Override
    public final void reset() {
        firstNode = null;
        totalNumberData = 0;
    }

    @Override
    public ListInterface<T> search(Predicate<T> criteria) {
        ListInterface<T> matchingData = new LinkedList<>();
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
    public int countNode() {
        return countNodes(firstNode);
    }

    public int countNodes(Node firstNode) {
        if (firstNode == null) {
            return 0;
        } else {
            return 1 + countNodes(firstNode.next);
        }
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
        Node newNode = new Node(newData);

        if (!isEmpty()) {
            // Update the next reference of the last node
            lastNode.next = newNode; 
        } else {
            // If the list is empty, set the new node as the first node
            firstNode = newNode; 
        }
        
        // Update the last node reference
        lastNode = newNode; 
        totalNumberData++;
        return true;
    }


    @Override 
    public boolean add(int newPosition, T newData) {
        // Invalid position
        if (newPosition < 1 || newPosition > totalNumberData + 1) {
            return false; 
        }

        Node newNode = new Node(newData);

        // add to the beginning of list
        if (isEmpty() || newPosition == 1) { 
            newNode.next = firstNode;
            firstNode = newNode;
            if (isEmpty()) {
                // Update lastNode if the list was empty
                lastNode = newNode; 
            }
        } else { // list is not empty and newPosition > 1
            Node nodeBefore = getNodeAtPosition(newPosition - 1);
            
            // make new node point to current node at newPosition
            newNode.next = nodeBefore.next; 
            // make the node before point to the new node
            nodeBefore.next = newNode; 
            if (newPosition == totalNumberData + 1) {
                // Update lastNode if adding at the end of the list
                lastNode = newNode; 
            }
        }

        totalNumberData++;
        return true;
    }

    @Override 
    public boolean remove(int position) {
        Node currentNode = getNodeAtPosition(position);
        
        // Invalid position
        if (currentNode == null) {
            return false; 
        }

        // remove first entry
        if (position == 1) { 
            if (totalNumberData == 1) {
                firstNode = null;
                lastNode = null;
            } else {
                firstNode = firstNode.next;
            }
        } else { // position > 1
            Node nodeBefore = getNodeAtPosition(position - 1);
            if (nodeBefore != null && nodeBefore.next != null) {
                // remove the node at newPosition
                nodeBefore.next = nodeBefore.next.next; 
                if (nodeBefore.next == null) {
                    // Update lastNode if the last node is removed
                    lastNode = nodeBefore; 
                }
            }
        }
        totalNumberData--;
        
        // operation succeeds
        return true; 
    }

    @Override 
    public boolean update(int newPosition, T newData) {
        // Invalid position
        if (newPosition < 1 || newPosition > totalNumberData) {
            return false; 
        }

        Node currentNode = firstNode;
        for (int i = 1; i < newPosition; ++i) {
            // advance currentNode to next node
            currentNode = currentNode.next;    
        }
        // currentNode is pointing to the node at new Position
        currentNode.data = newData;    

        return true;
    }

    @Override 
    public T getData(int position) {
        Node currentNode = getNodeAtPosition(position);
        
        if (currentNode == null) {
            return null; 
        }

        return currentNode.data;
    }

    @Override 
    public boolean contains(T newData) {
        for (Node currentNode = firstNode; currentNode != null; currentNode = currentNode.next) {
            if (newData.equals(currentNode.data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getTotalNumberOfData() {
        return totalNumberData;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }


    private Node getNodeAtPosition(int position) {
        if (position < 1 || position > totalNumberData) {
            return null; 
        }

        Node currentNode = firstNode;
        for (int i = 1; i < position; ++i) {
            currentNode = currentNode.next;  
        }

        return currentNode;
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
