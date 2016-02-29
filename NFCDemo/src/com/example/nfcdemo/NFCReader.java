package com.example.nfcdemo;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Parcelable;


	public  class NFCReader {
		Activity ThisActivity = null;
		private  NfcAdapter mAdapter;
		private  PendingIntent mPendingIntent;
		private  IntentFilter[] mFilters;
		private  String[][] mTechLists;
		private  NdefMessage[] mMessage;
		private String text="";
		NFCReader(Activity ThisActivity) {
			this.ThisActivity = ThisActivity;
		}

		void NFCtouch() {
			mAdapter = NfcAdapter.getDefaultAdapter(ThisActivity);
			mPendingIntent = PendingIntent.getActivity(ThisActivity, 0,
					new Intent(ThisActivity, ThisActivity.getClass())
							.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

			// Setup an intent filter for all MIME based dispatches

			IntentFilter ndef = new IntentFilter(
					NfcAdapter.ACTION_NDEF_DISCOVERED);
			try {
				ndef.addDataType("text/plain");
			} catch (MalformedMimeTypeException e) {
				throw new RuntimeException("fail", e);
			}
			mFilters = new IntentFilter[] { ndef, };

			// Setup a tech list for all NfcF tags
			mTechLists = new String[][] { new String[] { NfcF.class.getName() } };
		}

		void NFCResume() {
			if (mAdapter != null)
				mAdapter.enableForegroundDispatch(ThisActivity, mPendingIntent,
						mFilters, mTechLists);
		}

		void NFCPause() {

			if (mAdapter != null)
				mAdapter.disableForegroundDispatch(ThisActivity);
			
		}
		void NFCNewIntent(Intent intent){
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				mMessage = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					mMessage[i] = (NdefMessage) rawMsgs[i];
				}
			}
			NdefMessage msg = mMessage[0];
			try {
				byte[] payload = msg.getRecords()[0].getPayload();
				// Get the Text Encoding
				String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8"
						: "UTF-16";
				// Get the Language Code
				int languageCodeLength = payload[0] & 0077;
				String languageCode = new String(payload, 1, languageCodeLength,
						"US-ASCII");
				// Get the Text
				String text = new String(payload, languageCodeLength + 1,
						payload.length - languageCodeLength - 1, textEncoding);
				this.text=text;

			} catch (Exception e) {

			}
		}
		String gettext(){
			return text;
		}
	}
