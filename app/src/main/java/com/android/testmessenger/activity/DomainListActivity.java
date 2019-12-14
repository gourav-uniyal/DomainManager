package com.android.testmessenger.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.testmessenger.interfaces.ApiInterface;
import com.android.testmessenger.libs.ApiClient;
import com.android.testmessenger.model.Domain;
import com.android.testmessenger.R;
import com.android.testmessenger.adapter.HomeAdapter;
import com.android.testmessenger.model.Message;
import com.android.testmessenger.model.ResponseDomain;
import com.android.testmessenger.model.ResponseDomainList;
import com.android.testmessenger.model.ResponseMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DomainListActivity extends AppCompatActivity {

    @BindView(R.id.rv_domain_list)
    RecyclerView rvDomainList;
    @BindView(R.id.progressBar_domain_list)
    ProgressBar footerProgressBar;


    private ArrayList<Domain> arrayList;
    private HomeAdapter homeAdapter;

    private static String folderPath;
    private String year;
    private String month;
    private String date;
    private String country;
//    private String state;
//    private String city;

    private String message = "";

    private int PAGE_START = 1;
    private int TOTAL_PAGE = 1;

    private static final String TAG = "DomainListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_domain_list );

        ButterKnife.bind( this );

        getIntentData( );

        initRecyclerView( );

        getDomainList( 1 );
    }

    /**
     * this function fetch intent data
     */
    void getIntentData() {
        folderPath = getIntent( ).getStringExtra( "folderPath" );
        year = getIntent( ).getStringExtra( "year" );
        month = getIntent( ).getStringExtra( "month" );
        date = getIntent( ).getStringExtra( "date" );
        country = getIntent( ).getStringExtra( "country" );
//        state = getIntent().getStringExtra( "state" );
//        city = getIntent().getStringExtra( "city" );
    }

    /**
     * this function initialises recyclerview
     */
    void initRecyclerView() {

        arrayList = new ArrayList<>( );

        rvDomainList.setLayoutManager( new LinearLayoutManager( getApplicationContext( ), RecyclerView.VERTICAL, false ) );
        rvDomainList.setNestedScrollingEnabled( false );
        rvDomainList.addOnScrollListener( new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
                int lastVisibleItem = 0;
                int totalItemCount = Objects.requireNonNull( recyclerView.getAdapter( ) ).getItemCount( );
                lastVisibleItem = ((LinearLayoutManager) Objects.requireNonNull( recyclerView.getLayoutManager( ) )).findLastVisibleItemPosition( );

                if (PAGE_START < TOTAL_PAGE)
                    if (lastVisibleItem == totalItemCount - 1) {
                        ++PAGE_START;
                        footerProgressBar.setVisibility( View.VISIBLE );
                        getDomainList( PAGE_START );
                    }
            }
        } );


        homeAdapter = new HomeAdapter( getApplicationContext( ), arrayList );
        rvDomainList.setAdapter( homeAdapter );
    }


    /**
     * this function calls api to get domain list
     */
    void getDomainList(int page) {
        HashMap<String, String> map = new HashMap<>( );
        map.put( "year", year );
        map.put( "month", month );
        map.put( "date", date );
        map.put( "country", country );
//        map.put( "state", state);
//        map.put( "city", city);

        ApiInterface apiInterface = ApiClient.getRetrofitInstance( ).create( ApiInterface.class );
        Call<ResponseDomain> call = apiInterface.getDomainList( page, map );
        call.enqueue( new Callback<ResponseDomain>( ) {
            @Override
            public void onResponse(Call<ResponseDomain> call, Response<ResponseDomain> response) {
                ResponseDomain responseDomain = response.body( );
                Log.d( TAG, "onResponse: "+ response.body() );
                if (responseDomain != null && responseDomain.getStatus( ).equals( "success" )) {
                    TOTAL_PAGE = Integer.parseInt( responseDomain.getResponseDomainList( ).getLastPage( ) );
                    arrayList.addAll( responseDomain.getResponseDomainList( ).getDomainArrayList( ) );
                    homeAdapter.notifyDataSetChanged( );
                    footerProgressBar.setVisibility( View.GONE );
                }
            }
            @Override
            public void onFailure(Call<ResponseDomain> call, Throwable t) {
                Toast.makeText( DomainListActivity.this, t.getLocalizedMessage( ), Toast.LENGTH_SHORT ).show( );
            }
        } );
    }

    /**
     * this function creates the excel file in app folder
     */
    void createExcelFile() {

        File file = new File( folderPath, "06-11-19" + getRandomString( ) + ".xls" );
        WorkbookSettings wbSettings = new WorkbookSettings( );
        wbSettings.setLocale( new Locale( "en", "EN" ) );
        try {
            WritableWorkbook workbook = Workbook.createWorkbook( file, wbSettings );
            createExcelSheet( workbook );
            workbook.write( );
            workbook.close( );
            Toast.makeText( this, "Exported", Toast.LENGTH_SHORT ).show( );
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

    /**
     * this function creates excel sheet.
     */
    void createExcelSheet(WritableWorkbook workbook) {

        try {
            WritableSheet sheet = workbook.createSheet( "sheet1", 0 );

            sheet.addCell( new Label( 0, 0, "Name" ) );
            sheet.addCell( new Label( 1, 0, "DomainName" ) );
            sheet.addCell( new Label( 2, 0, "Country" ) );
            sheet.addCell( new Label( 3, 0, "CompanyName" ) );
            sheet.addCell( new Label( 4, 0, "PhoneNumber" ) );

            for (int i = 0; i < arrayList.size( ); i++) {
                sheet.addCell( new Label( 0, i + 1, arrayList.get( i ).getName( ) ) );
                sheet.addCell( new Label( 1, i + 1, arrayList.get( i ).getDomainName( ) ) );
                sheet.addCell( new Label( 2, i + 1, arrayList.get( i ).getRegistrantCountry( ) ) );
//                sheet.addCell( new Label( 2, i + 1, arrayList.get(i).getCity() ) );
//                sheet.addCell( new Label( 3, i + 1, arrayList.get(i).getCompanyName( ) ) );
                sheet.addCell( new Label( 4, i + 1, arrayList.get( i ).getRegistrantNumber( ) ) );
            }

        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

    /**
     * this function generate random string of given length
     *
     * @return string of length n
     */
    String getRandomString() {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder( 4 );

        for (int i = 0; i < 4; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length( ) * Math.random( ));
            // add Character one by one in end of sb
            sb.append( AlphaNumericString.charAt( index ) );
        }
        return sb.toString( );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 2) {
            if (data != null) {
                year = data.getStringExtra( "year" );
                month = data.getStringExtra( "month" );
                date = data.getStringExtra( "date" );
                country = data.getStringExtra( "country" );
//                state = data.getStringExtra( "state" );
//                city = data.getStringExtra( "city" );
                getDomainList( 1 );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate( R.menu.domain_list, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );
        if (id == R.id.action_exports) {
            createExcelFile( );
            return true;
        } else if (id == R.id.action_filter) {
            Intent intent = new Intent( getApplicationContext( ), HomeActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            intent.putExtra( "filter", "y" );
            startActivityForResult( intent, 2 );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

}