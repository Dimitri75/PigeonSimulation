package classes.list;

import java.util.*;

/**
 * Created by Dimitri on 01/11/2015.
 */
public class CircularQueue<T> extends LinkedList<T>{
    protected int fixedSize;

    public CircularQueue(int size){
        super();
        this.fixedSize = size;
    }

    public int getFixedSize(){
        return fixedSize;
    }

    public void push(T o){
        addFirst(o);

        if (size() > fixedSize)
            removeLast();
    }

    public T pushAndPopExcedent(T o){
        addFirst(o);

        if (size() > fixedSize) {
            T tmpLast = getLast();
            removeLast();
            return tmpLast;
        }
        return null;
    }
}