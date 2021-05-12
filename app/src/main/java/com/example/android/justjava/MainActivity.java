package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int numberOfCoffees=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        /** check boxes**/
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
//        Log.v("MainActivity", "Has whipped cream: "+hasWhippedCream);
        int price=calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage=createOrderSummary(name, price, hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL,a);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Jast Java Order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

        /** needed only for display on app**/
//        displayMessage(priceMessage);
    }
    private int calculatePrice(boolean cream, boolean chocolate){
        int price = numberOfCoffees*5;
        if(cream){
            price+=numberOfCoffees;
        }
        if(chocolate){
            price+=numberOfCoffees*2;
        }
        return price;
    }

    private String createOrderSummary(String addName, int num, boolean addWhippedCream, boolean addChocolate){
        String message="Name: "+addName;
        message+="\nWith Whipped Cream? "+addWhippedCream;
        message+="\nWith Chocolate? "+addChocolate;
        message+="\nQuantity: "+numberOfCoffees;
        message+="\nTotal: $"+num;
        message+="\nThank You!";
        return message;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(numberOfCoffees==100){
            Toast.makeText(this,"You cannot have more than 100 cup", Toast.LENGTH_SHORT).show();
            return;
        }else
        numberOfCoffees+=1;
        display(numberOfCoffees);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(numberOfCoffees==1){
            Toast.makeText(this,"You cannot order less than 1 cup", Toast.LENGTH_SHORT).show();
            return;
        }else numberOfCoffees-=1;
        display(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given text on the screen.
     */
    /** needed only for display on app**/
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}