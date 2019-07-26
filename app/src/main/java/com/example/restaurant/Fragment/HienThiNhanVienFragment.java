package com.example.restaurant.Fragment;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiNhanVien;
import com.example.restaurant.DAO.NhanVienDAO;
import com.example.restaurant.DTO.NhanVienDTO;
import com.example.restaurant.DangKyActivity;
import com.example.restaurant.R;
import com.example.restaurant.ThemThucDonActivity;
import com.example.restaurant.TrangChuActivity;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {

//    public static int REQUEST_CODE_THEM = 115;
    ListView listViewNhanVien;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOList;
    FragmentManager fragmentManager;
    AdapterHienThiNhanVien adapterHienThiNhanVien;
    int maquyen;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_hienthinhanvien, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);

        listViewNhanVien = (ListView) view.findViewById(R.id.listViewNhanVien);
        nhanVienDAO = new NhanVienDAO(getActivity());

        fragmentManager = getActivity().getSupportFragmentManager();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        HienThiDanhSachNhanVien();

        if (maquyen == 1){
            registerForContextMenu(listViewNhanVien);
        }
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
        int vitri = menuInfo.position;
        int manhanvien = nhanVienDTOList.get(vitri).getMANV();

        switch (id){
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                iDangKy.putExtra("manhanvien", manhanvien);
                startActivity(iDangKy);
                ;break;

            case R.id.itXoa:
                boolean kiemtra = nhanVienDAO.XoaNhanVienTheoMa(manhanvien);
                if (kiemtra){
                    HienThiDanhSachNhanVien();
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
            MenuItem itThemNhanVien = menu.add(1,R.id.itThemNhanVien, 1, R.string.itthemnhanvien);
            itThemNhanVien.setIcon(R.drawable.ic_item_themnhanvien);
            itThemNhanVien.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent itThemNhanVien = new Intent(getActivity(), DangKyActivity.class);
                startActivity(itThemNhanVien);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void HienThiDanhSachNhanVien(){
        nhanVienDTOList = nhanVienDAO.LayDanhSachNhanVien();
        adapterHienThiNhanVien = new AdapterHienThiNhanVien(getActivity(), R.layout.custom_layout_hienthinhanvien, nhanVienDTOList);
        listViewNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetChanged();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_THEM){
//            if (resultCode == Activity.RESULT_OK){
//                Intent intent = data;
//                boolean kiemtranhanvien = intent.getBooleanExtra("ketquathem", false);
//                if (kiemtranhanvien){
//                    HienThiDanhSachNhanVien();
//                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
}
