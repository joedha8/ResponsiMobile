package com.gmail.yudhapratama539.responsimobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.yudhapratama539.responsimobile.model.Whislist;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.NoteColumns.DATE;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.NoteColumns.HARGA;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.NoteColumns.KEINGINAN;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.TABLE_NOTE;

public class WhislistHelper {
    private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    public WhislistHelper(Context context){
        this.context = context;
    }

    public WhislistHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Whislist> query(){
        ArrayList<Whislist> arrayList = new ArrayList<Whislist>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,_ID +" DESC"
                ,null);
        cursor.moveToFirst();
        Whislist whislist;
        if (cursor.getCount()>0) {
            do {
                whislist = new Whislist();
                whislist.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                whislist.setKeinginan(cursor.getString(cursor.getColumnIndexOrThrow(KEINGINAN)));
                whislist.setHarga(cursor.getString(cursor.getColumnIndexOrThrow(HARGA)));
                whislist.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(whislist);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Whislist whislist){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(KEINGINAN, whislist.getKeinginan());
        initialValues.put(HARGA, whislist.getHarga());
        initialValues.put(DATE, whislist.getDate());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Whislist whislist){
        ContentValues args = new ContentValues();
        args.put(KEINGINAN, whislist.getKeinginan());
        args.put(HARGA, whislist.getHarga());
        args.put(DATE, whislist.getDate());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + whislist.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_NOTE, _ID + " = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}