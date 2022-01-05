package com.example.reminder;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteItem extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView listView;
//        ListAdapter adapter;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText editTextSearchItem;
    Button buttonDelet;
//    private Object View;
    //    CharSequence charSequence;
//    private Object CharSequence;

//    public void onPointerCaptureChanged(boolean z) {
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
//        buttonDelet = (Button) findViewById(R.id.btn_delet);
//        buttonDelet.setOnClickListener((View.OnClickListener) this);
        listView = (ListView) findViewById(R.id.Lv_items);
        editTextSearchItem = (EditText) findViewById(R.id.et_search);
        getItems();

    }


    private void getItems() {

        loading = ProgressDialog.show(this, "Loading", "please wait", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbx6HZsHSoAstchRklTe2k2EuwM0Qi72JliZgBH9slfbxEY5vR5xTXSBhZkpz-sySTOqcA/exec?action=getItems",
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

                String itemName = jo.getString("itemName");
                String amount = jo.getString("amount");
                String date = jo.getString("date1");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


                //   String price = jo.getString("price");


                HashMap<String, String> item = new HashMap<>();
                item.put("itemName", itemName);
                item.put("amount", amount);
                item.put("date1", date);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // item.put("price",price);

                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this, list, R.layout.delete_item_row,
                new String[]{"itemName", "amount", "date1"}, new int[]{R.id.dv_item_name, R.id.tv_amount, R.id.tv_date});


        listView.setAdapter(adapter);
        loading.dismiss();

        editTextSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DeleteItem.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {

        if (view == this.buttonDelet) {
            Intent intent = new Intent(this, ItemDetails.class);
            HashMap hashMap = (HashMap) adapterView.getItemAtPosition(i);
//            String str = ((String) hashMap.get("itemId")).toString();
            String str2 = ((String) hashMap.get("itemName")).toString();
            String str3 = ((String) hashMap.get("amount")).toString();
            String str4 = ((String) hashMap.get("date1")).toString();
//            intent.putExtra("itemId", str);
            intent.putExtra("itemName", str2);
            intent.putExtra("amount", str3);
            intent.putExtra("date1", str4);
            startActivity(intent);

        }
    }
}