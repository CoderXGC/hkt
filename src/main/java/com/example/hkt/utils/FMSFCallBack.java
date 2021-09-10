package com.example.hkt.utils;

import com.sun.jna.Pointer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FMSFCallBack implements HCNetSDK.FMSGCallBack {
    @Override
    public void invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        String sAlarmType = new String();
//            DefaultTableModel alarmTableModel = ((DefaultTableModel) jTableAlarm.getModel());//获取表格模型

        String[] newRow = new String[3];
        //报警时间
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String[] sIP = new String[2];
        String imgName = dateFormat.format(new Date()) + ".jpg";
        String clImgName = "cl" + dateFormat.format(new Date()) + ".jpg";


        //lCommand是传的报警类型
        switch (lCommand) {
            //9000报警
            case HCNetSDK.COMM_ALARM_V30:
                HCNetSDK.NET_DVR_ALARMINFO_V30 strAlarmInfoV30 = new HCNetSDK.NET_DVR_ALARMINFO_V30();
                strAlarmInfoV30.write();
                Pointer pInfoV30 = strAlarmInfoV30.getPointer();
                pInfoV30.write(0, pAlarmInfo.getByteArray(0, strAlarmInfoV30.size()), 0, strAlarmInfoV30.size());
                strAlarmInfoV30.read();

                switch (strAlarmInfoV30.dwAlarmType) {
                    case 0:
                        sAlarmType = new String("信号量报警");
                        break;
                    case 1:
                        sAlarmType = new String("硬盘满");
                        break;
                    case 2:
                        sAlarmType = new String("信号丢失");
                        break;
                    case 3:
                        sAlarmType = new String("移动侦测");
                        break;
                    case 4:
                        sAlarmType = new String("硬盘未格式化");
                        break;
                    case 5:
                        sAlarmType = new String("读写硬盘出错");
                        break;
                    case 6:
                        sAlarmType = new String("遮挡报警");
                        break;
                    case 7:
                        sAlarmType = new String("制式不匹配");
                        break;
                    case 8:
                        sAlarmType = new String("非法访问");
                        break;
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
//                    alarmTableModel.insertRow(0, newRow);
                break;

            //8000报警
            case HCNetSDK.COMM_ALARM:
                HCNetSDK.NET_DVR_ALARMINFO strAlarmInfo = new HCNetSDK.NET_DVR_ALARMINFO();
                strAlarmInfo.write();
                Pointer pInfo = strAlarmInfo.getPointer();
                pInfo.write(0, pAlarmInfo.getByteArray(0, strAlarmInfo.size()), 0, strAlarmInfo.size());
                strAlarmInfo.read();

                switch (strAlarmInfo.dwAlarmType) {
                    case 0:
                        sAlarmType = new String("信号量报警");
                        break;
                    case 1:
                        sAlarmType = new String("硬盘满");
                        break;
                    case 2:
                        sAlarmType = new String("信号丢失");
                        break;
                    case 3:
                        sAlarmType = new String("移动侦测");
                        break;
                    case 4:
                        sAlarmType = new String("硬盘未格式化");
                        break;
                    case 5:
                        sAlarmType = new String("读写硬盘出错");
                        break;
                    case 6:
                        sAlarmType = new String("遮挡报警");
                        break;
                    case 7:
                        sAlarmType = new String("制式不匹配");
                        break;
                    case 8:
                        sAlarmType = new String("非法访问");
                        break;
                }

                newRow[0] = dateFormat.format(today);
                //报警类型
                newRow[1] = sAlarmType;
                //报警设备IP地址
                sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                newRow[2] = sIP[0];
//                    alarmTableModel.insertRow(0, newRow);
                break;

            //ATM DVR transaction information
            case HCNetSDK.COMM_TRADEINFO:
                //处理交易信息报警
                break;

            //IPC接入配置改变报警
            case HCNetSDK.COMM_IPCCFG:
                // 处理IPC报警
                break;

            case 0x2800:    //交通抓拍结果上传
                HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                strPlateResult.write();
                Pointer pPlateInfo = strPlateResult.getPointer();
                pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                strPlateResult.read();
                try {
                    String srt3 = new String(strPlateResult.struPlateInfo.sLicense, "GBK");
                    sAlarmType = sAlarmType + ":抓拍上传，车牌：" + srt3;
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
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
                break;
            case 0x3050:    //交通抓拍的终端图片上传
                HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                strItsPlateResult.write();
                Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
                strItsPlateResult.read();
                try {
                    byte byDangerousVehicles = strItsPlateResult.byDangerousVehicles;
                    String carInfo = new String(strItsPlateResult.struPlateInfo.sLicense, "GBK");
                    sAlarmType = "是否危化品：" + byDangerousVehicles + "-->" + sAlarmType + ",车辆类型："
                            + CarType.getCarType(strItsPlateResult.byVehicleType + "".trim())
                            + ",交通抓拍上传，车牌：" + carInfo;
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("type", CarType.getCarType(strItsPlateResult.byVehicleType + "".trim()));
//                    车辆类型
                    String filename = "D:\\imgUpload\\" + new String(pAlarmer.sDeviceIP).trim() + "\\";
                    String carFileName = "D:\\carImg\\" + new String(pAlarmer.sDeviceIP).trim() + "\\";
                    if ("黄".equals(carInfo.substring(0, 1).trim())) {
                        paramMap.put("plateNumber", carInfo.substring(1, carInfo.length()).trim());//车牌号
                        paramMap.put("byCountry", carInfo.substring(1, 2).trim());//省份
                        paramMap.put("byColor", carInfo.substring(0, 1).trim());//车牌颜色
                        paramMap.put("cameraIp", new String(pAlarmer.sDeviceIP).trim());//ip地址
                        paramMap.put("picTime", dateFormat.format(new Date()));//当前时间
                        paramMap.put("wSpeed", String.valueOf(new Random().nextInt(55 - 5) + 5));//速度 这是假的
                        paramMap.put("byIllegalType", BreakRulesType.getBreakRulesType(strItsPlateResult.wIllegalType));
                        for (int i = 0; i < strItsPlateResult.dwPicNum; i++) {
                            if (strItsPlateResult.struPicInfo[i].dwDataLen > 0) {
                                FileOutputStream fout;
                                if (strItsPlateResult.struPicInfo[i].byType == 0) { //车牌图片
                                    File file = new File(filename + imgName);
                                    if (!file.getParentFile().exists()) {
                                        file.getParentFile().mkdirs();
                                    }
                                    fout = new FileOutputStream(filename + imgName);
                                    System.out.println("文件路径" + filename + imgName);
                                    //将字节写入文件
                                    long offset = 0;
//                                    ByteBuffer buffers = ByteBuffer.wrap(strItsPlateResult.struPicInfo[i].pBuffer, (int) offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                    ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                    byte[] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                                    buffers.rewind();
                                    buffers.get(bytes);
                                    fout.write(bytes);
                                    fout.close();
                                }
                                if (strItsPlateResult.struPicInfo[i].byType == 1) {//车辆场景图片
                                    File file = new File(carFileName + clImgName);
                                    if (!file.getParentFile().exists()) {
                                        file.getParentFile().mkdirs();
                                    }
                                    fout = new FileOutputStream(carFileName + clImgName);
                                    System.out.println("文件路径" + carFileName + clImgName);
                                    //将字节写入文件
                                    long offset = 0;
//                                    ByteBuffer buffers = ByteBuffer.wrap(strItsPlateResult.struPicInfo[i].pBuffer, (int) offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                    ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                    byte[] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                                    buffers.rewind();
                                    buffers.get(bytes);
                                    fout.write(bytes);
                                    fout.close();
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
                System.out.println("未知报警类型");
                break;
        }
    }
};