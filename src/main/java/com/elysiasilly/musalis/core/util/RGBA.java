package com.elysiasilly.musalis.core.util;

public class RGBA {

    private int R;
    private int G;
    private int B;
    private int A;

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

    public RGBA shade(float bias) {
        this.R = (int) (this.R / bias);
        this.G = (int) (this.G / bias);
        this.B = (int) (this.B / bias);
        return this;
    }

    public RGBA aberrate(float bias) {
        RGBA temp = new RGBA(0, 0, 0, 0);
        temp.R = (int) (this.R > this.G && this.R > this.B ? this.R * .1 : this.R / bias);
        temp.G = (int) (this.G > this.R && this.G > this.B ? this.G * .1 : this.G / bias);
        temp.B = (int) (this.B > this.G && this.B > this.R ? this.B * .1 : this.B / bias);
        this.R = temp.R;
        this.G = temp.G;
        this.B = temp.B;
        return this;
    }
}
