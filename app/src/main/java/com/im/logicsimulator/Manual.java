package com.im.logicsimulator;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Manual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        TextView[] all = new TextView[11];
        all[0] = findViewById(R.id.ManualHead);
        all[1] = findViewById(R.id.head1);
        all[2] = findViewById(R.id.head2);
        all[3] = findViewById(R.id.head3);
        all[4] = findViewById(R.id.head4);
        all[5] = findViewById(R.id.head5);
        all[6] = findViewById(R.id.content1);
        all[7] = findViewById(R.id.content2);
        all[8] = findViewById(R.id.content3);
        all[9] = findViewById(R.id.content4);
        all[10] = findViewById(R.id.content5);

        Typeface typeBold = Typeface.createFromAsset(getAssets(), "fonts/Cuprum-Bold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Cuprum-Regular.ttf");

        for (int i = 0; i < 11; i++) {
            if (i <= 5) {
                all[i].setTypeface(typeBold);
            } else
                all[i].setTypeface(type);
        }

        InputStream is = this.getResources().openRawResource(R.raw.manaul_content);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String data;

        int lineCounter = 0;
        if (is != null) {
            try {
                while ((data = reader.readLine()) != null) {
                    switch (lineCounter) {
                        case 0:
                            all[6].setText(data);
                            break;
                        case 1:
                            all[7].setText(data);
                            break;
                        case 2:
                            all[8].setText(data);
                            break;
                        case 3:
                            all[9].setText(data);
                            break;
                        case 4:
                            all[10].setText(data);
                            break;
                    }
                    lineCounter++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

