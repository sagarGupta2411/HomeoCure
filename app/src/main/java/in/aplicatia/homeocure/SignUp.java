package in.aplicatia.homeocure;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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

public class SignUp extends AppCompatActivity {

    TextView patient_name, email, mobile,weight, age,patient_address, state, city;
    RadioGroup gender;
    Button submit;
    String gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        patient_name=findViewById(R.id.patient_name);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        weight=findViewById(R.id.weight);
        age=findViewById(R.id.age);
        patient_address=findViewById(R.id.patient_address);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);

        gender=findViewById(R.id.gender);
        submit=findViewById(R.id.submit);


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.male) {
                    gen="male";
                } else if(i == R.id.female) {
                    gen="femate";
                } else {
                    Toast.makeText(getApplicationContext(), "select gender",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SignUp.Sign().execute();

            }
        });



    }


    @SuppressLint("StaticFieldLeak")
    class Sign extends AsyncTask<Void, Void, String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            JSONObject json = new JSONObject();
            try {
                json.put("name",patient_name.getText().toString());
                json.put("gender",gen);
                json.put("username",email.getText().toString());
                json.put("mobile",mobile.getText().toString());
                json.put("weight",weight.getText().toString());
                json.put("age",age.getText().toString());
                json.put("state",state.getText().toString());
                json.put("city",city.getText().toString());
                json.put("address",patient_address.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json.toString());

            Request request = new Request.Builder()
                    .url("https://maytas.000webhostapp.com/signup.php")
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
            Toast.makeText(SignUp.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
