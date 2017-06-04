package weathertestdemo.wipro.com.wiprotestdemo;

/**
 * Created by jagdishnagar on 5/28/2017.
 */

public class WeatherForecast {

    public String mTime , mMaxTemperature , mMinTemperature ;

    public String getmTime() {
        return mTime;
    }

    public String getmMinTemperature() {
        return mMinTemperature;
    }

    public void setmMinTemperature(String mMinTemperature) {
        this.mMinTemperature = mMinTemperature;
    }

    public String getmMaxTemperature() {
        return mMaxTemperature;
    }

    public void setmMaxTemperature(String mMaxTemperature) {
        this.mMaxTemperature = mMaxTemperature;
    }

    public void setmTime(String mTime) {

        this.mTime = mTime;

    }
}
