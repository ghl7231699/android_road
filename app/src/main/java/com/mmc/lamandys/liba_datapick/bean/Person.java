package com.mmc.lamandys.liba_datapick.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    String name;

    public Person() {
    }

    public Person(Parcel in) {
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
