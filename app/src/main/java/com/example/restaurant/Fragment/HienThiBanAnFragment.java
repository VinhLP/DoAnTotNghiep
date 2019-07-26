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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiBanAn;
import com.example.restaurant.DAO.BanAnDAO;
import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.BanAnDTO;
import com.example.restaurant.DTO.GoiMonDTO;
import com.example.restaurant.R;
import com.example.restaurant.SuaBanAnActivity;
import com.example.restaurant.ThanhToanActivity;
import com.example.restaurant.ThemBanAnActivity;
import com.example.restaurant.ThemThucDonActivity;
import com.example.restaurant.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HienThiBanAnFragment extends Fragment{

    public static int REQUEST_CODE_THEM = 111;
    public static int REQUEST_CODE_SUA = 222;

    GridView gvHienThiBanAn;
    FragmentManager fragmentManager;

    BanAnDAO banAnDAO;
    List<BanAnDTO> banAnDTOList;
    GoiMonDAO goiMonDAO;

    AdapterHienThiBanAn adapterHienThiBanAn;

    int maban;
    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.trangchu);

        gvHienThiBanAn = (GridView) view.findViewById(R.id.gvHienThiBanAn);
        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        banAnDAO = new BanAnDAO(getActivity());
        banAnDTOList = banAnDAO.LayTatCaBanAn();

        HienThiDanhSachBanAn();

        if (maquyen == 1){
            registerForContextMenu(gvHienThiBanAn);
        }

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    // code chức năng sửa, xóa bàn ăn
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banAnDTOList.get(vitri).getMaBan();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban", maban);
                startActivityForResult(intent, REQUEST_CODE_SUA);

                Toast.makeText(getActivity(), "ma ban" + maban, Toast.LENGTH_SHORT).show();
                ;break;

            case R.id.itXoa:
                boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
                if (kiemtra){
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathatbai), Toast.LENGTH_SHORT).show();
                }
                ;break;
        }
        return super.onContextItemSelected(item);
    }

    // code chức năng item thêm bàn ăn
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen == 1){
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.itthembanan);
            itThemBanAn.setIcon(R.drawable.ic_item_thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn, REQUEST_CODE_THEM);
                ;break;
        }
        return true;
    }

    public void HienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(), R.layout.custom_layout_hienthibanan, banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEM){
            if (resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem", false);
                if (kiemtra){
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == REQUEST_CODE_SUA){
            if (resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtra", false);
                if (kiemtra){
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
