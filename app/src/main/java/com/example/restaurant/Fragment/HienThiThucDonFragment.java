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
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.restaurant.DAO.LoaiMonAnDAO;
import com.example.restaurant.DAO.MonAnDAO;
import com.example.restaurant.DTO.LoaiMonAnDTO;
import com.example.restaurant.R;
import com.example.restaurant.SuaLoaiThucDonActivity;
import com.example.restaurant.ThemThucDonActivity;
import com.example.restaurant.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {

    public static int REQUEST_CODE_SUA = 117;

    GridView gridView;

    List<LoaiMonAnDTO> loaiMonAnDTOS;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;

    FragmentManager fragmentManager;
    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon;

    int maban;
    int maloai;
    int magoimon = 0;

    int maquyen;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        gridView = (GridView) view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        monAnDAO = new MonAnDAO(getActivity());

        HienThiLoaiMonAn();

        Bundle bDuLieuThucDon = getArguments();
        if (bDuLieuThucDon != null){
            maban = bDuLieuThucDon.getInt("maban");

            magoimon = bDuLieuThucDon.getInt("magoimon");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                maloai = loaiMonAnDTOS.get(position).getMaLoai();

                Bundle bundle = new Bundle();
                bundle.putInt("maloai", maloai);
                bundle.putInt("maban", maban);
                bundle.putInt("magoimon", magoimon);

                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
                hienThiDanhSachMonAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");
                transaction.commit();

                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN){
                            getFragmentManager().popBackStack("hienthigiohang", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                        return false;            }
                });
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

        if (maquyen == 1){
            registerForContextMenu(gridView);
        }
        return view;
    }

    // code xử lý item sửa, xóa LOẠI MÓN ĂN...
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
        int maloai = loaiMonAnDTOS.get(vitri).getMaLoai();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaLoaiThucDonActivity.class);
                intent.putExtra("maloai", maloai);
                startActivityForResult(intent, REQUEST_CODE_SUA);

                ;break;

            case R.id.itXoa:
                boolean kiemtraloaimonan = loaiMonAnDAO.XoaLoaiMonAnTheoMa(maloai);
                boolean kiemtramonan = monAnDAO.XoaMonAnTheoMaLoaiMonAn(maloai);
                if (kiemtraloaimonan == true && kiemtramonan == true){
                    HienThiLoaiMonAn();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathatbai), Toast.LENGTH_SHORT).show();
                }
                ;break;
        }
        return super.onContextItemSelected(item);
    }

    // code xử lý Item thêm LOẠI MÓN ĂN
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

    public void HienThiLoaiMonAn(){
        loaiMonAnDTOS = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAnThucDon = new AdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hienloaimonan, loaiMonAnDTOS);
        gridView.setAdapter(adapterHienThiLoaiMonAnThucDon);
        adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SUA){
            if (resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtra", false);
                if (kiemtra){
                    HienThiLoaiMonAn();
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
