package com.jzd.jzshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ReceiptAddressEntity implements Parcelable {
    private String name;
    private String addressName;
    private String phone;
    private boolean isDefault;
    public ReceiptAddressEntity() {
    }

    protected ReceiptAddressEntity(Parcel in) {
        name = in.readString();
        addressName = in.readString();
        phone = in.readString();
        isDefault = in.readByte() != 0;
    }

    public static final Creator<ReceiptAddressEntity> CREATOR = new Creator<ReceiptAddressEntity>() {
        @Override
        public ReceiptAddressEntity createFromParcel(Parcel in) {
            return new ReceiptAddressEntity(in);
        }

        @Override
        public ReceiptAddressEntity[] newArray(int size) {
            return new ReceiptAddressEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(addressName);
        dest.writeString(phone);
        dest.writeByte((byte) (isDefault ? 1 : 0));
    }
}
