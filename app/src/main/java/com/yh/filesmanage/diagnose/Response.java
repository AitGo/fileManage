package com.yh.filesmanage.diagnose;

/**
 * @创建者 ly
 * @创建时间 2019/4/15
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class Response<T> {

    /**
     * Success : true
     * Message : null
     * MessageType : null
     * Data : null
     */

    private boolean Success;
    private String Message;
    private String MessageType;
    private T Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}