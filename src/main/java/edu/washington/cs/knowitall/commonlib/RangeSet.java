package edu.washington.cs.knowitall.commonlib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Joiner;

/***
 * An immutable class that represent an interval.
 * @author michael
 *
 */
public class RangeSet extends AbstractRange implements Serializable {
    private static final long serialVersionUID = -5916908704306283230L;

    private final int start;
    private final int end;
    
    private final List<Range> ranges;
    
    public RangeSet(Range[] ranges) {
        List<Range> rangeList = new ArrayList<Range>(ranges.length);
        
        int start = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;
        for (Range range : ranges) {
            if (!range.isEmpty()) {
                rangeList.add(range);
                
                start = Math.min(start, range.getStart());
                end = Math.max(start, range.getStart());
            }
        }
        
        this.start = start;
        this.end = end;
        
        this.ranges = new ArrayList<Range>(rangeList.size());
        Collections.sort(rangeList);
        for (int i = 0; i < rangeList.size() - 1; i++) {
            Range current = rangeList.get(i);
            Range next = rangeList.get(i + 1);
            if (current.isAdjacentOrOverlaps(next)) {
                rangeList.set(i + 1, current.join(next));
            }
            else {
                this.ranges.add(current);
            }
        }
        
        this.ranges.add(rangeList.get(rangeList.size() - 1));
    }
    
    public boolean isEmpty() {
        if (ranges.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getStart() {
        return start;
    }
    
    public int getEnd() {
        return end;
    }

    public boolean contains(int i) {
        for (Range range : this.ranges) {
            if (range.contains(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean contains(Range otherRange) {
        for (Range range : this.ranges) {
            if (range.contains(otherRange)) {
                return true;
            }
        }
        
        return false;
    }

    public static RangeSet getLeft(RangeSet range1, RangeSet range2) {
        if (range1.getStart() <= range2.getStart()) {
            return range1;
        } else {
            return range2;
        }
    }

    public static RangeSet getRight(RangeSet range1, RangeSet range2) {
        if (range1.getStart() > range2.getStart()) {
            return range1;
        } else {
            return range2;
        }
    }

    public String toString() {
        List<String> parts = new ArrayList<String>();
        for (Range range : this.ranges) {
            parts.add(range.toString());
        }
        
        return Joiner.on(", ").join(parts);
    }
}
