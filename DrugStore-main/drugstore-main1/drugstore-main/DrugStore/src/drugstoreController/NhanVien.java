/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drugstoreController;

import java.awt.Image;

/**
 *
 * @author User
 */
public class NhanVien {
    private int maNV;
    private String tenNV;
    private String diaChi;
    private String soDienThoai;
    private float Luong;
    private String tenLoai;
    private byte[] hinhAnh;
    private String caLamViec;
    private String taiKhoan;
    private String matKhau;

    public NhanVien(int maNV, String tenNV, String diaChi, String soDienThoai, byte[] hinhAnh,float Luong, String tenLoai, String caLamViec, String taiKhoan, String matKhau) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.hinhAnh = hinhAnh;
        this.Luong = Luong;
        this.tenLoai = tenLoai;
        this.caLamViec = caLamViec;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public NhanVien(){
        
    }

    public float getLuong() {
        return Luong;
    }

    public void setLuong(float Luong) {
        this.Luong = Luong;
    }
    
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
   
    public String getCaLamViec() {
        return caLamViec;
    }

    public void setCaLamViec(String caLamViec) {
        this.caLamViec = caLamViec;
    }
    
    public String getTaiKhoan(){
        return taiKhoan;
    }
    public void setTaiKhoan(String taiKhoan){
        this.taiKhoan = taiKhoan;
    }
    
    public String getMatKhau(){
        return matKhau;
    }
    public void setMatKhau(String matKhau){
        this.matKhau = matKhau;
    }
}
