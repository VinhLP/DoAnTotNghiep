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

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edTenBanAn;
    Button btnDongYThemBanAn;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);

        edTenBanAn = (EditText) findViewById(R.id.edTenBanAn);
        btnDongYThemBanAn = (Button) findViewById(R.id.btnDongYThemBanAn);

        banAnDAO = new BanAnDAO(this);
        btnDongYThemBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String sTenBanAn = edTenBanAn.getText().toString();
        if (sTenBanAn != null || sTenBanAn.equals("")){
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem", kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else {
            Toast.makeText(this, getResources().getString(R.string.vuilongnhaptenban), Toast.LENGTH_SHORT).show();
        }
    }
}
