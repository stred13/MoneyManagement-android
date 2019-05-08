package com.example.moneymanagement_android.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Util {

    public static String formatCurrency(long price) {
        NumberFormat formatter = new DecimalFormat("#,### đ");
        String formattedNumber = formatter.format(price);
        return formattedNumber;
    }
}
