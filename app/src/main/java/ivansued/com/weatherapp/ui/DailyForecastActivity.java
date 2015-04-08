package ivansued.com.weatherapp.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.Arrays;

import ivansued.com.weatherapp.R;
import ivansued.com.weatherapp.adapters.DayAdapter;
import ivansued.com.weatherapp.weather.Day;

public class DailyForecastActivity extends ListActivity {
    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
//        String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
//                "Friday", "Saturday"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, daysOfTheWeek);
//        setListAdapter(adapter);

        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }
}
