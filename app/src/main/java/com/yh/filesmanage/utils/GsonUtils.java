package com.yh.filesmanage.utils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yh.filesmanage.diagnose.FileInfo;
import com.yh.filesmanage.diagnose.Response;
import com.yh.filesmanage.diagnose.ResponseList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ikidou.reflect.TypeBuilder;

/**
 * @创建者 ly
 * @创建时间 2019/4/22
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class GsonUtils {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(new Gson().fromJson(elem, cls));
        }
        return list;
    }


    public static <T> Response<List<T>> fromJsonArray1(String json, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(Response.class)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        return new Gson().fromJson(json, type);
    }

    public static <T> Response<T> fromJsonObject(String json, Class<String> clazz) {
        Type type = TypeBuilder
                .newInstance(Response.class)
                .addTypeParam(clazz)
                .build();
        return new Gson().fromJson(json, type);
    }

    public static <T> Response<T> fromJsonArray(String json, Class<ResponseList> clazz) {
        Type type = TypeBuilder
                .newInstance(Response.class)
                .addTypeParam(clazz)
                .build();
        return new Gson().fromJson(json, type);
    }


    public static List<FileInfo> map2List(ResponseList<Map<String,Object>> responseList) {
        List<FileInfo> fileInfos = new ArrayList<>();
        List<Map<String,Object>> list1 = responseList.getList();
        for(int i = 0; i < list1.size(); i++) {
            FileInfo fileInfo = new FileInfo();
            Double id = (Double) list1.get(i).get("id");
            int ii = id.intValue();
            fileInfo.setId(ii + "");
            fileInfo.setFolder_no((String) list1.get(i).get("folder_no"));
            fileInfo.setMaintitle((String) list1.get(i).get("maintitle"));
            fileInfo.setResponsibleby((String) list1.get(i).get("responsibleby"));
            fileInfo.setCreate_time((String) list1.get(i).get("create_time"));
            fileInfo.setSbt_word((String) list1.get(i).get("sbt_word"));
            Double filing_year = ( Double) list1.get(i).get("filing_year");
            if(filing_year != null) {
                int f = filing_year.intValue();
                fileInfo.setFiling_year(f);
            }

            fileInfo.setCase_no((String) list1.get(i).get("case_no"));
            Double archive_type_id = (Double) list1.get(i).get("archive_type_id");
            if(archive_type_id != null) {
                int a = archive_type_id.intValue();
                fileInfo.setArchive_type_id(a);
            }
            fileInfo.setBarcode((String) list1.get(i).get("barcode"));
            fileInfo.setBox_barcode((String) list1.get(i).get("box_barcode"));
            fileInfo.setHouse_no((String) list1.get(i).get("house_no"));
            fileInfo.setShelf_no((String) list1.get(i).get("shelf_no"));
            fileInfo.setStatus((String) list1.get(i).get("status"));
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }
}
