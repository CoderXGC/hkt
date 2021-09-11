package com.example.hkt.controller;/**
 * @title: WebSocketController
 * @projectName hkt
 * @description: TODO
 * @author White
 * @date 2021/9/714:11
 */

import com.example.hkt.TailfLogThread;
import com.example.hkt.utils.ClientDemo;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

/**
 * @className    : WebSocketController
 * @description  : [描述说明该类的功能]  
 * @author       : [XuGuangchao]
 * @version      : [v1.0]
 * @createTime   : [2021/9/7 14:11]
 * @updateUser   : [LiuYanQiang]
 * @updateTime   : [2021/9/7 14:11]
 * @updateRemark : [描述说明本次修改内容] 
 */

@ServerEndpoint("/log")
@RestController
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        logger.info("进入链接....");
        String username = "admin";
        String password = "passw0rd";
        int userid;
        Short port = 8000;
        String ip = "172.18.2.125";
        ClientDemo clientDemo =new ClientDemo();
        Pointer pUser = null;
        try {
            clientDemo.CameraInit(session);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        userid=clientDemo.register(username,password,ip);
        clientDemo.SetupAlarmChan();
        clientDemo.StartAlarmListen();
   //     clientDemo.zhuaTu();
     /*   try {
            process = Runtime.getRuntime().exec("tail -f /logs/es-sync/es-sync.log");
            inputStream = process.getInputStream();
            TailfLogThread thread = new TailfLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}
