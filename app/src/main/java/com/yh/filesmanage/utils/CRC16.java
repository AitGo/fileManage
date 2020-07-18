package com.yh.filesmanage.utils;


import com.yh.filesmanage.base.BaseEvent;
import com.yh.filesmanage.base.Constants;
import com.yh.filesmanage.diagnose.FileInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRC16_CCITT：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在前，高位在后，结果与0x0000异或
 * CRC16_CCITT_FALSE：多项式x16+x12+x5+1（0x1021），初始值0xFFFF，低位在后，高位在前，结果与0x0000异或
 * CRC16_XMODEM：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在后，高位在前，结果与0x0000异或
 * CRC16_X25：多项式x16+x12+x5+1（0x1021），初始值0xffff，低位在前，高位在后，结果与0xFFFF异或
 * CRC16_MODBUS：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0x0000异或
 * CRC16_IBM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0x0000异或
 * CRC16_MAXIM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
 * CRC16_USB：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0xFFFF异或
 * CRC16_DNP：多项式x16+x13+x12+x11+x10+x8+x6+x5+x2+1（0x3D65），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
 * <p>
 * （1）、预置1个16位的寄存器为十六进制FFFF（即全为1），称此寄存器为CRC寄存器；
 * （2）、把第一个8位二进制数据（既通讯信息帧的第一个字节）与16位的CRC寄存器的低8位相异或，把结果放于CRC寄存器，高八位数据不变；
 * （3）、把CRC寄存器的内容右移一位（朝低位）用0填补最高位，并检查右移后的移出位；
 * （4）、如果移出位为0：重复第3步（再次右移一位）；如果移出位为1，CRC寄存器与多项式A001（1010 0000 0000 0001）进行异或；
 * （5）、重复步骤3和4，直到右移8次，这样整个8位数据全部进行了处理；
 * （6）、重复步骤2到步骤5，进行通讯信息帧下一个字节的处理；
 * （7）、将该通讯信息帧所有字节按上述步骤计算完成后，得到的16位CRC寄存器的高、低字节进行交换；
 * （8）、最后得到的CRC寄存器内容即为：CRC码。
 * <p>
 * 以上计算步骤中的多项式0xA001是0x8005按位颠倒后的结果。
 * 0x8408是0x1021按位颠倒后的结果。
 * 在线校验工具
 * http://www.ip33.com/crc.html
 * https://blog.csdn.net/htmlxx/article/details/17369105
 * <p>
 * Author:Water
 * Time:2018/11/19 0019 15:03
 */
public class CRC16 {

    public static void main(String args[]) {
        //测试数据
//        byte[] bytes = new byte[]{ (byte) 0x1B, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x01, (byte) 0x80, (byte) 0x05
//                , (byte) 0x01
//                , (byte) 0x01
//                , (byte) 0x01, (byte) 0x83
//                , (byte) 0x02, (byte) 0x83
//                , (byte) 0x03, (byte) 0x83
//                , (byte) 0x04, (byte) 0x83
//                , (byte) 0x05, (byte) 0x83
//                , (byte) 0x06, (byte) 0x83
//                , (byte) 0x07, (byte) 0x83
//                , (byte) 0x08, (byte) 0xE0, (byte) 0x04, (byte) 0x01, (byte) 0x50, (byte) 0xDC, (byte) 0x8B, (byte) 0x65, (byte) 0xCB
//                , (byte) 0x09, (byte) 0x83
//                , (byte) 0x0A, (byte) 0x83
//                , (byte) 0x0B, (byte) 0x83
//                , (byte) 0x0C, (byte) 0xE0, (byte) 0x04, (byte) 0x01, (byte) 0x50, (byte) 0xDC, (byte) 0x63, (byte) 0x47, (byte) 0xCF
//                , (byte) 0x0D, (byte) 0xE0, (byte) 0x04, (byte) 0x01, (byte) 0x50, (byte) 0xDC, (byte) 0x63, (byte) 0x47, (byte) 0xCF
//                , (byte) 0x0E, (byte) 0x83
//                , (byte) 0x0F, (byte) 0xE0, (byte) 0x04, (byte) 0x01, (byte) 0x50, (byte) 0xDC, (byte) 0x63, (byte) 0xAA, (byte) 0x8F
//                , (byte) 0xEE, (byte) 0xE3};

        byte[] bytes = new byte[]{ (byte) 0x1B, (byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x01, (byte) 0x80, (byte) 0x05
                , (byte) 0x01
                , (byte) 0x01
                , (byte) 0x01, (byte) 0x83
                , (byte) 0x02, (byte) 0x83
                , (byte) 0x03, (byte) 0x83
                , (byte) 0x04, (byte) 0x83
                , (byte) 0x05, (byte) 0x83
                , (byte) 0x06, (byte) 0x83
                , (byte) 0x07, (byte) 0x83
                , (byte) 0x08, (byte) 0x83
                , (byte) 0x09, (byte) 0x83
                , (byte) 0x0A, (byte) 0x83
                , (byte) 0x0B, (byte) 0x83
                , (byte) 0x0C, (byte) 0x83
                , (byte) 0x0D, (byte) 0x83
                , (byte) 0x0E, (byte) 0x83
                , (byte) 0x0F, (byte) 0xE0, (byte) 0x04, (byte) 0x01, (byte) 0x50, (byte) 0xDC, (byte) 0x63, (byte) 0x91, (byte) 0xF0
                , (byte) 0x45, (byte) 0x8D};

//        byte[] bytes = new byte[]{ (byte) 0xAC, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
//                , (byte) 0x1A
//                , (byte) 0x46
//                , (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
//                , (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9E};

//
//        int parseInt = Integer.parseInt(substring2, 16);
//        int parseInt2 = Integer.parseInt(substring3, 16);
        byte[] bytes2 = {(byte) 74, (byte) 0xae};
        byte[] bytes1 = {(byte) 0x4A, (byte) 174};
//        byte[] bytes3 = {(byte) parseInt, (byte) parseInt2};
//        for(int j = 0; j < bytes2.length;j++ ) {
//            String s = HexUtil.byteToHexString(bytes2[j]);
//            int intForHexString = HexUtil.getIntForHexInt(10);
//            System.out.println(intForHexString + ":");
//        }
        int i = CRC16_CCITT(bytes);
        byte[] socketBytes = HexUtil.getSocketBytes(bytes);
        System.out.println(HexUtil.byte2HexStr(socketBytes) + ":");

        //读取单层UID
        if("80".equals(HexUtil.byteToHexString(bytes[5]))) {
            List<FileInfo> fileInfos = new ArrayList<>();
            List<FileInfo> cwInfos = new ArrayList<>();
            int index = 9;
            while (index < bytes.length - 2) {
                if (!"83".equals(HexUtil.byteToHexString(bytes[index + 1]))) {
                    String s = HexUtil.byteToHexString(bytes[index]);
                    int id = HexUtil.getIntForHexString(s);
                    byte[] destBytes = new byte[8];
                    System.arraycopy(bytes, index + 1, destBytes, 0, 8);
                    String uid = HexUtil.byte2HexStrNoSpace(destBytes);
                    index += 9;
                    //保存id和uid
//                    LogUtils.e("index:" + index + "   boxNo:" + id + "   uid:" + uid);
                    System.out.println("   boxNo:" + id + "   uid:" + uid);

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ToastUtils.showLong("boxNo:" + s + "   uid:" + uid);
//                            //查询数据库，对比数据
//                            FileInfo info = new FileInfo();
//                            info.setHouseSNo(SPUtils.getParam(mContext, Constants.SP_NO_HOUSE, 1) + "");
//                            info.setAreaNO(SPUtils.getParam(mContext, Constants.SP_NO_AREA, 1) + "");
//                            info.setCabinetNo(SPUtils.getParam(mContext, Constants.SP_NO_CABINET, 1) + "");
//                            info.setFaceNo("1");
//                            info.setClassNo("1");
//                            info.setLayerNo(SPUtils.getParam(mContext, Constants.SP_NO_LAYER, 1) + "");
//                            List<FileInfo> infos = DBUtils.selectFileInfo(info, s, uid);
//                            if (infos.size() > 0) {
//                                for (FileInfo info1 : infos) {
//                                    info1.setRev1("");
//                                    //在位状态
////                                                info1.setStatus();
//                                    DBUtils.insertOrReplaceFileInfo(info1);
//                                }
////                                            fileInfos.addAll(infos);
//                            } else {
//                                //错位，查询错位的档案信息
//                                List<FileInfo> fileInfos1 = DBUtils.selectFileInfo(info, s);
//                                if (fileInfos1.size() > 0) {
//                                    for (FileInfo info1 : fileInfos1) {
//                                        String shelfNo = StringUtils.getShelfNo(info, s);
//                                        info1.setRev1(shelfNo);
//                                        //修改状态
////                                                    info1.setStatus();
//                                        DBUtils.insertOrReplaceFileInfo(info1);
//                                        cwInfos.add(info);
//                                    }
//                                }
//                            }
//                        }
//                    });

                } else {
                    index += 2;
                }
            }
        }
//        int intForHexString = Integer.toHexString(i);;
        System.out.println(Integer.toHexString(i) + ":");
        byte[] srtbyte = {1};
        try {
            String res = new String(srtbyte,"UTF-8");
            System.out.println(res + ":");
            Byte b = srtbyte[0];
            String tString1 = Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
            System.out.println(tString1 + ":");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String backString = "";
//        for (int i = 0; i < size; i++) {
//            LogUtils.e("接收到串口回调=" + bytes[i]);
//            backString = backString + HexUtil.byteToHexString(bytes[i]);
//        }
        //接受到命令后解析
//        if(!("AC").equals(HexUtil.byteToHexString(bytes[0])) || !("9E").equals(HexUtil.byteToHexString(bytes[bytes.length - 1]))) {
//            ToastUtils.showShort("命令不完整");
//            return;
//        }
//        if(bytes.length < 26) {
//            ToastUtils.showShort("命令不完整");
//            return;
//        }
//        String hightState = HexUtil.byteToHexString(bytes[3]);
//        //发送状态码
//        BaseEvent.CommonEvent event = BaseEvent.CommonEvent.UPDATE_STATE;
//        event.setObject(hightState);
//        EventBus.getDefault().post(event);
//        String hightAddress = HexUtil.byteToHexString(bytes[4]);
//        String lowState = HexUtil.byteToHexString(bytes[5]);
//        String lowAddress = HexUtil.byteToHexString(bytes[6]);
//        String fixedTemperature = HexUtil.byteToHexString(bytes[7]);
//        String fixedHumidity = HexUtil.byteToHexString(bytes[8]);
//        String pm25 = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[9],bytes[10]});
//        String tvoc = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[11],bytes[12]});
//        String co2 = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[13],bytes[14]});
//        String hightErrorLayer = HexUtil.byteToHexString(bytes[15]);
//        String lowErrorLayer = HexUtil.byteToHexString(bytes[16]);
////                String reportCode = HexUtil.byteToHexString(bytes[17]);
//
//        //判断报警位
////        checkReportCode(bytes[17]);
//        //温度
//        int temperature = HexUtil.getIntForHexString(HexUtil.byte2HexStrNoSpace(new byte[]{bytes[18],bytes[19]}));
////                temperature = HexUtil.getIntForHexString(backString.substring(37,41));
////        LogUtils.e("接受到的命令：temperature " + temperature);
//        //湿度
//        int humidity = HexUtil.getIntForHexString(HexUtil.byte2HexStrNoSpace(new byte[]{bytes[20],bytes[21]}));
////                humidity = HexUtil.getIntForHexString(backString.substring(41,45));
////        LogUtils.e("接受到的命令：humidity " + humidity);
//        String checkCab = HexUtil.byteToHexString(bytes[22]);
//        //甲醛
//        String forma = HexUtil.byte2HexStrNoSpace(new byte[]{bytes[23],bytes[24]});
//
//        String layer = HexUtil.byteToHexString(bytes[25]);
//        int layerNo = HexUtil.getIntForHexString(layer);
    }

    /**
     * CRC16_CCITT：多项式x16+x12+x5+1（0x8408），初始值0x0000，低位在前，高位在后，结果与0x0000异或
     * 0x8408是0x1021按位颠倒后的结果。
     * @param buffer
     * @return
     */
    public static int CRC16_CCITT(byte[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x8408;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
//        wCRCin=(wCRCin<<8)|(wCRCin>>8);
        wCRCin &= 0xffff;
        return wCRCin ^= 0xffff;

    }

    /**
     * CRC16_CCITT：多项式x16+x12+x5+1（0x8408），初始值0x0000，低位在前，高位在后，结果与0x0000异或
     * 0x8408是0x1021按位颠倒后的结果。
     * @param buffer
     * @return
     */
    public static int CRC16_CCITT1021(byte[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x8408;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
//        wCRCin=(wCRCin<<8)|(wCRCin>>8);
        wCRCin &= 0xffff;
        return wCRCin ^= 0x0000;

    }

    /**
     * CRC-CCITT (0xFFFF)
     * CRC16_CCITT_FALSE：多项式x16+x12+x5+1（0x1021），初始值0xFFFF，低位在后，高位在前，结果与0x0000异或
     *
     * @param buffer
     * @return
     */
    public static int CRC16_CCITT_FALSE(byte[] buffer) {
        int wCRCin = 0xffff;
        int wCPoly = 0x1021;
        for (byte b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        return wCRCin ^= 0x0000;
    }

    /**
     * CRC-CCITT (XModem)
     * CRC16_XMODEM：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在后，高位在前，结果与0x0000异或
     *
     * @param buffer
     * @return
     */
    public static int CRC16_XMODEM(byte[] buffer) {
        int wCRCin = 0x0000; // initial value 65535
        int wCPoly = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)
        for (byte b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        return wCRCin ^= 0x0000;
    }


    /**
     * CRC16_X25：多项式x16+x12+x5+1（0x1021），初始值0xffff，低位在前，高位在后，结果与0xFFFF异或
     * 0x8408是0x1021按位颠倒后的结果。
     * @param buffer
     * @return
     */
    public static int CRC16_X25(byte[] buffer) {
        int wCRCin = 0xffff;
        int wCPoly = 0x8408;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0xffff;
    }

    /**
     * CRC-16 (Modbus)
     * CRC16_MODBUS：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0x0000异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer
     * @return
     */
    public static int CRC16_MODBUS(byte[] buffer) {
        int wCRCin = 0xffff;
        int POLYNOMIAL = 0xa001;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0x0000;
    }

    /**
     * CRC-16
     * CRC16_IBM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0x0000异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer
     * @return
     */
    public static int CRC16_IBM(byte[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0xa001;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0x0000;
    }

    /**
     * CRC16_MAXIM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer
     * @return
     */
    public static int CRC16_MAXIM(byte[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0xa001;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0xffff;
    }

    /**
     * CRC16_USB：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0xFFFF异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer
     * @return
     */
    public static int CRC16_USB(byte[] buffer) {
        int wCRCin = 0xFFFF;
        int wCPoly = 0xa001;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0xffff;
    }

    /**
     * CRC16_DNP：多项式x16+x13+x12+x11+x10+x8+x6+x5+x2+1（0x3D65），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
     * 0xA6BC是0x3D65按位颠倒后的结果
     * @param buffer
     * @return
     */
    public static int CRC16_DNP(byte[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0xA6BC;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0xffff;
    }
}
