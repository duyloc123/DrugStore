/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package drugstore.layout;

import drugstoreController.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khang
 */
public class formNhanVien extends javax.swing.JFrame {

    ConnectionDB cn = new ConnectionDB();
    Connection conn;
    
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
                    txtmakhachhang.setText(tableKH.getValueAt(tableKH.getSelectedRow(),0)+"");
                    cbbGT1.setSelectedItem(tableKH.getValueAt(tableKH.getSelectedRow(), 3));
                    txttenkh.setText(tableKH.getValueAt(tableKH.getSelectedRow(),1)+"");
                    txtsdtkh.setText(tableKH.getValueAt(tableKH.getSelectedRow(),4)+"");
                    txtdckh.setText(tableKH.getValueAt(tableKH.getSelectedRow(),5)+"");
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
        final String headerThuoc[] = {"Mã thuốc", "Loại thuốc","Nhà cung cấp","Tên thuốc","Ngày sản xuất","Hạn sử dụng","Đơn vị tính","Giá nhập","Giá bán","Số lương nhập","Hình ảnh","Trạng thái"};
        final DefaultTableModel thuoc = new DefaultTableModel(headerThuoc,0);

    
        public void loadTableThuoc(){
        try{
            conn = cn.getConnection();
            int number;
            Vector row;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select *from Thuoc");
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
                    txttenthuoc.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),2)+"");
                    txtDVT.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),3)+"");
                    txtgb.setText(tableSPThuoc.getValueAt(tableSPThuoc.getSelectedRow(),4)+"");
                    
            }
            }
        });
    }
    /**
     * Creates new form formNhanVien
     */
    public formNhanVien() {
        initComponents();
        loadTableKH();
        loadTableThuoc();
        loadTableSPThuoc();
        initGioiTinh();
        initNV();
        initKH();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        left = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        hangHoa = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        khachHang = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        hoaDon = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        top = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        main = new javax.swing.JTabbedPane();
        mains = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        mainkhachHang = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnThemKH = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtmakhachhang = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtdckh = new javax.swing.JTextField();
        txtsdtkh = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txttenkh = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jlabel2 = new javax.swing.JLabel();
        cbbGT1 = new javax.swing.JComboBox<>();
        jlabel3 = new javax.swing.JLabel();
        jDate1 = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableKH = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        mainhoadon = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
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
        txttenthuoc = new javax.swing.JTextField();
        btnThemSPHD = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSPThuoc = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        txtmaHD = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jdateHD = new com.toedter.calendar.JDateChooser();
        jLabel72 = new javax.swing.JLabel();
        cbbnhanvien = new javax.swing.JComboBox<>();
        cbbkhachhang = new javax.swing.JComboBox<>();
        jLabel75 = new javax.swing.JLabel();
        txttongTien = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        tinhTong = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableThuoc = new javax.swing.JTable();
        jLabel74 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        left.setBackground(new java.awt.Color(153, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/58995861.png"))); // NOI18N

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

        khachHang.setBackground(java.awt.Color.lightGray);
        khachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        khachHang.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        khachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                khachHangMouseClicked(evt);
            }
        });
        khachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/employee.png"))); // NOI18N
        khachHang.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Thông Tin Khách Hàng");
        khachHang.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 150, 40));

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
                                .addComponent(khachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftLayout.createSequentialGroup()
                        .addGroup(leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(khachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(hoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(hangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(232, 232, 232))
        );

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
        jLabel12.setText("Nhân Viên");

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
                .addGap(0, 0, Short.MAX_VALUE))
        );
        mainsLayout.setVerticalGroup(
            mainsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        main.addTab("Trang Chủ", mains);

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
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel26)
                        .addComponent(jLabel23))
                    .addComponent(jlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbGT1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txttenkh, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(txtmakhachhang))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtsdtkh, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtdckh, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jlabel3))
                    .addComponent(jDate1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmakhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(txtdckh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttenkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsdtkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlabel2)
                        .addComponent(cbbGT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlabel3))
                    .addComponent(jDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drugstore/img/search (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel46))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(mainkhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2))
        );

        main.addTab("Khách Hàng", mainkhachHang);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel68.setText("Sản Phẩm Thuốc");

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
                    .addComponent(txttenthuoc))
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
                        .addComponent(txttenthuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        btnThemSPHD.setBackground(new java.awt.Color(51, 255, 51));
        btnThemSPHD.setForeground(new java.awt.Color(51, 51, 51));
        btnThemSPHD.setText("Thêm sản phẩm");
        btnThemSPHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPHDActionPerformed(evt);
            }
        });

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
                        .addGap(25, 25, 25)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(btnThemSPHD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemSPHD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addGap(276, 276, 276)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(59, Short.MAX_VALUE)))
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel69.setText("Thông Tin Hóa Đơn");

        jLabel70.setText("Mã Hóa Đơn");

        jLabel71.setText("Tên Khách Hàng");

        jLabel73.setText("Ngày Lập");

        jLabel72.setText("Nhân Viên");

        cbbnhanvien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbkhachhang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel75.setText("Tổng Tiền");

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Tên sản phẩm", "Giá bán", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane6.setViewportView(tableHoaDon);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tinhTong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel69))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jLabel70)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtmaHD)
                                        .addGap(88, 88, 88))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jdateHD, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel71)
                                    .addComponent(jLabel72))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbnhanvien, 0, 137, Short.MAX_VALUE)
                            .addComponent(cbbkhachhang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel69)
                .addGap(34, 34, 34)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71)
                    .addComponent(txtmaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel72)
                            .addComponent(cbbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jdateHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel73))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(tinhTong))
                .addGap(36, 36, 36)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addGap(190, 190, 190)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(182, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout mainhoadonLayout = new javax.swing.GroupLayout(mainhoadon);
        mainhoadon.setLayout(mainhoadonLayout);
        mainhoadonLayout.setHorizontalGroup(
            mainhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainhoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        mainhoadonLayout.setVerticalGroup(
            mainhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainhoadonLayout.createSequentialGroup()
                .addGroup(mainhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainhoadonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        main.addTab("Hóa Đơn", mainhoadon);

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
        jScrollPane1.setViewportView(tableThuoc);

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel74.setText("Sản Phẩm Thuốc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(498, 498, 498)
                        .addComponent(jLabel74)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        main.addTab("Hàng Hóa", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 250, Short.MAX_VALUE)
                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, 1244, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                cbbkhachhang.addItem(name);
                
            }
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            cbbkhachhang.setModel(comboBoxModel);
        }catch(Exception ex){
            
        }
    } 
        private void initGioiTinh(){
        String[] gioiTinh = new String[]{
            "Nam","Nữ"
        };
        DefaultComboBoxModel<String> cbxModel = new DefaultComboBoxModel<>(gioiTinh);
        
        cbbGT1.setModel(cbxModel);
    }
        private void xoatrangKH(){
        txtmakhachhang.setText("");
        txttenkh.setText("");
        txtdckh.setText("");
        txtsdtkh.setText("");
        jDate1.setDate(null);
        loadTableKH();
    }
         private void xoatrangHD(){
        txtmaHD.setText("");
        txttongTien.setText("");
        jdateHD.setDate(null);
    }
    
    private void xoatrangSPThuoc(){
        txtMT.setText("");
        txttenthuoc.setText("");
        txtSL.setText("");
        txtgb.setText("");
        txtDVT.setText("");
        txtTT.setText("");
    }
    private void hangHoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hangHoaMouseClicked
        main.setSelectedIndex(3);
    }//GEN-LAST:event_hangHoaMouseClicked

    private void khachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khachHangMouseClicked
        main.setSelectedIndex(1);
    }//GEN-LAST:event_khachHangMouseClicked

    private void hoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonMouseClicked
        main.setSelectedIndex(2);
    }//GEN-LAST:event_hoaDonMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
                JFrame frame = new JFrame("Exit");
        if(JOptionPane.showConfirmDialog(frame,"Bạn có chắc muốn thoát!","Thông Báo",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
         System.exit(0);
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        conn = cn.getConnection();
        
        try{
            if(txtmakhachhang.getText().equals("") || txttenkh.getText().equals("") || txtdckh.getText().equals("") || txtsdtkh.getText().equals("") ||
                    jDate1.getDate().equals("")
                    ){
                JOptionPane.showMessageDialog(this,"Bạn cần nhập đầy đủ dữ liệu");
            }else{
                String sql_check_pk = "select ID_KH from KhachHang where ID_KH = '" + txtmakhachhang.getText()+"'";
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
                    ps.setInt(1,Integer.parseInt(txtmakhachhang.getText()));
                    ps.setString(4,(String) cbbGT1.getSelectedItem());
                    ps.setString(2,txttenkh.getText());
                    ps.setString(5,txtsdtkh.getText());
                    ps.setString(6,txtdckh.getText());
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

    private void txtSLInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSLInputMethodTextChanged

    }//GEN-LAST:event_txtSLInputMethodTextChanged

    private void txtTTPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTTPropertyChange

    }//GEN-LAST:event_txtTTPropertyChange

    private void tinhTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tinhTTActionPerformed
        float sl = Float.parseFloat(txtSL.getText());
        double tt ;
        float gb = Float.parseFloat(txtgb.getText());
        tt = sl*gb;
        txtTT.setText(String.valueOf(tt));
    }//GEN-LAST:event_tinhTTActionPerformed

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
                String data[] = {txttenthuoc.getText(),txtgb.getText(),txtSL.getText(),txtTT.getText()};
                models.addRow(data);
                xoatrangSPThuoc();
                loadTableSPThuoc();

            }
        }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnThemSPHDActionPerformed

    private void tableSPThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSPThuocMouseClicked

    }//GEN-LAST:event_tableSPThuocMouseClicked

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
            java.util.logging.Logger.getLogger(formNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemSPHD;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JComboBox<String> cbbGT1;
    private javax.swing.JComboBox<String> cbbkhachhang;
    private javax.swing.JComboBox<String> cbbnhanvien;
    private javax.swing.JPanel hangHoa;
    private javax.swing.JPanel hoaDon;
    private javax.swing.JButton jButton19;
    private com.toedter.calendar.JDateChooser jDate1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField2;
    private com.toedter.calendar.JDateChooser jdateHD;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JPanel khachHang;
    private javax.swing.JPanel left;
    private javax.swing.JTabbedPane main;
    private javax.swing.JPanel mainhoadon;
    private javax.swing.JPanel mainkhachHang;
    private javax.swing.JPanel mains;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tableKH;
    private javax.swing.JTable tableSPThuoc;
    private javax.swing.JTable tableThuoc;
    private javax.swing.JButton tinhTT;
    private javax.swing.JButton tinhTong;
    private javax.swing.JPanel top;
    private javax.swing.JTextField txtDVT;
    private javax.swing.JTextField txtMT;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTT;
    private javax.swing.JTextField txtdckh;
    private javax.swing.JTextField txtgb;
    private javax.swing.JTextField txtmaHD;
    private javax.swing.JTextField txtmakhachhang;
    private javax.swing.JTextField txtsdtkh;
    private javax.swing.JTextField txttenkh;
    private javax.swing.JTextField txttenthuoc;
    private javax.swing.JTextField txttongTien;
    // End of variables declaration//GEN-END:variables
}
