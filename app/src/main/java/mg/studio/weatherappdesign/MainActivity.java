package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://wthrcdn.etouch.cn/weather_mini?city=重庆";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String temperature) {
            //Update the temperature displayed

            //json the data
            //String wendu = "", ganmao = "", high = "", low = "", date = "", fengxiang = "", fengli = "", type = "";
            String wendu = "",ganmao = "";
            JSONObject tem = JSONObject.fromObject(temperature);
            JSONObject data = tem.getJSONObject("data");
            wendu = data.getString("wendu") + "\n";
            ganmao = data.getString("ganmao") + "\n";

            //read the next five days of the weather

                JSONArray forecast = data.getJSONArray("forecast");

                //high += Weather.getString("high") + "\n";
                //low += Weather.getString("low") + "\n";
                //date += Weather.getString("date") + "\n";
                //fengxiang += Weather.getString("fengxiang") + "\n";
                //fengli += Weather.getString("fengli") + "\n";
                //type += Weather.getString("type") + "\n";
                String  high[]=new String[5],low[]=new String[5],date[]=new String[5],fengxiang[]=new String[5],fengli[]=new String[5],type[]=new String[5];
                for (int i=0;i<5;i++)
                {
                    JSONObject Weather = forecast.getJSONObject(i);
                    high[i] = Weather.getString("high") + "\n";
                    low[i] = Weather.getString("low") + "\n";
                    date[i] = Weather.getString("date") + "\n";
                    fengxiang[i] = Weather.getString("fengxiang") + "\n";
                    fengli[i] = Weather.getString("fengli") + "\n";
                    type[i] = Weather.getString("type");
                }


            //change the image of the weather
                if (type[0].equals("阴")==true) {
                    ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.partly_sunny_small);
                } else if (type[0].equals("小雨")==true) {
                    ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.rainy_small);
                } else {
                    ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.sunny_small);
                }

            if (type[1].equals("阴")==true) {
                ((ImageView) findViewById(R.id.firstpic)).setImageResource(R.drawable.partly_sunny_small);
            } else if (type[1].equals ("小雨")==true) {
                ((ImageView) findViewById(R.id.firstpic)).setImageResource(R.drawable.rainy_small);
            } else {
                ((ImageView) findViewById(R.id.firstpic)).setImageResource(R.drawable.sunny_small);
            }

            if (type[2].equals("阴")==true) {
                ((ImageView) findViewById(R.id.secondpic)).setImageResource(R.drawable.partly_sunny_small);
            } else if (type[2].equals ("小雨")==true) {
                ((ImageView) findViewById(R.id.secondpic)).setImageResource(R.drawable.rainy_small);
            } else {
                ((ImageView) findViewById(R.id.secondpic)).setImageResource(R.drawable.sunny_small);
            }

            if (type[3].equals("阴")==true) {
                ((ImageView) findViewById(R.id.thirdpic)).setImageResource(R.drawable.partly_sunny_small);
            } else if (type[3].equals ("小雨")==true) {
                ((ImageView) findViewById(R.id.thirdpic)).setImageResource(R.drawable.rainy_small);
            } else {
                ((ImageView) findViewById(R.id.thirdpic)).setImageResource(R.drawable.sunny_small);
            }

            if (type[4].equals("阴")==true) {
                ((ImageView) findViewById(R.id.fourthpic)).setImageResource(R.drawable.partly_sunny_small);
            } else if (type[4].equals ("小雨")==true) {
                ((ImageView) findViewById(R.id.fourthpic)).setImageResource(R.drawable.rainy_small);
            } else {
                ((ImageView) findViewById(R.id.fourthpic)).setImageResource(R.drawable.sunny_small);
            }


            //change the information about the next five days
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(wendu);
            ((TextView) findViewById(R.id.Today)).setText(date[0]);
            ((TextView) findViewById(R.id.tv_news)).setText(ganmao);
            ((TextView) findViewById(R.id.firstext)).setText(date[1]+high[1]+low[1]+fengxiang[1]+fengli[1]);
            ((TextView) findViewById(R.id.secondtext)).setText(date[2]+high[2]+low[2]+fengxiang[2]+fengli[2]);
            ((TextView) findViewById(R.id.thirdtext)).setText(date[3]+high[3]+low[3]+fengxiang[3]+fengli[3]);
            ((TextView) findViewById(R.id.fourthtext)).setText(date[4]+high[4]+low[4]+fengxiang[4]+fengli[4]);



        }
    }
}
