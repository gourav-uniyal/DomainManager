package com.android.testmessenger.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.android.testmessenger.R;
import com.android.testmessenger.adapter.MainAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView( R.id.rv_main ) RecyclerView recyclerView;

    private MainAdapter mainAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ButterKnife.bind( this );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission_group.CALL_LOG, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.SEND_SMS}, 1 );
        } else
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS}, 1 );

        arrayList = new ArrayList<>(  );

        arrayList.add( "9997260969" );
        arrayList.add( "8126374588" );
        arrayList.add( "9634659750" );

        mainAdapter = new MainAdapter(getApplicationContext(), arrayList);
        recyclerView.setLayoutManager( new LinearLayoutManager(this, RecyclerView.VERTICAL, false) );
        recyclerView.setAdapter( mainAdapter );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText( this, "Please grant the permission", Toast.LENGTH_SHORT ).show( );
            }
        }
    }

}