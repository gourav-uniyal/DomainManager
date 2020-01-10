package com.android.testmessenger.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "verification")
public class Verification {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "device_id")
    private String deviceId;
    @ColumnInfo(name = "device_model")
    private String deviceModel;
    @ColumnInfo(name = "key")
    private String key;
    @ColumnInfo(name = "device_version")
    private String deviceVersion;
    @ColumnInfo(name = "device_serial")
    private String deviceSerial;
    @ColumnInfo(name = "user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
