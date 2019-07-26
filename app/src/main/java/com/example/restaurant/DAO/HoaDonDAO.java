package com.example.restaurant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant.DTO.ChiTietHoaDonDTO;
import com.example.restaurant.DTO.HoaDonDTO;
import com.example.restaurant.Database.CreateDatabase;

public class HoaDonDAO {

    SQLiteDatabase database;

    public HoaDonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemHoaDon(HoaDonDTO hoaDonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_HOADON_MABAN, hoaDonDTO.getMABAN());
        contentValues.put(CreateDatabase.TB_HOADON_MANV, hoaDonDTO.getMANV());
        contentValues.put(CreateDatabase.TB_HOADON_NGAYTHANHTOAN, hoaDonDTO.getNGAYTHANHTOAN());
        contentValues.put(CreateDatabase.TB_HOADON_TONGTIEN, hoaDonDTO.getTONGTIEN());

        long mahoadon = database.insert(CreateDatabase.TB_HOADON, null, contentValues);

        return mahoadon;
    }

}
