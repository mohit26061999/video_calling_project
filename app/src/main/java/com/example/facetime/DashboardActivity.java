package com.example.facetime;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {
    EditText secreteCodebox;
    Button JoinBtn,ShareBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        secreteCodebox =findViewById(R.id.CodeBox);
        JoinBtn = findViewById(R.id.JoinBtn);
        ShareBtn = findViewById(R.id.ShareBtn);
        URL serverURL;
            try {
                serverURL=new URL("https://meet.jit.si");
                JitsiMeetConferenceOptions defaultOptions =
                        new JitsiMeetConferenceOptions.Builder()
                                .setFeatureFlag("welcomepage.Enabled",false)
                                .setServerURL(serverURL)
                                .build();
                JitsiMeet.setDefaultConferenceOptions(defaultOptions);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }


            JoinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(secreteCodebox.getText().toString())
                            .setFeatureFlag("welcomepage.Enabled",false)
                            .build();
                    JitsiMeetActivity.launch(DashboardActivity.this,options);
                }
            });


        
    }

}