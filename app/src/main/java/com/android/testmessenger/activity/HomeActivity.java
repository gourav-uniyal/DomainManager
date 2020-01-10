package com.android.testmessenger.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.testmessenger.R;
import com.android.testmessenger.interfaces.ApiInterface;
import com.android.testmessenger.libs.ApiClient;
import com.android.testmessenger.model.Month;
import com.android.testmessenger.model.ResponseDomain;
import com.android.testmessenger.model.ResponseDomainList;
import com.android.testmessenger.model.ResponseFilters;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    //region Variable Declaration
    @BindView(R.id.spinner_year)
    MaterialSpinner spinnerYear;
    @BindView(R.id.spinner_month)
    MaterialSpinner spinnerMonth;
    @BindView(R.id.spinner_date)
    MaterialSpinner spinnerDate;
    @BindView(R.id.spinner_country)
    MaterialSpinner spinnerCountry;
    @BindView(R.id.progressBar_home)
    ProgressBar progressBar;
    @BindView(R.id.btn_show_list)
    Button btnShowList;
    /*@BindView(R.id.spinner_state)
    MaterialSpinner spinnerState;
    @BindView(R.id.spinner_city)
    MaterialSpinner spinnerCity;*/

    private HashMap<String, String> monthHashMap;

    private ArrayList<String> yearArrayList;
    private ArrayList<Month> monthArrayList;
    private ArrayList<String> dateArrayList;
    private ArrayList<String> countryArrayList;
//    private ArrayList<String> stateArrayList;
//    private ArrayList<String> cityArrayList;

    private String selectedYear;
    private String selectedMonth;
    private String selectedDate;
    private String selectedCountry;
    /*private String selectedState = "all";
    private String selectedCity = "all";*/

    private String filter = "n";

    private static String folderPath;
    private static long back_pressed;

    private static final String TAG = "HomeActivity";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        ButterKnife.bind( this );

        hideViewItems( );

        requestPermission( );

        init( );

        getYearDetails( );
    }

    void init() {
        filter = getIntent( ).getStringExtra( "filter" );

        yearArrayList = new ArrayList<>( );
        monthArrayList = new ArrayList<>( );
        dateArrayList = new ArrayList<>( );
        countryArrayList = new ArrayList<>( );
  /*      stateArrayList = new ArrayList<>( );
        stateArrayList.add( "all" );
        cityArrayList = new ArrayList<>( );
        cityArrayList.add( "all" );*/
    }

    /***
     * this function request runtime permissions for app
     */
    void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission_group.CALL_LOG, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.SEND_SMS}, 1 );
        } else
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS}, 1 );
    }

    /**
     * this function calls api to get the filter data
     */
    void getYearDetails() {
        progressBar.setVisibility( View.VISIBLE );
        ApiInterface apiInterface = ApiClient.getRetrofitInstance( ).create( ApiInterface.class );
        Call<ResponseFilters> call = apiInterface.getYearFilter( );
        call.enqueue( new Callback<ResponseFilters>( ) {
            @Override
            public void onResponse(Call<ResponseFilters> call, Response<ResponseFilters> response) {
                ResponseFilters domainFilter = response.body( );
                if (domainFilter != null) {
                    if (domainFilter.getStatus( ).equals( "success" )) {
                        yearArrayList.addAll( (domainFilter.getYearArrayList( )) );
                        setSpinnerYear( );
                        progressBar.setVisibility( View.GONE );
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseFilters> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
                progressBar.setVisibility( View.GONE );
            }
        } );
    }

    void getMonthDetails(String year) {
        progressBar.setVisibility( View.VISIBLE );
        HashMap<String,String> map = new HashMap<>(  );
        map.put("year", year);

        ApiInterface apiInterface = ApiClient.getRetrofitInstance( ).create( ApiInterface.class );
        Call<ResponseFilters> call = apiInterface.getMonthFilter(map);
        call.enqueue( new Callback<ResponseFilters>( ) {
            @Override
            public void onResponse(Call<ResponseFilters> call, Response<ResponseFilters> response) {
                ResponseFilters domainFilter = response.body( );
                if (domainFilter != null) {
                    if (domainFilter.getStatus( ).equals( "success" )) {
                        monthArrayList.addAll( (domainFilter.getMonthArrayList( )) );
                        setSpinnerMonth( );
                        progressBar.setVisibility( View.GONE );
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseFilters> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
                progressBar.setVisibility( View.GONE );
            }
        });
    }

    void getDateDetails(String year, String month) {
        progressBar.setVisibility( View.VISIBLE );
        HashMap<String,String> map = new HashMap<>(  );
        map.put("year", year);
        map.put( "month", month );

        ApiInterface apiInterface = ApiClient.getRetrofitInstance( ).create( ApiInterface.class );
        Call<ResponseFilters> call = apiInterface.getDateFilter( map );
        call.enqueue( new Callback<ResponseFilters>( ) {
            @Override
            public void onResponse(Call<ResponseFilters> call, Response<ResponseFilters> response) {
                ResponseFilters domainFilter = response.body( );
                if (domainFilter != null) {
                    if (domainFilter.getStatus( ).equals( "success" )) {
                        dateArrayList.addAll( (domainFilter.getDateArrayList( )) );
                        setSpinnerDate( );
                        progressBar.setVisibility( View.GONE );
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseFilters> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
                progressBar.setVisibility( View.GONE );
            }
        } );
    }

    void getCountryDetails(String year, String month, String date) {
        progressBar.setVisibility( View.VISIBLE );
        HashMap<String,String> map = new HashMap<>(  );
        map.put("year", year);
        map.put( "month", month );
        map.put( "date", date );

        ApiInterface apiInterface = ApiClient.getRetrofitInstance( ).create( ApiInterface.class );
        Call<ResponseFilters> call = apiInterface.getCountryFilter( map );
        call.enqueue( new Callback<ResponseFilters>( ) {
            @Override
            public void onResponse(Call<ResponseFilters> call, Response<ResponseFilters> response) {
                ResponseFilters domainFilter = response.body( );
                if (domainFilter != null) {
                    if (domainFilter.getStatus( ).equals( "success" )) {
                        countryArrayList.addAll( (domainFilter.getCountryArrayList( )) );
                        setSpinnerCountry( );
                        progressBar.setVisibility( View.GONE );
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseFilters> call, Throwable t) {
                Toast.makeText( getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
                progressBar.setVisibility( View.GONE );
            }
        } );
    }

    void setSpinnerYear() {
        yearArrayList.remove( null );
        spinnerYear.setItems( yearArrayList );

        spinnerYear.setOnItemSelectedListener( (MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            selectedYear = item;
            spinnerMonth.setVisibility( View.VISIBLE );
            monthArrayList.clear();
            dateArrayList.clear();
            countryArrayList.clear();
            getMonthDetails(item);
        } );
    }

    void setSpinnerMonth() {
        ArrayList<String> arrayList = new ArrayList<>( );
        for (Month month : monthArrayList) {
            Log.v( TAG, "month" + month.getName( ) );
            arrayList.add( month.getName( ) );
        }
        spinnerMonth.setItems( arrayList );
        spinnerMonth.setOnItemSelectedListener( (MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            selectedMonth = monthArrayList.get( position ).getId( );
            dateArrayList.clear();
            countryArrayList.clear();
            spinnerDate.setVisibility( View.VISIBLE );
            getDateDetails(selectedYear, selectedMonth);
        } );
    }

    void setSpinnerDate() {
        dateArrayList.remove( null );
        spinnerDate.setItems( dateArrayList );
        spinnerDate.setOnItemSelectedListener( (MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            selectedDate = item;
            countryArrayList.clear();
            spinnerCountry.setVisibility( View.VISIBLE );
            getCountryDetails(selectedYear, selectedMonth, selectedDate);
        } );
    }

    void setSpinnerCountry() {
        countryArrayList.remove( null );
        spinnerCountry.setItems( countryArrayList );
        spinnerCountry.setOnItemSelectedListener( (MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            selectedCountry = item;
            btnShowList.setVisibility( View.VISIBLE );
        } );
    }

  /*  void setSpinnerState() {
        stateArrayList.remove( null );
        spinnerState.setItems( stateArrayList );
        spinnerState.setOnItemSelectedListener( (MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            selectedState = item;
        } );
    }

    void setSpinnerCity() {

        cityArrayList.remove( null );
        spinnerCity.setItems( cityArrayList );
        spinnerCity.setOnItemSelectedListener( (MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            selectedCity = item;
        } );
    }*/

    @OnClick(R.id.btn_show_list)
    void onClickShowList() {
        if (filter != null) {
            if (filter.equals( "y" )) {
                Intent intent = new Intent( );
                intent.putExtra( "folderPath", folderPath );
                intent.putExtra( "year", selectedYear );
                intent.putExtra( "month", selectedMonth );
                intent.putExtra( "date", selectedDate );
                intent.putExtra( "country", selectedCountry );
                /*intent.putExtra( "state", selectedState );
                intent.putExtra( "city", selectedCity );*/
                setResult( 2 );
                finish( );
            }
        } else {
            Intent intent = new Intent( getApplicationContext( ), DomainListActivity.class );
            intent.putExtra( "folderPath", folderPath );
            intent.putExtra( "year", selectedYear );
            intent.putExtra( "month", selectedMonth );
            intent.putExtra( "date", selectedDate );
            intent.putExtra( "country", selectedCountry );
            /*intent.putExtra( "state", selectedState );
            intent.putExtra( "city", selectedCity );*/
            startActivity( intent );
        }
    }

    /**
     * this function hides the view on activity start
     */
    void hideViewItems() {
        spinnerMonth.setVisibility( View.GONE );
        spinnerDate.setVisibility( View.GONE );
        spinnerCountry.setVisibility( View.GONE );
      /*  spinnerState.setVisibility( View.GONE );
        spinnerCity.setVisibility( View.GONE );*/
    }

    /***
     * this function create a folder in phone storage
     */
    void createfolder() {
        boolean success = true;
        File folder = new File( Environment.getExternalStorageDirectory( ) + File.separator + "DomainManager" );
        if (!folder.exists( )) {
            success = folder.mkdirs( );
        }
        if (success)
            folderPath = folder.getAbsolutePath( );
    }

    /*
     */
/**
 * this function have hash map of months with serial number
 *//*

    void getMonths() {
        monthHashMap = new HashMap<>( );
        monthHashMap.put( "1", "January" );
        monthHashMap.put( "2", "Febrary" );
        monthHashMap.put( "3", "March" );
        monthHashMap.put( "4", "April" );
        monthHashMap.put( "5", "May" );
        monthHashMap.put( "6", "June" );
        monthHashMap.put( "7", "July" );
        monthHashMap.put( "8", "August" );
        monthHashMap.put( "9", "September" );
        monthHashMap.put( "10", "October" );
        monthHashMap.put( "11", "November" );
        monthHashMap.put( "12", "December" );
    }

    */

    /**
     * <K, V> K getMapKey(Map<K, V> map, V value) {
     * for (Map.Entry<K, V> entry : map.entrySet( )) {
     * if (entry.getValue( ).equals( value )) {
     * return entry.getKey( );
     * }
     * }
     * return null;
     * }
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText( getApplicationContext( ), "Permission Granted", Toast.LENGTH_SHORT ).show( );
                createfolder( );
            } else {
                Toast.makeText( this, "Please grant the permission", Toast.LENGTH_SHORT ).show( );
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (filter != null) {
            if (filter.equals( "y" ))
                finish( );
        } else {
            if (back_pressed + 1500 > System.currentTimeMillis( ))
                super.onBackPressed( );
            else
                Toast.makeText( getBaseContext( ), "Tap again to exit!", Toast.LENGTH_SHORT ).show( );
            back_pressed = System.currentTimeMillis( );
        }
    }
}