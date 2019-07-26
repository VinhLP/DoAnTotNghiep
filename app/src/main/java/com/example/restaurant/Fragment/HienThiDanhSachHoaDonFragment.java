package com.example.restaurant.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.restaurant.CustomAdapter.AdapterHienThiDanhSachHoaDon;
import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.GoiMonDTO;
import com.example.restaurant.DTO.HoaDonTTDTO;
import com.example.restaurant.R;
import com.example.restaurant.TrangChuActivity;

import java.util.List;

public class HienThiDanhSachHoaDonFragment extends Fragment {

    GridView gvDanhSachHoaDon;

    GoiMonDAO goiMonDAO;
    List<GoiMonDTO> goiMonDTOList;
//    List<HoaDonTTDTO> hoaDonTTDTOList;

    FragmentManager fragmentManager;
    AdapterHienThiDanhSachHoaDon adapterHienThiDanhSachHoaDon;

    int magoimon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthidanhsachhoadon, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thongke);

        gvDanhSachHoaDon = (GridView) view.findViewById(R.id.gvDanhSachHoaDon);

        goiMonDAO = new GoiMonDAO(getActivity());

        fragmentManager = getActivity().getSupportFragmentManager();

        HienThiDanhSachHoaDon();

        gvDanhSachHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                magoimon = goiMonDTOList.get(position).getMaGoiMon();

                Bundle bundle = new Bundle();
                bundle.putInt("magoimon", magoimon);


                HienThiChiTietHoaDonFragment hienThiChiTietHoaDonFragment = new HienThiChiTietHoaDonFragment();
                hienThiChiTietHoaDonFragment.setArguments(bundle);

                FragmentTransaction tranChiTietHoaDon = fragmentManager.beginTransaction();
                tranChiTietHoaDon.replace(R.id.content, hienThiChiTietHoaDonFragment).addToBackStack("hienthidanhsachhoadon");
                tranChiTietHoaDon.commit();
            }
        });

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthitrangchu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;            }
        });

        return view;

    }

//    public void HienThiDanhSachHoaDon(){
//        hoaDonTTDTOList = goiMonDAO.LayDanhSachGoiMon(magoimon);
//        adapterHienThiDanhSachHoaDon = new AdapterHienThiDanhSachHoaDon(getActivity(), R.layout.custom_layout_hienthidanhsachhoadon, hoaDonTTDTOList);
//        gvDanhSachHoaDon.setAdapter(adapterHienThiDanhSachHoaDon);
//        adapterHienThiDanhSachHoaDon.notifyDataSetChanged();
//    }

    public void HienThiDanhSachHoaDon(){
        goiMonDTOList = goiMonDAO.LayDanhSachGoiMon();
        adapterHienThiDanhSachHoaDon = new AdapterHienThiDanhSachHoaDon(getActivity(), R.layout.custom_layout_hienthidanhsachhoadon, goiMonDTOList);
        gvDanhSachHoaDon.setAdapter(adapterHienThiDanhSachHoaDon);
        adapterHienThiDanhSachHoaDon.notifyDataSetChanged();
    }
}
