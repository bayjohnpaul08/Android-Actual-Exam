package com.example.exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;

public class updateProduct extends AppCompatActivity{

    EditText edit_name, edit_unit, edit_price, edit_date_expiry, edit_inventory, edit_image;
    Button update_button, delete_button;

    String id, name, unit, date_expiry, inventory_cost, image, price, inventory;
    double product;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_unit = (EditText) findViewById(R.id.edit_unit);
        edit_price = (EditText) findViewById(R.id.edit_price);
        edit_date_expiry = (EditText) findViewById(R.id.edit_date_exp);
        edit_inventory = (EditText) findViewById(R.id.edit_inventory);
        edit_image = (EditText) findViewById(R.id.edit_image);
        update_button = (Button) findViewById(R.id.update);
        delete_button = (Button) findViewById(R.id.delete);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        // birthday
        edit_date_expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(updateProduct.this,
                        R.style.DatePickerTheme,
                        dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(cal.getTime());
                edit_date_expiry.setText(currentDate);
            }
        };

        getIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("0.00");
                double p = Double.parseDouble(edit_price.getText().toString());
                int i = Integer.parseInt(edit_inventory.getText().toString());

                // multiply to get the product of inventory and price
                product = i * p;
                DBHelper myDB = new DBHelper(updateProduct.this);
                name = edit_name.getText().toString().trim();
                unit = edit_unit.getText().toString().trim();
                price = df.format(p);
                date_expiry = edit_date_expiry.getText().toString().trim();
                inventory = (edit_inventory.getText().toString().trim());
                inventory_cost = df.format(product);
                image = edit_image.getText().toString().trim();
                myDB.updateData(id, name, unit, price, date_expiry, inventory, inventory_cost, image);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog();
            }
        });

    }

    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("unit") && getIntent().hasExtra("price") &&
                    getIntent().hasExtra("date_expiry") && getIntent().hasExtra("inventory") &&
                        getIntent().hasExtra("image")){

            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            unit = getIntent().getStringExtra("unit");
            price = getIntent().getStringExtra("price");
            date_expiry = getIntent().getStringExtra("date_expiry");
            inventory = getIntent().getStringExtra("inventory");
            image = getIntent().getStringExtra("image");

            //Setting Intent Data
            edit_name.setText(name);
            edit_unit.setText(unit);
            edit_price.setText(price);
            edit_date_expiry.setText(date_expiry);
            edit_inventory.setText(inventory);
            edit_image.setText(image);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void warningDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Are you sure you want to delete this product?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(updateProduct.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}