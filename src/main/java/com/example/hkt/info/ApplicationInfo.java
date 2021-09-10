package com.example.hkt.info;

public class ApplicationInfo {


    public static String path = System.getProperty("user.dir");

    public static String getHikvisionDllPath() {
        return hikvisionDllPath;
    }

    public static void setHikvisionDllPath(String hikvisionDllPath) {
        ApplicationInfo.hikvisionDllPath = hikvisionDllPath;
    }

    public static String hikvisionDllPath = System.getProperty("user.dir")+ "\\lib\\hikvision\\";

}
