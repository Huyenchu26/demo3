package com.example.admin.demo3.util;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NumberUtil {
    public static int hexeToDecimal(String hexa) {
        int decimel = 0;
        decimel = Integer.parseInt(hexa, 16);
        return decimel;
    }

}

