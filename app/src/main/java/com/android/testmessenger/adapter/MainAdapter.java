package com.android.testmessenger.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.testmessenger.Domain;
import com.android.testmessenger.R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Domain> arrayList;
    private static final String TAG = "MainAdapter";

    public MainAdapter(Context context, ArrayList<Domain> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.row_numbers, parent, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.lblCity.setText( arrayList.get( position ).getCity() );
        holder.lblCompanyName.setText( arrayList.get( position ).getCompanyName() );
        holder.lblDomainName.setText( arrayList.get( position ).getDomainName() );
        holder.lblName.setText( arrayList.get( position ).getName() );
        holder.lblPhoneNumber.setText( arrayList.get( position ).getPhoneNumber() );
        holder.imgCall.setOnClickListener( v -> startCall( arrayList.get( position ).getPhoneNumber() ) );
        holder.imgMessaging.setOnClickListener( v -> sendTextMessage( arrayList.get( position ).getPhoneNumber() ) );
        holder.imgWhatsapp.setOnClickListener( v -> sendWhatsAppMessage( arrayList.get( position ).getPhoneNumber() ) );
    }

    @Override
    public int getItemCount() {
        return arrayList.size( );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView( R.id.lbl_city ) TextView lblCity;
        @BindView( R.id.lbl_company_name ) TextView lblCompanyName;
        @BindView( R.id.lbl_domain_name ) TextView lblDomainName;
        @BindView( R.id.lbl_name ) TextView lblName;
        @BindView(R.id.lbl_phone_number) TextView lblPhoneNumber;
        @BindView(R.id.imageView_call) ImageView imgCall;
        @BindView(R.id.imageView_text_message) ImageView imgMessaging;
        @BindView(R.id.imageView_whatsapp_message) ImageView imgWhatsapp;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }

    /**
     * this function make call to the given @param phoneNumber and comes back to the app on back pressed.
     * @param phoneNumber
     */
    private void startCall(String phoneNumber) {
        Log.d( TAG, "startCall: True" );
        Intent callIntent = new Intent( Intent.ACTION_CALL, Uri.parse( "tel:" + phoneNumber ) );
        callIntent.addFlags( FLAG_ACTIVITY_NEW_TASK );
        context.startActivity( callIntent );
    }

    /**
     * this function open sms app and send sms to the given @param phoneNumber and returns to the app on back pressed.
     * @param phoneNumber
     */
    private void sendTextMessage(String phoneNumber) {
        Log.d( TAG, "sendTextMessage: True" );
        String messageToSend = "Hey! Whatsup";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("smsto:" + phoneNumber)); // This ensures only SMS apps respond
        intent.putExtra("sms_body", messageToSend);
        context.startActivity(intent);
        /*try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, messageToSend, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }*/
    }

    /**
     * this function open whatsapp chat of given number and message return to the app screen on exiting whatsapp.
     * @param phoneNumber
     */
    private void sendWhatsAppMessage(String phoneNumber) {
        Log.d( TAG, "sendWhatsAppMessage: True" );
        String messageToSend = "Hey! Whatsup";
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.addFlags( FLAG_ACTIVITY_NEW_TASK );
            sendIntent.setPackage("com.whatsapp");
            String url = "https://wa.me/" + "+91" + phoneNumber + "?text=" + URLEncoder.encode(messageToSend, "UTF-8");
            sendIntent.setData(Uri.parse(url));
            if(sendIntent.resolveActivity(context.getPackageManager()) != null)
                context.startActivity(sendIntent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace( );
        }
    }
}