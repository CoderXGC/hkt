package com.example.hkt.controller;

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
    @RequestMapping(value = "/test")
    public void test() {
        String username = "admin";
        String password = "passw0rd";
        Short port = 8000;
        int userid;
        String ip = "172.18.2.125";
        ClientDemo clientDemo = new ClientDemo();
        Pointer pUser = null;
        try {
            clientDemo.Camerainit();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        userid=clientDemo.register(username, password, ip);
     clientDemo.SetupAlarmChan();
    // clientDemo.StartAlarmListen();
       System.out.println("输出id"+userid);
        clientDemo.zhuaTu(userid);
    }
}