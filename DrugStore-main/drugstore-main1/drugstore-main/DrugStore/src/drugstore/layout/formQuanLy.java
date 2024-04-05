/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package drugstore.layout;


import drugstoreController.ConnectionDB;
import drugstoreController.HoaDon;
import drugstoreController.KhachHang;
import drugstoreController.NhanVien;
import java.awt.Color;
import java.awt.Image;
import java.awt.List;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author pjpjl
 */
public class formQuanLy extends javax.swing.JFrame {

    private ArrayList<NhanVien> dsnv = new ArrayList<>();
    private ArrayList<KhachHang> dskh = new ArrayList<>();
    
    private String hinhAnh = null;
    private String hinhAnhSP = null;
    byte[] dan_hinhAnh = null;

    final String header[] = {"Mã NV", "Loại NV","Tên NV", "Số điện thoại",
            "Giới tính", "Ngày sinh", "Hình ảnh", "Ca làm việc", "Tài khoản", "Mật khẩu"};
    final DefaultTableModel tb = new DefaultTableModel(header,0);
    
    ConnectionDB cn = new ConnectionDB();
    Connection conn;
    
        public void loadTableNV(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select *from NguoiDung");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 tb.addRow(row);
                tablenhanVien.setModel(tb);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
        
        
        tablenhanVien.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e){
                if(tablenhanVien.getSelectedRow() >=0){
                    txtmaNV.setText(tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),0)+"");
                    
                    int i = tablenhanVien.getSelectedRow();
                    TableModel model = tablenhanVien.getModel();
                    String chucvu = model.getValueAt(i,1).toString();
                    if(chucvu.equals("0")){
                        cbbCV.setSelectedItem("Vui lòng chọn");
                    }else if(chucvu.equals("1")){
                        cbbCV.setSelectedItem("Quản Lý");
                    }else{
                        cbbCV.setSelectedItem("Nhân Viên");
                    }
                    txttenNV.setText(tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),2)+"");
                    txtsdt.setText(tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),3)+"");
                    
                    cbbGT.setSelectedItem(tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(), 4));
                     try{
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),5));
                        jDate.setDate(date);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,ex);
                    }
                   // Xử lý hình ảnh từ Jtable lên JLabel và chỉnh sửa kích thước file ảnh
                    int selectedRow = tablenhanVien.getSelectedRow();
                    String imagePath = tablenhanVien.getValueAt(selectedRow,6).toString();
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage();
                    int imageWidth = 100;
                    int imageHeight = 100;
                    Image resizedImage = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_SMOOTH);
                    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
                    JLabel imageLabel = new JLabel();
                    FileLabel.setIcon(resizedImageIcon);
                    
                    cbbCLV.setSelectedItem(tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(), 7));
                    txtTaiKhoan.setText((String) tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),8));
                    txtMatKhau.setText((String) tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),9));
                    
            }
            }
        });
    }
        // hiển thị dữ liệu từ SQL lên table khách hàng
        final String header1[] = {"Mã khách hàng", "Tên khách hàng","Ngày sinh", "Giới tính",
            "Số điện thoại", "Địa chỉ"};
        final DefaultTableModel kh = new DefaultTableModel(header1,0);

    
        public void loadTableKH(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select *from KhachHang");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            kh.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 kh.addRow(row);
                tableKH.setModel(kh);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
        // Hiển thị khi thông tin khi chọn 1 hàng bên Jtable
        tableKH.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e){
                if(tableKH.getSelectedRow() >=0){
                    txtmaKH.setText(tableKH.getValueAt(tableKH.getSelectedRow(),0)+"");
                    cbbGT1.setSelectedItem(tableKH.getValueAt(tableKH.getSelectedRow(), 3));
                    txttenKH.setText(tableKH.getValueAt(tableKH.getSelectedRow(),1)+"");
                    txtSDTKH.setText(tableKH.getValueAt(tableKH.getSelectedRow(),4)+"");
                    txtDCKH.setText(tableKH.getValueAt(tableKH.getSelectedRow(),5)+"");
                    try{
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)tableKH.getValueAt(tableKH.getSelectedRow(),2));
                        jDate1.setDate(date);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,ex);
                    }
                }
            }
            
        });
        }
        final String headerNCC[] = {"Mã nhà cung cấp", "Tên nhà cung cấp","Số điện thoại", "Gmail",
            "Địa chỉ"};
        final DefaultTableModel ncc = new DefaultTableModel(headerNCC,0);

    
        public void loadTableNCC(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select *from NhaCungCap");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            ncc.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 ncc.addRow(row);
                tableNCC.setModel(ncc);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
         tableNCC.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e){
                if(tableNCC.getSelectedRow() >=0){
                    txtmaNCC.setText(tableNCC.getValueAt(tableNCC.getSelectedRow(),0)+"");
                    txttenNCC.setText(tableNCC.getValueAt(tableNCC.getSelectedRow(),1)+"");
                    txtsdtNCC.setText(tableNCC.getValueAt(tableNCC.getSelectedRow(),2)+"");
                    txtgmailNCC.setText(tableNCC.getValueAt(tableNCC.getSelectedRow(),3)+"");
                    txtdcNCC.setText(tableNCC.getValueAt(tableNCC.getSelectedRow(),4)+"");
                }
            }
            
        });
    }
        final String headerLT[] = {"Mã loại thuốc", "Tên loại thuốc"};
        final DefaultTableModel lt = new DefaultTableModel(headerLT,0);

    
        public void loadTableLT(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select *from LoaiThuoc");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            lt.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 lt.addRow(row);
                tableLT.setModel(lt);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
         tableLT.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e){
                if(tableLT.getSelectedRow() >=0){
                    txtloaithuoc.setText(tableLT.getValueAt(tableLT.getSelectedRow(),0)+"");
                    txttenloai.setText(tableLT.getValueAt(tableLT.getSelectedRow(),1)+"");
                }
            }
            
        });
    }
        final String headerThuoc[] = {"Mã thuốc", "Loại thuốc","Nhà cung cấp","Tên thuốc","Ngày sản xuất","Hạn sử dụng","Đơn vị tính","Giá nhập","Giá bán","Số lương nhập","Hình ảnh","Trạng thái"};
        final DefaultTableModel thuoc = new DefaultTableModel(headerThuoc,0);

    
        public void loadTableThuoc(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select ID_Thuoc,ID_LT,ID_NCC,TenThuoc,FORMAT(NgaySanXuat,'dd/MM/yyyy'),FORMAT(HanSuDung,'dd/MM/yyyy'),DonViTinh,FORMAT(GiaNhap,'#,###'),FORMAT(DonGia,'#,###'),SoLuongNhap,HinhAnh,TrangThai FROM Thuoc");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            thuoc.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 thuoc.addRow(row);
                tableThuoc.setModel(thuoc);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
        tableThuoc.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e){
                if(tableThuoc.getSelectedRow() >=0){
                    txtmathuoc.setText(tableThuoc.getValueAt(tableThuoc.getSelectedRow(),0)+"");
                    TableModel models = tableThuoc.getModel();
                    String loaithuoc = models.getValueAt(tableThuoc.getSelectedRow(),1).toString();
                    if(loaithuoc.equals("0")){
                        cbbLT.setSelectedItem("Vui lòng chọn");
                    }else if(loaithuoc.equals("1")){
                        cbbLT.setSelectedItem("vien");
                    }else{
                        cbbLT.setSelectedItem("sui");
                    }
                    cbbNCC.setSelectedItem(tableThuoc.getValueAt(tableThuoc.getSelectedRow(), 2));
                    String nhacungcap = models.getValueAt(tableThuoc.getSelectedRow(),2).toString();
                    if(nhacungcap.equals("0")){
                        cbbNCC.setSelectedItem("Vui lòng chọn");
                    }else if(loaithuoc.equals("1")){
                        cbbNCC.setSelectedItem("Pharmacity");
                    }else{
                        cbbNCC.setSelectedItem("Nhà thuốc quận 5");
                    }
                    txttenthuoc.setText(tableThuoc.getValueAt(tableThuoc.getSelectedRow(),3)+"");
                    try{
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)tableThuoc.getValueAt(tableThuoc.getSelectedRow(),4));
                        jdateNSX.setDate(date);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,ex);
                    }
                    try{
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)tableThuoc.getValueAt(tableThuoc.getSelectedRow(),5));
                        jdateHSD.setDate(date);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,ex);
                    }
                    
                    txtdvt.setText(tableThuoc.getValueAt(tableThuoc.getSelectedRow(),6)+"");
                    txtgianhap.setText(tableThuoc.getValueAt(tableThuoc.getSelectedRow(),7)+"");
                    txtgiaban.setText(tableThuoc.getValueAt(tableThuoc.getSelectedRow(),8)+"");
                    DefaultTableModel model = (DefaultTableModel)tableThuoc.getModel();
                    int selectedRow = tableThuoc.getSelectedRow();
                    int value = Integer.parseInt(tableThuoc.getValueAt(selectedRow, 9).toString());
                    txtsl.setValue(value);
                   // Xử lý hình ảnh từ Jtable lên JLabel và chỉnh sửa kích thước file ảnh
                    String imagePath = tableThuoc.getValueAt(selectedRow,10).toString();
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage();
                    int imageWidth = 100;
                    int imageHeight = 100;
                    Image resizedImage = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_SMOOTH);
                    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
                    JLabel imageLabel = new JLabel();
                    ImageLabel.setIcon(resizedImageIcon);
                    cbbtrangthai.setSelectedItem(tableThuoc.getValueAt(tableThuoc.getSelectedRow(), 11));
                    
                            int sl = 0;
                            float tt = 0;
                            float gb = 0;
                            tt = sl * gb;
                            txtTT.setText(String.valueOf(tt));
                    
            }
            }
        });
    }
        final String headerSPThuoc[] = {"Mã thuốc", "Loại thuốc","Tên thuốc","Đơn vị tính","Giá bán","Trạng thái"};
        final DefaultTableModel SPthuoc = new DefaultTableModel(headerSPThuoc,0);

    
        public void loadTableSPThuoc(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT Thuoc.ID_Thuoc,Thuoc.ID_LT,Thuoc.TenThuoc,Thuoc.DonViTinh,DonGia,Thuoc.TrangThai FROM THUOC");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            SPthuoc.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 SPthuoc.addRow(row);
                tableSPThuoc.setModel(SPthuoc);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
        tableSPThuoc.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e){
                if(tableSPThuoc.getSelectedRow() >=0){
                    txtMT.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),0)+"");
                    txtSP.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),2)+"");
                    txtDVT.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),3)+"");
                    txtgb.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),4)+"");
                    
            }
            }
        });
    }
        final String headerDSHD[] = {"ID_HD", "ID_ND","ID_KH","NgayLap","TongTien"};
        final DefaultTableModel DSHD = new DefaultTableModel(headerDSHD,0);

    
        public void loadTableDSHD(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select ID_HD,ID_ND,ID_KH,FORMAT(NgayLap,'dd/MM/yyyy'),FORMAT(TongTien,'#.###')FROM HOADON");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            DSHD.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 DSHD.addRow(row);
                tableDSHD.setModel(DSHD);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
    }
        final String headerCTHD[] = {"ID","Đơn giá","Số lượng","Đơn vị tính","Thành tiền"};
        final DefaultTableModel CTHD = new DefaultTableModel(headerCTHD,0);

    
        public void loadTableCTHD(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID_MACT,DonGia,SoLuong,DonViTinh,ThanhTien FROM CT_HoaDon");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            CTHD.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++)
                    row.addElement(rs.getString(i));
                 CTHD.addRow(row);
                tableHoaDon.setModel(CTHD);
            }
            st.close();
            rs.close();
            conn.close();
            
        } catch(Exception ex){
            
        }
    }

    /**
     * Creates new form JFramequanLy
     */
    public formQuanLy() {
        
        initComponents();
        loadTableNV();
        loadTableKH();
        loadTableNCC();
        loadTableLT();
        loadTableThuoc();
        loadTableSPThuoc();
        loadTableDSHD();
        initCV();
        initCaLamViec();
        initGioiTinh();
        initLoaiThuoc();
        initNCC();
        initTrangThai();
        initNV();
        initKH();
        
    }
     private void initNV() {
         conn = cn.getConnection();
        String sql = "SELECT HoTen FROM NguoiDung";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while(rs.next()){
                String name = rs.getString("HoTen");
                comboBoxModel.addElement(name);
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbnhanvien.setModel(comboBoxModel);
        }catch(Exception ex){
            
        }
    }
     
      private void initKH() {
         conn = cn.getConnection();
        String sql = "SELECT ID_KH, HoVaTen FROM KhachHang";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while(rs.next()){
                int id = rs.getInt("ID_KH");
                String name = rs.getString("HoVaTen");
                comboBoxModel.addElement(name);
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbkhachhang.setModel(comboBoxModel);
        }catch(Exception ex){
            
        }
    }
    
    private void initCV(){
         conn = cn.getConnection();
        String sql = "SELECT ID_LND, TenLND FROM LoaiNguoiDung";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while(rs.next()){
                int id = rs.getInt("ID_LND");
                String name = rs.getString("TenLND");
                comboBoxModel.addElement(name);
                cbbCV.addItem(name);
                
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbCV.setModel(comboBoxModel);
        }catch(Exception ex){
            
        }
    } 

    private void initChucVu() {
         conn = cn.getConnection();
        String sql = "SELECT TenLND FROM LoaiNguoiDung";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while(rs.next()){
                String name = rs.getString("TenLND");
                comboBoxModel.addElement(name);
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbCV.setModel(comboBoxModel);
        }catch(Exception ex){
            
        }
    }
    private void initCaLamViec() {
        String[] CLV = new String[]{
            "Sáng", "Trưa","Tối"
        };
        DefaultComboBoxModel<String> cbxModel = new DefaultComboBoxModel<>(CLV);

        cbbCLV.setModel(cbxModel);
    }
    private void initGioiTinh(){
        String[] gioiTinh = new String[]{
            "Nam","Nữ"
        };
        DefaultComboBoxModel<String> cbxModel = new DefaultComboBoxModel<>(gioiTinh);
        
        cbbGT.setModel(cbxModel);
        cbbGT1.setModel(cbxModel);
    }
    private void initLoaiThuoc(){
         conn = cn.getConnection();
        String sql = "SELECT TenLT FROM LoaiThuoc";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while(rs.next()){
                String name = rs.getString("TenLT");
                comboBoxModel.addElement(name);
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbLT.setModel(comboBoxModel);
            
        }catch(Exception ex){
            
        }
    }
    private void initNCC(){
        conn = cn.getConnection();
        String sql = "SELECT TenNCC FROM NhaCungCap";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
            while(rs.next()){
                String name = rs.getString("TenNCC");
                comboBoxModel.addElement(name);
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbNCC.setModel(comboBoxModel);
            
        }catch(Exception ex){
            
        }
    }
    private void initTrangThai() {
        String[] CLV = new String[]{
            "Còn hàng", "Hết hàng"
        };
        DefaultComboBoxModel<String> cbxModel = new DefaultComboBoxModel<>(CLV);

        cbbtrangthai.setModel(cbxModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        left = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        khachHang = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hangHoa = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        doanhThu = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        nhanVien = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        hoaDon = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        main = new javax.swing.JTabbedPane();
        mains = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        maindoanhThu = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        mainnhanVien = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtsdt = new javax.swing.JTextField();
        txttenNV = new javax.swing.JTextField();
        txtmaNV = new javax.swing.JTextField();
        jlabel = new javax.swing.JLabel();
        jlabel1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        cbbCV = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbbCLV = new javax.swing.JComboBox<>();
        jDate = new com.toedter.calendar.JDateChooser();
        FileButton = new javax.swing.JButton();
        FileLabel = new javax.swing.JLabel();
        cbbGT = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        txtTaiKhoan = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablenhanVien = new javax.swing.JTable();
        txtTimKiemNV = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        mainkhachHang = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnThemKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnSuaKH = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtmaKH = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtDCKH = new javax.swing.JTextField();
        txtSDTKH = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txttenKH = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jlabel2 = new javax.swing.JLabel();
        cbbGT1 = new javax.swing.JComboBox<>();
        jlabel3 = new javax.swing.JLabel();
        jDate1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableKH = new javax.swing.JTable();
        txtTimKiemKH = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        mainhanghoa = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        nhacungcap = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        txtmaNCC = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtdcNCC = new javax.swing.JTextField();
        txtsdtNCC = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txttenNCC = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtgmailNCC = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableNCC = new javax.swing.JTable();
        txtTimNCC = new javax.swing.JTextField();
        btnTimNCC = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtloaithuoc = new javax.swing.JTextField();
        txttenloai = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        btnThemLT = new javax.swing.JButton();
        btnXoaLT = new javax.swing.JButton();
        btnSuaLT = new javax.swing.JButton();
        txtTimLT = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableLT = new javax.swing.JTable();
        btnTimLT = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtmathuoc = new javax.swing.JTextField();
        txttenthuoc = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtgiaban = new javax.swing.JTextField();
        cbbLT = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        txtsl = new javax.swing.JSpinner();
        jLabel59 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txtgianhap = new javax.swing.JTextField();
        txtTimThuoc = new javax.swing.JTextField();
        btnXoaThuoc = new javax.swing.JButton();
        btnSuaThuoc = new javax.swing.JButton();
        jLabel74 = new javax.swing.JLabel();
        btnThemThuoc = new javax.swing.JButton();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        txtdvt = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        cbbtrangthai = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        cbbNCC = new javax.swing.JComboBox<>();
        jdateNSX = new com.toedter.calendar.JDateChooser();
        jdateHSD = new com.toedter.calendar.JDateChooser();
        ImageLabel = new javax.swing.JLabel();
        btnHTA = new javax.swing.JButton();
        btnTimThuoc = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableThuoc = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        mainhoadon = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSPThuoc = new javax.swing.JTable();
        btnThemSPHD = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtgb = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtTT = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtDVT = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtMT = new javax.swing.JTextField();
        tinhTT = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        txtSP = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        txtmaHD = new javax.swing.JTextField();
        txttongTien = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        cbbkhachhang = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        cbbnhanvien = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jdateHD = new com.toedter.calendar.JDateChooser();
        btnXacNhan = new javax.swing.JButton();
        tinhTong = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableDSHD = new javax.swing.JTable();
        jLabel80 = new javax.swing.JLabel();
        top = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        left.setBackground(new java.awt.Color(153, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/58995861.png"))); // NOI18N

        khachHang.setBackground(java.awt.Color.lightGray);
        khachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        khachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                khachHangMouseClicked(evt);
            }
        });
        khachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/rating.png"))); // NOI18N
        khachHang.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Thông Tin Nhân Viên");
        khachHang.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 180, 40));

        hangHoa.setBackground(java.awt.Color.lightGray);
        hangHoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hangHoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hangHoaMouseClicked(evt);
            }
        });
        hangHoa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/drug.png"))); // NOI18N
        hangHoa.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Hàng Hóa");
        hangHoa.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 70, 40));

        doanhThu.setBackground(java.awt.Color.lightGray);
        doanhThu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        doanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doanhThuMouseClicked(evt);
            }
        });
        doanhThu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/dashboard.png"))); // NOI18N
        doanhThu.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Thống kê doanh thu");
        doanhThu.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 160, 40));

        nhanVien.setBackground(java.awt.Color.lightGray);
        nhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nhanVien.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        nhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienMouseClicked(evt);
            }
        });
        nhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/employee.png"))); // NOI18N
        nhanVien.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Thông Tin Khách Hàng");
        nhanVien.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 150, 40));

        hoaDon.setBackground(java.awt.Color.lightGray);
        hoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoaDonMouseClicked(evt);
            }
        });
        hoaDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/bill.png"))); // NOI18N
        hoaDon.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 40));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setText("Hóa Đơn");
        hoaDon.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 70, 40));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftLayout.createSequentialGroup()
                .addComponent(doanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(leftLayout.createSequentialGroup()
                .addGroup(leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftLayout.createSequentialGroup()
                        .addGroup(leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(leftLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, leftLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel2))
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, leftLayout.createSequentialGroup()
                                .addComponent(nhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftLayout.createSequentialGroup()
                        .addGroup(leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(khachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftLayout.createSequentialGroup()
                .addGroup(leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftLayout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(leftLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(nhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(hoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(khachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(hangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(doanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        jPanel3.add(left, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 770));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1234, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 649, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainsLayout = new javax.swing.GroupLayout(mains);
        mains.setLayout(mainsLayout);
        mainsLayout.setHorizontalGroup(
            mainsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainsLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 116, Short.MAX_VALUE))
        );
        mainsLayout.setVerticalGroup(
            mainsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        main.addTab("Trang Chủ", mains);

        jLabel17.setText("Doanh thu");

        javax.swing.GroupLayout maindoanhThuLayout = new javax.swing.GroupLayout(maindoanhThu);
        maindoanhThu.setLayout(maindoanhThuLayout);
        maindoanhThuLayout.setHorizontalGroup(
            maindoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, maindoanhThuLayout.createSequentialGroup()
                .addContainerGap(719, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(575, 575, 575))
        );
        maindoanhThuLayout.setVerticalGroup(
            maindoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(maindoanhThuLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel17)
                .addContainerGap(520, Short.MAX_VALUE))
        );

        main.addTab("Doanh Thu", maindoanhThu);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setText("Thông tin nhân viên");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Mã nhân viên");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Tên nhân viên");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Số điện thoại");

        jlabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlabel.setText("Giới tính");

        jlabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlabel1.setText("Ngày sinh");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Hình ảnh");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Chức vụ");

        cbbCV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCVActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Ca làm việc");

        cbbCLV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbCLV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCLVActionPerformed(evt);
            }
        });

        FileButton.setText("Hiển Thị Ảnh");
        FileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileButtonActionPerformed(evt);
            }
        });

        cbbGT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Tài khoản");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Mật khẩu");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(jlabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jDate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtmaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel19)
                                                    .addComponent(jLabel18))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txttenNV)
                                                    .addComponent(txtsdt, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel28)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbCLV, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbCV, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel24))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                    .addComponent(txtMatKhau))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbGT, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(49, 49, 49)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(FileButton)
                                        .addGap(19, 19, 19)))))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtmaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabel)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txttenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlabel1))
                    .addComponent(jDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbCLV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FileButton)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        btnThem.setBackground(new java.awt.Color(51, 255, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/plus.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setIconTextGap(20);
        btnThem.setSelected(true);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(51, 255, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/remove (1).png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setIconTextGap(20);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(51, 255, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/updated.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setIconTextGap(20);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnXoa)
                        .addGap(61, 61, 61)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(164, 164, 164))
        );

        tablenhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablenhanVien);

        txtTimKiemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemNVActionPerformed(evt);
            }
        });
        txtTimKiemNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemNVKeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/search (1).png"))); // NOI18N
        jButton1.setText("Tìm");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemNV(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainnhanVienLayout = new javax.swing.GroupLayout(mainnhanVien);
        mainnhanVien.setLayout(mainnhanVienLayout);
        mainnhanVienLayout.setHorizontalGroup(
            mainnhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainnhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainnhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainnhanVienLayout.createSequentialGroup()
                        .addComponent(txtTimKiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        mainnhanVienLayout.setVerticalGroup(
            mainnhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainnhanVienLayout.createSequentialGroup()
                .addGroup(mainnhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainnhanVienLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(mainnhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        main.addTab("Nhân Viên", mainnhanVien);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnThemKH.setBackground(new java.awt.Color(51, 255, 255));
        btnThemKH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/plus.png"))); // NOI18N
        btnThemKH.setText("Thêm");
        btnThemKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnThemKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemKH.setIconTextGap(20);
        btnThemKH.setSelected(true);
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(51, 255, 255));
        btnXoaKH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/remove (1).png"))); // NOI18N
        btnXoaKH.setText("Xóa");
        btnXoaKH.setIconTextGap(20);
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnSuaKH.setBackground(new java.awt.Color(51, 255, 255));
        btnSuaKH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSuaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/updated.png"))); // NOI18N
        btnSuaKH.setText("Sửa");
        btnSuaKH.setIconTextGap(20);
        btnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKHActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Mã khách hàng");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Địa chỉ");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Số điện thoại");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Tên khách hàng");

        jlabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlabel2.setText("Giới tính");

        cbbGT1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jlabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlabel3.setText("Ngày sinh");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel26)
                        .addComponent(jLabel23))
                    .addComponent(jlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txttenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(txtmaKH)
                    .addComponent(cbbGT1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDCKH, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jlabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(txtDCKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlabel2)
                        .addComponent(cbbGT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlabel3))
                    .addComponent(jDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Thông tin khách hàng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(119, 119, 119)
                                .addComponent(btnXoaKH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(205, 205, 205))
        );

        tableKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableKH);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/search (1).png"))); // NOI18N
        jButton2.setText("Tìm");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemKH(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("", jPanel7);

        javax.swing.GroupLayout mainkhachHangLayout = new javax.swing.GroupLayout(mainkhachHang);
        mainkhachHang.setLayout(mainkhachHangLayout);
        mainkhachHangLayout.setHorizontalGroup(
            mainkhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        mainkhachHangLayout.setVerticalGroup(
            mainkhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        main.addTab("Khách Hàng", mainkhachHang);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Thông tin nhà cung cấp");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Mã nhà cung cấp");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("Địa chỉ");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Số điện thoại");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setText("Tên nhà cung cấp");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel46.setText("Gmail");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel42)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel46)))
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmaNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txttenNCC))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtsdtNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtdcNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(txtgmailNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(txtdcNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsdtNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgmailNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        jButton10.setBackground(new java.awt.Color(51, 255, 255));
        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/plus.png"))); // NOI18N
        jButton10.setText("Thêm");
        jButton10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setIconTextGap(20);
        jButton10.setSelected(true);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(51, 255, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/remove (1).png"))); // NOI18N
        jButton11.setText("Xóa");
        jButton11.setIconTextGap(20);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(51, 255, 255));
        jButton12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/updated.png"))); // NOI18N
        jButton12.setText("Sửa");
        jButton12.setIconTextGap(20);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113)
                                .addComponent(jButton11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableNCC);

        btnTimNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/search (1).png"))); // NOI18N
        btnTimNCC.setText("Tìm");
        btnTimNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimNCCbtnTimKiemKH(evt);
            }
        });
        btnTimNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimNCCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout nhacungcapLayout = new javax.swing.GroupLayout(nhacungcap);
        nhacungcap.setLayout(nhacungcapLayout);
        nhacungcapLayout.setHorizontalGroup(
            nhacungcapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhacungcapLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(nhacungcapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nhacungcapLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(nhacungcapLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(txtTimNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimNCC)))
                .addGap(2438, 2438, 2438))
        );
        nhacungcapLayout.setVerticalGroup(
            nhacungcapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nhacungcapLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(nhacungcapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimNCC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE))
            .addGroup(nhacungcapLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Nhà Cung Cấp", nhacungcap);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel44.setText("Thông tin loại hàng");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel45.setText("Mã loại");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel48.setText("Tên loại thuốc");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel48))
                .addGap(16, 16, 16)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtloaithuoc, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(txttenloai))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtloaithuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttenloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        btnThemLT.setBackground(new java.awt.Color(51, 255, 255));
        btnThemLT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThemLT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/plus.png"))); // NOI18N
        btnThemLT.setText("Thêm");
        btnThemLT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnThemLT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemLT.setIconTextGap(20);
        btnThemLT.setSelected(true);
        btnThemLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLTActionPerformed(evt);
            }
        });

        btnXoaLT.setBackground(new java.awt.Color(51, 255, 255));
        btnXoaLT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaLT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/remove (1).png"))); // NOI18N
        btnXoaLT.setText("Xóa");
        btnXoaLT.setIconTextGap(20);
        btnXoaLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLTActionPerformed(evt);
            }
        });

        btnSuaLT.setBackground(new java.awt.Color(51, 255, 255));
        btnSuaLT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSuaLT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/updated.png"))); // NOI18N
        btnSuaLT.setText("Sửa");
        btnSuaLT.setIconTextGap(20);
        btnSuaLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(btnThemLT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                .addComponent(btnXoaLT)
                                .addGap(78, 78, 78)
                                .addComponent(btnSuaLT, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemLT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaLT, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaLT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(240, 240, 240))
        );

        tableLT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tableLT);

        btnTimLT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/search (1).png"))); // NOI18N
        btnTimLT.setText("Tìm");
        btnTimLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimLTbtnTimKiemKH(evt);
            }
        });
        btnTimLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimLTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimLT, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimLT)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimLT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimLT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 136, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Loại Thuốc", jPanel12);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel62.setText("Số lượng");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel57.setText("Mã thuốc");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("Loại thuốc");

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel63.setText("Giá nhập");

        cbbLT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setText("Hình ảnh");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("Tên thuốc");

        jLabel64.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel64.setText("Giá bán");

        btnXoaThuoc.setBackground(new java.awt.Color(51, 255, 255));
        btnXoaThuoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/remove (1).png"))); // NOI18N
        btnXoaThuoc.setText("Xóa");
        btnXoaThuoc.setIconTextGap(20);
        btnXoaThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaThuocActionPerformed(evt);
            }
        });

        btnSuaThuoc.setBackground(new java.awt.Color(51, 255, 255));
        btnSuaThuoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSuaThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/updated.png"))); // NOI18N
        btnSuaThuoc.setText("Sửa");
        btnSuaThuoc.setIconTextGap(20);
        btnSuaThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThuocActionPerformed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel74.setText("Ngày sản xuất");

        btnThemThuoc.setBackground(new java.awt.Color(51, 255, 255));
        btnThemThuoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThemThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/plus.png"))); // NOI18N
        btnThemThuoc.setText("Thêm");
        btnThemThuoc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnThemThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemThuoc.setIconTextGap(20);
        btnThemThuoc.setSelected(true);
        btnThemThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocActionPerformed(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel76.setText("Hạn sử dụng");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel77.setText("Đơn vị tính");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel78.setText("Trạng thái");

        cbbtrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel79.setText("Nhà cung cấp");

        cbbNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnHTA.setText("Hiển thị ảnh");
        btnHTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHTAActionPerformed(evt);
            }
        });

        btnTimThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/search (1).png"))); // NOI18N
        btnTimThuoc.setText("Tìm");
        btnTimThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimThuocbtnTimKiemNV(evt);
            }
        });
        btnTimThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimThuocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(9, 9, 9))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txttenthuoc)
                    .addComponent(txtmathuoc)
                    .addComponent(cbbLT, 0, 174, Short.MAX_VALUE)
                    .addComponent(cbbNCC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(80, 80, 80)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(jLabel63)
                    .addComponent(jLabel62)
                    .addComponent(jLabel77))
                .addGap(58, 58, 58)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(txtdvt, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsl, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtgiaban, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtgianhap, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbbtrangthai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdateNSX, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jdateHSD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)))
                .addComponent(jLabel61)
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHTA, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(ImageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btnThemThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnXoaThuoc)
                        .addGap(46, 46, 46)
                        .addComponent(btnSuaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(txtTimThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel76)
                        .addComponent(jLabel74)
                        .addComponent(jLabel78)))
                .addGap(18, 18, 18)
                .addComponent(btnTimThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 376, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtmathuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txttenthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(69, 69, 69))
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbLT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jdateNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtsl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtgianhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdateHSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtgiaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(69, 69, 69))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbtrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(63, 63, 63))))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(ImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnHTA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimThuoc))
                        .addGap(0, 342, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel66.setText("Thông tin nhập hàng");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel66)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tableThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tableThuoc);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1214, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Nhập Hàng", jPanel15);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1207, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainhanghoaLayout = new javax.swing.GroupLayout(mainhanghoa);
        mainhanghoa.setLayout(mainhanghoaLayout);
        mainhanghoaLayout.setHorizontalGroup(
            mainhanghoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainhanghoaLayout.createSequentialGroup()
                .addGroup(mainhanghoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 113, Short.MAX_VALUE))
        );
        mainhanghoaLayout.setVerticalGroup(
            mainhanghoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainhanghoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        main.addTab("Hàng Hóa", mainhanghoa);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel68.setText("Sản Phẩm Thuốc");

        tableSPThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableSPThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSPThuocMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableSPThuoc);

        btnThemSPHD.setBackground(new java.awt.Color(51, 255, 51));
        btnThemSPHD.setForeground(new java.awt.Color(51, 51, 51));
        btnThemSPHD.setText("Thêm sản phẩm");
        btnThemSPHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPHDActionPerformed(evt);
            }
        });

        jLabel30.setText("Giá bán");

        jLabel31.setText("Số lượng");

        txtSL.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtSLInputMethodTextChanged(evt);
            }
        });

        jLabel32.setText("Thành tiền");

        txtTT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTTPropertyChange(evt);
            }
        });

        jLabel33.setText("Đơn vị tính ");

        jLabel34.setText("Mã thuốc");

        tinhTT.setBackground(new java.awt.Color(255, 102, 102));
        tinhTT.setText("+");
        tinhTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tinhTTActionPerformed(evt);
            }
        });

        jLabel35.setText("Tên sản phẩm");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(txtDVT))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel34))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtgb)
                            .addComponent(txtMT, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTT, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(txtSL)
                    .addComponent(txtSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tinhTT, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtgb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txtDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addComponent(txtTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tinhTT))
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jLabel68))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(btnThemSPHD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThemSPHD)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel69.setText("Thông Tin Hóa Đơn");

        jLabel70.setText("Mã Hóa Đơn");

        jLabel71.setText("Tên Khách Hàng");

        jLabel73.setText("Ngày Lập");

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel75.setText("Tổng Tiền");

        jButton19.setBackground(new java.awt.Color(51, 255, 255));
        jButton19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 51, 51));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/plus.png"))); // NOI18N
        jButton19.setText("Xác Nhận");
        jButton19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton19.setIconTextGap(20);
        jButton19.setSelected(true);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        cbbkhachhang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel72.setText("Nhân Viên");

        cbbnhanvien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Tên sản phẩm", "Giá bán", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane6.setViewportView(tableHoaDon);

        btnXacNhan.setBackground(new java.awt.Color(51, 255, 51));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 51, 51));
        btnXacNhan.setText("Thanh Toán");
        btnXacNhan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhan.setIconTextGap(20);
        btnXacNhan.setSelected(true);
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        tinhTong.setBackground(new java.awt.Color(255, 102, 102));
        tinhTong.setText("+");
        tinhTong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tinhTongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jLabel69))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                    .addComponent(jLabel75)
                                    .addGap(18, 18, 18)
                                    .addComponent(txttongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(tinhTong)
                                    .addGap(10, 10, 10))
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel70)
                                            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jdateHD, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                            .addComponent(txtmaHD)))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(47, 47, 47)))
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGap(117, 117, 117)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addComponent(jLabel71)
                                                .addGap(18, 18, 18)
                                                .addComponent(cbbkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel23Layout.createSequentialGroup()
                                                .addComponent(jLabel72)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cbbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel69)
                .addGap(35, 35, 35)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70)
                            .addComponent(jLabel71)
                            .addComponent(txtmaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel73)
                            .addComponent(jLabel72)
                            .addComponent(cbbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jdateHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(txttongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tinhTong))
                .addGap(36, 36, 36)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout mainhoadonLayout = new javax.swing.GroupLayout(mainhoadon);
        mainhoadon.setLayout(mainhoadonLayout);
        mainhoadonLayout.setHorizontalGroup(
            mainhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainhoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        mainhoadonLayout.setVerticalGroup(
            mainhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainhoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        main.addTab("Hóa Đơn", mainhoadon);

        tableDSHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tableDSHD);

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel80.setText("Danh Sách Hóa Đơn");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(448, 448, 448)
                .addComponent(jLabel80)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        main.addTab("Danh Sách Hóa Đơn", jPanel9);

        jPanel3.add(main, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 1350, 680));

        top.setBackground(new java.awt.Color(204, 255, 255));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/logout.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/user.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Admin");

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 996, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(95, 95, 95))
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel3.add(top, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 1240, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1466, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void khachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khachHangMouseClicked
        main.setSelectedIndex(2);
        loadTableKH();
    }//GEN-LAST:event_khachHangMouseClicked

    private void hangHoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hangHoaMouseClicked
        main.setSelectedIndex(4);
    }//GEN-LAST:event_hangHoaMouseClicked

    private void doanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doanhThuMouseClicked
        main.setSelectedIndex(1);
    }//GEN-LAST:event_doanhThuMouseClicked

    private void nhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienMouseClicked
        main.setSelectedIndex(3);
        loadTableNV();
    }//GEN-LAST:event_nhanVienMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        JFrame frame = new JFrame("Exit");
        if (JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn thoát!", "Thông Báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void hoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonMouseClicked
        main.setSelectedIndex(5);
    }//GEN-LAST:event_hoaDonMouseClicked
    // Thêm thông tin nhân viên
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
     conn = cn.getConnection();
        
        try{
            if(txtmaNV.getText().equals("") || txttenNV.getText().equals("") || txtsdt.getText().equals("")
                    || txtTaiKhoan.getText().equals("") || txtMatKhau.getText().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_ND from NguoiDung where ID_ND = '" + txtmaNV.getText()+"'";
                StringBuffer sb = new StringBuffer();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql_check_pk);
                if(rs.next()){
                    sb.append("Nhân viên này đã tồn tại");
                }
                if(sb.length()>0){
                    JOptionPane.showMessageDialog(this,sb.toString());
                }else{
                    String sql = "INSERT INTO NGUOIDUNG (ID_ND,ID_LND,HoTen,SDT,GioiTinh,NgaySinh,HinhAnh,CaLamViec,TaiKhoan,MatKhau) VALUES(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtmaNV.getText()));
                    ps.setInt(2, cbbCV.getSelectedIndex());
                    ps.setString(3,txttenNV.getText());
                    ps.setString(4,txtsdt.getText());
                    ps.setString(5,(String) cbbGT.getSelectedItem());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jDate.getDate());
                    ps.setString(6,date);
                    ps.setString(7,hinhAnh);
                    ps.setString(8, (String) cbbCLV.getSelectedItem());
                    ps.setString(9, txtTaiKhoan.getText());
                    ps.setString(10, txtMatKhau.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tablenhanVien.getModel();
                    model.setRowCount(0);
                    xoatrangNV();
                    loadTableNV();
                    JOptionPane.showMessageDialog(null,"Thêm mới thông tin thành công");
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void cbbCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbCVActionPerformed

    private void xoatrangNV(){
        txtmaNV.setText("");
        txttenNV.setText("");
        txtsdt.setText("");
        cbbGT.setSelectedItem("");
        cbbCV.setSelectedItem("");
        cbbCLV.setSelectedItem("");
        jDate.setDate(null);
        FileLabel.setText("");
        txtTaiKhoan.setText("");
        txtMatKhau.setText("");
        loadTableNV();
    }
    private void xoatrangKH(){
        txtmaKH.setText("");
        txttenKH.setText("");
        txtDCKH.setText("");
        txtSDTKH.setText("");
        jDate1.setDate(null);
        loadTableKH();
    }
    
    private void xoatrangNCC(){
        txtmaNCC.setText("");
        txttenNCC.setText("");
        txtsdtNCC.setText("");
        txtgmailNCC.setText("");
        txtdcNCC.setText("");
        loadTableNCC();
    }
    
    private void xoatrangLT(){
        txtloaithuoc.setText("");
        txttenloai.setText("");
        loadTableNCC();
    }
    
    private void xoatrangThuoc(){
        txtmathuoc.setText("");
        txttenthuoc.setText("");
        txtgianhap.setText("");
        txtgiaban.setText("");
        txtdvt.setText("");
        loadTableThuoc();
    }
    
    private void xoatrangHD(){
        txtmaHD.setText("");
        txttongTien.setText("");
        jdateHD.setDate(null);
    }
    
    private void xoatrangSPThuoc(){
        txtMT.setText("");
        txtSP.setText("");
        txtSL.setText("");
        txtgb.setText("");
        txtDVT.setText("");
        txtTT.setText("");
    }
    
    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        conn = cn.getConnection();
        
        try{
            if(txtmaKH.getText().equals("") || txttenKH.getText().equals("") || txtDCKH.getText().equals("") || txtSDTKH.getText().equals("") ||
                    jDate1.getDate().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_KH from KhachHang where ID_KH = '" + txtmaKH.getText()+"'";
                StringBuffer sb = new StringBuffer();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql_check_pk);
                if(rs.next()){
                    sb.append("Khách hàng này đã tồn tại");
                }
                if(sb.length()>0){
                    JOptionPane.showMessageDialog(this,sb.toString());
                }else{
                    String sql = "INSERT INTO KhachHang (ID_KH,HoVaTen,NgaySinh,GioiTinh,SDT,DiaChi) VALUES(?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtmaKH.getText()));
                    ps.setString(4,(String) cbbGT1.getSelectedItem());
                    ps.setString(2,txttenKH.getText());
                    ps.setString(5,txtSDTKH.getText());
                    ps.setString(6,txtDCKH.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jDate1.getDate());
                    ps.setString(3,date);
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableKH.getModel();
                    model.setRowCount(0);
                    xoatrangKH();
                    JOptionPane.showMessageDialog(null,"Thêm mới thông tin thành công");
                    loadTableKH();
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void txtTimKiemNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemNVKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiemNVKeyReleased

    private void btnTimKiemNV(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemNV
        String ten = txtTimKiemNV.getText();
        ArrayList<NhanVien> listFindNV = new ArrayList<>();
        for (int i = 0; i < dsnv.size(); i++) {
            NhanVien nv = dsnv.get(i);
            if (nv.getTenNV().equalsIgnoreCase(ten)) {
                listFindNV.add(nv);
            }
        }
        if (listFindNV.size() > 0) {
            JOptionPane.showMessageDialog(this, "OK");
            DefaultTableModel tbModel1 = (DefaultTableModel) tablenhanVien.getModel();
            tbModel1.setRowCount(0);
            for (NhanVien nv : listFindNV) {
                Object[] rowData = new Object[7];
                rowData[0] = nv.getMaNV();
                rowData[1] = nv.getTenNV();
                rowData[2] = nv.getSoDienThoai();
                rowData[3] = nv.getDiaChi();
                rowData[4] = nv.getTenLoai();
                rowData[5] = nv.getLuong();
                rowData[6] = nv.getHinhAnh();

                tbModel1.addRow(rowData);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên");
        }
        
    }//GEN-LAST:event_btnTimKiemNV

    private void btnTimKiemKH(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemKH
       

    }//GEN-LAST:event_btnTimKiemKH

    private void cbbCLVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCLVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbCLVActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        conn = cn.getConnection();
        try{
            String sql = "Delete NguoiDung where ID_ND ='"+txtmaNV.getText()+"'";
            Statement st = conn.createStatement();
            int chk = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa thông tin nhân viên này!",
                    "Thông báo!",JOptionPane.YES_NO_OPTION);
            if(chk == JOptionPane.YES_NO_OPTION){
                st.executeUpdate(sql);
                DefaultTableModel model = (DefaultTableModel)tableKH.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(this,"Xóa thành công");
                xoatrangNV();
                loadTableNV();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    // Xử lý hình ảnh
    private void FileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        hinhAnh = f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(hinhAnh).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        FileLabel.setIcon(imageIcon);
        
        try{
            File image = new File(hinhAnh);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum;(readNum=fis.read(buf))!=-1;){
                bos.write(buf,0,readNum);
            }
            dan_hinhAnh = bos.toByteArray();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        
    }//GEN-LAST:event_FileButtonActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
                conn = cn.getConnection();
        try{
            String sql = "Delete KhachHang where ID_KH ='"+txtmaKH.getText()+"'";
            Statement st = conn.createStatement();
            int chk = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa thông tin khách hàng này!",
                    "Thông báo!",JOptionPane.YES_NO_OPTION);
            if(chk == JOptionPane.YES_NO_OPTION){
                st.executeUpdate(sql);
                DefaultTableModel model = (DefaultTableModel)tableKH.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(this,"Xóa thành công");
                xoatrangKH();
                loadTableKH();
            }
        }catch(Exception ex){
             JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
        // TODO add your handling code here:
        conn = cn.getConnection();
        try{
            int id = Integer.parseInt(tableKH.getValueAt(tableKH.getSelectedRow(),0).toString());
            String sql = "UPDATE KhachHang SET HoVaTen = ?,NgaySinh = ?, GioiTinh = ?,SDT = ?,DiaChi = ? where ID_KH=" +id;
            PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(3,(String) cbbGT1.getSelectedItem());
                    ps.setString(1,txttenKH.getText());
                    ps.setString(4,txtSDTKH.getText());
                    ps.setString(5,txtDCKH.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jDate1.getDate());
                    ps.setString(2,date);
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableKH.getModel();
                    model.setRowCount(0);
                    xoatrangKH();
                    JOptionPane.showMessageDialog(null,"Sửa thông tin khách hàng thành công");
                    loadTableKH();
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnSuaKHActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        conn = cn.getConnection();
        try{
            String sql ="Select * from KhachHang where ID_KH=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,txtTimKiemKH.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                String setID = rs.getString("ID_KH");
                txtmaKH.setText(setID);
                
                String setTen = rs.getString("HoVaTen");
                txttenKH.setText(setTen);
                
                Date setNgaySinh = rs.getDate("NgaySinh");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                jDate1.setDate(setNgaySinh);
                
                String setGioiTinh = rs.getString("GioiTinh");
                cbbGT1.setSelectedItem(setGioiTinh);
                
                String setSDT = rs.getString("SDT");
                txtSDTKH.setText(setSDT);
                
                String setDiaChi = rs.getString("DiaChi");
                txtDCKH.setText(setDiaChi);
                
                 JOptionPane.showMessageDialog(null,"Tìm thấy thông tin khách hàng cần tìm");
            }
            else{
                xoatrangKH();
                JOptionPane.showMessageDialog(null,"Không tìm thấy thông tin khách hàng cần tìm");
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Sửa thông tiên nhan viên
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        conn = cn.getConnection();
        try{
            int id = Integer.parseInt(tablenhanVien.getValueAt(tablenhanVien.getSelectedRow(),0).toString());
            String sql = "UPDATE NguoiDung SET ID_LND = ?,HoTen = ?, SDT = ?,GioiTinh = ?,NgaySinh = ?,CaLamViec = ?,HinhAnh = ?,TaiKhoan = ?,MatKhau = ? where ID_ND=" +id;
            PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(4,(String) cbbGT.getSelectedItem());
                    ps.setString(2,txttenNV.getText());
                    ps.setInt(1, cbbCV.getSelectedIndex());
                    ps.setString(3,txtsdt.getText());
                    ps.setString(5,txtDCKH.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jDate.getDate());
                    ps.setString(5,date);
                    ps.setString(6,(String)cbbCLV.getSelectedItem());
                    ps.setString(7,hinhAnh);
                    ps.setString(8,txtTaiKhoan.getText());
                    ps.setString(9,txtMatKhau.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tablenhanVien.getModel();
                    model.setRowCount(0);
                    xoatrangNV();
                    JOptionPane.showMessageDialog(null,"Sửa thông tin nhân viên thành công");
                    loadTableNV();
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    // Tìm kiếm thông tin nhân viên
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        conn = cn.getConnection();
        try{
            String sql ="Select * from NguoiDung where ID_ND=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,txtTimKiemNV.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                String setID = rs.getString("ID_ND");
                txtmaNV.setText(setID);
                
                String setLND = rs.getString("ID_LND");
                cbbCV.setSelectedItem(setLND);
                
                String setHoTen = rs.getString("HoTen");
                txttenNV.setText(setHoTen);
                
                String setSDT = rs.getString("SDT");
                txtsdt.setText(setSDT);
                
                String setGioiTinh = rs.getString("GioiTinh");
                cbbGT.setSelectedItem(setGioiTinh);
                
                Date setNgaySinh = rs.getDate("NgaySinh");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                jDate.setDate(setNgaySinh);       
                
                String imagePath = rs.getString("HinhAnh");
                // Đọc hình ảnh từ file
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                // sửa kích thước ảnh
                int imageWidth = 100;
                int imageHeight = 100;
                Image resizedImage = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_SMOOTH);
                // Tạo ImageIcon mới từ hình ảnh đã được sửa kích thước
                ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
                // Hiển thị hình ảnh trên JLabel
                JLabel imageLabel = new JLabel();
                FileLabel.setIcon(resizedImageIcon);
                String setCLV = rs.getString("CaLamViec");
                cbbCLV.setSelectedItem(setCLV);
                
                String setTaiKhoan = rs.getString("TaiKhoan");
                txtTaiKhoan.setText(setTaiKhoan);
                
                String setMatKhau = rs.getString("MatKhau");
                txtMatKhau.setText(setMatKhau);
                
                 JOptionPane.showMessageDialog(null,"Tìm thấy thông tin nhân viên cần tìm");
            }
            else{
                xoatrangKH();
                JOptionPane.showMessageDialog(null,"Không tìm thấy thông tin nhân viên cần tìm");
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTimKiemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemNVActionPerformed

    // Thêm thông tin nhà cung cấp
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        conn = cn.getConnection();
        
        try{
            if(txtmaNCC.getText().equals("") || txttenNCC.getText().equals("") || txtsdtNCC.getText().equals("") || txtgmailNCC.getText().equals("") ||
                    txtdcNCC.getText().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_NCC from NhaCungCap where ID_NCC = '" + txtmaNCC.getText()+"'";
                StringBuffer sb = new StringBuffer();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql_check_pk);
                if(rs.next()){
                    sb.append("Nhà cung cấp này đã tồn tại");
                }
                if(sb.length()>0){
                    JOptionPane.showMessageDialog(this,sb.toString());
                }else{
                    String sql = "INSERT INTO NhaCungCap (ID_NCC,TenNCC,SDT,Gmail,DiaChi) VALUES(?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtmaNCC.getText()));
                    ps.setString(2,txttenNCC.getText());
                    ps.setString(3,txtsdtNCC.getText());
                    ps.setString(4,txtgmailNCC.getText());
                    ps.setString(5,txtdcNCC.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableNCC.getModel();
                    model.setRowCount(0);
                    xoatrangNCC();
                    JOptionPane.showMessageDialog(null,"Thêm mới thông tin thành công");
                    loadTableNCC();
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_jButton10ActionPerformed
    
    // Xóa thông tin nhà cung cấp
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
               conn = cn.getConnection();
        try{
            String sql = "Delete NhaCungCap where ID_NCC ='"+txtmaNCC.getText()+"'";
            Statement st = conn.createStatement();
            int chk = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa thông tin nhà cung cấp này!",
                    "Thông báo!",JOptionPane.YES_NO_OPTION);
            if(chk == JOptionPane.YES_NO_OPTION){
                st.executeUpdate(sql);
                DefaultTableModel model = (DefaultTableModel)tableNCC.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(this,"Xóa thành công");
                xoatrangNCC();
                loadTableNCC();
            }
        }catch(Exception ex){
             JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_jButton11ActionPerformed
    // Sửa thông tin nhà cung cấp
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        conn = cn.getConnection();
        try{
            int id = Integer.parseInt(tableNCC.getValueAt(tableNCC.getSelectedRow(),0).toString());
            String sql = "UPDATE NhaCungCap SET TenNCC = ?,SDT = ?, Gmail = ?,DiaChi = ? where ID_NCC=" +id;
            PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,txttenNCC.getText());
                    ps.setString(2,txtsdtNCC.getText());
                    ps.setString(3,txtgmailNCC.getText());
                    ps.setString(4,txtdcNCC.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableNCC.getModel();
                    model.setRowCount(0);
                    xoatrangNCC();
                    JOptionPane.showMessageDialog(null,"Sửa thông tin nhà cung cấp thành công");
                    loadTableNCC();
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnTimNCCbtnTimKiemKH(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimNCCbtnTimKiemKH
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimNCCbtnTimKiemKH

    private void btnTimNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimNCCActionPerformed
        conn = cn.getConnection();
        try{
            String sql ="Select * from NhaCungCap where ID_NCC=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,txtTimNCC.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                String setID = rs.getString("ID_NCC");
                txtmaNCC.setText(setID);
                
                String setTen = rs.getString("TenNCC");
                txttenNCC.setText(setTen);
                
                String setSDT = rs.getString("SDT");
                txtsdtNCC.setText(setSDT);
                
                String setGmail = rs.getString("Gmail");
                txtgmailNCC.setText(setGmail);
                
                String setDiaChi = rs.getString("DiaChi");
                txtDCKH.setText(setDiaChi);
                
                 JOptionPane.showMessageDialog(null,"Tìm thấy thông tin nhà cung cấp cần tìm");
            }
            else{
                xoatrangKH();
                JOptionPane.showMessageDialog(null,"Không tìm thấy thông tin nhà cung cấp cần tìm");
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnTimNCCActionPerformed
    // Thêm thông tin loại thuốc
    private void btnThemLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLTActionPerformed
        conn = cn.getConnection();
        
        try{
            if(txtloaithuoc.getText().equals("") || txttenloai.getText().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_LT from LoaiThuoc where ID_LT = '" + txtloaithuoc.getText()+"'";
                StringBuffer sb = new StringBuffer();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql_check_pk);
                if(rs.next()){
                    sb.append("Loại thuốc này đã tồn tại");
                }
                if(sb.length()>0){
                    JOptionPane.showMessageDialog(this,sb.toString());
                }else{
                    String sql = "INSERT INTO LoaiThuoc (ID_LT,TenLT) VALUES(?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtloaithuoc.getText()));
                    ps.setString(2,txttenloai.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableLT.getModel();
                    model.setRowCount(0);
                    xoatrangLT();
                    JOptionPane.showMessageDialog(null,"Thêm mới thông tin thành công");
                    loadTableLT();
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnThemLTActionPerformed

    private void btnXoaLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLTActionPerformed
                       conn = cn.getConnection();
        try{
            String sql = "Delete LoaiThuoc where ID_LT ='"+txtloaithuoc.getText()+"'";
            Statement st = conn.createStatement();
            int chk = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa thông tin loại thuốc này!",
                    "Thông báo!",JOptionPane.YES_NO_OPTION);
            if(chk == JOptionPane.YES_NO_OPTION){
                st.executeUpdate(sql);
                DefaultTableModel model = (DefaultTableModel)tableNCC.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(this,"Xóa thành công");
                xoatrangLT();
                loadTableLT();
            }
        }catch(Exception ex){
             JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnXoaLTActionPerformed

    private void btnSuaLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLTActionPerformed
        conn = cn.getConnection();
        try{
            int id = Integer.parseInt(tableLT.getValueAt(tableLT.getSelectedRow(),0).toString());
            String sql = "UPDATE LoaiThuoc SET TenLT = ? where ID_LT=" +id;
            PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1,txttenloai.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableLT.getModel();
                    model.setRowCount(0);
                    xoatrangLT();
                    JOptionPane.showMessageDialog(null,"Sửa thông tin loại thuốc thành công");
                    loadTableLT();
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnSuaLTActionPerformed

    private void btnTimLTbtnTimKiemKH(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimLTbtnTimKiemKH
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimLTbtnTimKiemKH

    private void btnTimLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimLTActionPerformed
                conn = cn.getConnection();
        try{
            String sql ="Select * from LoaiThuoc where ID_LT=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,txtTimLT.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                String setID = rs.getString("ID_LT");
                txtloaithuoc.setText(setID);
                
                String setTen = rs.getString("TenLT");
                txttenloai.setText(setTen);
                
                 JOptionPane.showMessageDialog(null,"Tìm thấy thông tin loại thuốc cần tìm");
            }
            else{
                xoatrangLT();
                JOptionPane.showMessageDialog(null,"Không tìm thấy thông tin loại thuốc cần tìm");
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnTimLTActionPerformed

    private void btnTimThuocbtnTimKiemNV(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimThuocbtnTimKiemNV
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimThuocbtnTimKiemNV

    private void btnTimThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimThuocActionPerformed
        // TODO add your handling code here:
        conn = cn.getConnection();
        try{
            String sql ="Select * from Thuoc where ID_Thuoc=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,txtTimThuoc.getText());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                
                String setID = rs.getString("ID_Thuoc");
                txttenthuoc.setText(setID);
                
                String setLT = rs.getString("ID_LT");
                cbbLT.setSelectedItem(setLT);
                
                String setNCC = rs.getString("ID_NCC");
                cbbNCC.setSelectedItem(setNCC);
                
                String setTenThuoc = rs.getString("TenThuoc");
                txttenthuoc.setText(setTenThuoc);
                
                Date setNSX = rs.getDate("NgaySanXuat");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                jdateNSX.setDate(setNSX);  
                
                Date setHSD = rs.getDate("HanSuDung");
                jdateHSD.setDate(setHSD);
                
                String setdvt = rs.getString("DonViTinh");
                txtdvt.setText(setdvt);
                
                String setGN = rs.getString("GiaNhap");
                txtgianhap.setText(setGN);
    
                String setdongia = rs.getString("DonGia");
                txtgiaban.setText(setdongia);
                
                String setSL = rs.getString("SoLuongNhap");
                int value = Integer.parseInt(setSL);
                txtsl.setValue(value);
                
                String imagePath = rs.getString("HinhAnh");
                // Đọc hình ảnh từ file
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                // sửa kích thước ảnh
                int imageWidth = 100;
                int imageHeight = 100;
                Image resizedImage = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_SMOOTH);
                // Tạo ImageIcon mới từ hình ảnh đã được sửa kích thước
                ImageIcon resizedImageIcon = new ImageIcon(resizedImage);
                // Hiển thị hình ảnh trên JLabel
                JLabel imageLabel = new JLabel();
                ImageLabel.setIcon(resizedImageIcon);
                
                String setTT = rs.getString("TrangThai");
                cbbtrangthai.setSelectedItem(setTT);
                
                 JOptionPane.showMessageDialog(null,"Tìm thấy thông tin thuốc cần tìm");
            }
            else{
                xoatrangThuoc();
                JOptionPane.showMessageDialog(null,"Không tìm thấy thông tin thuốc cần tìm");
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnTimThuocActionPerformed

    private void btnThemThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuocActionPerformed
       conn = cn.getConnection();
        
        try{
            if(txtmathuoc.getText().equals("") || txttenthuoc.getText().equals("") || txtgianhap.getText().equals("")
                    || txtgiaban.getText().equals("") || txtdvt.getText().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_Thuoc from Thuoc where ID_Thuoc = '" + txtmathuoc.getText()+"'";
                StringBuffer sb = new StringBuffer();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql_check_pk);
                if(rs.next()){
                    sb.append("Sản phẩm thuốc này đã tồn tại");
                }
                if(sb.length()>0){
                    JOptionPane.showMessageDialog(this,sb.toString());
                }else{
                    String sql = "INSERT INTO Thuoc (ID_Thuoc,ID_LT,ID_NCC,TenThuoc,NgaySanXuat,HanSuDung,DonViTinh,GiaNhap,DonGia,SoLuongNhap,HinhAnh,TrangThai) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtmathuoc.getText()));
                    ps.setInt(2, cbbLT.getSelectedIndex());
                    ps.setInt(3,cbbNCC.getSelectedIndex());
                    ps.setString(4,txttenthuoc.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jdateNSX.getDate());
                    ps.setString(5,date);
                    String date1 = sdf.format(jdateHSD.getDate());
                    ps.setString(6,date1);
                    ps.setString(7, txtdvt.getText());
                    ps.setString(8,txtgianhap.getText());
                    ps.setString(9, txtgiaban.getText());
                    ps.setInt(10,((int)txtsl.getValue()));
                    ps.setString(11, hinhAnhSP);
                    ps.setString(12, (String) cbbtrangthai.getSelectedItem());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tablenhanVien.getModel();
                    model.setRowCount(0);
                    xoatrangThuoc();
                    loadTableThuoc();
                    JOptionPane.showMessageDialog(null,"Thêm mới thông tin thành công");
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnThemThuocActionPerformed

    private void btnHTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHTAActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        hinhAnhSP = f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(hinhAnhSP).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        ImageLabel.setIcon(imageIcon);
        
        try{
            File image = new File(hinhAnhSP);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum;(readNum=fis.read(buf))!=-1;){
                bos.write(buf,0,readNum);
            }
            dan_hinhAnh = bos.toByteArray();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        
    }//GEN-LAST:event_btnHTAActionPerformed

    private void btnXoaThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaThuocActionPerformed
         conn = cn.getConnection();
        try{
            String sql = "Delete Thuoc where ID_Thuoc ='"+txtmathuoc.getText()+"'";
            Statement st = conn.createStatement();
            int chk = JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa thông tin sản phẩm thuốc này!",
                    "Thông báo!",JOptionPane.YES_NO_OPTION);
            if(chk == JOptionPane.YES_NO_OPTION){
                st.executeUpdate(sql);
                DefaultTableModel model = (DefaultTableModel)tableThuoc.getModel();
                model.setRowCount(0);
                JOptionPane.showMessageDialog(this,"Xóa thành công");
                xoatrangThuoc();
                loadTableThuoc();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnXoaThuocActionPerformed

    private void btnSuaThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThuocActionPerformed
        conn = cn.getConnection();
        try{
            int id = Integer.parseInt(tableThuoc.getValueAt(tableThuoc.getSelectedRow(),0).toString());
            String sql = "UPDATE Thuoc SET ID_LT = ?,ID_NCC = ?, TenThuoc = ?,NgaySanXuat = ?,HanSuDung = ?,DonViTinh = ?,GiaNhap = ?,DonGia = ?,SoLuongNhap = ?,HinhAnh = ?,TrangThai = ? where ID_Thuoc=" +id;
            PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, cbbLT.getSelectedIndex());
                    ps.setInt(2,cbbNCC.getSelectedIndex());
                    ps.setString(3, txttenthuoc.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jdateNSX.getDate());
                    ps.setString(4,date);
                    String date1 = sdf.format(jdateHSD.getDate());
                    ps.setString(5,date1);
                    ps.setString(6, txtdvt.getText());
                    ps.setString(7,txtgianhap.getText());
                    ps.setString(8, txtgiaban.getText());
                    ps.setInt(9,((int)txtsl.getValue()));
                    ps.setString(10, hinhAnhSP);
                    ps.setString(11, (String) cbbtrangthai.getSelectedItem());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tablenhanVien.getModel();
                    model.setRowCount(0);
                    xoatrangNV();
                    JOptionPane.showMessageDialog(null,"Sửa thông tin hàng hóa thành công");
                    loadTableNV();
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnSuaThuocActionPerformed

    
    // Thêm hóa đơn
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed

        conn = cn.getConnection();

        try{
            if(txtmaHD.getText().equals("") || jdateHD.getDate().equals("")
            ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_HD from HoaDon where ID_HD = '" + txtmaHD.getText()+"'";
                StringBuffer sb = new StringBuffer();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql_check_pk);
                if(rs.next()){
                    sb.append("Hóa đơn này đã tồn tại");
                }
                if(sb.length()>0){
                    JOptionPane.showMessageDialog(this,sb.toString());
                }else{
                    String sql = "INSERT INTO HoaDon (ID_HD,ID_ND,ID_KH,NgayLap,TongTien) VALUES(?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtmaHD.getText()));
                    ps.setInt(2,cbbnhanvien.getSelectedIndex());
                    ps.setInt(3,cbbkhachhang.getSelectedIndex());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jdateHD.getDate());
                    ps.setString(4,date);
                    ps.setFloat(5, Float.parseFloat(txttongTien.getText()));
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableHoaDon.getModel();
                    model.setRowCount(0);
                    xoatrangHD();
                    JOptionPane.showMessageDialog(null,"Thanh toán thành công");
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    // Thêm sản phẩm vào hóa đơn
    private void tableSPThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSPThuocMouseClicked

    }//GEN-LAST:event_tableSPThuocMouseClicked

    
    private void btnThemSPHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPHDActionPerformed
        conn = cn.getConnection();
        
        try{
            if(txtMT.getText().equals("")|| txtgb.getText().equals("") || txtSL.getText().equals("") ||
                    txtDVT.getText().equals("") || txtTT.getText().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{{
                    String sql = "INSERT INTO CT_HoaDon (ID_HD,ID_Thuoc,DonGia,SoLuong,DonViTinh,ThanhTien) VALUES(?,?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,Integer.parseInt(txtmaHD.getText()));
                    ps.setInt(2,Integer.parseInt(txtMT.getText()));
                    ps.setFloat(3,Float.parseFloat(txtgb.getText()));
                    ps.setFloat(4,Float.parseFloat(txtSL.getText()));
                    ps.setString(5,txtDVT.getText());
                    ps.setString(6,txtTT.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableSPThuoc.getModel();
                    model.setRowCount(0);
                    JOptionPane.showMessageDialog(null,"Thêm mới sản phẩm thành công");
                    DefaultTableModel models = (DefaultTableModel)tableHoaDon.getModel();
                    String data[] = {txtSP.getText(),txtgb.getText(),txtSL.getText(),txtTT.getText()};
                    models.addRow(data);
                    xoatrangSPThuoc();
                    loadTableSPThuoc();
                    
                }
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnThemSPHDActionPerformed

    
    private void txtSLInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSLInputMethodTextChanged

    }//GEN-LAST:event_txtSLInputMethodTextChanged


    
    private void tinhTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tinhTTActionPerformed
        float sl = Float.parseFloat(txtSL.getText());
        double tt ;
        float gb = Float.parseFloat(txtgb.getText());
        tt = sl*gb;
        txtTT.setText(String.valueOf(tt));
    }//GEN-LAST:event_tinhTTActionPerformed

    private void txtTTPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTTPropertyChange

    }//GEN-LAST:event_txtTTPropertyChange

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        conn = cn.getConnection();
        try{
            String sql = "UPDATE HoaDon SET ID_ND = ?,ID_KH = ?, NgayLap = ?,TongTien = ? where ID_HD=" +txtmaHD.getText();
            PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1,cbbkhachhang.getSelectedIndex());
                    ps.setInt(2,cbbnhanvien.getSelectedIndex());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jdateHD.getDate());
                    ps.setString(3,date);
                    ps.setString(4,txttongTien.getText());
                    ps.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel)tableHoaDon.getModel();
                    model.setRowCount(0);
                    xoatrangHD();
                    JOptionPane.showMessageDialog(null,"Thanh toán hóa đơn thành công");
                    loadTableDSHD();
            
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void tinhTongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tinhTongActionPerformed
        double tong = 0;
        for(int i=0;i<tableHoaDon.getRowCount();i++){
            tong += Double.parseDouble(tableHoaDon.getValueAt(i,3).toString());
            txttongTien.setText(String.valueOf(tong));
        }
    }//GEN-LAST:event_tinhTongActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formQuanLy.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formQuanLy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FileButton;
    private javax.swing.JLabel FileLabel;
    private javax.swing.JLabel ImageLabel;
    private javax.swing.JButton btnHTA;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnSuaLT;
    private javax.swing.JButton btnSuaThuoc;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemLT;
    private javax.swing.JButton btnThemSPHD;
    private javax.swing.JButton btnThemThuoc;
    private javax.swing.JButton btnTimLT;
    private javax.swing.JButton btnTimNCC;
    private javax.swing.JButton btnTimThuoc;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaLT;
    private javax.swing.JButton btnXoaThuoc;
    private javax.swing.JComboBox<String> cbbCLV;
    private javax.swing.JComboBox<String> cbbCV;
    private javax.swing.JComboBox<String> cbbGT;
    private javax.swing.JComboBox<String> cbbGT1;
    private javax.swing.JComboBox<String> cbbLT;
    private javax.swing.JComboBox<String> cbbNCC;
    private javax.swing.JComboBox<String> cbbkhachhang;
    private javax.swing.JComboBox<String> cbbnhanvien;
    private javax.swing.JComboBox<String> cbbtrangthai;
    private javax.swing.JPanel doanhThu;
    private javax.swing.JPanel hangHoa;
    private javax.swing.JPanel hoaDon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDate;
    private com.toedter.calendar.JDateChooser jDate1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private com.toedter.calendar.JDateChooser jdateHD;
    private com.toedter.calendar.JDateChooser jdateHSD;
    private com.toedter.calendar.JDateChooser jdateNSX;
    private javax.swing.JLabel jlabel;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JPanel khachHang;
    private javax.swing.JPanel left;
    private javax.swing.JTabbedPane main;
    private javax.swing.JPanel maindoanhThu;
    private javax.swing.JPanel mainhanghoa;
    private javax.swing.JPanel mainhoadon;
    private javax.swing.JPanel mainkhachHang;
    private javax.swing.JPanel mainnhanVien;
    private javax.swing.JPanel mains;
    private javax.swing.JPanel nhacungcap;
    private javax.swing.JPanel nhanVien;
    private javax.swing.JTable tableDSHD;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tableKH;
    private javax.swing.JTable tableLT;
    private javax.swing.JTable tableNCC;
    private javax.swing.JTable tableSPThuoc;
    private javax.swing.JTable tableThuoc;
    private javax.swing.JTable tablenhanVien;
    private javax.swing.JButton tinhTT;
    private javax.swing.JButton tinhTong;
    private javax.swing.JPanel top;
    private javax.swing.JTextField txtDCKH;
    private javax.swing.JTextField txtDVT;
    private javax.swing.JTextField txtMT;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSP;
    private javax.swing.JTextField txtTT;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemNV;
    private javax.swing.JTextField txtTimLT;
    private javax.swing.JTextField txtTimNCC;
    private javax.swing.JTextField txtTimThuoc;
    private javax.swing.JTextField txtdcNCC;
    private javax.swing.JTextField txtdvt;
    private javax.swing.JTextField txtgb;
    private javax.swing.JTextField txtgiaban;
    private javax.swing.JTextField txtgianhap;
    private javax.swing.JTextField txtgmailNCC;
    private javax.swing.JTextField txtloaithuoc;
    private javax.swing.JTextField txtmaHD;
    private javax.swing.JTextField txtmaKH;
    private javax.swing.JTextField txtmaNCC;
    private javax.swing.JTextField txtmaNV;
    private javax.swing.JTextField txtmathuoc;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txtsdtNCC;
    private javax.swing.JSpinner txtsl;
    private javax.swing.JTextField txttenKH;
    private javax.swing.JTextField txttenNCC;
    private javax.swing.JTextField txttenNV;
    private javax.swing.JTextField txttenloai;
    private javax.swing.JTextField txttenthuoc;
    private javax.swing.JTextField txttongTien;
    // End of variables declaration//GEN-END:variables
}
