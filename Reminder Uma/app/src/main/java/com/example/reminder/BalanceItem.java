package com.example.reminder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BalanceItem extends AppCompatActivity{
    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_item);

        listView = (ListView) findViewById(R.id.Bv_items);

        getBalanc();

    }


    private void getBalanc() {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwIYiJj5-6pwcPKKAcdEt2EQF3eIySh5XPJVqg9QhXhlcFe4O4k-A86ljnVFq0I5uzUUA/exec?action=getBalance",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String uName = jo.getString("uName");
                String amount = jo.getString("amount");
              //  String date1 = jo.getString("date1");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



                //   String price = jo.getString("price");


                HashMap<String, String> item = new HashMap<>();
                item.put("uName", uName);
                item.put("amount", amount);
           //     item.put("date1", date1);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // item.put("price",price);

                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this,list,R.layout.balance_item_row,
                new String[]{"uName","amount"},new int[]{R.id.bv_u_name,R.id.bv_amount});


        listView.setAdapter(adapter);
        loading.dismiss();
    }


}

