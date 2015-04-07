package ivansued.com.weatherapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements LocationListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Current mCurrent;

    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.precipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    private double mLatitude = 37.8267;
    private double mLongitude = -122.423;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria cri=new Criteria();
        String provider=locationManager.getBestProvider(cri,false);

        if(provider!=null & !provider.equals(""))
        {
            locationManager.requestLocationUpdates(provider, 2000, 1, this);
            Location location=locationManager.getLastKnownLocation(provider);

            if(location!=null)
            {
                onLocationChanged(location);
            }
            else{
                Toast.makeText(getApplicationContext(), "location not found", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Provider is null",Toast.LENGTH_LONG).show();
        }



        ButterKnife.inject(this);
        mProgressBar.setVisibility(View.INVISIBLE);
//        final double latitude = 37.8267;
//        final double longitude = -122.423;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(mLatitude, mLongitude);
            }
        });

        getForecast(mLatitude, mLongitude);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "8120c751d7795565aa90cc4716bf5eb9";

        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                "/" + latitude + "," + longitude;

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mCurrent = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception Caught", e);
                    }
                }
            });
            Log.d(TAG, "Main UI Code is running");
        }else{
            alertUserAboutNetworkError();
            //Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        mLocationLabel.setText(mCurrent.getTimeZone()+"");
        mTemperatureLabel.setText(mCurrent.getTemperature() + "");
        mTimeLabel.setText("At " + mCurrent.getFormattedTime() + " it will be");
        mHumidityValue.setText(mCurrent.getHumidity()+"");
        mPrecipValue.setText(mCurrent.getPrecipChance()+"%");
        mSummaryLabel.setText(mCurrent.getSummary());

        Drawable drawable = getResources().getDrawable(mCurrent.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");
        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }

    private void alertUserAboutNetworkError() {
        AlertDialogFragment dialog = AlertDialogFragment
                .newInstance(getString(R.string.network_connection_error), getString(R.string.network_connection_message));
        dialog.show(getFragmentManager(), "network_error_message");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = AlertDialogFragment
                .newInstance(getString(R.string.error_title), getString(R.string.error_message));
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
