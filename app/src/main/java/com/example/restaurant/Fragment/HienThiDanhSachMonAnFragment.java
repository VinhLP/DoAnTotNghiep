package com.example.restaurant.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.example.restaurant.DAO.MonAnDAO;
import com.example.restaurant.DTO.MonAnDTO;
import com.example.restaurant.R;
import com.example.restaurant.SoLuongActivity;
import com.example.restaurant.ThemThucDonActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {

    GridView gridView;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOList;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;

    int maban;
    int maloai;
    int magoimon;

    int maquyen;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthidanhsachmonan, container, false);
        setHasOptionsMenu(true);

        gridView = (GridView) view.findViewById(R.id.gvHienThiDSMonAn);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        monAnDAO = new MonAnDAO(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null){
            maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");
            magoimon = bundle.getInt("magoimon");

            HienThiDanhSachMonAn();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (maban != 0){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban", maban);
                        iSoLuong.putExtra("mamonan", monAnDTOList.get(position).getMaMonAn());
                        iSoLuong.putExtra("magoimon", magoimon);

                        startActivity(iSoLuong);
                    }
                }
            });
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        if (maquyen == 1){
            registerForContextMenu(gridView);
        }
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    // hàm xử lý sửa và xóa món ăn
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamonan = monAnDTOList.get(vitri).getMaMonAn();

        switch (id){
            case R.id.itSua:
                Intent iThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                iThucDon.putExtra("mamonan", mamonan);
                startActivity(iThucDon);

                ;break;

            case R.id.itXoa:
                boolean kiemtra = monAnDAO.XoaMonAnTheoMa(mamonan);
                if (kiemtra){
                    HienThiDanhSachMonAn();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathatbai), Toast.LENGTH_SHORT).show();
                }
                ;break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen == 1){
            MenuItem itThemThucDon = menu.add(1,R.id.itThemThucDon, 1, R.string.itthemthucdon);
            itThemThucDon.setIcon(R.drawable.ic_item_themthucdon);
            itThemThucDon.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void HienThiDanhSachMonAn(){
        monAnDTOList = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);
        adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(), R.layout.custom_layout_hienthidanhsachmonan, monAnDTOList);
        gridView.setAdapter(adapterHienThiDanhSachMonAn);
        adapterHienThiDanhSachMonAn.notifyDataSetChanged();
    }
}
