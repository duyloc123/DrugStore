/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drugstoreController;

import java.util.Date;

/**
 *
 * @author User
 */
public class HoaDon {
    private int ID_HD;
    private int ID_NV;
    private int ID_KH;
    private Date NgayLap;
    private float TongTien;

    public HoaDon(int ID_HD, int ID_NV, int ID_KH, Date NgayLap, float TongTien) {
        this.ID_HD = ID_HD;
        this.ID_NV = ID_NV;
        this.ID_KH = ID_KH;
        this.NgayLap = NgayLap;
        this.TongTien = TongTien;
    }

    public int getID_HD() {
        return ID_HD;
    }

    public void setID_HD(int ID_HD) {
        this.ID_HD = ID_HD;
    }

    public int getID_NV() {
        return ID_NV;
    }

    public void setID_NV(int ID_NV) {
        this.ID_NV = ID_NV;
    }

    public int getID_KH() {
        return ID_KH;
    }

    public void setID_KH(int ID_KH) {
        this.ID_KH = ID_KH;
    }

    public Date getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(Date NgayLap) {
        this.NgayLap = NgayLap;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }
    
    
}
