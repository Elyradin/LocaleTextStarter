package com.example.localetextstarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private double mPrice;
    private double price100;
    private static final double saudiKurs = 3.75;
    private static final double indKurs = 14968.65;

    String mFormattedPrice;

    private NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelp();
            }
        });

        Date myDate = new Date();
        long expirationDate = myDate.getTime() + TimeUnit.DAYS.toMillis(5);
        myDate.setTime(expirationDate);

        String formatDate = DateFormat.getDateInstance().format(myDate);
        TextView expiredTextView = findViewById(R.id.date);
        expiredTextView.setText(formatDate);

        EditText masukanHarga = findViewById(R.id.price);
        TextView totalHarga = findViewById(R.id.total);
        findViewById(R.id.button_kirim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrice = Double.parseDouble(masukanHarga.getText().toString());

                NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance();
                String deviceLocale = Locale.getDefault().getCountry();

                switch (deviceLocale){
                    case "ID":
                        mPrice = mPrice * indKurs;

                    case "SA":
                        mPrice = mPrice * saudiKurs;

                    default:
                        break;
                }
                price100 = mPrice * 100;
                mFormattedPrice = mCurrencyFormat.format(price100);
                totalHarga.setText(mFormattedPrice);
            }
        });
    }

    /**
     * Shows the Help screen.
     */
    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    /**
     * Creates the options menu and returns true.
     *
     * @param menu       Options menu
     * @return boolean   True after creating options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles options menu item clicks.
     *
     * @param item      Menu item
     * @return boolean  True if menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_language) {
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}