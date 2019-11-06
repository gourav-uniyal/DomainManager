package com.android.testmessenger.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.android.testmessenger.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationActivity extends AppCompatActivity {

    @BindView( R.id.edt_verification_token ) EditText edtVerificationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verification );
        ButterKnife.bind( this );

    }

    @OnClick(R.id.btn_verification_verify)
    void onClickVerify(){

        boolean isEmptyField = false;

        String verificationToken = edtVerificationToken.getText().toString().trim();

        if (TextUtils.isEmpty(verificationToken)){
            isEmptyField = true;
            edtVerificationToken.setError("required");
        }

        if(!isEmptyField) {
            Toast.makeText( this, "Verification Completed", Toast.LENGTH_SHORT ).show( );
            Intent intent = new Intent( getApplicationContext( ), HomeActivity.class );
            startActivity( intent );
            finish( );
        }
    }
}