package com.example.amine.cse535_asst1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";
    @BindView(R.id.et_patient_name)
    EditText et_patient_name;

    @BindView(R.id.et_age)
    EditText et_age;

    @BindView(R.id.et_patient_id)
    EditText et_patient_id;

    @BindView(R.id.bt_run)
    Button bt_run;

    @BindView(R.id.bt_stop)
    Button bt_stop;

    @BindView(R.id.rg_sex)
    RadioGroup rg_sex;

    @BindView(R.id.rb_male)
    RadioButton rb_male;

    @BindView(R.id.rb_female)
    RadioButton rb_female;

    @BindView(R.id.gv_graph)
    GraphView graph;

    //random to generate the random data points
    final Random random = new Random();
    //x is the x axis here refers to time
    int x = 0;
    //handler to schedule an update every 1 second.
    final Handler handler = new Handler();
    //reference to the runnable that we are running every second
    Runnable run_every_interval;
    //if the heart rate monitor is running
    boolean running = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling


        graph.setTitle("HeartBeat Monitor");


    }

    @OnClick(R.id.bt_run)
    public void run() {
        if(!running) {
            Log.d(TAG, "button run");
            final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(x, 0),

            });
            x = x + 1;
            graph.addSeries(series);
            run_every_interval = new Runnable() {
                @Override
                public void run() {

                    series.appendData(new DataPoint(x, random.nextDouble()), true, 50, false);
                    x = x + 1;
                    handler.postDelayed(this, 1000);
                }
            };
            //run after 1 sec
            running = true;
            handler.postDelayed(run_every_interval, 1000);
        }

    }

    @OnClick(R.id.bt_stop)
    public void stop() {
        Log.d(TAG,"button stop");
        x = 0;
        graph.removeAllSeries();
        if(run_every_interval !=null) {
            handler.removeCallbacks(run_every_interval);
            running = false;
        }
    }
}
