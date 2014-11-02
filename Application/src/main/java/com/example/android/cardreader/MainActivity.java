/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.cardreader;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.android.bluetooth.v2.DeviceScanActivity;
import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;
import com.example.android.enums.Codeepy;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private boolean isNfcExist = true;
    private boolean isNfcEnabled;
    private boolean isBluetoothEnabled;
    private boolean isNfcBroadcast = false;

    private ImageButton btnNfc;
    private ImageButton btnBluetooth;
    private ImageButton btnSwm;

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CardReaderFragment fragment = new CardReaderFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        btnNfc = (ImageButton) findViewById(R.id.btn_nfc);
        btnBluetooth = (ImageButton) findViewById(R.id.btn_bluetooth);
        btnSwm = (ImageButton) findViewById(R.id.btn_swm);

//        btnNfc.setOnClickListener(this);
//        btnBluetooth.setOnClickListener(this);
//        btnSwm.setOnClickListener(this);
//        btnSwm.setOnLongClickListener(this);
    }

    /**
     * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
     * the activity had been stopped, but is now again being displayed to the
     * user.  It will be followed by {@link #onResume}.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onCreate
     * @see #onStop
     * @see #onResume
     */
    @Override
    protected void onStart() {
        super.onStart();
        NfcManager manager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter == null) {
            isNfcExist = false;
            finish();
            return;
        }

        if (adapter.isEnabled()) {
            isNfcEnabled = true;
            btnNfc.setBackground(getResources().getDrawable(R.drawable.button_on));
        } else {
            isNfcEnabled = false;
            btnNfc.setBackground(getResources().getDrawable(R.drawable.button_off));
        }

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            isBluetoothEnabled = true;
            btnBluetooth.setBackground(getResources().getDrawable(R.drawable.button_on));
        } else {
            isBluetoothEnabled = false;
            btnBluetooth.setBackground(getResources().getDrawable(R.drawable.button_off));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
//        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
//        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_bluetooth:
                Intent intent = new Intent(this, DeviceScanActivity.class);
                startActivity(intent);
//            case R.id.menu_toggle_log:
//                mLogShown = !mLogShown;
//                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
//                if (mLogShown) {
//                    output.setDisplayedChild(1);
//                } else {
//                    output.setDisplayedChild(0);
//                }
//                supportInvalidateOptionsMenu();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Create a chain of targets that will receive log data */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nfc:
                if (!isNfcExist) {
                    Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                }
                break;
            case R.id.btn_bluetooth:
                startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                break;
            case R.id.btn_swm:
                Log.v(Codeepy.TAG.toString(), "IM SLAVE");
                btnSwm.setBackground(getResources().getDrawable(R.drawable.button_on));
                break;
        }
    }


    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    public boolean onLongClick(View v) {
        Log.v(Codeepy.TAG.toString(), "IM MASTER");
        btnSwm.setBackground(getResources().getDrawable(R.drawable.button_master));
        if (!isNfcBroadcast) {
            isNfcBroadcast = true;
//            nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
//                    null);
        } else {
            isNfcBroadcast = false;
//            nfcAdapter.disableReaderMode(this);
        }
        return true;
    }
}
