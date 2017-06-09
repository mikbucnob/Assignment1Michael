package com.example.michael.assignment1michael;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class ScreenCustomerisation extends AppCompatActivity {

    int red=0; int blue=0; int green=0;
    LinearLayout linearLayout;
    SharedPreferences sharedPref = null;
    String fontSizeString="12";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_customerisation);
        linearLayout = (LinearLayout) findViewById(R.id.screenChoice);
        ((SeekBar) findViewById(R.id.redBar)).setOnSeekBarChangeListener(sbl);
        ((SeekBar) findViewById(R.id.greenBar)).setOnSeekBarChangeListener(sbl);
        ((SeekBar) findViewById(R.id.blueBar)).setOnSeekBarChangeListener(sbl);
        final TextView fontchanger=(TextView)findViewById(R.id.fontchecker);
        final Spinner sizer=(Spinner) findViewById(R.id.fontsizeSpinner);

        //sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        sharedPref = getApplicationContext().getSharedPreferences("myprefs", 0);
        editor = sharedPref.edit();
        //internally an xml but android reads and writes putString into xml
        //editor.commit();

        sizer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sizer.getSelectedItem().equals("Font Size 16")){
                    //if (sizer.getSelectedItemPosition()==0){
                    fontSizeString="16";
                }
                else if(sizer.getSelectedItem().equals("Font Size 20")){
                    fontSizeString="20";
                }
                else if(sizer.getSelectedItem().equals("Font Size 25")){
                    fontSizeString="25";
                }
                else if(sizer.getSelectedItem().equals("Font Size 30")){
                    fontSizeString="30";
                }
                fontchanger.setTextSize(Integer.parseInt(fontSizeString));
                editor.putString("fontsize", fontSizeString);
                editor.commit();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }

    SeekBar.OnSeekBarChangeListener sbl =new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch (seekBar.getId()) {
                case R.id.redBar:
                    red = seekBar.getProgress();
                    break;
                case R.id.greenBar:
                    green = seekBar.getProgress();
                    break;
                case R.id.blueBar:
                    blue = seekBar.getProgress();
                    break;
            }
            int c = Color.rgb(red, green, blue);
            String colorString=String.valueOf(c);
            Log.d("ColorSet", "Setting color : " + colorString);
            editor.putString("rgb", colorString);
            editor.commit();
            linearLayout.setBackgroundColor(c);
            String color = sharedPref.getString("rgb", "");
            Log.d("ColorSet", "Color set is : " + color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
