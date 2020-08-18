package com.jzd.jzshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LWH
 * @description:
 * @date :2019/12/12 15:39
 */
public class LocalMediaEntity implements Parcelable {
    /**
     * original path
     */
    private String path;
    /**
     * # Check the original button to get the return value
     * original path
     */
    private String originalPath;
    /**
     * compress path
     */
    private String compressPath;
    /**
     * cut path
     */
    private String cutPath;

    /**
     * Note: this field is only returned in Android Q version
     * <p>
     * Android Q image or video path
     */
    private String androidQToPath;
    /**
     * video duration
     */
    private long duration;
    /**
     * If the selected
     */
    private boolean isChecked;
    /**
     * If the cut
     */
    private boolean isCut;
    /**
     * media position of list
     */
    public int position;
    /**
     * The media number of qq choose styles
     */
    private int num;
    /**
     * The media resource type
     */
    private String mimeType;

    /**
     * Gallery selection mode
     */
    private int chooseModel;

    /**
     * If the compressed
     */
    private boolean compressed;
    /**
     * image or video width
     */
    private int width;
    /**
     * image or video height
     */
    private int height;

    /**
     * file size
     */
    private long size;

    /**
     * Whether the original image is displayed
     */
    private boolean isOriginal;


    protected LocalMediaEntity(Parcel in) {
        path = in.readString();
        originalPath = in.readString();
        compressPath = in.readString();
        cutPath = in.readString();
        androidQToPath = in.readString();
        duration = in.readLong();
        isChecked = in.readByte() != 0;
        isCut = in.readByte() != 0;
        position = in.readInt();
        num = in.readInt();
        mimeType = in.readString();
        chooseModel = in.readInt();
        compressed = in.readByte() != 0;
        width = in.readInt();
        height = in.readInt();
        size = in.readLong();
        isOriginal = in.readByte() != 0;
    }

    public static final Creator<LocalMediaEntity> CREATOR = new Creator<LocalMediaEntity>() {
        @Override
        public LocalMediaEntity createFromParcel(Parcel in) {
            return new LocalMediaEntity(in);
        }

        @Override
        public LocalMediaEntity[] newArray(int size) {
            return new LocalMediaEntity[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public String getCutPath() {
        return cutPath;
    }

    public void setCutPath(String cutPath) {
        this.cutPath = cutPath;
    }

    public String getAndroidQToPath() {
        return androidQToPath;
    }

    public void setAndroidQToPath(String androidQToPath) {
        this.androidQToPath = androidQToPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCut() {
        return isCut;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getChooseModel() {
        return chooseModel;
    }

    public void setChooseModel(int chooseModel) {
        this.chooseModel = chooseModel;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(originalPath);
        dest.writeString(compressPath);
        dest.writeString(cutPath);
        dest.writeString(androidQToPath);
        dest.writeLong(duration);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isCut ? 1 : 0));
        dest.writeInt(position);
        dest.writeInt(num);
        dest.writeString(mimeType);
        dest.writeInt(chooseModel);
        dest.writeByte((byte) (compressed ? 1 : 0));
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeLong(size);
        dest.writeByte((byte) (isOriginal ? 1 : 0));
    }
}
