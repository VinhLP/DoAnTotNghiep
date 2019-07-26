package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiGioHang extends BaseAdapter {

    Context context;
    int layout;
    List<GioHangDTO> gioHangDTOS;
    ViewHolderGioHang viewHolderGioHang;

    public AdapterHienThiGioHang (Context context, int layout, List<GioHangDTO> gioHangDTOS){
        this.context = context;
        this.layout = layout;
        this.gioHangDTOS = gioHangDTOS;
    }

    @Override
    public int getCount() {
        return gioHangDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderGioHang{
        TextView tvTenMonAnGioHang;
        TextView tvGiaTienMonAnGioHang;
        TextView tvSoLuongGioHang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderGioHang = new ViewHolderGioHang();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderGioHang.tvTenMonAnGioHang = (TextView) view.findViewById(R.id.tvTenMonAnGioHang);
            viewHolderGioHang.tvGiaTienMonAnGioHang = (TextView) view.findViewById(R.id.tvGiaTienMonAnGioHang);
            viewHolderGioHang.tvSoLuongGioHang = (TextView) view.findViewById(R.id.tvSoLuongGioHang);

            view.setTag(viewHolderGioHang);
        }else {
            viewHolderGioHang = (ViewHolderGioHang) view.getTag();
        }

        GioHangDTO gioHangDTO = gioHangDTOS.get(position);
        viewHolderGioHang.tvTenMonAnGioHang.setText(gioHangDTO.getTenMonAn());
        viewHolderGioHang.tvGiaTienMonAnGioHang.setText(String.valueOf(gioHangDTO.getGiaTien()));
        viewHolderGioHang.tvSoLuongGioHang.setText(String.valueOf(gioHangDTO.getSoLuong()));

        return view;
    }
}
