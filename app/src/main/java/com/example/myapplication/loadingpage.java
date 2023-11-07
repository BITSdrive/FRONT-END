package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import androidx.appcompat.app.AppCompatActivity;


public class loadingpage extends AppCompatActivity {

    private TextView resultTextView;
    private boolean initialDownloadCompleted = false;
    private String txtKey;

    Button homebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        resultTextView = findViewById(R.id.resultTextView);
        homebutton = findViewById(R.id.homebutton);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loadingpage.this, home.class );
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 처음 다운로드 작업 실행
                new NetworkTask().execute();
            }
        },   50 * 1000);
    }

    class NetworkTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                // 네트워크 작업 수행
                AWSCredentials awsCredentials = new BasicAWSCredentials("AKIASN5TEJIHGAWDYCGM", "6S35y1m1SwbxXwqY3d0TlCLRRe+ar8848bhyhedH");
                AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

                String bucketName = "s3-final-20231028";
                ObjectListing objectListing = s3Client.listObjects(bucketName);

                // Initialize variables to track the latest object
                S3ObjectSummary latestObject = null;
                long latestTimestamp = 0;

                for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                    if (summary.getKey().endsWith(".txt") && summary.getLastModified().getTime() > latestTimestamp) {
                        latestObject = summary;
                        latestTimestamp = summary.getLastModified().getTime();
                    }
                }

                if (latestObject != null) {
                    txtKey = latestObject.getKey();
                }

                if (txtKey != null) {
                    S3Object s3Object = s3Client.getObject(bucketName, txtKey);

                    InputStream inputStream = s3Object.getObjectContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder sb = new StringBuilder();
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (result != null) {
                        // Check the content of the downloaded .txt file
                        if (result.trim().equals("0")) {
                            resultTextView.setText("They are not same person");
                        } else if (result.trim().equals("1")) {
                            resultTextView.setText("They are same person");
                        } else {
                            // Handle unexpected content
                            resultTextView.setText("Unexpected content in the .txt file");
                        }
                    } else {
                        resultTextView.setText("Unable to download and read the latest .txt file.");
                    }

                    // 20초 후에 다음 페이지로 이동
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextActivity();
                        }
                    }, 20 * 1000); // 20초
                }
            });
        }

        private void nextActivity() {
            Intent intent = new Intent(loadingpage.this, Result.class);
            startActivity(intent);
            finish(); // 이전 액티비티를 종료
        }


    }
}