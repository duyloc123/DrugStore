/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drugstoreController;

/**
 *
 * @author User
 */
public class NhaCungCap {
    private int maNCC;
    private String tenNCC;
    private String soDienThoaiNCC;
    private String diaChiNCC;

    public NhaCungCap(int maNCC, String tenNCC, String soDienThoaiNCC, String diaChiNCC) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.soDienThoaiNCC = soDienThoaiNCC;
        this.diaChiNCC = diaChiNCC;
    }

    public NhaCungCap() {
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getSoDienThoaiNCC() {
        return soDienThoaiNCC;
    }

    public void setSoDienThoaiNCC(String soDienThoaiNCC) {
        this.soDienThoaiNCC = soDienThoaiNCC;
    }

    public String getDiaChiNCC() {
        return diaChiNCC;
    }

    public void setDiaChiNCC(String diaChiNCC) {
        this.diaChiNCC = diaChiNCC;
    }
    
    
}
