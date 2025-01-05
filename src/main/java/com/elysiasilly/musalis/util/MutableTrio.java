package com.elysiasilly.musalis.util;

public class MutableTrio<LEFT, MIDDLE, RIGHT> {

    public LEFT left;
    public MIDDLE middle;
    public RIGHT right;

    public MutableTrio(LEFT left, MIDDLE middle, RIGHT right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
}
