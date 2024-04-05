/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drugstoreController;

import java.awt.Image;
import java.util.Date;

/**
 *
 * @author User
 */
public class HangHoa {
    private int maThuoc;
    private String tenThuoc;
    private int tenLoaiThuoc;
    private Date ngaySX;
    private Date hanSD;
    private String donViTinh;
    private float giaNhap;
    private float donGia;
    private int soLuongNhap;
    private Image hinhAnh;
    private String trangThaiSP;

    public HangHoa(int maThuoc, String tenThuoc, int tenLoaiThuoc, Date ngaySX, Date hanSD, String donViTinh, float giaNhap, float donGia, int soLuongNhap, Image hinhAnh, String trangThaiSP) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.tenLoaiThuoc = tenLoaiThuoc;
        this.ngaySX = ngaySX;
        this.hanSD = hanSD;
        this.donViTinh = donViTinh;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.soLuongNhap = soLuongNhap;
        this.hinhAnh = hinhAnh;
        this.trangThaiSP = trangThaiSP;
    }

    public HangHoa() {
    }
    

    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public int getTenLoaiThuoc() {
        return tenLoaiThuoc;
    }

    public void setTenLoaiThuoc(int tenLoaiThuoc) {
        this.tenLoaiThuoc = tenLoaiThuoc;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public Date getHanSD() {
        return hanSD;
    }

    public void setHanSD(Date hanSD) {
        this.hanSD = hanSD;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public Image getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(Image hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTrangThaiSP() {
        return trangThaiSP;
    }

    public void setTrangThaiSP(String trangThaiSP) {
        this.trangThaiSP = trangThaiSP;
    }
    
    
    
}
