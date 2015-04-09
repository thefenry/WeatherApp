package ivansued.com.weatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ivansued.com.weatherapp.R;
import ivansued.com.weatherapp.weather.Hour;

/**
 * Created by isued on 4/9/2015.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {


    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder{
        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
            
        }
    }
}
