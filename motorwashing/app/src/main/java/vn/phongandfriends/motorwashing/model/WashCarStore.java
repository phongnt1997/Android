package vn.phongandfriends.motorwashing.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class WashCarStore implements Cloneable, Parcelable, Serializable {
    private String name;
    private String address;
    private int imageResource;
    private double longtitude;
    private double lattitude;
    private float distance;
    private float star;
    private int sale;
    private String price;

    public WashCarStore(String name, String address, int imageResource, double longtitude, double lattitude) {
        this.name = name;
        this.address = address;
        this.imageResource = imageResource;
        this.longtitude = longtitude;
        this.lattitude = lattitude;
    }

    public WashCarStore(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.imageResource = in.readInt();
        this.longtitude = in.readDouble();
        this.lattitude = in.readDouble();
        this.star = in.readFloat();
        this.distance= in.readFloat();
        this.sale = in.readInt();
        this.price = in.readString();
    }

    public WashCarStore(String name, String address, int imageResource, double longtitude, double lattitude, float distance, float star, int sale, String price) {
        this.name = name;
        this.address = address;
        this.imageResource = imageResource;
        this.longtitude = longtitude;
        this.lattitude = lattitude;
        this.distance = distance;
        this.star = star;
        this.sale = sale;
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public WashCarStore setPrice(String price) {
        this.price = price;
        return this;
    }

    public WashCarStore setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public WashCarStore setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getImageResource() {
        return imageResource;
    }

    public WashCarStore setImageResource(int imageResource) {
        this.imageResource = imageResource;
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(imageResource);
        dest.writeDouble(lattitude);
        dest.writeDouble(longtitude);
        dest.writeFloat(star);
        dest.writeFloat(distance);
        dest.writeInt(sale);
        dest.writeString(price);
    }

    public static final Parcelable.Creator<WashCarStore> CREATOR = new Parcelable.Creator<WashCarStore>() {

        @Override
        public WashCarStore createFromParcel(Parcel source) {
            return new WashCarStore(source);
        }

        @Override
        public WashCarStore[] newArray(int size) {
            return new WashCarStore[0];
        }
    };
}
