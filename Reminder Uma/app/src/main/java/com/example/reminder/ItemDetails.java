package com.example.reminder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemDetails extends AppCompatActivity implements View.OnClickListener{
    Button buttonDeleteItem;
    Button buttonEdit;
    String itemId;
    String itemName;
    String Amount;
    String date;
    TextView textViewId;
    TextView textViewname;
    TextView textViewitemAmount;
    TextView textViewdate;
    SimpleAdapter adapter;

   @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.item_details);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        itemName = intent.getStringExtra("itemName");
        Amount = intent.getStringExtra("amount");
        date = intent.getStringExtra("date1");
        textViewId = (TextView) findViewById(R.id.id_id);
        textViewname = (TextView) findViewById(R.id.id_item_name);
        textViewitemAmount = (TextView) findViewById(R.id.id_amount);
        textViewdate = (TextView) findViewById(R.id.id_date1);
        buttonEdit = (Button) findViewById(R.id.btn_edit);
        buttonEdit.setOnClickListener(this);
        buttonDeleteItem = (Button) findViewById(R.id.btn_delete);
        buttonDeleteItem.setOnClickListener(this);
        textViewId.setText(this.itemId);
        textViewname.setText(this.itemName);
        textViewitemAmount.setText(this.Amount);
        textViewdate.setText(this. date);
}
    private void deleteItemFromSheet() {
        final ProgressDialog show = ProgressDialog.show(this, "Deleting Item", "Please wait");
        StringRequest stringRequest = new StringRequest(1, Utilities.webAppUrl, new Response.Listener<String>() {
            public void onResponse(String str) {
                show.dismiss();
                Toast.makeText(ItemDetails.this, str,5000).show();
                ItemDetails.this.startActivity(new Intent(ItemDetails.this.getApplicationContext(), MainActivity.class));
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }

        }) {
                        public Map<String, String> getParams() {
                            HashMap hashMap = new HashMap();
                            hashMap.put("action", "deleteItem");
                            hashMap.put("itemId", ItemDetails.this.itemId);
                            return hashMap;
                        }
                    };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 0, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
//        int socketTimeOut = 50000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//
//
//        stringRequest.setRetryPolicy(policy);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
                }



    public void onClick(View view) {
        if (view == this.buttonEdit) {
            Intent intent = new Intent(this, AddItem.class);
            intent.putExtra("itemId", this.itemId);
            intent.putExtra("itemName", this.itemName);
            intent.putExtra("amount", this.Amount);
            intent.putExtra("date1", this.date);
            startActivity(intent);
        }
        if (view == this.buttonDeleteItem) {
            deleteItemFromSheet();
        }

    }
    }