package com.example.restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.restaurant.Fragment.HienThiBanAnFragment;
import com.example.restaurant.Fragment.HienThiNhanVienFragment;
import com.example.restaurant.Fragment.HienThiDanhSachHoaDonFragment;
import com.example.restaurant.Fragment.HienThiThongTinCaNhanFragment;
import com.example.restaurant.Fragment.HienThiThucDonFragment;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvTenNhanVien_Navigation, tvChucVuNhanVien;

    FragmentManager fragmentManager;

    int maquyen;
    int manhanvien = 0;

    SharedPreferences sharedPreferences;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview_trangchu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        tvTenNhanVien_Navigation = (TextView) view.findViewById(R.id.tvTenNhanVien_Navigation);
        tvChucVuNhanVien = (TextView) view.findViewById(R.id.tvChucVuNhanVien);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.mo, R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        tvTenNhanVien_Navigation.setText(tendn);

        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        if (maquyen == 1){
            tvChucVuNhanVien.setText(R.string.quanly);
        }else {
            tvChucVuNhanVien.setText(R.string.nhanvien);
        }

        manhanvien = getIntent().getIntExtra("manhanvien", 0);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.replace(R.id.content, hienThiBanAnFragment).addToBackStack("hienthitrangchu");
        tranHienThiBanAn.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                tranHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
                tranHienThiBanAn.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.replace(R.id.content, hienThiThucDonFragment);
                tranHienThiThucDon.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itNhanVien:
                if (maquyen == 1){
                    FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                    HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                    tranNhanVien.replace(R.id.content, hienThiNhanVienFragment);
                    tranNhanVien.commit();

                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;
                }

            case R.id.itThongKe:
                if (maquyen == 1){
                    FragmentTransaction tranThongKe = fragmentManager.beginTransaction();
                    HienThiDanhSachHoaDonFragment hienThiHoaDonFragment = new HienThiDanhSachHoaDonFragment();
                    tranThongKe.replace(R.id.content, hienThiHoaDonFragment);
                    tranThongKe.commit();

                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    ;break;
                }

            case R.id.itCaiDat:

                FragmentTransaction tranCaiDat = fragmentManager.beginTransaction();
                HienThiThongTinCaNhanFragment hienThiThongTinCaNhanFragment = new HienThiThongTinCaNhanFragment();

                Bundle bDuLieuCaNhan = new Bundle();
                bDuLieuCaNhan.putInt("manhanvien", manhanvien);
//                HienThiThongTinCaNhanFragment hienThiThongTinCaNhanFragment = new HienThiThongTinCaNhanFragment();
                hienThiThongTinCaNhanFragment.setArguments(bDuLieuCaNhan);

                tranCaiDat.replace(R.id.content, hienThiThongTinCaNhanFragment);
                tranCaiDat.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itDangXuat:
                Intent iDangNhap = new Intent(TrangChuActivity.this, DangNhapActivity.class);
                startActivity(iDangNhap);
                finish();
        }
        return false;
    }
}
