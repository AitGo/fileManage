package com.yh.filesmanage.diagnose;

import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.yh.filesmanage.utils.CRC16;

/**
 * @创建者 ly
 * @创建时间 2020/6/3
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class RFIDEntity implements ISendable {

    private byte head;
    private byte[] length;
    private byte[] address;
    private byte[] controllerCode;
    private byte[] orderNo;
    private byte[] data;
    private byte[] crcCode;

    public byte getHead() {
        return head;
    }

    public void setHead(byte head) {
        this.head = head;
    }

    public byte[] getLength() {
        return intToBytes(address.length + controllerCode.length + orderNo.length + data.length);
    }

    public void setLength() {
        this.length = intToBytes(address.length + controllerCode.length + orderNo.length + data.length);
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    public byte[] getControllerCode() {
        return controllerCode;
    }

    public void setControllerCode(byte[] controllerCode) {
        this.controllerCode = controllerCode;
    }

    public byte[] getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(byte[] orderNo) {
        this.orderNo = orderNo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getCrcCode() {
        return intToBytes(CRC16.CRC16_CCITT(byteMergerAll(length,address,controllerCode,orderNo,data)));
    }

    public void setCrcCode() {
        this.crcCode = intToBytes(CRC16.CRC16_CCITT(byteMergerAll(length,address,controllerCode,orderNo,data)));
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     *
     * @param src
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    private static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    @Override
    public byte[] parse() {
        return byteMergerAll(length,address,controllerCode,orderNo,crcCode);
    }
}
