package com.robertsimoes.quoteableapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.robertsimoes.quoteable.QuotePackage;
import com.robertsimoes.quoteable.Quoteable;

public class MainActivity extends AppCompatActivity implements Quoteable.ResponseReadyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Quoteable quoteable = new Quoteable(this,"default A","default Q");
        quoteable.request();


    }

    @Override
    public void onQuoteResponseReady(QuotePackage response) {
        Log.d("Main,",response.getQuote());
        Log.d("Main,",response.getAuthor());
    }

    @Override
    public void onQuoteResponseFailed(QuotePackage defaultResponse) {
        Log.d("Main,",defaultResponse.getQuote());
        Log.d("Main,",defaultResponse.getAuthor());
    }
}
