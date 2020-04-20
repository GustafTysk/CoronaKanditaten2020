package com.example.coronakanditaten2020;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class Datahandler {
     private static final String FILE_NAME = "example.txt";
     private static final String TAG = "TestaLog";


    public Datahandler() {
    }

    public void save(String textSave, Context ctx) {
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILE_NAME, ctx.MODE_PRIVATE);
            fos.write(textSave.getBytes());
            Log.d(TAG, "onCreate: Started");
            //Toast.makeText(Context , this, "Saved to " + ctx.getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(Context ctx) {
        FileInputStream fis = null;
        try {
            fis = ctx.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String textSave;
            while((textSave = br.readLine()) != null) {
                sb.append(textSave).append("\n");
            }

            //textSave.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
