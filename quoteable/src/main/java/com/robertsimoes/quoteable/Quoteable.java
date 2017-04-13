package com.robertsimoes.quoteable;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright (c) 2017 Robert Simoes
 * Using the Forismatic Quotes API, we can call and get a quote using a REST request.
 *
 * This is a very specific
 *
 * This depends on the android volley library to work.
 */

public class Quoteable {

    private final String TAG = "Quoteable";

    public Quoteable(Context context, String defaultQuote, String defaultAuthor) throws ClassCastException  {
        try {
            assert (context instanceof ResponseReadyListener);
            this.mCallback = (ResponseReadyListener) context;
            this.ctx=context;
            this.defaultQuote=defaultQuote;
            this.defaultAuthor=defaultAuthor;
        } catch (ClassCastException e) {
            throw new ClassCastException(ctx.toString() + ", you must implement the ResponseReadyListener Interface within the calling class");
        }
    }

    private String defaultQuote = "";
    private String defaultAuthor = "";
    private ResponseReadyListener mCallback;
    private Context ctx; // used to check network state --> increases coupling but decreases pain of implementation :(

        /**
         * Returns a random quote and author String Array from the Forismatic API, using REST.
         * {QUOTE, AUTHOR}
         */
        public void request()
                throws ClassCastException {


            RequestQueue queue = Volley.newRequestQueue(ctx);
            String url = "http://api.forismatic.com/api/1.0/?method=getQuote&lang=en&format=json";
            /* Instantiate Request Queue*/
            QuotePackage defaultPackage = createDefaultPayload();

            // Request string response from URL
            try {
                if (isNetworkAvailable()) {
                    StringRequest quoteRequest = new StringRequest(Request.Method.GET, url,
                            new QuoteListener(),
                            new QuoteErrorListener());

                    queue.add(quoteRequest);
                } else {
                    throw new NetworkErrorException("No network available.");
                }
            } catch (SecurityException security) {
                mCallback.onQuoteResponseFailed(defaultPackage);
                Log.e(TAG, "Failed to return quote, does you manifest have ACCESS_NETWORK_STATE and INTERNET permissions? \ntrace:\n" + security.getMessage());
            } catch (NetworkErrorException network) {
                mCallback.onQuoteResponseFailed(defaultPackage);
                Log.e(TAG, "Failed to return quote, maybe the network is offline? \ntrace:\n " +network.getMessage());
            } catch (Exception e) {
                mCallback.onQuoteResponseFailed(defaultPackage);
                Log.e(TAG, "Failed to return quote, here's the trace: " +e.getMessage());
            }

        }

    private QuotePackage createDefaultPayload() {
        return new QuotePackage(defaultAuthor,defaultQuote);
    }

    /**
         *
         */
        public interface ResponseReadyListener {
            void onQuoteResponseReady(QuotePackage response);
            void onQuoteResponseFailed(QuotePackage defaultResponse);
        }


        /**
         * Extension of Quote Error Listener that uses a status flag to
         * return whether there was an error or not.
         *
         *
         */
        private class QuoteErrorListener implements Response.ErrorListener {


            @Override
            public void onErrorResponse(VolleyError error) {
                QuotePackage response = createDefaultPayload();
                mCallback.onQuoteResponseFailed(response);
            }

        }

        private class QuoteListener implements Response.Listener {
            private final String quoteJSONTAG = "quoteText";
            private final String authorJSONTAG = "quoteAuthor";

            /**
             * The on response method is called, reading the JSON response into
             * the quote and author fields
             * @param response the response from the API
             */
            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject json = new JSONObject(((String) response).substring(0));
                    QuotePackage payload = new QuotePackage((String) json.get(authorJSONTAG),(String)json.get(quoteJSONTAG));
                    mCallback.onQuoteResponseReady(payload);
                } catch (JSONException e) {
                    /* Response Failed due to json exceptions */
                    Log.e(TAG, "Failed to read json response: " + e.getMessage());
                    mCallback.onQuoteResponseFailed(createDefaultPayload());
                }

            }

        }

        private boolean isNetworkAvailable() {
            ConnectivityManager manager = (ConnectivityManager) (ctx.getSystemService(Context.CONNECTIVITY_SERVICE));
            return manager.getActiveNetworkInfo().isConnectedOrConnecting();
        }
    }
