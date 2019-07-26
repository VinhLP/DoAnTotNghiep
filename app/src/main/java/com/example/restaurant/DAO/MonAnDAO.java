package com.example.restaurant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.restaurant.DTO.MonAnDTO;
import com.example.restaurant.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    SQLiteDatabase database;

    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAnDTO.getHinhAnh());

        long kiemtra = database.insert(CreateDatabase.TB_MONAN, null, contentValues);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAnDTO.getHinhAnh());

        long kiemtra = database.update(CreateDatabase.TB_MONAN, contentValues, CreateDatabase.TB_MONAN_MAMON + " = " + monAnDTO.getMaMonAn(), null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMonAnTheoMa(int mamonan){
        long kiemtra = database.delete(CreateDatabase.TB_MONAN, CreateDatabase.TB_MONAN_MAMON + " = " + mamonan, null);

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMonAnTheoMaLoaiMonAn(int maloai){
        long kiemtra = database.delete(CreateDatabase.TB_MONAN, CreateDatabase.TB_MONAN_MALOAI + " = " + maloai, null);

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<MonAnDTO> LayDanhSachMonAnTheoLoai(int maloai){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' ";

        List<MonAnDTO> monAnDTOS = new ArrayList<MonAnDTO>();
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();

            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)) + "");
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOS.add(monAnDTO);
            cursor.moveToNext();
        }
        return monAnDTOS;
    }

    public MonAnDTO LayDanhSachMonAnTheoMa(int mamonan){
        MonAnDTO monAnDTO = new MonAnDTO();

        String truyvan = " SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MAMON + " = " + mamonan;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)) + "");
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            cursor.moveToNext();
        }
        return monAnDTO;
    }
}
