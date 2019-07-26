package com.example.restaurant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiNhanVien;
import com.example.restaurant.DAO.NhanVienDAO;
import com.example.restaurant.DAO.QuyenDAO;
import com.example.restaurant.DTO.NhanVienDTO;
import com.example.restaurant.DTO.QuyenDTO;
import com.example.restaurant.Database.CreateDatabase;
import com.example.restaurant.Fragment.DatePickerFragment;
import com.example.restaurant.Fragment.HienThiNhanVienFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    EditText edTenDangNhapDK, edMatKhauDK, edNgaySinh, edCMND;
    RadioGroup rgGioiTinh;
    RadioButton rdNam, rdNu;
    Button btnDangKyDK, btnQuayLaiDangNhap;
    Spinner spinQuyenNV;
    String sGioiTinh;

    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;

    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;

    int manhanvien = 0;
    int landautien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        edTenDangNhapDK = (EditText) findViewById(R.id.edTenDangNhapDK);
        edMatKhauDK = (EditText) findViewById(R.id.edMatKhauDK);
        edNgaySinh = (EditText) findViewById(R.id.edNgaySinh);
        edCMND = (EditText) findViewById(R.id.edCMND);

        rgGioiTinh = (RadioGroup) findViewById(R.id.rgGioiTinh);
        rdNam = (RadioButton) findViewById(R.id.rdNam);
        rdNu = (RadioButton) findViewById(R.id.rdNu);

        btnDangKyDK = (Button) findViewById(R.id.btnDangKyDK);
        btnQuayLaiDangNhap = (Button) findViewById(R.id.btnQuayLaiDangNhap);

        spinQuyenNV = (Spinner) findViewById(R.id.spinQuyenNV);

        btnDangKyDK.setOnClickListener(this);
        btnQuayLaiDangNhap.setOnClickListener(this);
        edNgaySinh.setOnFocusChangeListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<String>();

        for (int i=0; i<quyenDTOList.size(); i++){
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }

        manhanvien = getIntent().getIntExtra("manhanvien", 0);
        landautien = getIntent().getIntExtra("landautien", 0);

        if (landautien == 0){
            quyenDAO.ThemQuyen("quản lý");
            quyenDAO.ThemQuyen("nhân viên");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataAdapter);
            spinQuyenNV.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            spinQuyenNV.setVisibility(View.GONE);
        }

        // code cập nhật nhân viên...
        if (manhanvien != 0){

            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manhanvien);

            edTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edNgaySinh.setText(nhanVienDTO.getNGAYSINH());
            edCMND.setText(String.valueOf(nhanVienDTO.getCMND()));

            String gioitinh = nhanVienDTO.getGIOITINH();
            if (gioitinh.equals("Nam")){
                rdNam.setChecked(true);
            }else {
                rdNu.setChecked(true);
            }
        }
    }

    // code thêm nhân viên lần đầu tiên...
    private void DongYThemNhanVien(){
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();

        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam"; break;
            case R.id.rdNu:
                sGioiTinh = "Nữ"; break;
        }

        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());

        if (sTenDangNhap == null && sTenDangNhap.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.batloitendangnhap), Toast.LENGTH_SHORT).show();
        }else if (sMatKhau == null && sMatKhau.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.batloimatkhau), Toast.LENGTH_SHORT).show();
        }else{
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setCMND(sCMND);

            if (landautien != 0){
                // gán mặc định quyền nhân viên là admin
                nhanVienDTO.setMAQUYEN(1);
            }else {
                // gán quyền bằng quyền mà admin chọn khi tạo nhân viên...
                int vitri = spinQuyenNV.getSelectedItemPosition();
                int maquyen = quyenDTOList.get(vitri).getMaQuyen();
                nhanVienDTO.setMAQUYEN(maquyen);
            }

            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if (kiemtra != 0){
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.dangkythanhcong), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.dangkythatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // code sửa nhân viên...
    private void SuaNhanVien(){
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam"; break;
            case R.id.rdNu:
                sGioiTinh = "Nữ"; break;
        }

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(manhanvien);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setGIOITINH(sGioiTinh);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setCMND(sCMND);

        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if (kiemtra){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathatbai), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDangKyDK:
                if (manhanvien != 0){
                    // code sửa nhân viên...
                    SuaNhanVien();
                }else {
                    // code thêm mới nhân viên...
                    DongYThemNhanVien();
                }
                finish();
                ;break;

            case R.id.btnQuayLaiDangNhap:
                finish();
                ;break;
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edNgaySinh:
                if (hasFocus){
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), "Ngày Sinh");
                }
                ;break;
        }
    }
}
