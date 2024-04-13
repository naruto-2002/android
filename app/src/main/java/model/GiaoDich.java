package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

public class GiaoDich implements Parcelable {
    private int ID,kieugd;
    private Date date;
    private float sotien;
    private LoaiGD loaiGD;
    private String mota;

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getKieugd() {
        return kieugd;
    }

    public void setKieugd(int kieugd) {
        this.kieugd = kieugd;
    }

    public GiaoDich(int ID, Date date, float sotien, LoaiGD loaiGD, String mota, int kieugd) {
        this.ID = ID;
        this.date = date;
        this.sotien = sotien;
        this.loaiGD = loaiGD;
        this.mota = mota;
        this.kieugd = kieugd;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getSotien() {
        return sotien;
    }

    public void setSotien(float sotien) {
        this.sotien = sotien;
    }

    public LoaiGD getLoaiGD() {
        return loaiGD;
    }

    public void setLoaiGD(LoaiGD loaiGD) {
        this.loaiGD = loaiGD;
    }

    protected GiaoDich(Parcel in) {
        ID = in.readInt();
        date = new Date(in.readLong());
        sotien = in.readFloat();
        mota = in.readString();
        kieugd = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeLong(date.getTime());
        dest.writeFloat(sotien);
        dest.writeString(mota);
        dest.writeInt(kieugd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<GiaoDich> CREATOR = new Parcelable.Creator<GiaoDich>() {
        @Override
        public GiaoDich createFromParcel(Parcel in) {
            return new GiaoDich(in);
        }

        @Override
        public GiaoDich[] newArray(int size) {
            return new GiaoDich[size];
        }
    };
}
