/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.EmptyStackException;
import java.util.Iterator;

/**
 *
 * @author Zy
 */
// linked - compulsory to create a Node class
public class LinkedStack<T> implements StackInterface<T> {

    private Node topNode; // pointer / reference

    public LinkedStack() {
        topNode = null;
    }

    @Override
    public void push(T newEntry) {
        Node newNode = new Node(newEntry);
        newNode.next = topNode; // Link the new node to the current top node
        topNode = newNode; // Set the new node as the top node
    }

    @Override
    public T pop() { // totally remove the item from stack
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T top = peek();
        if (topNode != null) {
            topNode = topNode.next;
        }
        return top;
    }

    // before remove permenantly from stack, copy out the item/data
    @Override
    public T peek() {
        T top = null;
        if (topNode != null) {
            top = topNode.value;
        }
        return top;
    }

    @Override
    public boolean isEmpty() {
        return topNode == null;
    }

    @Override
    public void clear() {
        topNode = null;
    }

    @Override
    public Iterator<T> getIterator() {
        return new StackIterator();
    }

    // Iterator class to iterate over the linked list
    private class StackIterator implements Iterator<T> {

        private Node currentNode = topNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            T data = currentNode.value;
            currentNode = currentNode.next;
            return data;
        }
    }

    // must have value and reference pointer
    private class Node {

        // attribute
        public T value;
        public Node next;

        // constructor
        public Node() {
            value = null;
            next = null;
        }

        public Node(T value, Node n) {
            this.value = value;
            next = n;
        }

        public Node(T value) {
            this.value = value;
            next = null;
        }
    }
}
