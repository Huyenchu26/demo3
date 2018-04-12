package com.example.admin.demo3.util;

import android.util.Log;

import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NumberUtil {
    public static long hexToDecimal(String hexa) {
        int decimel = 0;
        decimel = Integer.parseInt(hexa, 16);
        return decimel;
    }

    public static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }

    public static boolean checkRFID(String string){
        if (string.length() != 8) return false;
        long str12 = hexToDecimal(string.substring(0, 2));
        long s34 = hexToDecimal(string.substring(2, 4));
        long s56 = hexToDecimal(string.substring(4, 6));
        long x12 = hexToDecimal(string.substring(6, 8));
        if (x12 == ((str12 ^ s34) ^ s56)) {
            return true;
        } else {
            return false;
        }
    }

}

