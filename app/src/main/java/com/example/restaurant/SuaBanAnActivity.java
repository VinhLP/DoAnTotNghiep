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

import com.example.restaurant.DAO.BanAnDAO;
import com.example.restaurant.DTO.BanAnDTO;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edSuaTenBanAn;
    Button btnDongYSuaBanAn;
    BanAnDAO banAnDAO;
    int maban = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);

        edSuaTenBanAn = (EditText) findViewById(R.id.edSuaTenBanAn);
        btnDongYSuaBanAn = (Button) findViewById(R.id.btnDongYSuaBanAn);

        banAnDAO = new BanAnDAO(this);

        maban = getIntent().getIntExtra("maban", 0);

        // code load tên bàn khi sửa
        if (maban != 0){
            BanAnDTO banAnDTO = banAnDAO.LayDanhSachBanAnTheoMa(maban);
            edSuaTenBanAn.setText(banAnDTO.getTenBan());
        }

        btnDongYSuaBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tenban = edSuaTenBanAn.getText().toString();
        if (tenban.trim().equals("") || tenban.trim() != null){
            boolean kiemtra = banAnDAO.CapNhatLaiTenBan(maban, tenban);
            Intent intent = new Intent();
            intent.putExtra("kiemtra", kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }else {
            Toast.makeText(SuaBanAnActivity.this, getResources().getString(R.string.vuilongnhaptenban), Toast.LENGTH_SHORT).show();
        }
    }
}
