package com.example.nfcdemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NFC_dispatch extends Activity { // Read

	private static TextView mText;
	private Button btn;	
	NFCReader nfc = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_dispatch);

		mText = (TextView) findViewById(R.id.text2);
		btn = (Button) findViewById(R.id.btn2push);
		btn.setOnClickListener(go);

		nfc = new NFCReader(NFC_dispatch.this);
		nfc.NFCtouch();

	}

	private Button.OnClickListener go = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(NFC_dispatch.this, MainActivity.class);
			startActivity(it);
			NFC_dispatch.this.finish();
		}

	};

	@Override
	public void onNewIntent(Intent intent) {
		nfc.NFCNewIntent(intent);
		mText.setText(nfc.gettext());
	}

	@Override
	public void onResume() {
		super.onResume();
		nfc.NFCResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		nfc.NFCPause();

	}



}
