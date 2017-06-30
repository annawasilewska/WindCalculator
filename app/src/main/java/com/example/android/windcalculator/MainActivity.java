package com.example.android.windcalculator;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

        // Find relevant EditTexts
        knotsEditText = (EditText) findViewById(R.id.knots);
        beaufortEditText = (EditText) findViewById(R.id.beaufort);
        msEditText = (EditText) findViewById(R.id.m_s);
        kmhEditText = (EditText) findViewById(R.id.km_h);
        mphEditText = (EditText) findViewById(R.id.mph);

        // Find convert and clear Buttons
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage(R.string.about_alert);
                builder1.setCancelable(true);

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Validation checks if one or more fields have values
     */
    public void validate() {

        knotsString = knotsEditText.getText().toString().trim();
        beaufortString = beaufortEditText.getText().toString().trim();
        msString = msEditText.getText().toString().trim();
        kmhString = kmhEditText.getText().toString().trim();
        mphString = mphEditText.getText().toString().trim();

        if (TextUtils.isEmpty(knotsString) && TextUtils.isEmpty(beaufortString) && TextUtils.isEmpty(msString) && TextUtils.isEmpty(kmhString) && TextUtils.isEmpty(mphString)) {
            Toast.makeText(this, getString(R.string.enter_value), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, getString(R.string.enter_one_value), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Depending on the validation case, choose right conversion
     */
    public void convert() {

        switch (convertCase) {
            case 1:
                convertKnots();
                return;
            case 2:
                convertBeaufort();
                return;
            case 3:
                convertMs();
                return;
            case 4:
                convertKmh();
                return;
            case 5:
                convertMph();
        }
    }

    /**
     * Method converts knots to Beaufort scale, m/s, km/h and mph
     */
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

    /**
     * Method converts m/s to knots, Beaufort scale, km/h and mph
     */
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

    /**
     * Method converts km/h to knots, Beaufort scale, m/s and mph
     */
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

    /**
     * Method converts mph to knots, Beaufort scale, m/s and km/h
     */
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

    /**
     * Method converts Beaufort scale to knots, m/s, km/h and mph
     */
    public void convertBeaufort() {
        beaufort = Integer.valueOf(beaufortString);

        switch (beaufort) {
            case 0:
                knotsEditText.setText(getString(R.string.case_0_knots));
                msEditText.setText(getString(R.string.case_0_ms));
                kmhEditText.setText(getString(R.string.case_0_kmh));
                mphEditText.setText(getString(R.string.case_0_mph));
                return;
            case 1:
                knotsEditText.setText(getString(R.string.case_1_knots));
                msEditText.setText(getString(R.string.case_1_ms));
                kmhEditText.setText(getString(R.string.case_1_kmh));
                mphEditText.setText(getString(R.string.case_1_mph));
                return;
            case 2:
                knotsEditText.setText(getString(R.string.case_2_knots));
                msEditText.setText(getString(R.string.case_2_ms));
                kmhEditText.setText(getString(R.string.case_2_kmh));
                mphEditText.setText(getString(R.string.case_2_mph));
                return;
            case 3:
                knotsEditText.setText(getString(R.string.case_3_knots));
                msEditText.setText(getString(R.string.case_3_ms));
                kmhEditText.setText(getString(R.string.case_3_kmh));
                mphEditText.setText(getString(R.string.case_3_mph));
                return;
            case 4:
                knotsEditText.setText(getString(R.string.case_4_knots));
                msEditText.setText(getString(R.string.case_4_ms));
                kmhEditText.setText(getString(R.string.case_4_kmh));
                mphEditText.setText(getString(R.string.case_4_mph));
                return;
            case 5:
                knotsEditText.setText(getString(R.string.case_5_knots));
                msEditText.setText(getString(R.string.case_5_ms));
                kmhEditText.setText(getString(R.string.case_5_kmh));
                mphEditText.setText(getString(R.string.case_5_mph));
                return;
            case 6:
                knotsEditText.setText(getString(R.string.case_6_knots));
                msEditText.setText(getString(R.string.case_6_ms));
                kmhEditText.setText(getString(R.string.case_6_kmh));
                mphEditText.setText(getString(R.string.case_6_mph));
                return;
            case 7:
                knotsEditText.setText(getString(R.string.case_7_knots));
                msEditText.setText(getString(R.string.case_7_ms));
                kmhEditText.setText(getString(R.string.case_7_kmh));
                mphEditText.setText(getString(R.string.case_7_mph));
                return;
            case 8:
                knotsEditText.setText(getString(R.string.case_8_knots));
                msEditText.setText(getString(R.string.case_8_ms));
                kmhEditText.setText(getString(R.string.case_8_kmh));
                mphEditText.setText(getString(R.string.case_8_mph));
                return;
            case 9:
                knotsEditText.setText(getString(R.string.case_9_knots));
                msEditText.setText(getString(R.string.case_9_ms));
                kmhEditText.setText(getString(R.string.case_9_kmh));
                mphEditText.setText(getString(R.string.case_9_mph));
                return;
            case 10:
                knotsEditText.setText(getString(R.string.case_10_knots));
                msEditText.setText(getString(R.string.case_10_ms));
                kmhEditText.setText(getString(R.string.case_10_kmh));
                mphEditText.setText(getString(R.string.case_10_mph));
                return;
            case 11:
                knotsEditText.setText(getString(R.string.case_11_knots));
                msEditText.setText(getString(R.string.case_11_ms));
                kmhEditText.setText(getString(R.string.case_11_kmh));
                mphEditText.setText(getString(R.string.case_11_mph));
                return;
            case 12:
                knotsEditText.setText(getString(R.string.case_12_knots));
                msEditText.setText(getString(R.string.case_12_ms));
                kmhEditText.setText(getString(R.string.case_12_kmh));
                mphEditText.setText(getString(R.string.case_12_mph));
        }
    }

    /**
     * Helper method checks Beaufort scale
     * @param knots
     * @return beaufort
     */
    public int getBeaufort(double knots) {
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
        return beaufort;
    }

    /**
     * Method clears all fields
     */
    public void clear() {
        knotsEditText.setText("");
        beaufortEditText.setText("");
        msEditText.setText("");
        kmhEditText.setText("");
        mphEditText.setText("");
    }
}
