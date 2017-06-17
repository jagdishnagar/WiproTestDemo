package weathertestdemo.wipro.com.wiprotestdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jagdishnagar on 5/28/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<WeatherPojo> mWeatherDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTime, mTextViewMinTemperature ,mTextViewMaxTemperature,mTextViewTime ,mTextviewDayMonth ,mTextViewYear;

        public MyViewHolder(View view) {
            super(view);
            mTime = (TextView) view.findViewById(R.id.mTextViewTime);
            mTextViewMinTemperature = (TextView) view.findViewById(R.id.mTextViewMinTemperature);
            mTextViewMaxTemperature = (TextView) view.findViewById(R.id.mTextViewMaxTemperature);
            mTextViewTime = (TextView) view.findViewById(R.id.mTextViewTime);
            mTextviewDayMonth = (TextView) view.findViewById(R.id.mTextviewDayMonth);
            mTextViewYear = (TextView) view.findViewById(R.id.mTextViewYear);
        }
    }


    public WeatherAdapter(List<WeatherPojo> mWeatherDataList) {
        this.mWeatherDataList = mWeatherDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_forecast, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeatherPojo mwaether = mWeatherDataList.get(position);
         String mDateString = mwaether.getmTime();
         String [] mDaySplit = mDateString.split(" ");
         String [] mDate = mDaySplit[0].toString().split("-");
         String [] mTime = mDaySplit[1].toString().split(":");
         //holder.mTime.setText();
         Calendar mCalender = Calendar.getInstance();
         mCalender.set(Integer.parseInt(mDate[0]),Integer.parseInt(mDate[1]) ,Integer.parseInt(mDate[2]),Integer.parseInt(mTime[0]),Integer.parseInt(mTime[1]),Integer.parseInt(mTime[2]));
         if((mCalender.get(Calendar.AM_PM))== 0){
             if((mCalender.get(Calendar.HOUR))== 0){
                 holder.mTextViewTime.setText("12 "+"AM");
             }else{
                 holder.mTextViewTime.setText(""+(mCalender.get(Calendar.HOUR))+" AM");
             }
         }else {
             if((mCalender.get(Calendar.HOUR))== 0){
                 holder.mTextViewTime.setText("12 "+"PM");
             }else {
                 holder.mTextViewTime.setText("" + (mCalender.get(Calendar.HOUR)) + " PM");
             }
         }
         holder.mTextViewMinTemperature.setText("Temp "+""+((((Float.parseFloat(mwaether.getmMinTemperature()))-273.15))));
         holder.mTextViewMaxTemperature.setText("-"+((((Float.parseFloat(mwaether.getmMaxTemperature()))-273.15))));
         holder.mTextviewDayMonth.setText(mCalender.get(Calendar.DAY_OF_MONTH)+" "+new SimpleDateFormat("MMM").format(mCalender.getTime()));
         holder.mTextViewYear.setText(""+mCalender.get(Calendar.YEAR));
    }

    @Override
    public int getItemCount() {
        return mWeatherDataList.size();
    }
}