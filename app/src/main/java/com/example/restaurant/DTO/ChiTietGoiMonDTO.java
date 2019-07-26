package com.example.restaurant.DTO;

public class ChiTietGoiMonDTO {

    int MaMonAn, MaGoiMon, SoLuong, TongTienMonAn;
    String NgayThanhToan;

    public int getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        MaMonAn = maMonAn;
    }

    public int getMaGoiMon() {
        return MaGoiMon;
    }

    public void setMaGoiMon(int maGoiMon) {
        MaGoiMon = maGoiMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getTongTienMonAn() {
        return TongTienMonAn;
    }

    public void setTongTienMonAn(int tongTienMonAn) {
        TongTienMonAn = tongTienMonAn;
    }

    public String getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        NgayThanhToan = ngayThanhToan;
    }
}
