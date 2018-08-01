package com.gmail.yudhapratama539.responsimobile.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.yudhapratama539.responsimobile.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.getColumnInt;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.getColumnString;

public class Whislist implements Parcelable {
    private int id;
    private String keinginan;
    private String harga;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeinginan() {
        return keinginan;
    }

    public void setKeinginan(String keinginan) {
        this.keinginan = keinginan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.keinginan);
        dest.writeString(this.harga);
        dest.writeString(this.date);
    }

    public Whislist() {

    }

    public Whislist(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.keinginan = getColumnString(cursor, DatabaseContract.NoteColumns.KEINGINAN);
        this.harga = getColumnString(cursor, DatabaseContract.NoteColumns.HARGA);
        this.date = getColumnString(cursor, DatabaseContract.NoteColumns.DATE);
    }

    protected Whislist(Parcel in) {
        this.id = in.readInt();
        this.keinginan = in.readString();
        this.harga = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Whislist> CREATOR = new Parcelable.Creator<Whislist>() {
        @Override
        public Whislist createFromParcel(Parcel source) {
            return new Whislist(source);
        }

        @Override
        public Whislist[] newArray(int size) {
            return new Whislist[size];
        }
    };
}