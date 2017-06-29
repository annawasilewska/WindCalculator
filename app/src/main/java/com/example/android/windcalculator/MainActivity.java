package com.example.android.windcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText knotsEditText;
    private EditText beaufortEditText;
    private EditText msEditText;
    private EditText kmhEditText;
    private EditText mphEditText;

    private String knotsString;
    private String beaufortString;
    private String msString;
    private String kmhString;
    private String mphString;

    private double knots;
    private int beaufort;
    private double ms;
    private double kmh;
    private double mph;


    private boolean validation = true;
    private int convertCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        knotsEditText = (EditText) findViewById(R.id.knots);
        beaufortEditText = (EditText) findViewById(R.id.beaufort);
        msEditText = (EditText) findViewById(R.id.m_s);
        kmhEditText = (EditText) findViewById(R.id.km_h);
        mphEditText = (EditText) findViewById(R.id.mph);

        Button convertButton = (Button) findViewById(R.id.convert);
        Button clearButton = (Button) findViewById(R.id.clear);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                if (validation) {
                    convert();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    public void validate() {
        knotsString = "";
        beaufortString = "";
        msString = "";
        kmhString = "";
        mphString = "";

        knotsString = knotsEditText.getText().toString().trim();
        beaufortString = beaufortEditText.getText().toString().trim();
        msString = msEditText.getText().toString().trim();
        kmhString = kmhEditText.getText().toString().trim();
        mphString = mphEditText.getText().toString().trim();

        if (TextUtils.isEmpty(knotsString) && TextUtils.isEmpty(beaufortString) && TextUtils.isEmpty(msString) && TextUtils.isEmpty(kmhString) && TextUtils.isEmpty(mphString)) {
            Toast.makeText(this, "Enter value to convert", Toast.LENGTH_SHORT).show();
            validation = false;
        } else if (!TextUtils.isEmpty(knotsString) && TextUtils.isEmpty(beaufortString) && TextUtils.isEmpty(msString) && TextUtils.isEmpty(kmhString) && TextUtils.isEmpty(mphString)) {
            validation = true;
            convertCase = 1;
        } else if (TextUtils.isEmpty(knotsString) && !TextUtils.isEmpty(beaufortString) && TextUtils.isEmpty(msString) && TextUtils.isEmpty(kmhString) && TextUtils.isEmpty(mphString)) {
            validation = true;
            convertCase = 2;
        } else if (TextUtils.isEmpty(knotsString) && TextUtils.isEmpty(beaufortString) && !TextUtils.isEmpty(msString) && TextUtils.isEmpty(kmhString) && TextUtils.isEmpty(mphString)) {
            validation = true;
            convertCase = 3;
        } else if (TextUtils.isEmpty(knotsString) && TextUtils.isEmpty(beaufortString) && TextUtils.isEmpty(msString) && !TextUtils.isEmpty(kmhString) && TextUtils.isEmpty(mphString)) {
            validation = true;
            convertCase = 4;
        } else if (TextUtils.isEmpty(knotsString) && TextUtils.isEmpty(beaufortString) && TextUtils.isEmpty(msString) && TextUtils.isEmpty(kmhString) && !TextUtils.isEmpty(mphString)) {
            validation = true;
            convertCase = 5;
        } else {
            validation = false;
            Toast.makeText(this, "Enter only one value", Toast.LENGTH_SHORT).show();
        }
    }

    public void convert() {

        switch (convertCase) {
            case 1:
                Toast.makeText(this, "case 1", Toast.LENGTH_SHORT).show();
                convertKnots();
                return;
            case 2:
                Toast.makeText(this, "case 2", Toast.LENGTH_SHORT).show();
                convertBeaufort();
                return;
            case 3:
                Toast.makeText(this, "case 3", Toast.LENGTH_SHORT).show();
                convertMs();
                return;
            case 4:
                Toast.makeText(this, "case 4", Toast.LENGTH_SHORT).show();
                convertKmh();
                return;
            case 5:
                Toast.makeText(this, "case 5", Toast.LENGTH_SHORT).show();
                convertMph();
        }
    }

    public void convertKnots() {
        knots = Double.valueOf(knotsString);

        beaufort = getBeaufort(knots);
        ms = (knots * 1.852)/3.6;
        kmh = knots * 1.852;
        mph = knots * (1.852 * 0.62);

        beaufortEditText.setText(String.valueOf(beaufort));
        msEditText.setText(String.format("%.2f", ms).replace(",", "."));
        kmhEditText.setText(String.format("%.2f",kmh).replace(",", "."));
        mphEditText.setText(String.format("%.2f",mph).replace(",", "."));
    }

    public void convertMs() {
        ms = Double.valueOf(msString);

        knots = (3.6 * ms) / 1.852;
        beaufort = getBeaufort(knots);
        kmh = knots * 1.852;
        mph = knots * (1.852 * 0.62);

        knotsEditText.setText(String.format("%.2f", knots).replace(",", "."));
        beaufortEditText.setText(String.valueOf(beaufort));
        kmhEditText.setText(String.format("%.2f", kmh).replace(",", "."));
        mphEditText.setText(String.format("%.2f", mph).replace(",", "."));
    }

    public void convertKmh() {
        kmh = Double.valueOf(kmhString);

        knots = kmh / 1.852;
        beaufort = getBeaufort(knots);
        ms = (knots * 1.852)/3.6;
        mph = knots * (1.852 * 0.62);

        knotsEditText.setText(String.format("%.2f", knots).replace(",", "."));
        beaufortEditText.setText(String.valueOf(beaufort));
        msEditText.setText(String.format("%.2f", ms).replace(",", "."));
        mphEditText.setText(String.format("%.2f", mph).replace(",", "."));
    }

    public void convertMph() {
        mph = Double.valueOf(mphString);

        knots = mph / (1.852 * 0.62);
        beaufort = getBeaufort(knots);
        ms = (knots * 1.852)/3.6;
        kmh = knots * 1.852;

        knotsEditText.setText(String.format("%.2f", knots).replace(",", "."));
        beaufortEditText.setText(String.valueOf(beaufort));
        msEditText.setText(String.format("%.2f", ms).replace(",", "."));
        kmhEditText.setText(String.format("%.2f", kmh).replace(",", "."));
    }

    public void convertBeaufort() {
        beaufort = Integer.valueOf(beaufortString);

        switch (beaufort) {
            case 0:
                knotsEditText.setText("0");
                msEditText.setText("0 - 0.02");
                kmhEditText.setText("0");
                mphEditText.setText("0");
                return;
            case 1:
                knotsEditText.setText("1 - 3");
                msEditText.setText("0.3 - 1.5");
                kmhEditText.setText("1 - 6");
                mphEditText.setText("1 - 3");
                return;
            case 2:
                knotsEditText.setText("4 - 6");
                msEditText.setText("1.6 - 3.3");
                kmhEditText.setText("7 - 11");
                mphEditText.setText("4 - 7");
                return;
            case 3:
                knotsEditText.setText("7 - 10");
                msEditText.setText("3.4 - 5.4");
                kmhEditText.setText("12 - 19");
                mphEditText.setText("8 - 12");
                return;
            case 4:
                knotsEditText.setText("11 - 16");
                msEditText.setText("5.5 - 7.9");
                kmhEditText.setText("20 - 29");
                mphEditText.setText("13 - 18");
                return;
            case 5:
                knotsEditText.setText("17 - 21");
                msEditText.setText("8.0 - 10.7");
                kmhEditText.setText("30 - 39");
                mphEditText.setText("19 - 24");
                return;
            case 6:
                knotsEditText.setText("22 - 27");
                msEditText.setText("10.8 - 13.8");
                kmhEditText.setText("40 - 50");
                mphEditText.setText("25 - 31");
                return;
            case 7:
                knotsEditText.setText("28 - 33");
                msEditText.setText("13.9 - 17.1");
                kmhEditText.setText("51 - 62");
                mphEditText.setText("32 - 38");
                return;
            case 8:
                knotsEditText.setText("34 - 40");
                msEditText.setText("17.2 - 20.7");
                kmhEditText.setText("63 - 75");
                mphEditText.setText("39 - 46");
                return;
            case 9:
                knotsEditText.setText("41 - 47");
                msEditText.setText("20.8 - 24.4");
                kmhEditText.setText("76 - 87");
                mphEditText.setText("47 - 54");
                return;
            case 10:
                knotsEditText.setText("48 - 55");
                msEditText.setText("24.5 - 28.4");
                kmhEditText.setText("88 - 102");
                mphEditText.setText("55 - 63");
                return;
            case 11:
                knotsEditText.setText("56 - 63");
                msEditText.setText("28.5 - 32.6");
                kmhEditText.setText("103 - 117");
                mphEditText.setText("64 - 72");
                return;
            case 12:
                knotsEditText.setText("63+");
                msEditText.setText("32.6+");
                kmhEditText.setText("117+");
                mphEditText.setText("72+");
        }
    }

    public int getBeaufort(double knots) {
        Log.i(MainActivity.class.getSimpleName(), "in getBeaufort method, knots = " + knots);
        if (knots < 0.5) {
            beaufort = 0;
        } else if ( knots >= 0.5 && knots < 3.5) {
            beaufort = 1;
        } else if ( knots >= 3.5 && knots < 6.5) {
            beaufort = 2;
        } else if ( knots >= 6.5 && knots < 10.5) {
            beaufort = 3;
        } else if ( knots >= 10.5 && knots < 16.5) {
            beaufort = 4;
        } else if ( knots >= 16.5 && knots < 21.5) {
            beaufort = 5;
        } else if ( knots >= 21.5 && knots < 27.5) {
            beaufort = 6;
        } else if ( knots >= 27.5 && knots < 33.5) {
            beaufort = 7;
        } else if ( knots >= 33.5 && knots < 40.5) {
            beaufort = 8;
        } else if ( knots >= 40.5 && knots < 47.5) {
            beaufort = 9;
        } else if ( knots >= 47.5 && knots < 55.5) {
            beaufort = 10;
        } else if ( knots >= 55.5 && knots < 63.5) {
            beaufort = 11;
        } else if ( knots >= 63.5) {
            beaufort = 12;
        }
        Log.i(MainActivity.class.getSimpleName(), "in getBeaufort method, beaufort = " + beaufort);
        return beaufort;
    }

    public void clear() {
        knotsEditText.setText("");
        beaufortEditText.setText("");
        msEditText.setText("");
        kmhEditText.setText("");
        mphEditText.setText("");
    }
}
