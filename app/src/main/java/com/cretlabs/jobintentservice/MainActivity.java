package com.cretlabs.jobintentservice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.cretlabs.jobintentservice.Worker.SHOW_RESULT;

public class MainActivity extends AppCompatActivity implements WorkerResultReceiver.Receiver {
    WorkerResultReceiver mWorkerResultReceiver;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWorkerResultReceiver = new WorkerResultReceiver(new Handler());
        mWorkerResultReceiver.setReceiver(this);
        mTextView = findViewById(R.id.tv_data);
        showDataFromBackground(MainActivity.this, mWorkerResultReceiver);
    }

    private void showDataFromBackground(MainActivity mainActivity, WorkerResultReceiver mWorkerResultReceiver) {
        Worker.enqueueWork(mainActivity, mWorkerResultReceiver);
    }

    public void showData(String data) {
        mTextView.setText(String.format("%s\n%s", mTextView.getText(), data));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case SHOW_RESULT:
                if (resultData != null) {
                    showData(resultData.getString("data"));
                }
                break;
        }
    }
}
