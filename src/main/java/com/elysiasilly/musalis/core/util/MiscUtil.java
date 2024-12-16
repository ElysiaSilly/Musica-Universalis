package com.elysiasilly.musalis.core.util;

public class MiscUtil {

    public boolean ifNull(Object...objects) {
        for(Object object : objects) if(object == null) return true;
        return false;
    }
}
