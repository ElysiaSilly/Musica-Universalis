package com.elysiasilly.musalis.core.util;

import net.minecraft.util.RandomSource;

public class ColourUtil {

    public static RGBA ABGRToRGBA(int ABGR) {
        return new RGBA(
                ABGR & 0xFF,
                ABGR >> 8 & 0xFF,
                ABGR >> 16 & 0xFF,
                ABGR >> 24 & 0xFF
        );
    }

    public static int RGBAToABGR(RGBA RGBA) { // TODO : where alpha
        return RGBA.r() + (RGBA.g() * 256) + (RGBA.b() * 256 * 256);
    }

    public static RGBA DecToRGBA(int dec) {
        int B = dec / 65536;
        int G = (dec - B * 65536) / 256;
        int R = dec - B * 65536 - G * 256;
        return new RGBA(B, G, R, 255);
    }

    public static int RGBAToDec(RGBA RGBA) {
        return RGBA.r() * 65536 + RGBA.g() * 256 + RGBA.b();
    }

    public static int RGBAToHex(RGBA RGBA) {
        return (RGBA.r() << 16) + (RGBA.g() << 8) + (RGBA.b());
    }

    public static RGBA HexToRGBA(int hex) {
        //int c = 0xff03c0; // 16712640
        int R = (hex & 0xff0000) >> 16;
        int G = (hex & 0x00ff00) >> 8;
        int B = (hex & 0x0000ff);
        return new RGBA(R, G, B, 255);
    }

    public static int HexToDec(int hex) {
        return RGBAToDec(HexToRGBA(hex));
    }

    public static RGBA randomColour(RandomSource ran) {
        return new RGBA(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
    }

    public static RGBA white() {
        return new RGBA(255, 255, 255);
    }
}
