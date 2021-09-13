package com.example.hkt; /**
 * @title: TailfLogThread
 * @projectName hkt
 * @description: TODO
 * @author White
 * @date 2021/9/714:15
 */

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @className    : TailfLogThread
 * @description  : [描述说明该类的功能]  
 * @author       : [XuGuangchao]
 * @version      : [v1.0]
 * @createTime   : [2021/9/7 14:15]
 * @updateUser   : [XuGuangchao]
 * @updateTime   : [2021/9/7 14:15]
 * @updateRemark : [描述说明本次修改内容] 
 */

public class TailfLogThread extends Thread {

    public BufferedReader reader;
    public Session session;

    public TailfLogThread(InputStream in, Session session) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.session = session;

    }

    @Override
    public void run() {
        String line;
        try {
            while((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                session.getBasicRemote().sendText(line + "<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

