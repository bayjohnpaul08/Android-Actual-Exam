package com.example.exam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList prod_id, prod_name, prod_unit, prod_price, prod_date_exp, prod_inventory, prod_inventory_cost, prod_image;

    Adapter(Activity activity, Context context, ArrayList prod_id, ArrayList prod_name, ArrayList prod_unit, ArrayList prod_price, ArrayList prod_date_exp, ArrayList prod_inventory, ArrayList prod_inventory_cost, ArrayList prod_image) {
        this.activity = activity;
        this.context = context;
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.prod_unit = prod_unit;
        this.prod_price = prod_price;
        this.prod_date_exp = prod_date_exp;
        this.prod_inventory = prod_inventory;
        this.prod_inventory_cost = prod_inventory_cost;
        this.prod_image = prod_image;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        holder.prod_id_txt.setText(String.valueOf(prod_id.get(position)));
        holder.prod_name_txt.setText(String.valueOf(prod_name.get(position)));
        holder.prod_unit_txt.setText(String.valueOf(prod_unit.get(position)));
        holder.prod_price_txt.setText(String.valueOf("₱ " + prod_price.get(position)));
        holder.prod_date_exp_txt.setText(String.valueOf(prod_date_exp.get(position)));
        holder.prod_inventory_txt.setText(String.valueOf(prod_inventory.get(position)));
        holder.prod_inventory_cost_txt.setText(String.valueOf("₱ " + prod_inventory_cost.get(position)));
        Glide.with(context).load(String.valueOf(prod_image.get(position))).placeholder(R.drawable.noimage).into(holder.prod_image_txt);

        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, updateProduct.class);
                intent.putExtra("id", String.valueOf(prod_id.get(position)));
                intent.putExtra("name", String.valueOf(prod_name.get(position)));
                intent.putExtra("unit", String.valueOf(prod_unit.get(position)));
                intent.putExtra("price", String.valueOf(prod_price.get(position)));
                intent.putExtra("date_expiry", String.valueOf(prod_date_exp.get(position)));
                intent.putExtra("inventory", String.valueOf(prod_inventory.get(position)));
                intent.putExtra("image", String.valueOf(prod_image.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prod_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView prod_id_txt, prod_name_txt, prod_unit_txt, prod_price_txt, prod_date_exp_txt, prod_inventory_txt, prod_inventory_cost_txt;
        ImageView prod_image_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_id_txt = itemView.findViewById(R.id.id_text);
            prod_name_txt = itemView.findViewById(R.id.name_text);
            prod_unit_txt = itemView.findViewById(R.id.unit_text);
            prod_price_txt = itemView.findViewById(R.id.price_text);
            prod_date_exp_txt = itemView.findViewById(R.id.date_exp_text);
            prod_inventory_txt = itemView.findViewById(R.id.inventory_text);
            prod_inventory_cost_txt = itemView.findViewById(R.id.inventory_cost_text);
            prod_image_txt = itemView.findViewById(R.id.imageView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }
}
