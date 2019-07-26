package com.example.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.DAO.LoaiMonAnDAO;
import com.example.restaurant.DTO.LoaiMonAnDTO;

public class SuaLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edSuaLoaiThucDon;
    Button btnDongYSuaLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;
    int maloai = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sualoaithucdon);

        edSuaLoaiThucDon = (EditText) findViewById(R.id.edSuaLoaiThucDon);
        btnDongYSuaLoaiThucDon = (Button) findViewById(R.id.btnDongYSuaLoaiThucDon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        maloai = getIntent().getIntExtra("maloai", maloai);

        // code load tên loại thực đơn khi sửa
        if (maloai != 0){
            LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDAO.LayDanhSachLoaiMonAnTheoMa(maloai);
            edSuaLoaiThucDon.setText(loaiMonAnDTO.getTenLoai());
        }

        btnDongYSuaLoaiThucDon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tenloai = edSuaLoaiThucDon.getText().toString();
        if (tenloai.trim().equals("") || tenloai.trim() != null){
            boolean kiemtra = loaiMonAnDAO.CapNhatLaiTenLoaiMonAn(maloai, tenloai);
            Intent intent = new Intent();
            intent.putExtra("kiemtra", kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }else {
            Toast.makeText(SuaLoaiThucDonActivity.this, getResources().getString(R.string.vuilongnhaptenloaithudon), Toast.LENGTH_SHORT).show();

        }
    }
}
