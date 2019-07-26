package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurant.DTO.GoiMonDTO;
import com.example.restaurant.DTO.HoaDonTTDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiDanhSachHoaDon extends BaseAdapter {

    Context context;
    int layout;
    List<GoiMonDTO> goiMonDTOList;
//    List<HoaDonTTDTO> hoaDonTTDTOS;

    ViewHolderHoaDon viewHolderHoaDon;

    public AdapterHienThiDanhSachHoaDon(Context context, int layout, List<GoiMonDTO> goiMonDTOList){
        this.context = context;
        this.layout = layout;
        this.goiMonDTOList = goiMonDTOList;
    }
//    public AdapterHienThiDanhSachHoaDon(Context context, int layout, List<HoaDonTTDTO> hoaDonTTDTOS){
//        this.context = context;
//        this.layout = layout;
//        this.hoaDonTTDTOS = hoaDonTTDTOS;
//    }

    @Override
    public int getCount() {
        return goiMonDTOList.size();
    }
//    public int getCount() {
//    return hoaDonTTDTOS.size();
//}

    @Override
    public Object getItem(int position) {
        return goiMonDTOList.get(position);
    }
//    @Override
//    public Object getItem(int position) {
//        return hoaDonTTDTOS.get(position);
//    }

    @Override
    public long getItemId(int position) {
        return goiMonDTOList.get(position).getMaGoiMon();
    }
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }

    public class ViewHolderHoaDon{
        TextView tvNgayTTHD, tvMaHoaDon, tvTenBanTTHD, tvTenNhanVienTTHD, tvTongTienTTHD;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderHoaDon = new ViewHolderHoaDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderHoaDon.tvNgayTTHD = (TextView) view.findViewById(R.id.tvNgayTTHD);
            viewHolderHoaDon.tvMaHoaDon = (TextView) view.findViewById(R.id.tvMaHoaDon);
            viewHolderHoaDon.tvTenBanTTHD = (TextView) view.findViewById(R.id.tvTenBanTTHD);
            viewHolderHoaDon.tvTenNhanVienTTHD = (TextView) view.findViewById(R.id.tvTenNhanVienTTHD);
            viewHolderHoaDon.tvTongTienTTHD = (TextView) view.findViewById(R.id.tvTongTienTTHD);

            view.setTag(viewHolderHoaDon);
        }else {
            viewHolderHoaDon = (ViewHolderHoaDon) view.getTag();
        }

        GoiMonDTO goiMonDTO = goiMonDTOList.get(position);
        viewHolderHoaDon.tvMaHoaDon.setText(String.valueOf(goiMonDTO.getMaGoiMon()));
        viewHolderHoaDon.tvNgayTTHD.setText(goiMonDTO.getNgayGoi());
        viewHolderHoaDon.tvTenBanTTHD.setText(String.valueOf(goiMonDTO.getMaBan()));
        viewHolderHoaDon.tvTenNhanVienTTHD.setText(String.valueOf(goiMonDTO.getMaNV()));
        viewHolderHoaDon.tvTongTienTTHD.setText(String.valueOf(goiMonDTO.getTongTien()));

//        HoaDonTTDTO hoaDonTTDTO = hoaDonTTDTOS.get(position);
//        viewHolderHoaDon.tvNgayTTHD.setText(hoaDonTTDTO.getNgayThanhToan());
//        viewHolderHoaDon.tvTenBanTTHD.setText(String.valueOf(hoaDonTTDTO.getTenBanAnThanhToan()));
//        viewHolderHoaDon.tvTenNhanVienTTHD.setText(String.valueOf(hoaDonTTDTO.getNhanVienThanhToan()));
//        viewHolderHoaDon.tvTongTienTTHD.setText(String.valueOf(hoaDonTTDTO.getTongTienThanhToan()));

        return view;
    }
}
