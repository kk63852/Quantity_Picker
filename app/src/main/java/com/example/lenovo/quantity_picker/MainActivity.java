package com.example.lenovo.quantity_picker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if(quantity<0){
            quantity=0;
            display(quantity);}
        else{
        display(quantity);}
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox  whippedCream=(CheckBox)findViewById(R.id.whipped_cream);
        boolean hasWhippedCream=whippedCream.isChecked();
        CheckBox  Choco=(CheckBox)findViewById(R.id.chocolate);
        boolean hasChoc=Choco.isChecked();
        EditText text=(EditText)findViewById(R.id.name_field);
        String name=text.getText().toString();

int price=calculatePrice(hasChoc,hasWhippedCream);
String priceMessage=createOrderSummary(price,hasWhippedCream,hasChoc,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "order for:"+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }




    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price value on the screen.
     */
    private String createOrderSummary(int price, boolean addWhippedCream,boolean addchoc,String name) {
        String priceMessage="Name:"+name;
        priceMessage +="\nAdd whipped cream:"+ addWhippedCream;
        priceMessage +="\nAdd chocolate:"+addchoc;
        priceMessage+="\nQuantity:"+quantity;
        priceMessage+="\nTotal:"+price;
        priceMessage+="\n"+getString(R.string.thank_you);

        return priceMessage;
    }
    private int calculatePrice(boolean addchoc,boolean addwhippedcream) {int base=5;
    if(addchoc){base+=2;}
    if(addwhippedcream){base+=1;}

        // Calculate the total order price by multiplying by the quantity
        return quantity *base;
    }




}