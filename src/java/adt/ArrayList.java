package adt;

/**
 * @author Frank M. Carrano
 * @version 2.0
 */
import java.util.Iterator;
import java.io.Serializable;

public class ArrayList<T> implements ListInterface<T>, Serializable {

    private T[] array;
    private int totalNumberData;
    private static final int DEFAULT_CAPACITY = 5;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        totalNumberData = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T newData) {
        if (isArrayFull()) {
            doubleArray();
        }

        array[totalNumberData] = newData;
        totalNumberData++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= totalNumberData + 1)) {
            if (isArrayFull()) {
                doubleArray();
            }
            makeRoom(newPosition);
            array[newPosition - 1] = newEntry;
            totalNumberData++;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T remove(int position) {
        T result = null;

        if ((position >= 1) && (position <= totalNumberData)) {
            result = array[position - 1];

            if (position < totalNumberData) {
                removeGap(position);
            }

            totalNumberData--;
        }

        return result;
    }

    @Override
    public void clear() {
        totalNumberData = 0;
    }

    @Override
    public boolean replace(int position, T newData) {
        boolean isSuccessful = true;

        if ((position >= 1) && (position <= totalNumberData)) {
            array[position - 1] = newData;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getData(int position) {
        T result = null;

        if ((position >= 1) && (position <= totalNumberData)) {
            result = array[position - 1];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < totalNumberData); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
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
        return totalNumberData == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    private void doubleArray() {
        T[] oldArray = array;
        array = (T[]) new Object[oldArray.length * 2];
        for (int i = 0; i < oldArray.length; i++) {
            array[i] = oldArray[i];
        }
    }

    private boolean isArrayFull() {
        return totalNumberData == array.length;
    }

//   @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < totalNumberData;
            }

            @Override
            public T next() {
                return array[currentIndex++];
            }
        };
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < totalNumberData; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    /**
     * Task: Makes room for a new entry at newPosition. Precondition: 1 <=
     * newPosition <= numberOfEntries + 1; numberOfEntries is array's
     * numberOfEntries before addition.
     */
    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = totalNumberData - 1;

        // move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    /**
     * Task: Shifts entries that are beyond the entry to be removed to the next
     * lower position. Precondition: array is not empty; 1 <= givenPosition <
     * numberOfEntries; numberOfEntries is array's numberOfEntries before
     * removal.
     */
    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition - 1;
        int lastIndex = totalNumberData - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element. Shifts any subsequent elements to the right (adds one
     * to their indices).
     *
     * @param givenPosition The position of the element to be replaced.
     * @param newEntry The new element to be stored at the specified position.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean set(int givenPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((givenPosition >= 1) && (givenPosition <= totalNumberData)) {
            array[givenPosition - 1] = newEntry;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }
    
    
}
