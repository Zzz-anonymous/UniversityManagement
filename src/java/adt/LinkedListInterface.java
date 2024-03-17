/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 *
 * @author Zy
 */
public interface LinkedListInterface<T> {
    
    public Iterator<T> getIterator();
    
    public LinkedListInterface<T> search(Predicate<T> criteria);
    
  /**
   * Task: Adds a new entry to the end of the list. Entries currently in the
   * list are unaffected. The list's size is increased by 1.
   *
   * @param newData the object to be added as a new entry
   * @return true if the addition is successful, or false if the list is full
   */
    
  public boolean add(T newData);

  /**
   * Task: Adds a new entry at a specified position within the list. Entries
   * originally at and above the specified position are at the next higher
   * position within the list. The list's size is increased by 1.
   *
   * @param newPosition an integer that specifies the desired position of the
   * new entry
   * @param newData the object to be added as a new entry
   * @return true if the addition is successful, or false if either the list is
   * full, newPosition < 1, or
   *          newPosition > getNumberOfEntries()+1
   */
  public boolean add(int newPosition, T newData);

  /**
   * Task: Removes the entry at a given position from the list. Entries
   * originally at positions higher than the given position are at the next
   * lower position within the list, and the list's size is decreased by 1.
   *
   * @param position an integer that indicates the position of the entry to
   * be removed
   * @return a reference to the removed entry or null, if either the list was
   * empty, givenPosition < 1, or
   *          givenPosition > getNumberOfEntries()
   */
  public T remove(int position);

  /**
   * Task: Removes all entries from the list.
   */
  public void clear();

  /**
   * Task: Replaces the entry at a given position in the list.
   *
   * @param position an integer that indicates the position of the entry to
   * be replaced
   * @param newData the object that will replace the entry at the position
   * givenPosition
   * @return true if the replacement occurs, or false if either the list is
   * empty, givenPosition < 1, or givenPosition > getNumberOfEntries()
   */
  public boolean replace(int position, T newData);

  /**
   * Task: Retrieves the entry at a given position in the list.
   *
   * @param position an integer that indicates the position of the desired
   * entry
   * @return a reference to the indicated entry or null, if either the list is
   * empty, givenPosition < 1, or givenPosition > getNumberOfEntries()
   */
  public T getData(int position);

  /**
   * Task: Sees whether the list contains a given entry.
   *
   * @param data the object that is the desired entry
   * @return true if the list contains anEntry, or false if not
   */
  public boolean contains(T data);

  /**
   * Task: Gets the number of entries in the list.
   *
   * @return the integer number of entries currently in the list
   */
  public int getTotalNumberOfData();

  /**
   * Task: Sees whether the list is empty.
   *
   * @return true if the list is empty, or false if not
   */
  public boolean isEmpty();

  /**
   * Task: Sees whether the list is full.
   *
   * @return true if the list is full, or false if not
   */
  public boolean isFull();

}
