package com.example.internal_external_storage;

import android.content.ContextWrapper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText ed1;
    Button b1,b2;
    String filename = "myText";
    String filePath = "/MyStorage";
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText)findViewById(R.id.ed1);
        b1 =(Button)findViewById(R.id.submit);
        b2 =(Button)findViewById(R.id.show);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            b1.setEnabled(false);
        }
        else {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            file = new File(contextWrapper.getExternalFilesDir(filePath),filename);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed1.getText().toString();

                if (name.equals("")) {
                    ed1.setError("");
                }

                try {
                    FileOutputStream fout = new FileOutputStream(file);
                    fout.write(name.getBytes());
                    fout.close();

                    ed1.setText("");


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fin = new FileInputStream(file);
                    DataInputStream inputStream = new DataInputStream(fin);
                    InputStreamReader reader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(reader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line  = null;
                    while((line = bufferedReader.readLine())!= null)
                    {
                        stringBuilder.append(line);
                    }

                    fin.close();
                    ed1.setText(""+ stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        // Internal Storage

//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = ed1.getText().toString();
//
//                if (name.equals(""))
//                {
//                    ed1.setError("Please write name");
//                }
//                else
//                {
//                    try{
//                        FileOutputStream fout = openFileOutput(filename,MODE_PRIVATE);
//                        OutputStreamWriter writer = new OutputStreamWriter(fout);
//                        writer.write(name);
//                        writer.close();
//                        ed1.setText("");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    FileInputStream fin = openFileInput(filename);
//                    InputStreamReader reader = new InputStreamReader(fin);
//
//                    BufferedReader bufferedReader = new BufferedReader(reader);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line = null;
//                    while((line = bufferedReader.readLine())!= null){
//                        stringBuilder.append(line);
//
//                    }
//                    fin.close();
//                    reader.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private static boolean isExternalStorageReadOnly()
    {
        String extStorage = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorage))
        {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable()
    {
        String extStorage =Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorage))
        {
            return true;
        }
        return false;
    }
}
