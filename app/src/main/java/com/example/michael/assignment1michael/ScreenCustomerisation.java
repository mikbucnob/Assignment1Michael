package com.example.michael.assignment1michael;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    Button textresizeButton;
    SharedPreferences sharedPref = null;
    String rgbString="000000";
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
        textresizeButton=(Button) findViewById(R.id.textresize);
        final TextView fontchanger=(TextView)findViewById(R.id.fontchecker);
        final Spinner sizer=(Spinner) findViewById(R.id.fontsizeSpinner);

        //sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        sharedPref = getApplicationContext().getSharedPreferences("myprefs", 0);
        editor = sharedPref.edit();
        //internally an xml but android reads and writes putString into xml
        //editor.commit();

        textresizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                //if (sizer.equals("Font Size 12")){
                if (sizer.getSelectedItemPosition()==0){
                    //font size 12 option
                    fontSizeString="12";
                    fontchanger.setTextSize(12);
                }
                else if (sizer.getSelectedItemPosition()==1){
                    //font size 16 option
                    fontSizeString="16";
                    fontchanger.setTextSize(16);
                }
                else if (sizer.getSelectedItemPosition()==2){
                    //font size 22 option
                    fontSizeString="22";
                    fontchanger.setTextSize(22);
                }
                else if (sizer.getSelectedItemPosition()==3){
                    //font size 30 option
                    fontSizeString="30";
                    fontchanger.setTextSize(30);
                }
                editor.putString("fontsize", fontSizeString);
                editor.commit();
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
