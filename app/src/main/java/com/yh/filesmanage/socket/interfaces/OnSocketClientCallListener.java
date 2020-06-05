package com.yh.filesmanage.socket.interfaces;

public class OnSocketClientCallListener implements OnSocketClientCallBackList{
        @Override
        public void onSocketConnectionSuccess(String msg) {

        }

        @Override
        public void onSocketConnectionFailed(String msg, Exception e) {

        }

        @Override
        public void onSocketDisconnection(String msg, Exception e) {

        }

        @Override
        public void onSocketReadResponse(byte[] bytes) {

        }

        @Override
        public void onSocketWriteResponse(byte[] bytes) {

        }
}