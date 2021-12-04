package com.example.myapplication2;

public class Data {
    private static int i[]=new int[100];
    private static int bj[]=new int[100];

    public static int getI(int a){
        return i[a];
    }
    public static int getBJ(int a){
        return bj[a];
    }
    public static void setBj(int a,int b){
        Data.bj[b]=a;
    }
    public static void setI(int a,int b){
        Data.i[b]=a;
    }
}
