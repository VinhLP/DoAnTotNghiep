package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurant.DTO.NhanVienDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiNhanVien extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVienDTO> nhanVienDTOList;
    ViewHolerNhanVien viewHolerNhanVien;

    public AdapterHienThiNhanVien(Context context, int layout, List<NhanVienDTO> nhanVienDTOList){
        this.context = context;
        this.layout = layout;
        this.nhanVienDTOList = nhanVienDTOList;
    }


    @Override
    public int getCount() {
        return nhanVienDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanVienDTOList.get(position).getMANV();
    }

    public class ViewHolerNhanVien{
        ImageView imHinhNhanVien;
        TextView tvTenNhanVien, tvCMND;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolerNhanVien = new ViewHolerNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolerNhanVien.imHinhNhanVien = (ImageView) view.findViewById(R.id.imHinhNhanVien);
            viewHolerNhanVien.tvTenNhanVien = (TextView) view.findViewById(R.id.tvTenNhanVien);
            viewHolerNhanVien.tvCMND = (TextView) view.findViewById(R.id.tvCMND);

            view.setTag(viewHolerNhanVien);
        }else {
            viewHolerNhanVien = (ViewHolerNhanVien) view.getTag();
        }

        NhanVienDTO nhanVienDTO = nhanVienDTOList.get(position);

        viewHolerNhanVien.tvTenNhanVien.setText(nhanVienDTO.getTENDN());
        viewHolerNhanVien.tvCMND.setText(String.valueOf(nhanVienDTO.getCMND()));

        return view;
    }
}
