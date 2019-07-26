package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.Fragment.HienThiGioHangFragment;

import java.util.List;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {

    int maban, mamonan;
    int magoimon;

    EditText edSoLuongMonAn;
    Button btnDongYThemSoLuongMonAn;

    GoiMonDAO goiMonDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);

        edSoLuongMonAn = (EditText) findViewById(R.id.edSoLuongMonAn);
        btnDongYThemSoLuongMonAn = (Button) findViewById(R.id.btnDongYThemSoLuongMonAn);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamonan = intent.getIntExtra("mamonan", 0);
        magoimon = intent.getIntExtra("magoimon", 0);

        btnDongYThemSoLuongMonAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");

        Bundle bMonAnGioHang = new Bundle();
        bMonAnGioHang.putInt("maban", maban);
        bMonAnGioHang.putInt("magoimon", magoimon);
        bMonAnGioHang.putInt("mamonan", mamonan);

        HienThiGioHangFragment hienThiGioHangFragment = new HienThiGioHangFragment();
        hienThiGioHangFragment.setArguments(bMonAnGioHang);

        boolean kiemtra = goiMonDAO.KiemTraMonAnDaTonTai(magoimon, mamonan);
        if (kiemtra){
            int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon, mamonan);
            int soluongmoi = Integer.parseInt(edSoLuongMonAn.getText().toString());

            int tongsoluong = soluongcu + soluongmoi;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(tongsoluong);

            boolean kiemtracapnhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            if (kiemtracapnhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }else {
            int soluonggoi = Integer.parseInt(edSoLuongMonAn.getText().toString());

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(soluonggoi);

            boolean kiemtracapnhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
            if (kiemtracapnhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
