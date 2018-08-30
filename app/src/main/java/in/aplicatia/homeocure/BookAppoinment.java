package in.aplicatia.homeocure;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookAppoinment extends AppCompatActivity {

    TextView name, specialization, qualification, morning, evening;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoinment);

        new GetDetails().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class GetDetails extends AsyncTask<Void, Void, String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            JSONObject json = new JSONObject();
            try {
                json.put("city",getIntent().getStringExtra("city"));
                json.put("clinic_address",getIntent().getStringExtra("clinic"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json.toString());

            Request request = new Request.Builder()
                    .url("https://maytas.000webhostapp.com/get_doctor.php")
                    .post(body)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ist",e.getMessage());
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("2nd",e.getMessage());

            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("result",s+"");
            Toast.makeText(BookAppoinment.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
