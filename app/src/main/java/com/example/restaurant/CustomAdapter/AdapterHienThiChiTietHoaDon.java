package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.ChiTietHoaDonDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiChiTietHoaDon extends BaseAdapter {

    Context context;
    int layout;
    List<ChiTietHoaDonDTO> chiTietHoaDonDTOList;

    ViewHolderChiTietHoaDon viewHolderChiTietHoaDon;

    public AdapterHienThiChiTietHoaDon(Context context, int layout, List<ChiTietHoaDonDTO> chiTietHoaDonDTOList){
        this.context = context;
        this.layout = layout;
        this.chiTietHoaDonDTOList = chiTietHoaDonDTOList;
    }

    @Override
    public int getCount() {
        return chiTietHoaDonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return chiTietHoaDonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderChiTietHoaDon{
        TextView tvTenMonAnHoaDon, tvSoLuongMonAnHoaDon, tvGiaTienMonAnHoaDon;

        TextView tvTenMonAnGioHang;
        TextView tvGiaTienMonAnGioHang;
        TextView tvSoLuongGioHang;

        ImageView imHinhMonAnHoaDon;
        CardView cardView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolderChiTietHoaDon = new ViewHolderChiTietHoaDon();
            view = inflater.inflate(layout, parent, false);


            viewHolderChiTietHoaDon.tvTenMonAnGioHang = (TextView) view.findViewById(R.id.tvTenMonAnGioHang);
            viewHolderChiTietHoaDon.tvGiaTienMonAnGioHang = (TextView) view.findViewById(R.id.tvGiaTienMonAnGioHang);
            viewHolderChiTietHoaDon.tvSoLuongGioHang = (TextView) view.findViewById(R.id.tvSoLuongGioHang);

            viewHolderChiTietHoaDon.imHinhMonAnHoaDon = (ImageView) view.findViewById(R.id.imHinhMonAnHoaDon);
            viewHolderChiTietHoaDon.cardView = (CardView) view.findViewById(R.id.cardView);

            view.setTag(viewHolderChiTietHoaDon);
        }else {
            viewHolderChiTietHoaDon = (ViewHolderChiTietHoaDon) view.getTag();
        }

        ChiTietHoaDonDTO chiTietHoaDonDTO = chiTietHoaDonDTOList.get(position);


        return view;
    }
}
