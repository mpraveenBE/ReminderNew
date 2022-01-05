package com.example.reminder;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//@RequiresApi(api = Build.VERSION_CODES.P)
public class AddItem extends AppCompatActivity implements View.OnClickListener {


    EditText editTextItemName,editTextAmount,editTextDate;
    Calendar calendar;
    Button buttonAddItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        editTextItemName = (EditText)findViewById(R.id.et_item_name);
        editTextAmount = (EditText)findViewById(R.id.et_amount);
        editTextDate = findViewById(R.id.et_date);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date1= new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateCalendar();

            }
            private void updateCalendar(){
                String Format = "dd-MMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.UK);
                editTextDate.setText(sdf.format(calendar.getTime()));
            }
        };
        editTextDate.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View v){
                                                new DatePickerDialog(AddItem.this, date1, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                                            }
                                        }
        );


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String name = editTextItemName.getText().toString().trim();
        final String amount = editTextAmount.getText().toString().trim();
        final String date1 = editTextDate.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbx6HZsHSoAstchRklTe2k2EuwM0Qi72JliZgBH9slfbxEY5vR5xTXSBhZkpz-sySTOqcA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(AddItem.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("itemName",name);
                parmas.put("amount",amount);
                parmas.put("date1",date1);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }




    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){
            addItemToSheet();

            //Define what to do when button is clicked
        }
    }
}