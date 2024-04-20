/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author Zy
 */
public interface StackInterface<T>{

  public void push(T newEntry);

  public T pop();
 
  public T peek();

 
  public boolean isEmpty();

  
  public void clear();
  
  public Iterator<T> getIterator(); 
} // end StackInterface

