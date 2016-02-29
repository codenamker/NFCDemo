package com.example.nfcdemo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//P2P代刚
@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private NfcAdapter adapter;
   
    private NdefMessage mMessage;
    private Button btn;
	Tag mytag;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		btn = (Button) findViewById(R.id.btn2get);
		btn.setOnClickListener(go);
				
		adapter = NfcAdapter.getDefaultAdapter(this);
		
			
        if (adapter != null) {
            
            Toast.makeText(this, "already set ndefmessage", Toast.LENGTH_LONG).show();
        } else {
        	Toast.makeText(this, "adapter is null", Toast.LENGTH_LONG).show();
        }
       
        
        mMessage = new NdefMessage(createTextRecord("糑! 硂琌いゅ代刚",Locale.getDefault(),true));		
		
	}
	//材把计boolean琌∕﹚ノutf8┪琌utf16絪絏種
	public NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
		
	    byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
	    Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
	    byte[] textBytes = payload.getBytes(utfEncoding);
	    int utfBit = encodeInUtf8 ? 0 : (1 << 7);
	    char status = (char) (utfBit + langBytes.length);
	    byte[] data = new byte[1 + langBytes.length + textBytes.length];
	    data[0] = (byte) status;
	    System.arraycopy(langBytes, 0, data, 1, langBytes.length);
	    System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
	    NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
	    NdefRecord.RTD_TEXT, new byte[0], data);
	    return record;
	}
	

	private Button.OnClickListener go = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(MainActivity.this,NFC_dispatch.class);
			startActivity(it);
			MainActivity.this.finish();
		}
		
	};

	//@SuppressWarnings("deprecation")
	@Override
	public void onResume(){
		super.onResume();
		if(adapter != null) adapter.enableForegroundNdefPush(this,mMessage);
		
	}
	
	//@SuppressWarnings("deprecation")
	@Override
	public void onPause(){
		super.onPause();
		if(adapter != null) adapter.disableForegroundNdefPush(this);
	}                              //⊿disable, 硈ぇ弄常Τ拜肈
}
