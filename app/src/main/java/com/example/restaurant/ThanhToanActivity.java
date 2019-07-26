package com.example.restaurant;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    ImageButton btnThanhToanHD;
    TextView tvTongTien;

    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;
    List<GioHangDTO> gioHangDTOList;

    AdapterHienThiThanhToan adapterHienThiThanhToan;

    long tongtien = 0;
    int maban;
    int magoimon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthithanhtoan);

        // tìm bên giao diện
        gridView = (GridView) findViewById(R.id.gvThanhToan);
        btnThanhToanHD = (ImageButton) findViewById(R.id.btnThanhToanHD);
        tvTongTien = (TextView) findViewById(R.id.tvTongTien);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        // xử lý tính tổng tiền và cập nhật lại tình trạng bàn...
        maban = getIntent().getIntExtra("maban", 0);
        if (maban != 0){
            HienThiThanhToan();
            for (int i = 0; i<gioHangDTOList.size(); i++){
                int soluong = gioHangDTOList.get(i).getSoLuong();
                int giatien = gioHangDTOList.get(i).getGiaTien();

                tongtien += (soluong*giatien);
            }
            tvTongTien.setText(getResources().getString(R.string.thanhtien) + tongtien);
        }

        // khai báo sự kiện click cho nút thanh toán hóa đơn...
        btnThanhToanHD.setOnClickListener(this);
    }

    private void HienThiThanhToan(){
        magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        gioHangDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this, R.layout.custom_layout_hienthithanhtoan, gioHangDTOList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            // xử lý nút thanh toán...
            case R.id.btnThanhToanHD:

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                String ngaythanhtoan = dateFormat.format(calendar.getTime());

                ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
                chiTietGoiMonDTO.setNgayThanhToan(ngaythanhtoan);

                boolean kiemtraban = banAnDAO.CapNhatLaiTinhTrangBan(maban, "false");
                boolean kiemtragoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");
                boolean kiemtra = goiMonDAO.ThanhToanHoaDon(chiTietGoiMonDTO);

                if (kiemtraban && kiemtragoimon && kiemtra){
                    HienThiThanhToan();
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhthoanthanhcong), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhtoanthatbai), Toast.LENGTH_SHORT).show();
                }

                // code chuyển trang từ ThanhToanActivity sang TrangChuActivity...
                Intent iTrangChu = new Intent(ThanhToanActivity.this, TrangChuActivity.class);
                iTrangChu.putExtra("maban", maban);
                startActivity(iTrangChu);

                ;break;
        }
    }
}
