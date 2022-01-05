package com.example.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button buttonAddItem, buttonListItem, buttonBalance, buttonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonListItem = (Button)findViewById(R.id.btn_list_items);
        buttonBalance = (Button)findViewById(R.id.btn_balance_item);
        buttonDelete = (Button)findViewById(R.id.btn_delete_data);
        buttonAddItem.setOnClickListener(this);
        buttonListItem.setOnClickListener(this);
        buttonBalance.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == buttonAddItem) {

            Intent intent = new Intent(this, AddItem.class);
            startActivity(intent);
        }
        if (v == buttonListItem) {

            Intent intent = new Intent(this, ListItem.class);
            startActivity(intent);
        }
        if (v == buttonBalance) {

            Intent intent = new Intent(this, BalanceItem.class);
            startActivity(intent);
        }
        if (v == buttonDelete) {

            Intent intent = new Intent(this, DeleteItem.class);
            startActivity(intent);
        }

    }
}

