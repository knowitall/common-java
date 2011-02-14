package edu.washington.cs.knowitall.commonlib.mutable;

public class MutableInteger {
    int contents;
    
    public MutableInteger(int value) {
        this.contents = value;
    }
    
    public int value() {
        return this.contents;
    }
    
    public void increment() {
        this.contents++;
    }
}
