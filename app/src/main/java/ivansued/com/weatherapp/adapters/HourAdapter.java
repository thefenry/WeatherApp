package ivansued.com.weatherapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import ivansued.com.weatherapp.weather.Hour;

/**
 * Created by isued on 4/9/2015.
 */
public class HourAdapter extends BaseAdapter {
    private Context mContext;
    private Hour[] mHours;

    public HourAdapter(Context context, Hour[] hours){
        mContext = context;
        mHours = hours;
    }

    @Override
    public int getCount() {
        return mHours.length;
    }

    @Override
    public Object getItem(int position) {
        return mHours[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        Hour hour = mHours[position];
        holder.iconImageView.setImageResource((day.getIconId()));
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        if (position == 0){
            holder.dayLabel.setText("Today");
        }else{
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }

        return convertView;
    }
}
