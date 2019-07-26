package com.example.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.restaurant.DAO.LoaiMonAnDAO;
import com.example.restaurant.DAO.MonAnDAO;
import com.example.restaurant.DTO.LoaiMonAnDTO;
import com.example.restaurant.DTO.MonAnDTO;

import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINHTHUCDON = 112;

    EditText edThemTenMonAn, edThemGiaTien;
    Button btnThemLoaiThucDon;
    Button btnDongYThemThucDon, btnThoatThemThucDon;
    Spinner spinloaithucdon;
    ImageView imHinhThucDon;

    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOS;

    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;

    String sDuongDanHinh;
    int vitri;
    int maloai;
    int mamonan = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        btnThemLoaiThucDon = (Button) findViewById(R.id.btnThemLoaiThucDon);
        spinloaithucdon = (Spinner) findViewById(R.id.spinloaithucdon);
        imHinhThucDon = (ImageView) findViewById(R.id.imHinhThucDon);
        btnDongYThemThucDon = (Button) findViewById(R.id.btnDongYThemThucDon);
        btnThoatThemThucDon = (Button) findViewById(R.id.btnThoatThemThucDon);
        edThemTenMonAn = (EditText) findViewById(R.id.edThemTenMonAn);
        edThemGiaTien = (EditText) findViewById(R.id.edThemGiaTien);

        HienThiSpinnerLoaiMonAn();

        btnThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemThucDon.setOnClickListener(this);
        btnThoatThemThucDon.setOnClickListener(this);

        mamonan = getIntent().getIntExtra("mamonan", 0);

        // Hàm load thông tin khi cập nhật món ăn...
        if (mamonan != 0){
            MonAnDTO monAnDTO = monAnDAO.LayDanhSachMonAnTheoMa(mamonan);

            edThemTenMonAn.setText(monAnDTO.getTenMonAn());
            edThemGiaTien.setText(monAnDTO.getGiaTien());
        }
    }

    public void HienThiSpinnerLoaiMonAn(){
        loaiMonAnDTOS = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this, R.layout.custom_layout_spinloaithucdon, loaiMonAnDTOS);
        spinloaithucdon.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    public void DongYThemThucDon(){
        vitri = spinloaithucdon.getSelectedItemPosition();
        maloai = loaiMonAnDTOS.get(vitri).getMaLoai();

        String tenmonan = edThemTenMonAn.getText().toString();
        String giatien = edThemGiaTien.getText().toString();

        if (tenmonan != null && !tenmonan.equals("") && giatien != null && !giatien.equals("")){
            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setTenMonAn(tenmonan);
            monAnDTO.setGiaTien(giatien);
            monAnDTO.setHinhAnh(sDuongDanHinh);
            monAnDTO.setMaLoai(maloai);

            boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
            if (kiemtra){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, getResources().getString(R.string.batloithemthucdon), Toast.LENGTH_SHORT).show();
        }
    }

    public void SuaThucDon(){
        vitri = spinloaithucdon.getSelectedItemPosition();
        maloai = loaiMonAnDTOS.get(vitri).getMaLoai();

        String tenmonan = edThemTenMonAn.getText().toString();
        String giatien = edThemGiaTien.getText().toString();

        MonAnDTO monAnDTO = new MonAnDTO();
        monAnDTO.setMaMonAn(mamonan);
        monAnDTO.setTenMonAn(tenmonan);
        monAnDTO.setGiaTien(giatien);
        monAnDTO.setHinhAnh(sDuongDanHinh);
        monAnDTO.setMaLoai(maloai);

        boolean kiemtra = monAnDAO.SuaMonAn(monAnDTO);
        if (kiemtra){
            Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThemLoaiThucDon:
                Intent iThemLoaiThucDon = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiThucDon, REQUEST_CODE_THEMLOAITHUCDON);
                ;break;

            case R.id.imHinhThucDon:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình thực đơn"), REQUEST_CODE_MOHINHTHUCDON);
                ;break;

            case R.id.btnDongYThemThucDon:
                if (mamonan != 0){
                    // code sửa món ăn
                    SuaThucDon();

                }else {
                    // code thêm món ăn
                    DongYThemThucDon();
                }
                finish();
                ;break;

            case R.id.btnThoatThemThucDon:
                finish();
                ;break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if (resultCode == Activity.RESULT_OK && data != null){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon", false);
                if (kiemtra){
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == REQUEST_CODE_MOHINHTHUCDON){
            if (resultCode == Activity.RESULT_OK && data != null){
                sDuongDanHinh = data.getData().toString();
                imHinhThucDon.setImageURI(data.getData());
            }
        }
    }
}
