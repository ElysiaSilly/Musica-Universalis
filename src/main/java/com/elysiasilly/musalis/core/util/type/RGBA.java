package com.elysiasilly.musalis.core.util.type;

public class RGBA {

    private final int R;
    private final int G;
    private final int B;
    private final int A;

    public RGBA(int R, int G, int B, int A) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.A = A;
    }

    public int red() {
        return R;
    }

    public int green() {
        return G;
    }

    public int blue() {
        return B;
    }

    public int alpha() {
        return A;
    }


}
