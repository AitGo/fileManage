package com.yh.filesmanage.diagnose;

/**
 * @创建者 ly
 * @创建时间 2020/7/17
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
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

    private int id;
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
    private String shelf_no;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolder_no() {
        return folder_no;
    }

    public void setFolder_no(String folder_no) {
        this.folder_no = folder_no;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getResponsibleby() {
        return responsibleby;
    }

    public void setResponsibleby(String responsibleby) {
        this.responsibleby = responsibleby;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSbt_word() {
        return sbt_word;
    }

    public void setSbt_word(String sbt_word) {
        this.sbt_word = sbt_word;
    }

    public int getFiling_year() {
        return filing_year;
    }

    public void setFiling_year(int filing_year) {
        this.filing_year = filing_year;
    }

    public String getCase_no() {
        return case_no;
    }

    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }

    public int getArchive_type_id() {
        return archive_type_id;
    }

    public void setArchive_type_id(int archive_type_id) {
        this.archive_type_id = archive_type_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBox_barcode() {
        return box_barcode;
    }

    public void setBox_barcode(String box_barcode) {
        this.box_barcode = box_barcode;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getShelf_no() {
        return shelf_no;
    }

    public void setShelf_no(String shelf_no) {
        this.shelf_no = shelf_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
