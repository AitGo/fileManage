package com.yh.filesmanage.diagnose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @创建者 ly
 * @创建时间 2020/7/17
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
@Entity
public class FileInfo {

    /**
     * id : 1324
     * folder_no : 0001
     * maintitle : 测试档案01
     * responsibleby :
     * create_time : 2020-07-13T08:52:49.9
     * sbt_word :
     * filing_year : 0
     * case_no :
     * archive_type_id : 1
     * barcode : 00000001
     * box_barcode : null
     * house_no : 00000001
     * shelf_no : 0101022010103
     * status : 1
     */

    @Id
    private String id;
    private String folder_no;
    private String maintitle;
    private String responsibleby;
    private String create_time;
    private String sbt_word;
    private int filing_year;
    private String case_no;
    private int archive_type_id;
    private String barcode;
    private String box_barcode;
    private String house_no;
    /**
     * 密集架架位地址
     * 01       02  03   2                   03    04   01
     * 库房号   区  列   面（1左2右）         节    层    本
     */
    private String shelf_no;
    private String status;//0：未上架 1：在架 2：出借预留 3：申请（预留） 4：待上架 5：出借不预留 6：销毁 7：下架

    private String houseSNo;//库房号
    private String areaNO;//区
    private String cabinetNo;//列
    private String faceNo;//面(1左2右）
    private String classNo;//节
    private String layerNo;//层
    private String boxNo;//盒

    private String rev1;//正确架位号
    private String rev2;
    private String rev3;
    private String rev4;
    @Generated(hash = 569647901)
    public FileInfo(String id, String folder_no, String maintitle,
            String responsibleby, String create_time, String sbt_word,
            int filing_year, String case_no, int archive_type_id, String barcode,
            String box_barcode, String house_no, String shelf_no, String status,
            String houseSNo, String areaNO, String cabinetNo, String faceNo,
            String classNo, String layerNo, String boxNo, String rev1, String rev2,
            String rev3, String rev4) {
        this.id = id;
        this.folder_no = folder_no;
        this.maintitle = maintitle;
        this.responsibleby = responsibleby;
        this.create_time = create_time;
        this.sbt_word = sbt_word;
        this.filing_year = filing_year;
        this.case_no = case_no;
        this.archive_type_id = archive_type_id;
        this.barcode = barcode;
        this.box_barcode = box_barcode;
        this.house_no = house_no;
        this.shelf_no = shelf_no;
        this.status = status;
        this.houseSNo = houseSNo;
        this.areaNO = areaNO;
        this.cabinetNo = cabinetNo;
        this.faceNo = faceNo;
        this.classNo = classNo;
        this.layerNo = layerNo;
        this.boxNo = boxNo;
        this.rev1 = rev1;
        this.rev2 = rev2;
        this.rev3 = rev3;
        this.rev4 = rev4;
    }
    @Generated(hash = 1367591352)
    public FileInfo() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFolder_no() {
        return this.folder_no;
    }
    public void setFolder_no(String folder_no) {
        this.folder_no = folder_no;
    }
    public String getMaintitle() {
        return this.maintitle;
    }
    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }
    public String getResponsibleby() {
        return this.responsibleby;
    }
    public void setResponsibleby(String responsibleby) {
        this.responsibleby = responsibleby;
    }
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getSbt_word() {
        return this.sbt_word;
    }
    public void setSbt_word(String sbt_word) {
        this.sbt_word = sbt_word;
    }
    public int getFiling_year() {
        return this.filing_year;
    }
    public void setFiling_year(int filing_year) {
        this.filing_year = filing_year;
    }
    public String getCase_no() {
        return this.case_no;
    }
    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }
    public int getArchive_type_id() {
        return this.archive_type_id;
    }
    public void setArchive_type_id(int archive_type_id) {
        this.archive_type_id = archive_type_id;
    }
    public String getBarcode() {
        return this.barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getBox_barcode() {
        return this.box_barcode;
    }
    public void setBox_barcode(String box_barcode) {
        this.box_barcode = box_barcode;
    }
    public String getHouse_no() {
        return this.house_no;
    }
    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }
    public String getShelf_no() {
        return this.shelf_no;
    }
    public void setShelf_no(String shelf_no) {
        this.shelf_no = shelf_no;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getHouseSNo() {
        return this.houseSNo;
    }
    public void setHouseSNo(String houseSNo) {
        this.houseSNo = houseSNo;
    }
    public String getAreaNO() {
        return this.areaNO;
    }
    public void setAreaNO(String areaNO) {
        this.areaNO = areaNO;
    }
    public String getCabinetNo() {
        return this.cabinetNo;
    }
    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }
    public String getFaceNo() {
        return this.faceNo;
    }
    public void setFaceNo(String faceNo) {
        this.faceNo = faceNo;
    }
    public String getClassNo() {
        return this.classNo;
    }
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }
    public String getLayerNo() {
        return this.layerNo;
    }
    public void setLayerNo(String layerNo) {
        this.layerNo = layerNo;
    }
    public String getBoxNo() {
        return this.boxNo;
    }
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }
    public String getRev1() {
        return this.rev1;
    }
    public void setRev1(String rev1) {
        this.rev1 = rev1;
    }
    public String getRev2() {
        return this.rev2;
    }
    public void setRev2(String rev2) {
        this.rev2 = rev2;
    }
    public String getRev3() {
        return this.rev3;
    }
    public void setRev3(String rev3) {
        this.rev3 = rev3;
    }
    public String getRev4() {
        return this.rev4;
    }
    public void setRev4(String rev4) {
        this.rev4 = rev4;
    }
    

}
