package com.example.restaurant.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurant.DTO.NhanVienDTO;
import com.example.restaurant.R;

import java.util.List;

public class AdapterHienThiThongTinCaNhan extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVienDTO> nhanVienDTOList;

    ViewHolderHienThiThongTinCaNhan viewHolderHienThiThongTinCaNhan;

    public AdapterHienThiThongTinCaNhan (Context context, int layout, List<NhanVienDTO> nhanVienDTOList){
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

    public class ViewHolderHienThiThongTinCaNhan{
        TextView tvTenDangNhap, tvMatKhau, tvGioiTinh, tvNgaySinh, tvCMND;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderHienThiThongTinCaNhan = new ViewHolderHienThiThongTinCaNhan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderHienThiThongTinCaNhan.tvTenDangNhap = (TextView) view.findViewById(R.id.tvTenDangNhap);
            viewHolderHienThiThongTinCaNhan.tvMatKhau = (TextView) view.findViewById(R.id.tvMatKhau);
            viewHolderHienThiThongTinCaNhan.tvGioiTinh = (TextView) view.findViewById(R.id.tvGioiTinh);
            viewHolderHienThiThongTinCaNhan.tvNgaySinh = (TextView) view.findViewById(R.id.tvNgaySinh);
            viewHolderHienThiThongTinCaNhan.tvCMND = (TextView) view.findViewById(R.id.tvCMND);

            view.setTag(viewHolderHienThiThongTinCaNhan);
        }else {
            viewHolderHienThiThongTinCaNhan = (ViewHolderHienThiThongTinCaNhan) view.getTag();
        }

        NhanVienDTO nhanVienDTO = nhanVienDTOList.get(position);
        viewHolderHienThiThongTinCaNhan.tvTenDangNhap.setText(nhanVienDTO.getTENDN());
        viewHolderHienThiThongTinCaNhan.tvMatKhau.setText(nhanVienDTO.getMATKHAU());
        viewHolderHienThiThongTinCaNhan.tvGioiTinh.setText(nhanVienDTO.getGIOITINH());
        viewHolderHienThiThongTinCaNhan.tvNgaySinh.setText(nhanVienDTO.getNGAYSINH());
        viewHolderHienThiThongTinCaNhan.tvCMND.setText(nhanVienDTO.getCMND());

        return view;
    }
}
