package com.example.moneymanagement_android.utils;

import android.net.Uri;

import com.example.moneymanagement_android.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String formatCurrency(long price) {
        NumberFormat formatter = new DecimalFormat("#,### đ");
        String formattedNumber = formatter.format(price);
        return formattedNumber;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = sdf.format(date);
        sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(date);
        return day + " tháng " + month + " " + year;
    }

    public static String getPathForResouce(int id) {
        return Uri.parse("android:resouce://"  + R.class.getPackage().getName() + "/" + id).toString();
    }

    public static String FromatStringToDate(String str) {
        String year = str.substring(6, 10);
        String month = str.substring(3, 5);
        String day = str.substring(0, 2);
        return year + "-" + month + "-" + day;
    }

    public static String FromatStringToDateDDMM(String str) {
        //2019-05-29
        String month = str.substring(5, 7);
        String day = str.substring(8, 10);

        return day + "/" + month;
    }

    public static String formatPrice(String s) {
        String str = s.toString().replaceAll("[^\\d]", "");
        double s1 = 0;
        try {
            s1 = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
        ((DecimalFormat) nf2).applyPattern("###,###.###");

        return nf2.format(s1);
    }
}
