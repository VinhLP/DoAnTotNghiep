package com.example.restaurant.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.restaurant.CustomAdapter.AdapterHienThiChiTietHoaDon;
import com.example.restaurant.CustomAdapter.AdapterHienThiGioHang;
import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.ChiTietHoaDonDTO;
import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.R;
import com.example.restaurant.TrangChuActivity;

import java.util.List;

public class HienThiChiTietHoaDonFragment extends Fragment {

    GridView gvHienThiDSMonAnHD;

    GoiMonDAO goiMonDAO;
    List<GioHangDTO> gioHangDTOList;
    List<ChiTietHoaDonDTO> chiTietHoaDonDTOList;

    AdapterHienThiChiTietHoaDon adapterHienThiChiTietHoaDon;
    AdapterHienThiGioHang adapterHienThiGioHang;

    int magoimon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthichitiethoadon, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.chitiethoadon);

        gvHienThiDSMonAnHD = (GridView) view.findViewById(R.id.gvHienThiDSMonAnHD);

        goiMonDAO = new GoiMonDAO(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null){
            magoimon = bundle.getInt("magoimon");
        }
        HienThiDanhSachMonAnHoaDon();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthidanhsachhoadon", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });


        return view;
    }

//    public void HienThiDanhSachMonAnHoaDon(){
//        gioHangDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
//        adapterHienThiGioHang = new AdapterHienThiGioHang(getActivity(), R.layout.custom_layout_hienthichitietmonanhoadon, gioHangDTOList);
//        gvHienThiDSMonAnHD.setAdapter(adapterHienThiGioHang);
//        adapterHienThiGioHang.notifyDataSetChanged();
//    }

    public void HienThiDanhSachMonAnHoaDon(){
        gioHangDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiGioHang = new AdapterHienThiGioHang(getActivity(), R.layout.custom_layout_hienthichitietmonanhoadon, gioHangDTOList);
        gvHienThiDSMonAnHD.setAdapter(adapterHienThiGioHang);
        adapterHienThiGioHang.notifyDataSetChanged();
    }
}
