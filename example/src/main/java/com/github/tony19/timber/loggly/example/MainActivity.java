package com.github.tony19.timber.loggly.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.tony19.timber.loggly.LogglyTree;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Plant a LogglyTree for logging with Timber. Use tony19's demo
        // token for testing purposes only. Get your own token from
        // https://www.loggly.com/docs/customer-token-authentication-token/
        final String LOGGLY_TOKEN = "1e29e92a-b099-49c5-a260-4c56a71f7c89";
        Timber.plant(new LogglyTree(LOGGLY_TOKEN));

        Timber.tag("timber-loggly demo");
        Timber.d("activity created");
        Timber.i("hello world!");
    }
}
