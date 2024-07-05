package com.example.e_commerce.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.e_commerce.Model.Cart;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.CreditCard;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.OrderDetails;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Model.User;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    final static String dataName = "DataBase";
    SQLiteDatabase database;

    public Database(@Nullable Context context) {
        super(context, dataName, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  user (id integer primary key  autoincrement , " +
                "name text not null, " +
                "email text not null," +
                "password text not null, " +
                "gender text not null, " +
                "birthdate text , " +
                "job text )");

        db.execSQL("create table category (id integer primary key  autoincrement , " +
                "name text not null," +
                "image text not null)");

        db.execSQL("create table product (id integer primary key autoincrement, " +
                "name text not null ," +
                "image text ," +
                "price real not null , " +
                "selled integer , " +
                "quantity integer not null , " +
                "cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");

        db.execSQL("create table cart (product_id integer primary key, " +
                "user_id integer, " +
                "product_name text not null ," +
                "product_image text ," +
                "product_price real not null , " +
                "product_quantity integer not null , " +
                "product_cat_id integer not null ," +
                "foreign key (product_cat_id)references category (id)," +
                "foreign key (product_id)references product (id)," +
                "foreign key (user_id)references user (id))");

        db.execSQL("create table orders (id integer primary key autoincrement, " +
                "date text not null ," +
                "user_id integer not null ," +
                "address text not null , " +
                "feedback text not null , " +
                "rate real," +
                "foreign key (user_id)references user (id))");

        db.execSQL("create table order_details (order_id integer not null, " +
                "product_id integer not null," +
                "product_name text," +
                "quantity integer not null ," +
                "foreign key (order_id)references orders (id), " +
                "foreign key (product_id)references product (id)," +
                "PRIMARY KEY (order_id, product_id))");

        db.execSQL("create table credit_card (id integer primary key autoincrement, " +
                "user_id integer not null, " +
                "card_number text not null, " +
                "expire_month integer not null, " +
                "expire_year integer not null, " +
                "cvv text not null, " +
                "foreign key (user_id) references user (id))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists orders");
        db.execSQL("drop table if exists cart");
        db.execSQL("drop table if exists order_details");
        onCreate(db);
    }

    public void insert_user(User user) {

        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("birthdate", user.getBirthdate());
        values.put("job", user.getJob());
        values.put("gender", user.getGender());
        database.insert("user", null, values);
        database.close();
    }
    public void addCreditCard(int userId, CreditCard creditCard) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();

        // Assuming CreditCard class has appropriate getters for these fields
        values.put("user_id", userId);
        values.put("card_number", creditCard.getNumber());
        values.put("expire_month", creditCard.getExpireDateMonth());
        values.put("expire_year", creditCard.getExpireDateYear());
        values.put("cvv", creditCard.getCVV());
        database.insert("credit_card", null, values);
        database.close();
    }
    public Cursor user_login(String username, String pass) {
        database = getReadableDatabase();
        String[] args = {username, pass};
        Cursor cursor = database.rawQuery("select * from user where name =? and  password =? ", args);

        if (cursor != null)
            cursor.moveToFirst();

        database.close();
        return cursor;
    }

    public void some_category() {
        Category category = new Category();
        category.setImage("https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/41n7TZpQuvL._AC_UY327_FMwebp_QL65_.jpg");
        category.setName("PlayStation");
        add_category(category);

        category.setImage("https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/71FHsBXbiVL._AC_UL480_FMwebp_QL65_.jpg");
        category.setName("PC");
        add_category(category);

        category.setImage("https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/411vPjx7yWL._AC_UL480_FMwep_QL65_.jpg");
        category.setName("Clothes ");
        add_category(category);
    }

    public void some_product() {
        Product product = new Product();
        product.setImage("https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/51tbWVPtckL._AC_UY327_FMwebp_QL65_.jpg");
        product.setName("Sony PlayStation");
        product.setPrice(4000);
        product.setQuantity(2);
        product.setCat_id(1);
        add_product(product);

        product = new Product();
        product.setImage("https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/71rbwmEdSSL._AC_UY327_FMwebp_QL65_.jpg");
        product.setName("Lenovo laptop");
        product.setPrice(15000);
        product.setQuantity(5);
        product.setCat_id(2);
        add_product(product);

        product = new Product();
        product.setImage("https://m.media-amazon.com/images/W/WEBP_402378-T1/images/I/811wZEP2oxL._AC_UL480_FMwebp_QL65_.jpg");
        product.setName("Sweatshirt");
        product.setPrice(500);
        product.setQuantity(10);
        product.setCat_id(3);
        add_product(product);
    }

    public void add_category(Category category) {

        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("image", category.getImage());

        database.insert("category", null, values);
        database.close();
    }

    public void add_product(Product product) {

        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("image", product.getImage());
        values.put("price", product.getPrice());
        values.put("selled", 0);
        values.put("quantity", product.getQuantity());
        values.put("cate_id", product.getCat_id());

        database.insert("product", null, values);
        database.close();
    }

    public ArrayList<Product> get_products() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from product", null);
        ArrayList<Product> arrayList = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
//            (id "name "image "price "selled "quantity "cate_id integer
            arrayList.add(new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public ArrayList<Product> get_category_products(int id) {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from product where id = '" + id + "'", null);
        ArrayList<Product> arrayList = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
//            (id "name "image "price "selled "quantity "cate_id integer
            arrayList.add(new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public ArrayList<Category> get_categories() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from category", null);

        ArrayList<Category> arrayList = new ArrayList<>();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            arrayList.add(new Category(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }
    public ArrayList<User> getUsers() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from user", null);
        ArrayList<User> userList = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setGender(cursor.getString(4));
            user.setBirthdate(cursor.getString(5));
            user.setJob(cursor.getString(6));

            userList.add(user);
            cursor.moveToNext();
        }

        database.close();
        return userList;
    }
    public ArrayList<Product> search_product(String name) {
        database = getReadableDatabase();
        String[] args = {"%" + name + "%"};
        Cursor cursor = database.rawQuery("select * from Product where name like?", args);
        ArrayList<Product> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrayList.add(new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public String frgot_password(String email) {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select password from user where email = '" + email + "'", null);
        cursor.moveToFirst();
        String password;
        if (cursor.getCount() > 0)
            password = cursor.getString(0);
        else
            password = null;
        database.close();
        return password;
    }

    public void add_to_cart(Product product, int user_id) {

        database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("product_id", product.getId());
        values.put("user_id", user_id);
        values.put("product_name", product.getName());
        values.put("product_image", product.getImage());
        values.put("product_price", product.getPrice());
        values.put("product_quantity", product.getQuantity());
        values.put("product_cat_id", product.getCat_id());

        database.insert("cart", null, values);
        database.close();
    }

    public ArrayList<Cart> get_cart(int user_id) {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from cart where user_id = '" + user_id + "'", null);
        ArrayList<Cart> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrayList.add(new Cart(
                    cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getInt(1),
                    cursor.getInt(5),
                    cursor.getInt(6)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public void delete_from_cart(int product_id, int user_id) {
        database = getReadableDatabase();
        database.delete("cart", "user_id = '" + user_id + "' and product_id = '" + product_id + "'", null);
        database.close();
    }

    public void change_cart_quantity(int product_id, int user_id, int quantity) {
        database = getReadableDatabase();
        String strSQL = "UPDATE cart SET product_quantity = '" + quantity + "' WHERE product_id = '" + product_id + "' and user_id = '" + user_id + "'";
        database.execSQL(strSQL);
    }

    public void delete_product(int product_id) {
        database = getReadableDatabase();
        database.delete("product", "id = '" + product_id + "'", null);
        database.close();
    }
    public void deleteUser(int userId) {
        database = getWritableDatabase();
        database.delete("user", "id = '" + userId + "'", null);
        database.close();
    }

    public void delete_category(int category_id) {
        database = getReadableDatabase();
        database.delete("category", "id = '" + category_id + "'", null);
        database.close();
    }

    public void edit_product(Product product) {
        database = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("id", product.getId());
        row.put("name", product.getName());
        row.put("image", product.getImage());
        row.put("price", product.getPrice());
        row.put("selled", product.getSold());
        row.put("quantity", product.getQuantity());
        row.put("cate_id", product.getCat_id());

        database.update("product", row, "id = '" + product.getId() + "'", null);
        database.close();
    }
    public void editUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name", user.getName());
        row.put("email", user.getEmail());
        row.put("password", user.getPassword());
        row.put("birthdate", user.getBirthdate());
        row.put("job", user.getJob());
        row.put("gender", user.getGender());
        database.update("user", row, "id = '" + user.getId() + "'", null);
        database.close();
    }

    public void edit_category(Category category) {
        database = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("id", category.getId());
        row.put("name", category.getName());
        row.put("image", category.getImage());

        database.update("category", row, "id = '" + category.getId() + "'", null);
        database.close();
    }

    public void increase_selled(int product_id, int quantity) {
        database = getWritableDatabase();
        int selled = -1;
        Cursor cursor = database.rawQuery("SELECT * FROM  product WHERE  id = '" + product_id + "'", null);
        cursor.moveToFirst();
        selled = cursor.getInt(4);
        selled += quantity;
        ContentValues values = new ContentValues();
        values.put("selled", selled);
        database.update("product ", values, "id = '" + product_id + "'", null);
        database.close();
    }

    public void confirm_order(Order order, ArrayList<Cart> carts) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", order.getDate());
        values.put("user_id", order.getUser_id());
        values.put("address", order.getAddress());
        values.put("feedback", order.getFeedback());
        values.put("rate", order.getRate());
        database.insert("orders", null, values);

        int order_id = -1;
        Cursor cursor = database.rawQuery("SELECT * FROM  orders WHERE  id = (SELECT MAX(id) FROM orders)", null);
        cursor.moveToFirst();
        order_id = cursor.getInt(0);
        order_id++;

        for (int i = 0; i < carts.size(); i++) {
            ContentValues values2 = new ContentValues();
            values2.put("order_id", order_id);
            values2.put("product_id", carts.get(i).getProduct_id());
            values2.put("product_name", carts.get(i).getName());
            values2.put("quantity", carts.get(i).getQuantity());
            database.insert("order_details", null, values2);
            increase_selled(carts.get(i).getProduct_id(), carts.get(i).getQuantity());
        }
        database = getReadableDatabase();
        database.delete("cart", "user_id = '" + order.getUser_id() + "'", null);
        database.close();
    }

    public String get_most_selled() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM  product WHERE  selled = (SELECT MAX(selled) FROM product)", null);
        cursor.moveToFirst();
        String product_name = cursor.getString(1);

        return product_name;
    }

    public ArrayList<Order> get_orders() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders", null);
        ArrayList<Order> arrayList = new ArrayList<>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int orderId = cursor.getInt(0);
            int userId = cursor.getInt(2);
            String date = cursor.getString(1);
            String address = cursor.getString(3);
            String feedback = cursor.getString(4);
            double rate = cursor.getDouble(5);

            Order order = new Order.Builder(userId, date, address)
                    .order_id(orderId)
                    .feedback(feedback)
                    .rate(rate)
                    .build();

            arrayList.add(order);

            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public ArrayList<OrderDetails> get_report(String date) {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where date = '" + date + "'", null);
        ArrayList<OrderDetails> arrayList = new ArrayList<>();
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            arrayList.add(new OrderDetails(cursor.getInt(0)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public ArrayList<OrderDetails> get_report(String date, int user_id){
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where date = '" + date + "' and user_id = '" + user_id + "'", null);
        ArrayList<OrderDetails> arrayList = new ArrayList<>();
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            arrayList.add(new OrderDetails(cursor.getInt(0)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public ArrayList<Product> get_order_details(int order_id){
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from order_details where order_id = '" + order_id + "'", null);
        ArrayList<Product> arrayList = new ArrayList<>();
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            arrayList.add(new Product(cursor.getInt(3), cursor.getString(2)));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }

    public ArrayList<String> getUsersIds() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from user", null);
        ArrayList<String> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(0));
            cursor.moveToNext();
        }
        database.close();
        return arrayList;
    }
    public void clearCart(int user_id) {
        database = getWritableDatabase();
        database.delete("cart", "user_id = '" + user_id + "'", null);
        database.close();
    }

}
