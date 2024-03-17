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

/**
 * CircularArrayQueue.java  A class that implements the ADT Queue using a
 * circular array with one unused location.
 *
 * @author Frank M. Carrano
 * @version 2.0
 */
public class CircularArrayQueue<T> implements QueueInterface<T> {

  private T[] array; // circular array of array entries and one unused location
  private int frontIndex;
  private int backIndex;
  private static final int DEFAULT_CAPACITY = 5;

  public CircularArrayQueue() {
    this(DEFAULT_CAPACITY);
  }

  public CircularArrayQueue(int initialCapacity) {
    array = (T[]) new Object[initialCapacity + 1];
    frontIndex = 0;
    backIndex = initialCapacity;
  }

  @Override
  public void enqueue(T newEntry) {
    if (!isArrayFull()) {
      backIndex = (backIndex + 1) % array.length;
      array[backIndex] = newEntry;
    }
  }

  @Override
  public T getFront() {
    T front = null;

    if (!isEmpty()) {
      front = array[frontIndex];
    }

    return front;
  }

    @Override
  public T dequeue() {
    T front = null;

    if (!isEmpty()) {
      front = array[frontIndex];
      array[frontIndex] = null;
      frontIndex = (frontIndex + 1) % array.length;
    }

    return front;
  }

    @Override
  public boolean isEmpty() {
    return frontIndex == ((backIndex + 1) % array.length);
  }

    @Override
  public void clear() {
    if (!isEmpty()) {
      for (int index = frontIndex; index != backIndex; index = (index + 1) % array.length) {
        array[index] = null;
      }
      array[backIndex] = null;
    }

    frontIndex = 0;
    backIndex = array.length - 1;
  }

  private boolean isArrayFull() {
    return frontIndex == ((backIndex + 2) % array.length);
  }

  @Override
  public Iterator<T> getIterator() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  // Method to convert the circular array queue to a string
  public String getCircularArrayQueueAsString() {
    StringBuilder sb = new StringBuilder();
    CircularArrayQueue<T> tempQueue = new CircularArrayQueue<>(array.length - 1);

    // Copy elements from original queue to temporary queue
    while (!isEmpty()) {
        T element = dequeue();
        tempQueue.enqueue(element);
        sb.append(element);
        if (!tempQueue.isEmpty()) {
            sb.append(", ");
        }
    }

    // Restore elements back to original queue
    while (!tempQueue.isEmpty()) {
        enqueue(tempQueue.dequeue());
    }

    return sb.toString();
  }
}

