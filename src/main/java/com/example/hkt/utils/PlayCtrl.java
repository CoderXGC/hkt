package com.example.hkt.utils;


import com.example.hkt.info.ApplicationInfo;
import com.sun.jna.Native;
import com.sun.jna.examples.win32.W32API;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;


//播放库函数声明,PlayCtrl.dll
public interface PlayCtrl extends StdCallLibrary {
    PlayCtrl INSTANCE = (PlayCtrl) Native.loadLibrary(ApplicationInfo.hikvisionDllPath + "PlayCtrl.dll", PlayCtrl.class);

    public static final int STREAME_REALTIME = 0;
    public static final int STREAME_FILE = 1;

    boolean PlayM4_GetPort(IntByReference nPort);

    boolean PlayM4_OpenStream(int nPort, ByteByReference pFileHeadBuf, int nSize, int nBufPoolSize);

    boolean PlayM4_InputData(int nPort, ByteByReference pBuf, int nSize);

    boolean PlayM4_CloseStream(int nPort);

    boolean PlayM4_SetStreamOpenMode(int nPort, int nMode);

    boolean PlayM4_Play(int nPort, W32API.HWND hWnd);

    boolean PlayM4_Stop(int nPort);

    boolean PlayM4_SetSecretKey(int nPort, int lKeyType, String pSecretKey, int lKeyLen);
}