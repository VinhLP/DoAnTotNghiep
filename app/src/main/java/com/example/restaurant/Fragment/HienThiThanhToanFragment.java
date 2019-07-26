package com.example.restaurant.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.CustomAdapter.AdapterHienThiThanhToan;
import com.example.restaurant.DAO.BanAnDAO;
import com.example.restaurant.DAO.GoiMonDAO;
import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.DTO.GoiMonDTO;
import com.example.restaurant.R;
import com.example.restaurant.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HienThiThanhToanFragment extends Fragment implements  View.OnClickListener{

    GridView gridView;
    ImageButton btnThanhToanHD;
    TextView tvTongTien;

    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;
    List<GioHangDTO> gioHangDTOList;

    AdapterHienThiThanhToan adapterHienThiThanhToan;
    FragmentManager fragmentManager;

    long tongtien = 0;
    long tongtienmonan = 0;
    int maban;
    int magoimon;
    int soluong;
    int giatien;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithanhtoan, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thanhtoan);

        // tìm bên giao diện
        gridView = (GridView) view.findViewById(R.id.gvThanhToan);
        btnThanhToanHD = (ImageButton) view.findViewById(R.id.btnThanhToanHD);
        tvTongTien = (TextView) view.findViewById(R.id.tvTongTien);

        goiMonDAO = new GoiMonDAO(getActivity());
        banAnDAO = new BanAnDAO(getActivity());

        fragmentManager = getActivity().getSupportFragmentManager();

        // xử lý tính tổng tiền và cập nhật lại tình trạng bàn...
//        maban = getIntent().getIntExtra("maban", 0);
        Bundle bDuLieuThanhToan = getArguments();
        if (bDuLieuThanhToan != null){
            maban = bDuLieuThanhToan.getInt("maban");
            if (maban != 0){
                HienThiThanhToan();
                for (int i = 0; i<gioHangDTOList.size(); i++){
                    soluong = gioHangDTOList.get(i).getSoLuong();
                    giatien = gioHangDTOList.get(i).getGiaTien();

                    tongtienmonan = (soluong*giatien);
                    tongtien += (soluong*giatien);
                }
                tvTongTien.setText(getResources().getString(R.string.thanhtien) + tongtien);
            }
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthigiohang", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        // khai báo sự kiện click cho nút thanh toán hóa đơn...
        btnThanhToanHD.setOnClickListener(this);

        return view;
    }

    private void HienThiThanhToan(){
        magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        gioHangDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiThanhToan = new AdapterHienThiThanhToan(getActivity(), R.layout.custom_layout_hienthithanhtoan, gioHangDTOList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToanHD:

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                String ngaythanhtoan = dateFormat.format(calendar.getTime());

                ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
                chiTietGoiMonDTO.setMaGoiMon(magoimon);
                chiTietGoiMonDTO.setNgayThanhToan(ngaythanhtoan);
                chiTietGoiMonDTO.setTongTienMonAn((int) tongtienmonan);

                GoiMonDTO goiMonDTO = new GoiMonDTO();
                goiMonDTO.setMaGoiMon(magoimon);
                goiMonDTO.setTongTien((int) tongtien);

                boolean kiemtraban = banAnDAO.CapNhatLaiTinhTrangBan(maban, "false");
                boolean kiemtragoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");
                boolean kiemtra = goiMonDAO.ThanhToanHoaDon(chiTietGoiMonDTO);
                boolean kiemtrathanhtoan = goiMonDAO.CapNhatTongTienHoaDonKhiThanhToan(goiMonDTO);

                if (kiemtraban && kiemtragoimon){
                    if (kiemtra && kiemtrathanhtoan){
                        HienThiThanhToan();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.thanhthoanthanhcong), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.thanhtoanthatbai), Toast.LENGTH_SHORT).show();
                }

//                if (tongtien != 0){
//                    Calendar calendar = Calendar.getInstance();
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
//                    String ngaythanhtoan = dateFormat.format(calendar.getTime());
//
//                    ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
//                    chiTietGoiMonDTO.setMaGoiMon(magoimon);
//                    chiTietGoiMonDTO.setNgayThanhToan(ngaythanhtoan);
//                    chiTietGoiMonDTO.setTongTienMonAn((int) tongtienmonan);
//
//                    GoiMonDTO goiMonDTO = new GoiMonDTO();
//                    goiMonDTO.setMaGoiMon(magoimon);
//                    goiMonDTO.setTongTien((int) tongtien);
//
//                    boolean kiemtraban = banAnDAO.CapNhatLaiTinhTrangBan(maban, "false");
//                    boolean kiemtragoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");
//                    boolean kiemtra = goiMonDAO.ThanhToanHoaDon(chiTietGoiMonDTO);
//                    boolean kiemtrathanhtoan = goiMonDAO.CapNhatTongTienHoaDonKhiThanhToan(goiMonDTO);
//
//                    if (kiemtraban && kiemtragoimon){
//                        if (kiemtra && kiemtrathanhtoan){
//                            HienThiThanhToan();
//                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.thanhthoanthanhcong), Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//
//                    }
//                }

                // code chuyển trang từ ThanhToanActivity sang TrangChuActivity...
                Intent iTrangChu = new Intent(getActivity(), TrangChuActivity.class);
                iTrangChu.putExtra("maban", maban);
                startActivity(iTrangChu);

                ;break;
        }
    }
}
