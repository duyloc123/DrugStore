/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drugstoreController;

/**
 *
 * @author User
 */
public class HoaDonChiTiet {
    private int ID_HD;
    private int ID_Thuoc;
    private float DonGia;
    private float SoLuong;
    private String DonViTinh;

    public HoaDonChiTiet(int ID_HD, int ID_Thuoc, float DonGia, float SoLuong, String DonViTinh) {
        this.ID_HD = ID_HD;
        this.ID_Thuoc = ID_Thuoc;
        this.DonGia = DonGia;
        this.SoLuong = SoLuong;
        this.DonViTinh = DonViTinh;
    }

    public int getID_HD() {
        return ID_HD;
    }

    public void setID_HD(int ID_HD) {
        this.ID_HD = ID_HD;
    }

    public int getID_Thuoc() {
        return ID_Thuoc;
    }

    public void setID_Thuoc(int ID_Thuoc) {
        this.ID_Thuoc = ID_Thuoc;
    }

    public float getDonGia() {
        return DonGia;
    }

    public void setDonGia(float DonGia) {
        this.DonGia = DonGia;
    }

    public float getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(float SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getDonViTinh() {
        return DonViTinh;
    }

    public void setDonViTinh(String DonViTinh) {
        this.DonViTinh = DonViTinh;
    }
    
    
}
