package com.example.hkt.info;

public class ApplicationInfo {


    public  String path = System.getProperty("user.dir");

    public  String getHikvisionDllPath() {
        return hikvisionDllPath;
    }

    public static void setHikvisionDllPath(String hikvisionDllPath) {
        ApplicationInfo.hikvisionDllPath = hikvisionDllPath;
    }

    public static String hikvisionDllPath = System.getProperty("user.dir")+ "\\lib\\hikvision\\";

}
