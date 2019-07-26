package com.example.restaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.DAO.NhanVienDAO;
import com.example.restaurant.Fragment.HienThiThongTinCaNhanFragment;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edTenDangNhapDN, edMatKhauDN;
    Button btnDangNhapDN, btnDangKyDN;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        edTenDangNhapDN = (EditText) findViewById(R.id.edTenDangNhapDN);
        edMatKhauDN = (EditText) findViewById(R.id.edMatKhauDN);
        btnDangNhapDN = (Button) findViewById(R.id.btnDangNhapDN);
        btnDangKyDN = (Button) findViewById(R.id.btnDangKyDN);

        nhanVienDAO = new NhanVienDAO(this);
        btnDangNhapDN.setOnClickListener(this);
        btnDangKyDN.setOnClickListener(this);
        HienThiButtonDangNhapVsDangKy();
    }

    private void HienThiButtonDangNhapVsDangKy(){
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
        if (kiemtra) {
            btnDangNhapDN.setVisibility(View.VISIBLE);
            btnDangKyDN.setVisibility(View.GONE);
        }else {
            btnDangNhapDN.setVisibility(View.GONE);
            btnDangKyDN.setVisibility(View.VISIBLE);
        }
    }

    public void btnDangNhap(){
        String sTenDangNhap = edTenDangNhapDN.getText().toString();
        String sMatKhau = edMatKhauDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);

        if (kiemtra != 0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen", maquyen);
            editor.commit();

            Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            iTrangChu.putExtra("tendn", edTenDangNhapDN.getText().toString());
            iTrangChu.putExtra("manhanvien", kiemtra);


//            Bundle bDuLieuCaNhan = new Bundle();
//            bDuLieuCaNhan.putInt("manhanvien", kiemtra);
//            HienThiThongTinCaNhanFragment hienThiThongTinCaNhanFragment = new HienThiThongTinCaNhanFragment();
//            hienThiThongTinCaNhanFragment.setArguments(bDuLieuCaNhan);


            startActivity(iTrangChu);
        }else {
            Toast.makeText(DangNhapActivity.this, getResources().getString(R.string.dangnhapthatbai), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDangKy(){
        Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
        iDangKy.putExtra("landautien", 1);
        startActivity(iDangKy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButtonDangNhapVsDangKy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDangNhapDN:
                btnDangNhap();
                ;break;
            case R.id.btnDangKyDN:
                btnDangKy();
                ;break;
        }
    }
}
