package edu.washington.cs.knowitall.commonlib;

public abstract class AbstractRange {
    public abstract boolean isEmpty();
    public abstract int getStart();
    public abstract int getEnd();
    public abstract boolean contains(int i);
    public abstract boolean contains(Range range);
    

    /***
     * Returns true if this range ends before the other range starts.
     * @param range
     * @return
     */
    public boolean isLeftOf(AbstractRange range) {
        return this.getEnd() < range.getStart();
    }

    /***
     * Returns true if this range ends before the other range starts.
     * @param range
     * @return
     */
    public boolean isRightOf(AbstractRange range) {
        return range.getEnd() < this.getStart();
    }
    
    /***
     * Returns true if this range starst before the other range starts.
     * @param range
     * @return
     */
    public boolean startsLeftOf(AbstractRange range) {
        return this.getStart() < range.getStart();
    }

    /***
     * Returns true if this range starts before the other range starts.
     * @param range
     * @return
     */
    public boolean startsRightOf(AbstractRange range) {
        return range.getStart() < this.getStart();
    }
}
