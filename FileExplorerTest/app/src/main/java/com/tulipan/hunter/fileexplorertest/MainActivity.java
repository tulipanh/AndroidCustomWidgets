package com.tulipan.hunter.fileexplorertest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static boolean mReadPermissionsGranted = false;
    private static final int REQUEST_READ_STORAGE = 101;

    private FileExplorer mExplorer;
    private ArrayList<String> mFilesList;
    private ListView mListView;
    private ArrayAdapter<String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExplorer = new FileExplorer(this);
        mFilesList = mExplorer.getFileNames();
        mFilesList.add(0, "..");

        mListView = (ListView) findViewById(R.id.test_listview);
        mListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mFilesList);
        mListView.setAdapter(mListAdapter);
        mListView.setTextFilterEnabled(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) mExplorer.setParentToCurrent();
                else mExplorer.setChildToCurrent(mFilesList.get(position));
                mFilesList.clear();
                for (String s : mExplorer.getFileNames()) mFilesList.add(s);
                mFilesList.add(0, "..");
                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    public boolean checkReadPermissions() {
        if (mReadPermissionsGranted) {
            return true;
        } else {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
            } else {
                mReadPermissionsGranted = true;
            }
            return mReadPermissionsGranted;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mReadPermissionsGranted = true;
                } else {
                    mReadPermissionsGranted = false;
                }
                break;
        }
    }
}
