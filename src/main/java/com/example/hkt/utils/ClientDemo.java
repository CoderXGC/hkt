package com.example.hkt.utils;


import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
//方法库
public class ClientDemo {
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;//设备信息
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    public HCNetSDK hcNetSDK;
    public int lUserID;//用户句柄
    public int UserID;//用户句柄
    public NativeLong lAlarmHandle;//报警布防句柄
    public NativeLong lListenHandle;//报警监听句柄
    public Session session;
  //  FMSGCallBack fMSFCallBack;//报警回调函数实现


    public String m_sDeviceIP;//已登录设备的IP地址
    public String username; //设备用户名
    public String password;//设备登录密码
    public int iPort;//设备端口号

    public ClientDemo() {
        //initComponents();
        int lUserID = -1;
        lAlarmHandle = new NativeLong(-1);
        lListenHandle = new NativeLong(-1);
       // fMSFCallBack = null;

    }
    //
    public void CameraInit(){
        //初始化
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true){
            System.out.println("初始化失败");
        }else{
            System.out.println("初始化成功");
        }
    }
    public void Camerainit() throws UnknownHostException {
        String username = "admin";
        String password = "passw0rd";
        Short port = 8000;
        String ip = "172.18.2.125";
        this.session=session;
        // 3. 注册
        // 3.1 注册准备
        //设置连接时间与重连时间
        hCNetSDK.NET_DVR_SetConnectTime(2000, 1);
        hCNetSDK.NET_DVR_SetReconnect(100000, true);
        //设备信息, 输出参数
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        // 注册设备-登录参数，包括设备地址、登录用户、密码等
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(ip.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, ip.length());
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(username.getBytes(), 0, m_strLoginInfo.sUserName, 0, username.length());
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(password.getBytes(), 0, m_strLoginInfo.sPassword, 0, password.length());
        m_strLoginInfo.wPort = 8000;
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();

        //初始化
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true) {
            System.out.println("初始化失败");
        } else {
            System.out.println("初始化成功");
        }
        int lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (lUserID < 0) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            System.out.println("NET_DVR_Login_V40()失败:" + errorCode);
            System.out.println(hCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(errorCode)));
            hCNetSDK.NET_DVR_Cleanup();
        } else {
            System.out.println("登录成功");
        }

    }
    //
    public void CameraInit(Session session) throws UnknownHostException {
        String username = "admin";
        String password = "passw0rd";
        Short port = 8000;
        String ip = "172.18.2.125";
        this.session=session;
        // 3. 注册
        // 3.1 注册准备
        //设置连接时间与重连时间
        hCNetSDK.NET_DVR_SetConnectTime(2000, 1);
        hCNetSDK.NET_DVR_SetReconnect(100000, true);
        //设备信息, 输出参数
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        // 注册设备-登录参数，包括设备地址、登录用户、密码等
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(ip.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, ip.length());
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(username.getBytes(), 0, m_strLoginInfo.sUserName, 0, username.length());
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(password.getBytes(), 0, m_strLoginInfo.sPassword, 0, password.length());
        m_strLoginInfo.wPort = 8000;
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();

        //初始化
        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true) {
            System.out.println("初始化失败");
        } else {
            System.out.println("初始化成功");
        }
        int lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (lUserID < 0) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            System.out.println("NET_DVR_Login_V40()失败:" + errorCode);
            System.out.println(hCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(errorCode)));
            hCNetSDK.NET_DVR_Cleanup();
        } else {
            System.out.println("登录成功");
        }

    }

    //注册
    public int register(String username, String password, String m_sDeviceIP) {
        //注册之前先注销已注册的用户,预览情况下不可注销
        if (lUserID > -1) {
            //先注销
            hCNetSDK.NET_DVR_Logout(lUserID);
            int lUserID = -1;
        }

        //注册
        m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        int iPort = 8000;
        System.out.println("注册，设备IP：" + m_sDeviceIP);
        lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) iPort, username, password, m_strDeviceInfo);

        int userID = lUserID;
        if (userID == -1) {
            System.out.println("注册失败");
        } else {
            System.out.println("注册成功,lUserID:" + userID);
        }
        return lUserID;
    }


    //布防
    public void SetupAlarmChan() {
        //6.启用布防
        HCNetSDK.NET_DVR_SETUPALARM_PARAM lpSetupParam = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        lpSetupParam.dwSize = 0;
        lpSetupParam.byLevel = 1;//布防优先级：0- 一等级（高），1- 二等级（中）
        lpSetupParam.byAlarmInfoType = 1;//上传报警信息类型: 0- 老报警信息(NET_DVR_PLATE_RESULT), 1- 新报警信息(NET_ITS_PLATE_RESULT)
        int lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, lpSetupParam);
        if (lAlarmHandle < 0) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            System.out.println("NET_DVR_SetupAlarmChan_V41 error, \n" + errorCode);
            System.out.println(hCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(errorCode)));
            hCNetSDK.NET_DVR_Logout(lUserID);
            hCNetSDK.NET_DVR_Cleanup();

        } else {
            System.out.println("布防成功,开始监测车辆");
        }
    }


    
    //开始监听
    public void StartAlarmListen() {
        //启动监听----------------------------------------------
        if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(this::MsesGCallBack, null)) {
            int errorCode = hCNetSDK.NET_DVR_GetLastError();
            System.out.println("启动监听失败，错误号:" + errorCode);
            System.out.println("启动监听失败，错误号:" + hCNetSDK.NET_DVR_GetLastError());
           /* hcNetSDK.NET_DVR_Logout(lUserID);
            hcNetSDK.NET_DVR_Cleanup();*/
          /*  throw new YunduoException("摄像头启动监听失败, " + hcNetSDK.NET_DVR_GetErrorMsg(new IntByReference(errorCode)));*/
        } else {
            System.out.println("启动监听成功");
            System.out.println("车牌识别中...");
        }
       // new Timer().schedule(new MyTimerTask1(), 2000);
        // 下面这段代码是每隔1秒,打印下当前的时间
        /*while (true) {
            try {
               // System.out.println(new Date().toLocaleString());
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

      /*  int iListenPort = 8000;
        Pointer pUser = null;

        if (fMSFCallBack == null)
        {
             fMSFCallBack = new FMSGCallBack();
        }
        lListenHandle = hCNetSDK.NET_DVR_StartListen_V30(m_sDeviceIP, (short)iListenPort,fMSFCallBack, pUser);
        if(lListenHandle.intValue() < 0)
        {
            System.out.println("启动监听失败");
        }else{
             System.out.println("启动监听成功");
        }*/



    //测试抓图
    public int zhuaTu(int lUserID) {
   /*     // 7.手动抓图
     HCNetSDK.NET_DVR_MANUALSNAP lpInter = new HCNetSDK.NET_DVR_MANUALSNAP();
        HCNetSDK.NET_DVR_PLATE_RESULT lpOuter = new HCNetSDK.NET_DVR_PLATE_RESULT();
        // 调用NET_DVR_ManualSnap之前要手动给NET_DVR_PLATE_RESULT的缓存区设置大小，详细叫api
        // 不知道会不会CG，后期再优化
        lpOuter.pBuffer1 = new Memory(1024 * 1024);
        this.UserID=lUserID;
        if (hCNetSDK.NET_DVR_ManualSnap(UserID, lpInter, lpOuter)) {
            System.out.println("手动抓图成功");
            try {
                String carInfo = new String(lpOuter.struPlateInfo.sLicense, "GBK");
                String sAlarmType1 = "车牌颜色：" + lpOuter.struPlateInfo.byColor + ",交通抓拍上传，车牌：" + carInfo;
                System.out.println("车牌号是"+carInfo);
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("type", CarType.getCarType(lpOuter.byVehicleType + "".trim()));//车辆类型
                if (carInfo.contains("<licensePlate>")) {
                    int start = carInfo.indexOf("<licensePlate>") + ("<licensePlate>".length());
                    int end = carInfo.indexOf("</licensePlate>");
                    String carNo = carInfo.substring(start, end);
                    System.out.println("车牌号是：" + carNo);
                }
                paramMap.put("plateNumber", carInfo.substring(1, carInfo.length()).trim());//车牌号
                paramMap.put("byCountry", carInfo.substring(1, 2).trim());//省份
                paramMap.put("byColor", carInfo.substring(0, 1).trim());//车牌颜色
                paramMap.put("wSpeed", String.valueOf(new Random().nextInt(55 - 5) + 5));//速度 这是假的

                // 车牌照片存放位置
                SimpleDateFormat sfYMD = new SimpleDateFormat("yyyyMMdd");
                String filename = "F:"+ "\\车牌照片\\" + "\\抓拍\\" + sfYMD.format(new Date()) + "\\";
                SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
                String imgName = sf.format(new Date()) + ".jpg";
                System.out.println(lpOuter.dwSize);
                HCNetSDK.NET_DVR_PLATE_INFO info = lpOuter.struPlateInfo;


                File file = new File(filename + imgName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fout = new FileOutputStream(filename + imgName);


                System.out.println("文件路径" + filename + imgName);
                //将字节写入文件
              long offset = 0;
                ByteBuffer buffers = lpOuter.pBuffer1.getByteBuffer(offset, lpOuter.dwPicLen);
                byte[] bytes = new byte[lpOuter.dwPicLen];
                buffers.rewind();
                buffers.get(bytes);
                fout.write(bytes);
                fout.close();
              ByteBuffer buffers = lpOuter.pBuffer1.getByteBuffer(0, lpOuter.dwPicLen);
              byte[] bytes = new byte[lpOuter.dwPicLen];
                long offset = 0;
                buffers.rewind();
                buffers.get(bytes);
                return 1;

            } catch (UnsupportedEncodingException e) {
                System.out.println("抛出异常了1");
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("抛出异常了2");

            }
        } else {
            int errorCode = hcNetSDK.NET_DVR_GetLastError();
            System.out.println("手动抓图失败:" + errorCode);
            return 0;
        }*/
        HCNetSDK.NET_DVR_MANUALSNAP lpInter = new HCNetSDK.NET_DVR_MANUALSNAP();
        HCNetSDK.NET_DVR_PLATE_RESULT lpOuter = new HCNetSDK.NET_DVR_PLATE_RESULT();
        // 调用NET_DVR_ManualSnap之前要手动给NET_DVR_PLATE_RESULT的缓存区设置大小，详细叫api
        // 不知道会不会CG，后期再优化
        lpOuter.pBuffer1 = new Memory(1024 * 1024);
        this.UserID=lUserID;
        if (hCNetSDK.NET_DVR_ManualSnap(UserID, lpInter, lpOuter)) {
         //   int errorCode1 = hcNetSDK.NET_DVR_GetLastError();
        //    logger.info("错误代码1" + errorCode1 );
            try {
                String carInfo = new String(lpOuter.struPlateInfo.sLicense, "GBK");
                String sAlarmType = "车牌颜色：" + lpOuter.struPlateInfo.byColor + ",交通抓拍上传，车牌：" + carInfo;
                logger.info("手动1111抓拍识别信息：---》" + sAlarmType );

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                String carInfo = new String(lpOuter.struPlateInfo.sLicense, "GBK");
                String sAlarmType1 = "车牌颜色：" + lpOuter.struPlateInfo.byColor + ",交通抓拍上传，车牌：" + carInfo;
                System.out.println("车牌号是"+carInfo);
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("type", CarType.getCarType(lpOuter.byVehicleType + "".trim()));//车辆类型
                if (carInfo.contains("<licensePlate>")) {
                    int start = carInfo.indexOf("<licensePlate>") + ("<licensePlate>".length());
                    int end = carInfo.indexOf("</licensePlate>");
                    String carNo = carInfo.substring(start, end);
                    System.out.println("车牌号是：" + carNo);
                }
                paramMap.put("plateNumber", carInfo.substring(1, carInfo.length()).trim());//车牌号
                paramMap.put("byCountry", carInfo.substring(1, 2).trim());//省份
                paramMap.put("byColor", carInfo.substring(0, 1).trim());//车牌颜色
                paramMap.put("wSpeed", String.valueOf(new Random().nextInt(55 - 5) + 5));//速度 这是假的

                // 车牌照片存放位置
                SimpleDateFormat sfYMD = new SimpleDateFormat("yyyyMMdd");
                String filename = "F:"+ "\\车牌照片\\" + "\\抓拍\\" + sfYMD.format(new Date()) + "\\";
                SimpleDateFormat sf = new SimpleDateFormat("HHmmss");
                String imgName = sf.format(new Date()) + ".jpg";
                System.out.println("大小"+lpOuter.dwSize);
                HCNetSDK.NET_DVR_PLATE_INFO info = lpOuter.struPlateInfo;


                File file = new File(filename + imgName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fout = new FileOutputStream(filename + imgName);


                System.out.println("文件路径" + filename + imgName);
                //将字节写入文件
                long offset = 0;
                ByteBuffer buffers = lpOuter.pBuffer1.getByteBuffer(offset, lpOuter.dwPicLen);
                byte[] bytes = new byte[lpOuter.dwPicLen];
                buffers.rewind();
                buffers.get(bytes);
                fout.write(bytes);
                fout.close();
                return 1;

            } catch (UnsupportedEncodingException e) {
                System.out.println("抛出异常了1");
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("抛出异常了2");

            }

        }
       HCNetSDK.NET_DVR_SNAPCFG struSnapCfg = new HCNetSDK.NET_DVR_SNAPCFG();
        struSnapCfg.dwSize=struSnapCfg.size();
        struSnapCfg.bySnapTimes =1;
        struSnapCfg.wSnapWaitTime =1000;
        struSnapCfg.write();

     /*   if (false == hCNetSDK.NET_DVR_ContinuousShoot(lUserID, struSnapCfg)){
            int iErr = hCNetSDK.NET_DVR_GetLastError();
            System.out.println("网络触发失败，错误号：" + iErr);
            return 0;
        }else{
            System.out.println("抓图成功！");
            return 1;
        }*/
        return 1;

    }


    //车牌识别的回调
    private boolean MsesGCallBack(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
      //  System.out.println("〈－－进入回调,开始识别车牌－－〉");
        logger.info("〈－－进入回调,开始识别车牌－－〉");
        try {
            String sAlarmType = new String();
            String[] newRow = new String[3];
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date today = new Date();
            SimpleDateFormat sfYMD = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sfHMS = new SimpleDateFormat("HHmmss");
            String imgName = sfHMS.format(today) + ".jpg";
            String[] sIP = new String[2];
            switch (lCommand) {
                case 0x1102: // 行为分析信息
                    System.out.println("行为分析信息执行...");
                    break;
                case 0x1103: // 客流量统计报警信息
                    break;
                /*
                case 0x1107: // 事件数据信息
                break;
                case 0x1106: //人脸检测识别报警信息
                break;
                case 0x1112: // 人脸抓拍结果信息
                break;
                case 0x112a: // 人脸抓拍人员统计信息
                break;
                case 0x2902: // 人脸比对结果信息
                break;
                case 0x4010: // 人脸侦测报警信息
                break;
                case 0x4011: // 教师离开讲台报警
                break;
                case 0x4014: // 人员侦测信息
                break;
                case 0x4993: // 智能检测通用报警(Json或者XML数据结构)
                break;
                case 0x6009: // ISAPI协议报警信息
                break;
                case 0x4021: // AI开放平台接入视频检测报警信息
                break;
                case : //
                break;
                */
                case 0x2800:    //交通抓拍结果上传
                    HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                    strPlateResult.write();
                    Pointer pPlateInfo = strPlateResult.getPointer();
                    pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                    strPlateResult.read();
                    try {
                        String srt3 = new String(strPlateResult.struPlateInfo.sLicense, "GBK");
                        sAlarmType = sAlarmType + ":抓拍上传，车牌：" + srt3;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    newRow[0] = dateFormat.format(new Date());
                    //报警类型
                    newRow[1] = sAlarmType;
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    newRow[2] = sIP[0];
                    System.out.println("交通抓拍结果上传执行..."+new SimpleDateFormat("HHmmss"));
                    break;
                case 0x3050:    //交通抓拍的终端图片上传
                    // v.2
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                    strItsPlateResult.write();
                    Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                    pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
                    strItsPlateResult.read();
                    try {
                        String srt3 = new String(strItsPlateResult.struPlateInfo.sLicense, "GBK");
                        sAlarmType = sAlarmType + ",车牌颜色：" + strItsPlateResult.struPlateInfo.byColor + ",交通抓拍上传，车牌：" + srt3;
                      //  System.out.println("识别车牌完成输出"+sAlarmType+"时间："+dateFormat.format(new Date()));
                        logger.info("识别车牌完成输出"+sAlarmType+"时间："+dateFormat.format(new Date()));

                        // 向页面发送数据
                        String ip = sIP[0];
                        if (session != null ){

                                    if (srt3.endsWith("\n")) {
                                        srt3 = srt3.replace("\n", "");
                                    }
                                    String message = "{\"code\":1,\"message\":\"" + srt3 + "\"}"+sAlarmType;
                                    synchronized (session) {
                                        session.getBasicRemote().sendText(message);
                                        //System.out.println("传送前端完成"+"时间"+dateFormat.format(new Date()));

                                        logger.info("传送前端完成code1"+"时间"+dateFormat.format(new Date()));
                                    }
                        }
                        Map<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("type", CarType.getCarType(strItsPlateResult.byVehicleType + "".trim()));//车辆类型
                        if (srt3.contains("<licensePlate>")) {
                            int start = srt3.indexOf("<licensePlate>") + ("<licensePlate>".length());
                            int end = srt3.indexOf("</licensePlate>");
                            String carNo = srt3.substring(start, end);
                            System.out.println("车牌号是：" + carNo);
                        }
                        paramMap.put("plateNumber", srt3.substring(1, srt3.length()).trim());//车牌号
                        paramMap.put("byCountry", srt3.substring(1, 2).trim());//省份
                        paramMap.put("byColor", srt3.substring(0, 1).trim());//车牌颜色
                        paramMap.put("cameraIp", new String(pAlarmer.sDeviceIP).trim());//ip地址
                        paramMap.put("picTime", dateFormat.format(new Date()));//当前时间
                        paramMap.put("wSpeed", String.valueOf(new Random().nextInt(55 - 5) + 5));//速度 这是假的
                        paramMap.put("byIllegalType", BreakRulesType.getBreakRulesType(strItsPlateResult.wIllegalType));

                        // 车牌照片
                        String filename = "F:"+ "\\车牌照片\\" + new String(pAlarmer.sDeviceIP).trim() + "\\" + sfYMD.format(today) + "\\";

                        // 车辆照片
                        String carFileName ="F:" + "\\车辆照片\\" + new String(pAlarmer.sDeviceIP).trim() + "\\" + sfYMD.format(today) + "\\";
                        System.out.println(strItsPlateResult.dwPicNum);
                      for (int i = 0; i < strItsPlateResult.dwPicNum; i++) {
                          logger.info("车牌图片写入文件开始"+dateFormat.format(new Date()));
                            HCNetSDK.NET_ITS_PICTURE_INFO info = strItsPlateResult.struPicInfo[i];
                            if (info.dwDataLen > 0) {
                                FileOutputStream fout;
                                if (info.byType == 0) { //车牌图片
                                   // File file = new File(filename + srt3 + "-" + imgName);
                                    File file = new File(filename + imgName);
                                    if (!file.getParentFile().exists()) {
                                        file.getParentFile().mkdirs();
                                    }
                                    try
                                    {
                                         fout=new FileOutputStream(file);

                                       // System.out.println("文件路径" + file.getAbsolutePath());
                                        logger.info("文件路径" + file.getAbsolutePath());
                                        //将字节写入文件
                                        long offset = 0;
                                        ByteBuffer buffers = info.pBuffer.getByteBuffer(offset, info.dwDataLen);
                                        byte[] bytes = new byte[info.dwDataLen];
                                        buffers.rewind();
                                        buffers.get(bytes);

                                        List<Byte> byteList = new ArrayList<>(bytes.length);
                                        for (byte aByte : bytes) {
                                            byteList.add(aByte);
                                        }
                                        if (session != null) {

                                            String message = "{\"code\":2, \"ip\":\"" + ip + "\", \"message\":" + byteList + "}";
                                            synchronized (session) {
                                                logger.info("发送前端数据开始code2"+dateFormat.format(new Date()));
                                                session.getBasicRemote().sendText(message);
                                                logger.info("发送前端数据完成code2"+dateFormat.format(new Date()));
                                            }

                                        }
                                        // 想本地文件里写入
                                        fout.write(bytes);
                                        fout.close();
                                        logger.info("车牌图片写入文件完成"+dateFormat.format(new Date()));
                                    }

                                    catch (FileNotFoundException e)
                                    {

                                        System.out.println("文件不存在或者文件不可读或者文件是目录");
                                    }
                                    catch (IOException e)
                                    {
                                        System.out.println("读取过程存在异常");
                                    }

                                  //  fout = new FileOutputStream(file);

                                }
                              if (info.byType == 1) {//车辆场景图片
                                  logger.info("车辆场景图片写入文件开始"+dateFormat.format(new Date()));
                                    //String clImgName = srt3 + "-" + imgName;
                                  String clImgName = imgName;
                                    File file = new File(carFileName + clImgName);
                                    if (!file.getParentFile().exists()) {
                                        file.getParentFile().mkdirs();
                                    }

                                  try
                                  {
                                      fout=new FileOutputStream(file);

                                   //   System.out.println("文件路径" + file.getAbsolutePath());
                                      //将字节写入文件
                                      long offset = 0;
                                      ByteBuffer buffers = info.pBuffer.getByteBuffer(offset, info.dwDataLen);
                                      byte[] bytes = new byte[info.dwDataLen];
                                      buffers.rewind();
                                      buffers.get(bytes);

                                      List<Byte> byteList = new ArrayList<>(bytes.length);
                                      for (byte aByte : bytes) {
                                          byteList.add(aByte);
                                      }


                                      // 想本地文件里写入
                                      fout.write(bytes);
                                      fout.close();
                                      logger.info("车辆场景图片写入文件完成"+dateFormat.format(new Date()));
                                  }

                                  catch (FileNotFoundException e)
                                  {

                                      System.out.println("文件不存在或者文件不可读或者文件是目录");
                                  }
                                  catch (IOException e)
                                  {
                                      System.out.println("读取过程存在异常");
                                  }
                                }
                            }
                        }

                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                default:
                    if (session != null ){
                        String message = "{\"code\":1,\"message\":\"" + "暂未识别车牌" + "\"}";
                        synchronized (session) {
                            session.getBasicRemote().sendText(message);
                            //System.out.println("传送前端完成"+"时间"+dateFormat.format(new Date()));

                            logger.info("传送前端完成code1"+"时间"+dateFormat.format(new Date()));
                        }
                    }
                   // System.out.println("未识别车辆"+dateFormat.format(new Date()));
                    logger.info("未识别车辆"+dateFormat.format(new Date()));
                    break;
            }
           //System.out.println("识别信息：---》" + sAlarmType + " lCommand: " + lCommand + " ip:" + new String(pAlarmer.sDeviceIP).trim() + " 时间:" + dateFormat.format(new Date()));
            logger.info("识别信息：---》" + sAlarmType + " lCommand: " + lCommand + " ip:" + new String(pAlarmer.sDeviceIP).trim() + " 时间:" + dateFormat.format(new Date()));
          //  System.out.println("识别完毕！"+dateFormat.format(new Date()));
            logger.info("识别完毕！\n");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败！");
            return false;
        }
    }

}

class MyTimerTask1 extends TimerTask {
    private static int count = 0;

    @Override
    public void run() {
        count = count % 2;
        count++;
        System.out.println("bombing!");
        new Timer().schedule(new MyTimerTask1(), 1000 * count);
    }}