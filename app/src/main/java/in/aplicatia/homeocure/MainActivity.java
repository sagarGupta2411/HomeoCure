package in.aplicatia.homeocure;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Dialog spinnerlayout;
    LinearLayout appoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appoint = findViewById(R.id.book_appointment);

        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetCities().execute();

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    class GetCities extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("https://maytas.000webhostapp.com/get_city.php")
                    .get()
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ist", e.getMessage());
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("2nd", e.getMessage());

            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();


            try {
                if (!new JSONObject(s).getBoolean("error")) {
                    final ArrayList<String> cities = new ArrayList<>();

                    JSONArray jsonArray = new JSONObject(s).getJSONArray("city");
                    Log.e("result", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        cities.add(jsonArray.getString(i));
                    }

                    spinnerlayout = new Dialog(MainActivity.this);
                    spinnerlayout.setContentView(R.layout.appoin_dialog);
                    spinnerlayout.show();

                    final Spinner spinner = spinnerlayout.findViewById(R.id.city);


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            MainActivity.this, android.R.layout.simple_spinner_item, cities);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            new GetClinic(cities.get(i)).execute();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetClinic extends AsyncTask<Void, Void, String> {
        String s;

         GetClinic(String s) {
            this.s = s;
        }

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();

            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");


            JSONObject json = new JSONObject();
            try {
                json.put("city",s);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json.toString());

            Request request = new Request.Builder()
                    .url("https://maytas.000webhostapp.com/get_clinic_address.php")
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ist", e.getMessage());
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("2nd", e.getMessage());

            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

            Log.e("result", s);

            try {
                if (!new JSONObject(s).getBoolean("error")) {
                    ArrayList<String> clinic = new ArrayList<>();

                    JSONArray jsonArray = new JSONObject(s).getJSONArray("clinic");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        clinic.add(jsonArray.getJSONObject(i).getString("address"));
                    }



                    final Spinner spinner = spinnerlayout.findViewById(R.id.clinic);
                    final Button go = spinnerlayout.findViewById(R.id.go);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            MainActivity.this, android.R.layout.simple_spinner_item, clinic);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                    go.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(MainActivity.this,BookAppoinment.class));
                        }
                    });







                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }


    }
}
