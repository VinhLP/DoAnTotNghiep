package com.example.restaurant.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiChiTietHoaDon;
import com.example.restaurant.CustomAdapter.AdapterHienThiGioHang;
import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.ChiTietHoaDonDTO;
import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.R;
import com.example.restaurant.ThanhToanActivity;
import com.example.restaurant.TrangChuActivity;

import java.util.List;

public class HienThiGioHangFragment extends Fragment implements View.OnClickListener {

    public static int REQUEST_CODE_SUA = 118;

    ListView listViewHienThiMonAnGioHang;
    FragmentManager fragmentManager;
    Button btnGoiMon;
    ImageButton btnThanhToan;

    GoiMonDAO goiMonDAO;
    List<GioHangDTO> gioHangDTOList;
    List<ChiTietGoiMonDTO> chiTietGoiMonDTOList;
    List<ChiTietHoaDonDTO> chiTietHoaDonDTOList;

    AdapterHienThiGioHang adapterHienThiGioHang;
    AdapterHienThiChiTietHoaDon adapterHienThiChiTietHoaDon;

    int maban;
    int magoimon;
    int mamonan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthigiohang, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.giohang);

        listViewHienThiMonAnGioHang = view.findViewById(R.id.listViewHienThiMonAnGioHang);

        btnGoiMon = (Button) view.findViewById(R.id.btnGoiMon);
        btnThanhToan = (ImageButton) view.findViewById(R.id.btnThanhToan);

        goiMonDAO = new GoiMonDAO(getActivity());

        fragmentManager = getActivity().getSupportFragmentManager();

        Bundle bDuLieuThucDon = getArguments();
        if (bDuLieuThucDon != null){
            maban = bDuLieuThucDon.getInt("maban");
        }

        HienThiGioHang();

        btnGoiMon.setOnClickListener(this);
        btnThanhToan.setOnClickListener(this);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthibanan", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        registerForContextMenu(listViewHienThiMonAnGioHang);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Bundle bMonAnGioHang = getArguments();
        if (bMonAnGioHang != null){
            maban = bMonAnGioHang.getInt("maban");
            magoimon = bMonAnGioHang.getInt("magoimon");
            mamonan = bMonAnGioHang.getInt("mamonan");
        }

        int vitri = menuInfo.position;
        mamonan = chiTietGoiMonDTOList.get(vitri).getMaMonAn();

        switch (id){
            case R.id.itSua:
                Toast.makeText(getActivity(), "ma mon an" + mamonan, Toast.LENGTH_SHORT).show();
                ;break;

            case R.id.itXoa:
                Log.d("vitri" + mamonan, "");

                ;break;
        }

        return super.onContextItemSelected(item);
    }

    public void HienThiGioHang(){
        magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");

        gioHangDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiGioHang = new AdapterHienThiGioHang(getActivity(), R.layout.custom_layout_hienthigiohang, gioHangDTOList);
        listViewHienThiMonAnGioHang.setAdapter(adapterHienThiGioHang);
        adapterHienThiGioHang.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGoiMon:

                Bundle bDuLieuThucDon = new Bundle();
                bDuLieuThucDon.putInt("maban", maban);
                bDuLieuThucDon.putInt("magoimon", magoimon);

                FragmentTransaction tranThucDonTransaction = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();

                hienThiThucDonFragment.setArguments(bDuLieuThucDon);

                tranThucDonTransaction.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthigiohang");
                tranThucDonTransaction.commit();

                break;

            case R.id.btnThanhToan:

                Bundle bDuLieuThanhToan = new Bundle();
                bDuLieuThanhToan.putInt("maban", maban);
                bDuLieuThanhToan.putInt("magoimon", magoimon);

                FragmentTransaction tranThanhToanTransaction = fragmentManager.beginTransaction();
                HienThiThanhToanFragment hienThiThanhToanFragment = new HienThiThanhToanFragment();

                hienThiThanhToanFragment.setArguments(bDuLieuThanhToan);

                tranThanhToanTransaction.replace(R.id.content, hienThiThanhToanFragment).addToBackStack("hienthigiohang");
                tranThanhToanTransaction.commit();

                ;break;
        }
    }
}