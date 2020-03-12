package com.screenable.tcivision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeReaderListener{
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private BarcodeReaderFragment mBarcodeReaderFragment;
    public Button connect;
    public EditText electionId;
    public EditText accessToken;
    public BarcodeReaderFragment barcodeReaderFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        connect= findViewById(R.id.connect);
        accessToken = findViewById(R.id.accesstoken);
        electionId = findViewById(R.id.electionid);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String at=accessToken.getText().toString();
                String eid=electionId.getText().toString();
                new SocketConnect().SocketConnect("connection",at,eid,getApplicationContext());
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
            }
        });


        mBarcodeReaderFragment = attachBarcodeReaderFragment();
    }
    private BarcodeReaderFragment attachBarcodeReaderFragment() {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        BarcodeReaderFragment fragment = BarcodeReaderFragment.newInstance(true, false);
        fragment.setListener(this);
        fragmentTransaction.replace(R.id.fm_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();

        return fragment;
    }



    @Override
    public void onScanned(Barcode barcode) {
//        alert server
//        if (SocketConnect == null){
//
//        }
//        TODO::Fix socket is null
        if (SocketConnect.isConnected){
            SocketConnect.socket.emit(accessToken.getText().toString()+electionId.getText().toString(),barcode.rawValue);
            Toast.makeText(getApplicationContext(),barcode.rawValue+" scanned", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"socket disconnected", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }


    public class ConnectToServer extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {

            return null;
        }
    }
}
