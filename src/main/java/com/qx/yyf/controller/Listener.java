package com.qx.yyf.controller;

import com.qx.yyf.util.JavaMailSend;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author yuyunfeng
 * @create_time 2018/12/7
 * @describe ${class}
 */
@Component
public class Listener {
    //101.37.117.13服务器监听
    private static long old_send_time= System.currentTimeMillis();

    @Scheduled(cron = "0/30 * * * * ?")
    public void listen(){
        if( isHostConnectable("101.37.117.13",8090)){
            System.out.println("IT'S OK!");
        }else {
            if(System.currentTimeMillis()-old_send_time>1000*10*60){
                old_send_time=System.currentTimeMillis();
                JavaMailSend. sendMail("626477063@qq.com","101.37.117.13服务器炸啦！","BOOM!");
            }
        }
    }

    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
