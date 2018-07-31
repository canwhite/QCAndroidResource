package com.byx.zz.timeselect;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.os.Build.VERSION_CODES.O;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.TimePickerDialogInterface{
    private Context context;
    private TimePickerDialog mTimePickerDialog;
    TextView tv_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context=this;
        mTimePickerDialog = new TimePickerDialog(context);


        tv_time= (TextView) findViewById(R.id.tv_time);

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.showDatePickerDialog();
            }
        });

    }

    @Override
    public void positiveListener() {
        // TODO Auto-generated method stub
        int year = mTimePickerDialog.getYear();
        int month = mTimePickerDialog.getMonth();
        int day = mTimePickerDialog.getDay();

        tv_time.setText(year + "-" + month + "-" + day);

    }

    @Override
    public void negativeListener() {
        // TODO Auto-generated method stub

    }
}
