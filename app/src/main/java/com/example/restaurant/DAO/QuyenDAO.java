package com.example.restaurant.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant.DTO.QuyenDTO;
import com.example.restaurant.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {
    SQLiteDatabase database;

    public QuyenDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN, tenquyen);
        database.insert(CreateDatabase.TB_QUYEN, null, contentValues);
    }

    public List<QuyenDTO> LayDanhSachQuyen(){
        List<QuyenDTO> quyenDTOS = new ArrayList<QuyenDTO>();
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_QUYEN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            QuyenDTO quyenDTO = new QuyenDTO();
            quyenDTO.setMaQuyen(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_MAQUYEN)));
            quyenDTO.setTenQuyen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_TENQUYEN)));

            quyenDTOS.add(quyenDTO);
            cursor.moveToNext();
        }
        return quyenDTOS;
    }

}
