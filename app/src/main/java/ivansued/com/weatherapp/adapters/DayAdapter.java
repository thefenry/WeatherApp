package ivansued.com.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ivansued.com.weatherapp.R;
import ivansued.com.weatherapp.weather.Day;

/**
 * Created by isued on 4/8/2015.
 */
public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day[] days){
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        //This gets the count this adapter will be using.
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        //gets item in the adapter given a certain position
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; //We are not using this. Tags items for easy reference.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    //This is the method that will populate the listview with the new list item as the user scrolls
        ViewHolder holder;

        if(convertView == null){
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Day day = mDays[position];
        holder.iconImageView.setImageResource((day.getIconId()));
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");
        holder.dayLabel.setText(day.getDayOfTheWeek());

        return convertView;
    }

    //This holds the temporary view for the list item we will display in the listview
    private static class ViewHolder{
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
