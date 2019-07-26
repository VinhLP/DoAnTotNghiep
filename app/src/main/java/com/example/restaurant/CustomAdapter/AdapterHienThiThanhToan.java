package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.DTO.ThanhToanDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter {

    Context context;
    int layout;
    ViewHolderThanhToan viewHolderThanhToan;
    List<GioHangDTO> gioHangDTOS;

    public AdapterHienThiThanhToan(Context context, int layout, List<GioHangDTO> gioHangDTOS){
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

    public class ViewHolderThanhToan{
        TextView tvTenMonAnThanhToan;
        TextView tvSoLuongThanhToan;
        TextView tvGiaTienThanhToan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderThanhToan.tvTenMonAnThanhToan = (TextView) view.findViewById(R.id.tvTenMonAnThanhToan);
            viewHolderThanhToan.tvSoLuongThanhToan = (TextView) view.findViewById(R.id.tvSoLuongThanhToan);
            viewHolderThanhToan.tvGiaTienThanhToan = (TextView) view.findViewById(R.id.tvGiaTienThanhToan);

            view.setTag(viewHolderThanhToan);
        }else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        GioHangDTO gioHangDTO = gioHangDTOS.get(position);
        viewHolderThanhToan.tvTenMonAnThanhToan.setText(gioHangDTO.getTenMonAn());
        viewHolderThanhToan.tvSoLuongThanhToan.setText(String.valueOf(gioHangDTO.getSoLuong()));
        viewHolderThanhToan.tvGiaTienThanhToan.setText(String.valueOf(gioHangDTO.getGiaTien()));

        return view;
    }
}