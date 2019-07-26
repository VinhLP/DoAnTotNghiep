package com.example.restaurant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;

import com.example.restaurant.DTO.NhanVienDTO;
import com.example.restaurant.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    SQLiteDatabase database;

    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraNhanVien(){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan, null);
        if (cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public long ThemNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVienDTO.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMAQUYEN());

        long kiemtra = database.insert(CreateDatabase.TB_NHANVIEN, null, contentValues);
        return kiemtra;
    }

    public boolean SuaNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVienDTO.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMAQUYEN());

        long kiemtra = database.update(CreateDatabase.TB_NHANVIEN, contentValues, CreateDatabase.TB_NHANVIEN_MANV + " = " + nhanVienDTO.getMANV(), null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaNhanVienTheoMa(int manhanvien){
        long kiemtra = database.delete(CreateDatabase.TB_NHANVIEN, CreateDatabase.TB_NHANVIEN_MANV + " = " + manhanvien, null);

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public int KiemTraDangNhap(String tendangnhap, String matkhau){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_TENDN + " = '" + tendangnhap
                + "' AND " + CreateDatabase.TB_NHANVIEN_MATKHAU + " = '" + matkhau + "'";

        int manhanvien = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            manhanvien = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV));
            cursor.moveToNext();
        }

        return manhanvien;
    }

    public List<NhanVienDTO> LayDanhSachNhanVien(){
        List<NhanVienDTO> nhanVienDTOS = new ArrayList<NhanVienDTO>();

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();

            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVienDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN)));

            nhanVienDTOS.add(nhanVienDTO);
            cursor.moveToNext();
        }
        return nhanVienDTOS;
    }

    public NhanVienDTO LayDanhSachNhanVienTheoMa(int manhanvien){
        NhanVienDTO nhanVienDTO = new NhanVienDTO();

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manhanvien;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVienDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN)));

            cursor.moveToNext();
        }
        return nhanVienDTO;
    }

    public int LayQuyenNhanVien(int manv){
        int maquyen = 0;
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }
        return maquyen;
    }

}
