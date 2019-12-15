package com.im.logicsimulator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView credits = findViewById(R.id.CreditsHead);
        TextView erick = findViewById(R.id.Erick);
        TextView akshay = findViewById(R.id.Akshay);
        TextView ivan = findViewById(R.id.Ivan);
        TextView moises = findViewById(R.id.Moises);

        Typeface typeBold = Typeface.createFromAsset(getAssets(), "fonts/Cuprum-Bold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Cuprum-Regular.ttf");

        credits.setTypeface(typeBold);
        erick.setTypeface(type);
        akshay.setTypeface(type);
        ivan.setTypeface(type);
        moises.setTypeface(type);
    }
}
