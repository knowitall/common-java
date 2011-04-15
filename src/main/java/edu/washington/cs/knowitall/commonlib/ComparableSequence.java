package edu.washington.cs.knowitall.commonlib;

public class ComparableSequence<E extends Comparable<E>> implements Comparable<ComparableSequence<E>> {
    private final E[] comparables;
    
    public ComparableSequence(E[] comparables) {
        this.comparables = comparables;
    }
    
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        else if (other == null) {
            return false;
        }
        else if (! (other instanceof ComparableSequence<?>)) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        ComparableSequence<E> comperable = (ComparableSequence<E>)other;
        return this.compareTo(comperable) == 0;
    }
    
    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < comparables.length; i++) {
            hash *= comparables[i].hashCode();
            hash *= 39;
        }
        
        return hash;
    }

    @Override
    public int compareTo(ComparableSequence<E> comparable) {
        return this.compareTo(comparable, 0);
    }
    
    public int compareTo(ComparableSequence<E> comparable, int index) {
        int compare = this.comparables[index].compareTo(comparable.comparables[index]);
        if (compare == 0) {
            if (index == comparables.length - 1) {
                return 0; // equal
            }
            else {
                return this.compareTo(comparable, index + 1);
            }
        }
        else {
            return compare;
        }
    }
}
