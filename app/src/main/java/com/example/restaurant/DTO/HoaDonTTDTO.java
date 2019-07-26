package com.example.restaurant.DTO;

public class HoaDonTTDTO {

    String NgayThanhToan, TenBanAnThanhToan, NhanVienThanhToan;
    int TongTienThanhToan;

    public String getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        NgayThanhToan = ngayThanhToan;
    }

    public String getTenBanAnThanhToan() {
        return TenBanAnThanhToan;
    }

    public void setTenBanAnThanhToan(String tenBanAnThanhToan) {
        TenBanAnThanhToan = tenBanAnThanhToan;
    }

    public String getNhanVienThanhToan() {
        return NhanVienThanhToan;
    }

    public void setNhanVienThanhToan(String nhanVienThanhToan) {
        NhanVienThanhToan = nhanVienThanhToan;
    }

    public int getTongTienThanhToan() {
        return TongTienThanhToan;
    }

    public void setTongTienThanhToan(int tongTienThanhToan) {
        TongTienThanhToan = tongTienThanhToan;
    }
}
