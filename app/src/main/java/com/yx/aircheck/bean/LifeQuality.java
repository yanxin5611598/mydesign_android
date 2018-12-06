package com.yx.aircheck.bean;

public class LifeQuality {
    int mTemperature;
    float mTumidity;
    String mAirQuality;
    String mIllumination;
    String mNoise;

    public LifeQuality(int mTemperature, float mTumidity, String mAirQuality, String mIllumination, String mNoise) {
        this.mTemperature = mTemperature;
        this.mTumidity = mTumidity;
        this.mAirQuality = mAirQuality;
        this.mIllumination = mIllumination;
        this.mNoise = mNoise;
    }

    public int getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(int mTemperature) {
        this.mTemperature = mTemperature;
    }

    public float getmTumidity() {
        return mTumidity;
    }

    public void setmTumidity(float mTumidity) {
        this.mTumidity = mTumidity;
    }

    public String getmAirQuality() {
        return mAirQuality;
    }

    public void setmAirQuality(String mAirQuality) {
        this.mAirQuality = mAirQuality;
    }

    public String getmIllumination() {
        return mIllumination;
    }

    public void setmIllumination(String mIllumination) {
        this.mIllumination = mIllumination;
    }

    public String getmNoise() {
        return mNoise;
    }

    public void setmNoise(String mNoise) {
        this.mNoise = mNoise;
    }

    @Override
    public String toString() {
        return "LifeQuality{" +
                "mTemperature=" + mTemperature +
                ", mTumidity=" + mTumidity +
                ", mAirQuality='" + mAirQuality + '\'' +
                ", mIllumination='" + mIllumination + '\'' +
                ", mNoise='" + mNoise + '\'' +
                '}';
    }
}
