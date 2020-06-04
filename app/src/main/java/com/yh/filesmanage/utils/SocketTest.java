package com.yh.filesmanage.utils;

import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.yh.filesmanage.diagnose.RFIDEntity;

/**
 * @创建者 ly
 * @创建时间 2020/6/3
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class SocketTest {

    public interface connectSuccessCallback {
        void connectSuccess(ConnectionInfo info);
    }

    public static void connectSocket(String ip, int port,connectSuccessCallback callback) {
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        ConnectionInfo info = new ConnectionInfo(ip, port);
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        IConnectionManager manager = OkSocket.open(info);
        //注册Socket行为监听器,SocketActionAdapter是回调的Simple类,其他回调方法请参阅类文档
        manager.registerReceiver(new SocketActionAdapter(){
            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
//                OkSocket.open(info)
//                        .send(new RFIDEntity());
                callback.connectSuccess(info);
            }
        });
        //调用通道进行连接
        manager.connect();
    }
}
