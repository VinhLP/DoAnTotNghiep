package com.example.restaurant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import com.example.restaurant.DTO.ChiTietGoiMonDTO;
import com.example.restaurant.DTO.GioHangDTO;
import com.example.restaurant.DTO.GoiMonDTO;
import com.example.restaurant.DTO.HoaDonTTDTO;
import com.example.restaurant.DTO.ThanhToanDTO;
import com.example.restaurant.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class GoiMonDAO {
    SQLiteDatabase database;

    public GoiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV, goiMonDTO.getMaNV());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMonDTO.getTinhTrang());

        long magoimon = database.insert(CreateDatabase.TB_GOIMON, null, contentValues);

        return magoimon;
    }

    public long LayMaGoiMonTheoMaBan(int maban, String tinhtrang){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = '" + maban + "' AND "
                + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhtrang + "' ";

        long magoimon = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));

            cursor.moveToNext();
        }
        return magoimon;
    }

    public boolean KiemTraMonAnDaTonTai(int magoimon, int mamonan){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        if (cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public int LaySoLuongMonAnTheoMaGoiMon(int magoimon, int mamonan){
        int soluong = 0;
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));

            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());

        long kiemtra = database.update(CreateDatabase.TB_CHITIETGOIMON, contentValues,CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon()
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMaMonAn(), null);

        if (kiemtra != 0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThemChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMonDTO.getMaMonAn());

        long kiemtra = database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);

        if (kiemtra != 0 ){
            return true;
        }else {
            return false;
        }
    }

    public List<GioHangDTO> LayDanhSachMonAnTheoMaGoiMon(int magoimon){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct, " + CreateDatabase.TB_MONAN + " ma WHERE "
                + " ct. " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma. " + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon + "' ";

        List<GioHangDTO> gioHangDTOS = new ArrayList<GioHangDTO>();
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            GioHangDTO gioHangDTO = new GioHangDTO();
            gioHangDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            gioHangDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            gioHangDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            gioHangDTOS.add(gioHangDTO);
            cursor.moveToNext();
        }
        return gioHangDTOS;
    }

//    public List<GioHangDTO> LayDanhSachMonAnTheoMaMonAn(int mamonan){
//        String truyvan = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct, " + CreateDatabase.TB_MONAN + " ma WHERE "
//                + " ct. " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma. " + CreateDatabase.TB_MONAN_MAMON
//                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = '" + mamonan + "' ";
//
//        List<GioHangDTO> gioHangDTOS = new ArrayList<GioHangDTO>();
//        Cursor cursor = database.rawQuery(truyvan, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            GioHangDTO gioHangDTO = new GioHangDTO();
//            gioHangDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
//            gioHangDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
//            gioHangDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
//
//            gioHangDTOS.add(gioHangDTO);
//            cursor.moveToNext();
//        }
//        return gioHangDTOS;
//    }

    public boolean CapNhatTrangThaiGoiMonTheoMaBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, maban);

        long kiemtra = database.update(CreateDatabase.TB_GOIMON, contentValues, CreateDatabase.TB_GOIMON_MABAN + " = '" + maban + "' ", null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThanhToanHoaDon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_NGAYTHANHTOAN, chiTietGoiMonDTO.getNgayThanhToan());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_TONGTIENMONAN, chiTietGoiMonDTO.getTongTienMonAn());

        long kiemtra = database.update(CreateDatabase.TB_CHITIETGOIMON, contentValues,CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon(), null);
        if (kiemtra != 0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean CapNhatTongTienHoaDonKhiThanhToan(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TONGTIEN, goiMonDTO.getTongTien());

        long kiemtrathanhtoan = database.update(CreateDatabase.TB_GOIMON, contentValues, CreateDatabase.TB_GOIMON_MAGOIMON + " = " + goiMonDTO.getMaGoiMon(), null);
        if (kiemtrathanhtoan != 0 ){
            return true;
        }else {
            return false;
        }
    }

    public List<GoiMonDTO> LayDanhSachGoiMon(){
        List<GoiMonDTO> goiMonDTOS = new ArrayList<GoiMonDTO>();

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_GOIMON;

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            GoiMonDTO goiMonDTO = new GoiMonDTO();

            goiMonDTO.setMaGoiMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON)));
            goiMonDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MABAN)));
            goiMonDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MANV)));
            goiMonDTO.setNgayGoi(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_NGAYGOI)));
            goiMonDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_TINHTRANG)));
            goiMonDTO.setTongTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_TONGTIEN)));

            goiMonDTOS.add(goiMonDTO);
            cursor.moveToNext();
        }
        return goiMonDTOS;
    }

//    public List<GioHangDTO> LayDanhSachMonAnTheoMaGoiMon(int magoimon) {
//        String truyvan = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct, " + CreateDatabase.TB_MONAN + " ma WHERE "
//                + " ct. " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma. " + CreateDatabase.TB_MONAN_MAMON
//                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon + "' ";
//
//        List<GioHangDTO> gioHangDTOS = new ArrayList<GioHangDTO>();
//        Cursor cursor = database.rawQuery(truyvan, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            GioHangDTO gioHangDTO = new GioHangDTO();
//            gioHangDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
//            gioHangDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
//            gioHangDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
//
//            gioHangDTOS.add(gioHangDTO);
//            cursor.moveToNext();
//        }
//        return gioHangDTOS;
//    }


//    public List<HoaDonTTDTO> LayDanhSachGoiMon(int magoimon){
//        String truyvan = " SELECT * FROM " + CreateDatabase.TB_GOIMON + " gm, " + CreateDatabase.TB_BANAN + " ba, " + CreateDatabase.TB_NHANVIEN + " nv WHERE "
//                + " gm. " + CreateDatabase.TB_GOIMON_MABAN + " = ba. " + CreateDatabase.TB_BANAN_MABAN + " AND gm. " + CreateDatabase.TB_GOIMON_MANV + " = nv. " + CreateDatabase.TB_NHANVIEN_MANV
//                + " AND " + CreateDatabase.TB_GOIMON_MAGOIMON + " = '" + magoimon + "' ";
//
//
//        List<HoaDonTTDTO> hoaDonTTDTOS = new ArrayList<HoaDonTTDTO>();
//        Cursor cursor = database.rawQuery(truyvan, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            HoaDonTTDTO hoaDonTTDTO = new HoaDonTTDTO();
//            hoaDonTTDTO.setTenBanAnThanhToan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));
//            hoaDonTTDTO.setNhanVienThanhToan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
//            hoaDonTTDTO.setNgayThanhToan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_NGAYGOI)));
//            hoaDonTTDTO.setTongTienThanhToan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_TONGTIEN)));
//
//            hoaDonTTDTOS.add(hoaDonTTDTO);
//            cursor.moveToNext();
//        }
//        return hoaDonTTDTOS;
//    }

}
