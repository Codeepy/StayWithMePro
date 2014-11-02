package com.example.android.bluetooth;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Created by dumbastic on 01/11/2014.
 */
public class BluetoothObject  {
    private String address;
    private String name;
    private int rssi;
    private byte[] scanRecord;

    public BluetoothObject(String address, String name, int rssi, byte[] scanRecord) {
        this.address = address;
        this.name = name;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }

    public void setScanRecord(byte[] scanRecord) {
        this.scanRecord = scanRecord;
    }

    public String getScanRecordString(int from, int to) {
        byte[] hex = Arrays.copyOfRange(scanRecord, from, to);
        StringBuilder sb = new StringBuilder(hex.length * 2);
        for(byte b: hex)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    public double getEstimateDistance()
    {
        int txPower = scanRecord[29];
        Log.i("TXPOWER", String.valueOf(rssi) + " " + String.valueOf(txPower) + " " + getScanRecordString(29,30));
        final double ratio = (double) rssi / (double) txPower;

        double distance;
        if (ratio < 1.0)
        {
            distance = Math.pow(ratio, 10);
        }
        else
        {
            distance = 0.89976 * Math.pow(ratio, 7.7095) + 0.111;
        }
        return txPower != 0 ? round(distance*100, 2) : 0;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
