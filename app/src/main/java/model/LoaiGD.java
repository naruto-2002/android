package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class LoaiGD implements Parcelable {
    private int ID,srcIcon;
    private String nameIcon;
    public LoaiGD( ) {
    }
    public LoaiGD( int ID, int srcIcon, String nameIcon) {
        this.srcIcon = srcIcon;
        this.ID = ID;
        this.nameIcon = nameIcon;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSrcIcon() {
        return srcIcon;
    }

    public void setSrcIcon(int srcIcon) {
        this.srcIcon = srcIcon;
    }

    public String getNameIcon() {
        return nameIcon;
    }

    public void setNameIcon(String nameIcon) {
        this.nameIcon = nameIcon;
    }

    protected LoaiGD(Parcel in) {
        ID = in.readInt();
        srcIcon = in.readInt();
        nameIcon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(srcIcon);
        dest.writeString(nameIcon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoaiGD> CREATOR = new Creator<LoaiGD>() {
        @Override
        public LoaiGD createFromParcel(Parcel in) {
            return new LoaiGD(in);
        }

        @Override
        public LoaiGD[] newArray(int size) {
            return new LoaiGD[size];
        }
    };
}
