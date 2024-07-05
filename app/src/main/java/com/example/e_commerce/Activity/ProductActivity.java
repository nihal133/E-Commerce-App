package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

public class ProductActivity extends AppCompatActivity {
    //incremental button and decremental
    ImageButton imageButton_increment_amount, imageButton_decrement_amount;
    //textview
    TextView textView_product_amount;
    //mount value
    int amount = 1;
    //initialization
    ImageView product_image;
    TextView tv_name, tv_price;
    Button btn_add;
    int quantity;

    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //database object
        Database db = new Database(this);

        n = getIntent();
        //get values
        int id =  n.getExtras().getInt("id") ;
        double price =  n.getExtras().getDouble("price") ;
        String name =  n.getExtras().getString("name") ;
        String image =  n.getExtras().getString("image") ;
        int cat_id = n.getExtras().getInt("cat_id") ;
        quantity = n.getExtras().getInt("quantity") ;
        int sold = n.getExtras().getInt("sold") ;
       // get items by id
        imageButton_increment_amount = findViewById(R.id.btn_increment_amount);
        imageButton_decrement_amount = findViewById(R.id.btn_decrement_amount);
        textView_product_amount = findViewById(R.id.textView_product_amount);
        product_image = findViewById(R.id.imageView_product_image);
        tv_name = findViewById(R.id.textView_product_name);
        tv_price = findViewById(R.id.textView_product_price);
        btn_add = findViewById(R.id.user_home_btn_add_to_cart);
        //take the url;
        String url = image;
        //get img
        Glide.with(getApplicationContext()).load(url).into(product_image);
        //set name and price
        tv_name.setText(name);
        tv_price.setText(price+"");
        //if increment the amount incremented
        imageButton_increment_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(textView_product_amount.getText().toString());
                amount++;
                textView_product_amount.setText(amount + "");
            }
        });
        //if decrement the amount is decremented
        imageButton_decrement_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(textView_product_amount.getText().toString());
                if(amount > 1){
                    amount--;
                    textView_product_amount.setText(amount + "");
                }
            }
        });
// when add this order
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user instance
                User user = User.getInstance();
                //set quantity
                quantity = Integer.parseInt(textView_product_amount.getText().toString());
                //make new product
                Product product = new Product(id, name, image, price, sold, quantity, cat_id);
                //add product in chart database
                db.add_to_cart(product, user.getId());
            }
        });
    }
}