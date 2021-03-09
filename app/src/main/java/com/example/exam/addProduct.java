package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class addProduct extends AppCompatActivity {
    EditText name, unit, price, date_exp, inventory, image;
    Button add;
    double product;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = (EditText) findViewById(R.id.name);
        unit = (EditText) findViewById(R.id.unit);
        price = (EditText) findViewById(R.id.price);
        date_exp = (EditText) findViewById(R.id.date_exp);
        inventory = (EditText) findViewById(R.id.inventory);
        image = (EditText) findViewById(R.id.image);
        add = (Button) findViewById(R.id.add);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        // birthday
        date_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(addProduct.this,
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
                date_exp.setText(currentDate);
            }
        };

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() && unit.getText().toString().isEmpty() && price.getText().toString().isEmpty() &&
                date_exp.getText().toString().isEmpty() && inventory.getText().toString().isEmpty()){
                    Toast.makeText(addProduct.this, "Please Complete the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    DecimalFormat df = new DecimalFormat("0.00");
                    double p = Double.parseDouble(price.getText().toString());
                    int i = Integer.parseInt(inventory.getText().toString());

                    // multiply to get the product of inventory and price
                    product = i * p;
                    DBHelper myDB = new DBHelper(addProduct.this);
                    myDB.addProduct(
                            name.getText().toString().trim(),
                            unit.getText().toString().trim(),
                            df.format(p),
                            date_exp.getText().toString().trim(),
                            inventory.getText().toString(),
                            df.format(product),
                            image.getText().toString());
                }

            }
        });
    }

}