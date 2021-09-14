package com.example.hkt.controller;

import com.example.hkt.info.CameraInfo;
import com.example.hkt.utils.ClientDemo;
import com.sun.jna.Pointer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

/**
 * @author White
 * @title: CameraController
 * @projectName hkt
 * @description: TODO
 * @date 2021/9/714:00
 */
@RestController










@RequestMapping("/zhuatu")
public class CameraController {
    CameraInfo cameraInfo =new CameraInfo();
    ClientDemo clientDemo = new ClientDemo();
    @RequestMapping(value = "/test")
    public void test() {
        String username = "admin";
        String password = "passw0rd";
        Short port = 8000;
        int userid;
        String ip = "172.18.2.125";

        Pointer pUser = null;


        if (cameraInfo.getLUserID()==1210) {
            clientDemo.CameraInit();
            userid = clientDemo.register(username, password, ip);
            System.out.println("传入"+userid);
            cameraInfo.setLUserID(userid);
            System.out.println("传出"+cameraInfo.getLUserID());
            clientDemo.SetupAlarmChan();
            clientDemo.StartAlarmListen();
            clientDemo.zhuaTu(userid);
        }else {
            clientDemo.zhuaTu(cameraInfo.getLUserID());
        }





    }
}