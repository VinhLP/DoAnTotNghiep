package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.DAO.BanAnDAO;
import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.BanAnDTO;
import com.example.restaurant.DTO.GoiMonDTO;
import com.example.restaurant.Fragment.HienThiGioHangFragment;
import com.example.restaurant.R;
import com.example.restaurant.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {

    Context context;
    int layout;
    List<BanAnDTO> banAnDTOList;
    ViewHolderBanAn viewHolderBanAn;

    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;

    FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;

        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }

    public class ViewHolderBanAn{
        TextView tvTenBanAn;
        ImageView imChuaGoiMon;
        CardView cvBanAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan, parent, false);
            viewHolderBanAn.tvTenBanAn = (TextView) view.findViewById(R.id.tvTenBanAn);
            viewHolderBanAn.imChuaGoiMon = (ImageView) view.findViewById(R.id.imChuaGoiMon);
            viewHolderBanAn.cvBanAn = (CardView) view.findViewById(R.id.cvBanAn);

            view.setTag(viewHolderBanAn);

        }else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        BanAnDTO banAnDTO = banAnDTOList.get(position);

        String kiemtratinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        if (kiemtratinhtrang.equals("true")){
            viewHolderBanAn.imChuaGoiMon.setImageResource(R.drawable.ic_banan_dagoimon);
        }else {
            viewHolderBanAn.imChuaGoiMon.setImageResource(R.drawable.ic_banan_chuagoimon);
        }

        viewHolderBanAn.tvTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.cvBanAn.setTag(position);

        viewHolderBanAn.cvBanAn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        int vitri = (int) viewHolderBanAn.cvBanAn.getTag();
        int maban = banAnDTOList.get(vitri).getMaBan();
        String tenban = viewHolderBanAn.tvTenBanAn.getText().toString();

        switch (id){
            case R.id.cvBanAn:
                Intent layITrangChu = ((TrangChuActivity)context).getIntent();
                int manhanvien = layITrangChu.getIntExtra("manhanvien", 0);

                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);

                if (tinhtrang.equals("false")){
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngaygoi = dateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maban);
                    goiMonDTO.setMaNV(manhanvien);
                    goiMonDTO.setNgayGoi(ngaygoi);
                    goiMonDTO.setTinhTrang("false");

                    long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                    banAnDAO.CapNhatLaiTinhTrangBan(maban, "true");
                    if (kiemtra == 0){
                        Toast.makeText(context, context.getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    }
                }

                Bundle bDuLieuThucDon = new Bundle();
                bDuLieuThucDon.putInt("maban", maban);

                HienThiGioHangFragment hienThiGioHangFragment = new HienThiGioHangFragment();
                hienThiGioHangFragment.setArguments(bDuLieuThucDon);

                FragmentTransaction tranGioHangTransaction = fragmentManager.beginTransaction();
                tranGioHangTransaction.replace(R.id.content, hienThiGioHangFragment).addToBackStack("hienthibanan");
                tranGioHangTransaction.commit();
                break;
        }
    }
}
