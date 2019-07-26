package com.example.restaurant.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurant.CustomAdapter.AdapterHienThiThongTinCaNhan;
import com.example.restaurant.DAO.NhanVienDAO;
import com.example.restaurant.DTO.NhanVienDTO;
import com.example.restaurant.DangKyActivity;
import com.example.restaurant.R;
import com.example.restaurant.TrangChuActivity;

import java.util.List;

public class HienThiThongTinCaNhanFragment extends Fragment implements View.OnClickListener {

    int manhanvien;

    NhanVienDAO nhanVienDAO;
    NhanVienDTO nhanVienDTO;
    List<NhanVienDTO> nhanVienDTOList;

    AdapterHienThiThongTinCaNhan adapterHienThiThongTinCaNhan;

    ImageButton btnSuaThongTinCaNhan;
    TextView tvTenDangNhap, tvMatKhau, tvGioiTinh, tvNgaySinh, tvCMND;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithongtincanhan, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.caidat);

        btnSuaThongTinCaNhan = (ImageButton) view.findViewById(R.id.btnSuaThongTinCaNhan);
        tvTenDangNhap = (TextView) view.findViewById(R.id.tvTenDangNhap);
        tvMatKhau = (TextView) view.findViewById(R.id.tvMatKhau);
        tvGioiTinh = (TextView) view.findViewById(R.id.tvGioiTinh);
        tvNgaySinh = (TextView) view.findViewById(R.id.tvNgaySinh);
        tvCMND = (TextView) view.findViewById(R.id.tvCMND);

        nhanVienDAO = new NhanVienDAO(getActivity());


        Bundle bDuLieuCaNhan = getArguments();
        if (bDuLieuCaNhan != null){
            manhanvien = bDuLieuCaNhan.getInt("manhanvien");

            nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manhanvien);
            tvTenDangNhap.setText(nhanVienDTO.getTENDN());
            tvMatKhau.setText(nhanVienDTO.getMATKHAU());
            tvGioiTinh.setText(nhanVienDTO.getGIOITINH());
            tvNgaySinh.setText(nhanVienDTO.getNGAYSINH());
            tvCMND.setText(String.valueOf(nhanVienDTO.getCMND()));

        }




        btnSuaThongTinCaNhan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnSuaThongTinCaNhan:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                iDangKy.putExtra("manhanvien", manhanvien);
                startActivity(iDangKy);
                ;break;
        }

    }
}
