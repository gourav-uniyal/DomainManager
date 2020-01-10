package com.android.testmessenger.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.testmessenger.R;
import com.android.testmessenger.dao.VerificationDao;
import com.android.testmessenger.database.AppDatabase;
import com.android.testmessenger.interfaces.ApiInterface;
import com.android.testmessenger.libs.ApiClient;
import com.android.testmessenger.model.ResponseVerification;
import com.android.testmessenger.model.Verification;
import java.lang.reflect.Method;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    @BindView(R.id.edt_verification_token) EditText edtVerificationToken;
    @BindView(R.id.progressBar_verification) ProgressBar progressBar;

    private VerificationDao verificationDao;
    private String androidDeviceId;
    private String model;
    private String version;
    private String serial;
    private static final String TAG = "VerificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verification );
        ButterKnife.bind( this );

        verificationDao = AppDatabase.getInstance( getApplicationContext( ) ).verificationDao( );

        deviceDetails();

    }

    void deviceDetails(){

        androidDeviceId = Settings.Secure.getString( getApplicationContext( ).getContentResolver( ), Settings.Secure.ANDROID_ID );

        model = Build.MODEL;

        version = Build.VERSION.RELEASE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (checkSelfPermission( Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            serial = Build.getSerial( );
        }
        else
            serial = getSerialNumber( );

        Log.v(TAG, "serial :" + serial );
        Log.v( "Android Device Id: ", androidDeviceId + "  Model No. : " + model + "  Version: " + version );
    }

    @OnClick(R.id.btn_verification_verify)
    void onClickVerify() {

        boolean isEmptyField = false;

        String key = edtVerificationToken.getText( ).toString( ).trim( );

        if (TextUtils.isEmpty( key )) {
            isEmptyField = true;
            edtVerificationToken.setError( "required" );
            Toast.makeText( this, "required", Toast.LENGTH_SHORT ).show( );
        }

        if (!isEmptyField) {
            Log.d( TAG, "Verification Code: " + "not empty" );
            Verification verification = new Verification( );
            verification.setDeviceId( androidDeviceId );
            verification.setDeviceModel( model );
            verification.setDeviceVersion( version );
            verification.setKey( key );
            verification.setDeviceSerial( serial );
            verification.setId( 1 );
            sendVerification( verification );
        }
    }

    /***
     * this function verifies that if key is valid or not.If it is valid, user can proceed to next screen.
     * @param verification verification is the object where all details are present of user phone
     */
    private void sendVerification(Verification verification) {

        Log.d( TAG, "sendVerification: " + "api function" );

        progressBar.setVisibility( View.VISIBLE );

        HashMap<String, String> map = new HashMap<>( );
        map.put( "key", verification.getKey());
        map.put( "device_id", verification.getDeviceId());
        map.put( "device_model", verification.getDeviceModel());
        map.put( "device_version", verification.getDeviceVersion());
        map.put( "device_serial_number", verification.getDeviceSerial());

        ApiInterface apiInterface = ApiClient.getRetrofitInstance( ).create( ApiInterface.class );
        Call<ResponseVerification> call = apiInterface.verificationTask( map );
        call.enqueue( new Callback<ResponseVerification>( ) {
            @Override
            public void onResponse(@NonNull Call<ResponseVerification> call, @NonNull Response<ResponseVerification> response) {
                Log.d( TAG, "sendVerification: " + "api called" );
                ResponseVerification responseVerification = response.body();
                Log.d( TAG, "onResponse: "+ response.body() );
                if(responseVerification!=null) {
                    if (responseVerification.getStatus( ).equals( "success" )) {
                        if (verificationDao.getVerificaion( 1 ) == null) {
                            verification.setUserId( responseVerification.getUserId() );
                            verificationDao.insert( verification );
                        }
                        progressBar.setVisibility( View.GONE );
                        startActivity( new Intent( getApplicationContext( ), HomeActivity.class ) );
                        finish( );
                    } else if (responseVerification.getStatus().equals( "fail" )) {
                        progressBar.setVisibility( View.GONE );
                        edtVerificationToken.setError( "Please enter correct Verification key" );
                    } else if (responseVerification.getStatus( ).equals( "expire" )) {
                        progressBar.setVisibility( View.GONE );
                        edtVerificationToken.setError( "Key has been expired" );
                        verificationDao.delete( verification );
                    } else if (responseVerification.getStatus( ).equals( "already verified" )) {
                        progressBar.setVisibility( View.GONE );
                        edtVerificationToken.setError( "This Device already have another key" );
                    } else if(responseVerification.getStatus().equals( "already used" )){
                        progressBar.setVisibility( View.GONE );
                        edtVerificationToken.setError( "This Key is already in used" );
                    }
                    Log.v( TAG, "FINAL RESPONSE: " + responseVerification.getStatus( ) );
                    Log.v( TAG, "status on response: " + responseVerification.getStatus( ) );
                }/*
                else {
                    Toast.makeText( getApplicationContext( ), "No response try again", Toast.LENGTH_SHORT ).show( );
                    progressBar.setVisibility( View.GONE );
                }*/
            }
            @Override
            public void onFailure(@NonNull Call<ResponseVerification> call, @NonNull Throwable t) {
                Log.v( TAG, "No response: " + t.getLocalizedMessage( ) );
                Toast.makeText( getApplicationContext(), "Response: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility( View.GONE );
            }
        } );
    }

    /***
     * this function return android serial number. It works only for api level < 26.
     * @return
     */
    public static String getSerialNumber() {
        String serialNumber;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ro.serialno");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "sys.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = Build.SERIAL;

            // If none of the methods above worked
            if (serialNumber.equals(""))
                serialNumber = null;
        } catch (Exception e) {
            e.printStackTrace();
            serialNumber = null;
        }

        return serialNumber;
    }
}