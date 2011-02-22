package edu.washington.cs.knowitall.commonlib;

public abstract class AbstractRange {
    public abstract boolean isEmpty();
    public abstract int getStart();
    public abstract int getEnd();
    public abstract boolean contains(int i);
    public abstract boolean contains(Range range);
    
    public boolean isLeftOf(AbstractRange range) {
        return this.getStart() < range.getStart();
    }

    public boolean isRightOf(AbstractRange range) {
        return range.getStart() < this.getStart();
    }
}
