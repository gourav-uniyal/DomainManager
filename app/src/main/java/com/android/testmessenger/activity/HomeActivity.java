package com.android.testmessenger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.testmessenger.Domain;
import com.android.testmessenger.R;
import com.android.testmessenger.adapter.MainAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class HomeActivity extends AppCompatActivity {

    @BindView( R.id.rv_main ) RecyclerView recyclerView;

    private MainAdapter mainAdapter;
    private ArrayList<Domain> arrayList;
    private static long back_pressed;
    private static String folderPath;
    private WritableWorkbook workbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        ButterKnife.bind( this );

        requestPermission();

        arrayList = new ArrayList<>( );

        Domain domain = new Domain( "Steve Jobs", "http://apple.com", "Dehradun", "Apple", "8126374588", "hey", "hey");
        arrayList.add( domain );
        arrayList.add( domain );

        mainAdapter = new MainAdapter(getApplicationContext(), arrayList);
        recyclerView.setLayoutManager( new LinearLayoutManager(this, RecyclerView.VERTICAL, false) );
        recyclerView.setAdapter( mainAdapter );
    }

    /**
     * this function creates the excel file in app folder
     */
    void createExcelFile(){

        File file = new File(folderPath, "file.xls");
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            createExcelSheet();
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this function creates excel sheet.
     */
    void createExcelSheet(){
        try {

            WritableSheet sheet = workbook.createSheet("sheet1", 0);

            sheet.addCell( new Label( 0, 0, "Name" ) );
            sheet.addCell( new Label( 1, 0, "DomainName" ) );
            sheet.addCell( new Label( 2, 0, "City") );
            sheet.addCell( new Label( 3, 0, "CompanyName" ) );
            sheet.addCell( new Label( 4, 0, "PhoneNumber" ) );
            sheet.addCell( new Label( 5, 0, "Whatsapp Message" ) );
            sheet.addCell( new Label( 6, 0, "SMS Text" ) );

            for (int i = 0; i < arrayList.size(); i++) {
                sheet.addCell( new Label( 0, i + 1, arrayList.get(i).getName() ) );
                sheet.addCell( new Label( 1, i + 1, arrayList.get(i).getDomainName() ) );
                sheet.addCell( new Label( 2, i + 1, arrayList.get(i).getCity() ) );
                sheet.addCell( new Label( 3, i + 1, arrayList.get(i).getCompanyName( ) ) );
                sheet.addCell( new Label( 4, i + 1, arrayList.get(i).getPhoneNumber() ) );
                sheet.addCell( new Label( 5, i + 1, arrayList.get(i).getWhatsappMessage()) );
                sheet.addCell( new Label( 6, i + 1, arrayList.get(i).getMessage() ) );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this function generate random string of given length
     * @param n -> length of string
     * @return string of length n
     */
    static String getRandomString(int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    /***
     * this function request runtime permissions for app
     */
    void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission_group.CALL_LOG, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.SEND_SMS}, 1 );
        } else
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS}, 1 );
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                createfolder();
            }
            else {
                Toast.makeText( this, "Please grant the permission", Toast.LENGTH_SHORT ).show( );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exports) {
            createExcelFile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 1500 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Tap again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}