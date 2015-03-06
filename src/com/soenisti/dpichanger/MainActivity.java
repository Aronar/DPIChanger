package com.soenisti.dpichanger;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.soenisti.dpichanger.CMDProcessor;


public class MainActivity extends Activity {

    private static String TAG = "DPIChanger";

    private RadioGroup radioDPI;
    private RadioButton radioDPIButton;
    private Button BtnSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    String DPI = System.getProperty("ro.sf.lcd.density");

    private void setLcdDensity(int newDensity) {
        Helpers.getMount("rw");
        new CMDProcessor().su.runWaitFor("busybox sed -i 's|ro.sf.lcd_density=.*|" + "ro.sf.lcd_density" + "=" + newDensity + "|' " + "/system/build.prop");
        Helpers.getMount("ro");
    }

    public void addListenerOnButton() {

        radioDPI = (RadioGroup) findViewById(R.id.radioDPI);
        BtnSet = (Button) findViewById(R.id.btnSet);

        BtnSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = radioDPI.getCheckedRadioButtonId();

                radioDPIButton = (RadioButton) findViewById(selectId);

                String newDPI = String.valueOf(radioDPIButton.getText());
                int newDensity = Integer.parseInt(newDPI);

                setLcdDensity(newDensity);

                Log.d(TAG, "DPI changed to" + newDensity);

                Toast.makeText(MainActivity.this, radioDPIButton.getText(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
