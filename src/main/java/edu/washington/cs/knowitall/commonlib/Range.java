package edu.washington.cs.knowitall.commonlib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.base.Joiner;

/***
 * An immutable class that represent an interval.
 * @author michael
 *
 */
public class Range extends AbstractRange 
implements Iterable<Integer>, Comparable<Range>, Serializable {
    private static final long serialVersionUID = -5916908704306283230L;

    public final static Range EMPTY = new Range(0, 0);

    private final int start;
    private final int length;
    
    private static final Comparator<Range> startComparator = new Comparator<Range>() {
        @Override
        public int compare(Range o1, Range o2) {
            Integer s1 = o1.getStart();
            return s1.compareTo(o2.getStart());
        }
    };

    public Range(int start, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Range length must be >= 0.");
        }
        if (start < 0) {
            throw new IllegalArgumentException("Range start must be >= 0.");
        }
        this.start = start;
        this.length = length;
    }
    
    public Range(int index) {
        this(index, 1);
    }
    
    /***
     * Returns a new range over the specified interval.
     * @param start The first item in the range (inclusive).
     * @param end The end of the range (exclusive).
     * @return a new range over the interval.
     */
    public static Range fromInterval(int start, int end) {
        return new Range(start, end - start);
    }
    
    public int size() {
        return this.getLength();
    }

    public Range shift(int n) {
        return new Range(start + n, length);
    }
    
    public boolean isEmpty() {
        if (this == EMPTY) {
            return true;
        }
        else {
            return this.length == 0;
        }
    }

    public int getStart() {
        return start;
    }
    
    public int getEnd() {
        return start + length;
    }

    public int getLength() {
        return length;
    }

    public int getLastIndex() {
        return start + length - 1;
    }

    public boolean contains(int i) {
        return getStart() <= i && i < getStart() + getLength(); 
    }
    
    public boolean contains(Range range) {
        return this.contains(range.getStart()) && this.contains(range.getEnd() - 1);
    }
    
    public boolean subset(Range range) {
        return range.contains(this);
    }
    
    /***
     * Extends a range by one unit on either end.
     * @param i the index to extend to
     * @return
     */
    public Range extend(int i) {
    	if (i < 0) {
    		throw new IllegalArgumentException("i < 0");
    	}
    	
    	if (this == Range.EMPTY){
    		return new Range(i);
    	}
    	else if (this.start == i + 1) {
    		return new Range(this.start - 1, this.length + 1);
    	}
    	else if (this.start + this.length == i) {
    		return new Range(this.start, this.length + 1);
    	}
    	else {
    		throw new IllegalArgumentException("i must border range: " + i);
    	}
    }

    public static Range getLeft(Range range1, Range range2) {
        if (range1.getStart() <= range2.getStart()) {
            return range1;
        } else {
            return range2;
        }
    }

    public static Range getRight(Range range1, Range range2) {
        if (range1.getStart() > range2.getStart()) {
            return range1;
        } else {
            return range2;
        }
    }


    public boolean overlapsWith(Range range) {
        if (this.isEmpty() || range.isEmpty()) {
            return false;
        }
        Range left = getLeft(this, range);
        Range right = getRight(this, range);
        return left.getStart() <= right.getStart() && 
        right.getStart() < left.getStart() + left.getLength();
    }

    public boolean isAdjacentTo(Range range) {
        Range left = getLeft(this, range);
        Range right = getRight(this, range);
        return left.getEnd() == right.getStart();
    }

    public boolean isAdjacentOrOverlaps(Range range) {
        return isAdjacentTo(range) || overlapsWith(range);
    }

    public Range join(Range range) {
        if (range.isEmpty()) {
            return this;
        }
        else if (this.isEmpty()) {
            return range;
        }
        if (isAdjacentOrOverlaps(range)) {
            int start = getLeft(this, range).getStart();
            int length = Math.max(getLastIndex(), range.getLastIndex()) + 1 - start;
            return new Range(start, length);
        } else {
            throw new IllegalArgumentException("Ranges must be adjacent or overlapping to merge.");
        }
    }
    
    public Range extend(Range range) {
        int start = getLeft(this, range).getStart();
        int end = getRight(this, range).getEnd();
        return Range.fromInterval(start, end);
    }

    public Range removeOverlap(Range range) {
        Integer newStart = -1;
        Integer newLength = 0;
        for (int i = getStart(); i < getStart() + getLength(); i++) {
            if (range.contains(i) && newStart >= 0) {
                break;
            } else if (!range.contains(i) && newStart == -1) {
                newStart = i;
                newLength = 1;
            } else if (!range.contains(i) && newStart >= 0) {
                newLength++;
            }
        }
        
        if (newStart < 0) {
        	return null;
        }
        else {
	        return new Range(newStart, newLength);
        }
    }

    public boolean equals(Range r) {
        return r.getStart() == getStart() && r.getLength() == getLength();
    }

    public String toString() {
        return "[" + start + ", " + this.getEnd() + ")";
    }
    
    public String toString(List<Object> parts) {
        return Joiner.on(", ").join(parts.subList(this.getStart(), this.getEnd()), ", ");
    }

    public static Comparator<Range> getStartComparator() {
        return startComparator;
    }

    public static int compareStarts(Range r1, Range r2) {
        return startComparator.compare(r1, r2);
    }
    
    /**
     * Checks weather the given set of ranges are disjoint, i.e. none of the
     * ranges overlap.
     * @param ranges
     */
    public static boolean isDisjoint(Collection<Range> ranges) {
        List<Range> rangesList = new ArrayList<Range>(ranges.size());
        rangesList.addAll(ranges);
        Collections.sort(rangesList);
        for (int i = 0; i < rangesList.size() - 1; i++) {
            Range rCurrent = rangesList.get(i);
            Range rNext = rangesList.get(i+1);
            if (rCurrent.overlapsWith(rNext)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + length;
        result = prime * result + start;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Range))
            return false;
        Range other = (Range) obj;
        if (length != other.length)
            return false;
        if (start != other.start)
            return false;
        return true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new RangeIterator(this);
    }

    private class RangeIterator implements Iterator<Integer> {

        private final int start;
        private final int length;
        private int i;

        public RangeIterator(int start, int length) {
            this.start = start;
            this.length = length;
            this.i = start;
        }

        public RangeIterator(Range r) {
            this(r.getStart(), r.getLength());
        }

        @Override
        public boolean hasNext() {
            return i < start + length;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                return i++;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public int compareTo(Range o) {
        return startComparator.compare(this, o);
    }

}
