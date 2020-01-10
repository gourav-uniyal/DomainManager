package com.android.testmessenger.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.testmessenger.R;
import com.android.testmessenger.dao.VerificationDao;
import com.android.testmessenger.database.AppDatabase;
import com.android.testmessenger.interfaces.ApiInterface;
import com.android.testmessenger.libs.ApiClient;
import com.android.testmessenger.model.ResponseVerification;
import com.android.testmessenger.model.Verification;
import com.android.testmessenger.service.CheckNetworkService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    //region Variable Declaration
    private VerificationDao verificationDao;
    private static final String TAG = "SplashActivity";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        startService( new Intent( this, CheckNetworkService.class ) );

        verificationDao = AppDatabase.getInstance( getApplicationContext( ) ).verificationDao( );
        Verification verification = verificationDao.getVerificaion( 1 );
        if (verification != null) {
            sendVerification( verification );
        } else {

            startActivity( new Intent( getApplicationContext( ), VerificationActivity.class ) );
            finish( );
        }
    }

    /***
     * this function verifies that if key is valid or not.If it is expired it will ask user for verification again.
     * @param verification
     */
    private void sendVerification(Verification verification) {

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
            public void onResponse(Call<ResponseVerification> call, Response<ResponseVerification> response) {
                ResponseVerification responseVerification = response.body( );
                Log.d( TAG, "onResponse: "+ response.body() );
                if(responseVerification != null) {
                    Log.v( TAG, responseVerification.getStatus( ) );
                    if (responseVerification.getStatus( ).equals( "success" )) {
                        Log.v( TAG, "Status : " + responseVerification.getStatus( ) );
                        startActivity( new Intent( getApplicationContext( ), HomeActivity.class ) );
                        finish( );
                    } else if (responseVerification.getStatus( ).equals( "expire" )) {
                        Toast.makeText( SplashActivity.this, "Key has been expired", Toast.LENGTH_SHORT ).show( );
                        verificationDao.delete( verification );
                        startActivity( new Intent( getApplicationContext( ), VerificationActivity.class ) );
                        finish( );
                    } else if(responseVerification.getStatus().equals( "fail" )){
                        Toast.makeText( SplashActivity.this, "Verification failed", Toast.LENGTH_SHORT ).show( );
                        verificationDao.delete( verification );
                        startActivity( new Intent( getApplicationContext( ), VerificationActivity.class ) );
                        finish( );
                    }

                }
                else
                    Toast.makeText( SplashActivity.this, "No response found", Toast.LENGTH_SHORT ).show( );
            }

            @Override
            public void onFailure(Call<ResponseVerification> call, Throwable t) {
                Log.v( TAG, "No response: " + t.getLocalizedMessage( ) );
                Toast.makeText( getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}