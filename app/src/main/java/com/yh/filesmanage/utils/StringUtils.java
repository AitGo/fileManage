package com.yh.filesmanage.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.yh.filesmanage.diagnose.FileInfo;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建者 ly
 * @创建时间 2019/3/15
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class StringUtils {

    public static boolean checkString(String str) {
        if(str != null && !str.equals("")) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPassword(String password) {
        if (password.length() < 6) {
            return false;
        } else {
            return true;
        }

    }

    public static String Date2String(Date date,String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Calendar Date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date Calendar2Date(Calendar calendar) {
        return calendar.getTime();
    }

    public static String Calendar2String(Calendar calendar,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    public static Calendar String2Calendar(String strdate, String format) {
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String long2String(long milSecond,String format) {
        Date date = new Date(milSecond);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }


    public static long String2long(String dateString,String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String copyToInternalPath(Activity activity, String OldPath){
        String NewPath = "";
        File mediaStorageDir = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Report");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return NewPath;
            }
        }
        String[] filename = OldPath.split("/");
        NewPath = new File(mediaStorageDir.getPath() + File.separator + filename[filename.length-1]).toString();
        Log.d("Anita", "new path = "+NewPath);
//        BackupRestore.copyFile(OldPath, NewPath);
//        BackupRestore.deleteFiles(new File(OldPath));
        return NewPath;
    }


    /**
     * java计算文件32位md5值
     * @param filePath 文件路径
     * @return
     */
    public static String md5HashCode32(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;//解释参见最下方
                if (val < 16) {
                    /**
                     * 如果小于16，那么val值的16进制形式必然为一位，
                     * 因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
                     * 此处高位补0。
                     */
                    hexValue.append("0");
                }
                //这里借助了Integer类的方法实现16进制的转换
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getRamdom(int count) {
        String strRand="" ;
        for(int i=0;i<count;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    /**
     * 压缩包23位名字，除开后缀19位
     * @param createDate
     * @return
     */
    public static String getCompareZipFileName(long createDate) {
        return "A" + createDate + getRamdom(5) + ".zip";
    }

    public static String dateToWeek(Date datetime) {
//        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
//        Date datet = null;
        //            datet = f.parse(datetime);
        cal.setTime(datetime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getNumber(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return i + "";
    }

    public static String selectState(String i) {
        String state = "";
        switch (i) {
            case "00":
                state = "与本列通讯不成功";
                break;
            case "01":
                state = "架体到位自动保护";
                break;
            case "02":
                state = "前通道门禁保护";
                break;
            case "03":
                state = "通道红外线保护";
                break;
            case "04":
                state = "等待操作";
                break;
            case "05":
                state = "资料显示";
                break;
            case "07":
                state = "所有架体关到位";
                break;
            case "08":
                state = "所有架体通风到位";
                break;
            case "09":
                state = "后通道门禁保护";
                break;
            case "0B":
                state = "正在开架移动";
                break;
            case "0C":
                state = "正在合架移动";
                break;
            case "0D":
                state = "题名显示";
                break;
            case "0E":
                state = "解除系统保护";
                break;
            case "0F":
                state = "解除通道内保护";
                break;
            case "10":
                state = "红外正常工作";
                break;
            case "11":
                state = "链路出错地址如下";
                break;
            case "12":
                state = "开架限位故障";
                break;
            case "13":
                state = "合架限位故障";
                break;
            case "14":
                state = "电机故障";
                break;
            case "15":
                state = "红外线故障";
                break;
            case "18":
                state = "启动电源";
                break;
            case "1A":
                state = "正在通风移动";
                break;
            case "1B":
                state = "移动按键允许";
                break;
            case "1C":
                state = "移动按键禁用";
                break;
            case "1D":
                state = "复位系统";
                break;
            case "1E":
                state = "正在通风移动";
                break;
            case "1F":
                state = "资料定位显示";
                break;
            case "20":
                state = "通讯故障";
                break;
            case "21":
                state = "底部红外光幕保护";
                break;
            case "22":
                state = "自动开架移动";
                break;
            case "23":
                state = "自动合架移动";
                break;
            case "24":
                state = "压力传感器挤压";
                break;
            case "25":
                state = "侧例门未完全上锁";
                break;
            case "26":
                state = "手动刹车锁定";
                break;
            case "27":
                state = "手动刹车解锁";
                break;
            case "29":
                state = "压力传感器松开";
                break;
            case "2A":
                state = "通道有人弹开架体";
                break;
            case "2B":
                state = "正在准备通风中";
                break;
            case "2C":
                state = "压力挤压弹开架体";
                break;
            case "2D":
                state = "自动进入待机";
                break;

            case "50":
                state = "正在开门";
                break;
            case "51":
                state = "正在关门";
                break;
            case "52":
                state = "层正在旋转";
                break;
            case "53":
                state = "开门到位";
                break;
            case "54":
                state = "关门到位";
                break;
            case "55":
                state = "层旋转到位";
                break;
            case "56":
                state = "正在盘点";
                break;
            case "57":
                state = "存取窗口有阻挡物";
                break;
        }
        return state;
    }

    public static String getShelfNo(FileInfo info, String s) {
        String shelfNo = "";
        shelfNo = info.getHouseSNo() + info.getAreaNO() + info.getCabinetNo() + info.getFaceNo() + info.getClassNo() + info.getLayerNo() + s;
        return shelfNo;
    }
}
