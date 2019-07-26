package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurant.DTO.MonAnDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<MonAnDTO> monAnDTOList;
    ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;

    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> monAnDTOList){
        this.context = context;
        this.layout = layout;
        this.monAnDTOList = monAnDTOList;
    }

    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnDTOList.get(position).getMaMonAn();
    }

    public class ViewHolderHienThiDanhSachMonAn{
        ImageView imHienThiDSMonAn;
        TextView tvTenDSMonAn, tvGiaTienDSMonAn;
        CardView cvDSMonAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
            view = inflater.inflate(layout, parent, false);
            viewHolderHienThiDanhSachMonAn.imHienThiDSMonAn = (ImageView) view.findViewById(R.id.imHienThiDSMonAn);
            viewHolderHienThiDanhSachMonAn.tvTenDSMonAn = (TextView) view.findViewById(R.id.tvTenDSMonAn);
            viewHolderHienThiDanhSachMonAn.tvGiaTienDSMonAn = (TextView) view.findViewById(R.id.tvGiaTienDSMonAn);
            viewHolderHienThiDanhSachMonAn.cvDSMonAn = (CardView) view.findViewById(R.id.cvDSMonAn);

            view.setTag(viewHolderHienThiDanhSachMonAn);
        }else {
            viewHolderHienThiDanhSachMonAn = (ViewHolderHienThiDanhSachMonAn) view.getTag();
        }

        MonAnDTO monAnDTO = monAnDTOList.get(position);
        String hinhanh = monAnDTO.getHinhAnh().toString();
        if (hinhanh == null && !hinhanh.equals("")){
            viewHolderHienThiDanhSachMonAn.imHienThiDSMonAn.setImageResource(R.drawable.hinhmonan);
        }
        else{
            Uri uri = Uri.parse(hinhanh);
            viewHolderHienThiDanhSachMonAn.imHienThiDSMonAn.setImageURI(uri);
        }

        viewHolderHienThiDanhSachMonAn.tvTenDSMonAn.setText(monAnDTO.getTenMonAn());
        viewHolderHienThiDanhSachMonAn.tvGiaTienDSMonAn.setText(context.getResources().getString(R.string.gia) + monAnDTO.getGiaTien());

        return view;
    }
}
