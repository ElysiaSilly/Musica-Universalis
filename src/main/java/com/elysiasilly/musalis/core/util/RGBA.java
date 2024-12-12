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

    public RGBA(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.A = 255;
    }

    public int r() {
        return R;
    }

    public int g() {
        return G;
    }

    public int b() {
        return B;
    }

    public int a() {
        return A;
    }

    public void r(int R) {
        this.R = R;
    }

    public void g(int G) {
        this.G = G;
    }

    public void b(int B) {
        this.B = B;
    }

    public void a(int A) {
        this.A = A;
    }

    public RGBA shade(float bias) {
        this.R = (int) (this.R / bias);
        this.G = (int) (this.G / bias);
        this.B = (int) (this.B / bias);
        return this;
    }

    public int toDec() {
        return ColourUtil.RGBAToDec(this);
    }

    public RGBA aberrate(float bias) { // todo
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
