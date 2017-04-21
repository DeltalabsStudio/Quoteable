# Quoteable

## Intro

Quoteable is a simple library to request random quotes from the free [Forismatic Quote API](http://forismatic.com/en/api/) to help implement quotes into your app.


## Install

[![](https://jitpack.io/v/robertsimoes/Quoteable.svg)](https://jitpack.io/#robertsimoes/Quoteable) Via [Jitpack.io](http://jitpack.io)

in root project build.gradle
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

in app project build.gradle
```
dependencies {
	        compile 'com.github.robertsimoes:Quoteable:1.0'
	}
```

## Usage

You'll need to implement the ResponseReadyListener interface into your calling activity.

Once implemented, create a new Quoteable instance and call the request function.

A new quote is given each time you make a `request()` call.

```
public class MainActivity extends AppCompatActivity implements Quoteable.ResponseReadyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Quoteable quoteable = new Quoteable(this,"Jim Rohn","Life is like the changing seasons.");
        quoteable.request();
    }

    @Override
    public void onQuoteResponseReady(QuotePackage response) {
    Log.d("MainActivity",response.getQuote());
    Log.d("MainActivity",response.getAuthor());
    }

    @Override
    public void onQuoteResponseFailed(QuotePackage defaultResponse) {
        Log.d("MainActivity",defaultResponse.getQuote());
        Log.d("MainActivity",defaultResponse.getAuthor());
    }
```

#### Note

The methods based on Android Volley Library, so you'll need to add:
```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

to your manifest.

## Author

[@RobertSimoes](http://robertsimoes.com/)
[GitHub](http://github.com/robertsimoes)
[Twitter](http://twitter.com/robertsimoes)

## License

```

MIT License

Copyright (c) 2017 Robert Simoes

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
