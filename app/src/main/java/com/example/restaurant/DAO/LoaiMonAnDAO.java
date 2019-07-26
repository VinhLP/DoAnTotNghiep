package com.example.restaurant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant.DTO.LoaiMonAnDTO;
import com.example.restaurant.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {
    SQLiteDatabase database;

    public LoaiMonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMonAn(String tenloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);

        long kiemtra = database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiMonAnTheoMa(int maloai){
        long kiemtra = database.delete(CreateDatabase.TB_LOAIMONAN, CreateDatabase.TB_LOAIMONAN_MALOAI + " = " + maloai, null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn(){
        List<LoaiMonAnDTO> loaiMonAnDTOS = new ArrayList<LoaiMonAnDTO>();

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOS.add(loaiMonAnDTO);

            cursor.moveToNext();
        }
        return loaiMonAnDTOS;
    }

    public String LayHinhLoaiMonAn(int maloai){
        String hinhanh = "";

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = ' " + maloai + " ' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1";
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        return hinhanh;
    }

    public boolean CapNhatLaiTenLoaiMonAn(int maloai, String tenloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);

        long kiemtra = database.update(CreateDatabase.TB_LOAIMONAN, contentValues, CreateDatabase.TB_LOAIMONAN_MALOAI + " = '" + maloai + "' ", null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public LoaiMonAnDTO LayDanhSachLoaiMonAnTheoMa(int maloai){
        LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_LOAIMONAN + " WHERE " + CreateDatabase.TB_LOAIMONAN_MALOAI + " = " + maloai;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));
            cursor.moveToNext();
        }
        return loaiMonAnDTO;
    }
}
