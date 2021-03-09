package com.example.exam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addButton;
    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;

    DBHelper myDB;
    ArrayList<String> prod_id, prod_name, prod_unit, prod_price, prod_date_exp, prod_inventory, prod_inventory_cost, prod_image;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);
        no_data = findViewById(R.id.no_data);
        empty_imageview = findViewById(R.id.empty_imageview);
        addButton =(FloatingActionButton) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addProduct.class));
            }
        });

        myDB = new DBHelper(MainActivity.this);
        prod_id = new ArrayList<>();
        prod_name = new ArrayList<>();
        prod_unit = new ArrayList<>();
        prod_price = new ArrayList<>();
        prod_date_exp = new ArrayList<>();
        prod_inventory = new ArrayList<>();
        prod_inventory_cost = new ArrayList<>();
        prod_image = new ArrayList<>();

        displayData();

        adapter = new Adapter(MainActivity.this,this, prod_id, prod_name, prod_unit, prod_price, prod_date_exp, prod_inventory, prod_inventory_cost, prod_image);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void displayData(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                prod_id.add(cursor.getString(0));
                prod_name.add(cursor.getString(1));
                prod_unit.add(cursor.getString(2));
                prod_price.add(cursor.getString(3));
                prod_date_exp.add(cursor.getString(4));
                prod_inventory.add(cursor.getString(5));
                prod_inventory_cost.add(cursor.getString(6));
                prod_image.add(cursor.getString(7));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            warningDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void warningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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
