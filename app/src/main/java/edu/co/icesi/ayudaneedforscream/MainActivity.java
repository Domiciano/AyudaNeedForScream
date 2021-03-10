package edu.co.icesi.ayudaneedforscream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    private SoundMeter meter;
    private boolean recording = false;
    private TextView meterTV ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meterTV = findViewById(R.id.meterTV);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO
        }, 11);

        meter = new SoundMeter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        meter.start();
        recording = true;

        new Thread(
                ()->{
                    while(recording){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        double amplitude = meter.getAmplitude();
                        runOnUiThread(
                                ()->{
                                    meterTV.setText("A: "+amplitude);
                                }
                        );
                        Log.e(">>>","Amplitude: "+amplitude);
                    }
                }
        ).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        meter.stop();
        recording = false;
    }
}