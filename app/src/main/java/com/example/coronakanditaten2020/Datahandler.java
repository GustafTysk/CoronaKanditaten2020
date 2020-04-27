package com.example.coronakanditaten2020;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Datahandler {
     private static final String FILE_NAME = "example.txt";
     Location[] heatmaplocations=new Location[12];

    public Datahandler() {
        CreatDummylocations();

    }

    public Location[] getHeatmaplocations() {
        return heatmaplocations;
    }

    void CreatDummylocations(){

        heatmaplocations[0] = new Location("59.858565", "17.638927", "2020-10-15", 0, 0, 2, 0, 3, 0, 1, 1, 0, 2, "2020-10-17 10:30");
        heatmaplocations[1] = new Location("59.858568", "17.638927", "2020-10-15", 1, 2, 3, 0, 2, 1, 1, 2, 1, 1, "2020-10-17 10:30");
        heatmaplocations[2] = new Location("59.858561", "17.638927", "2020-10-15", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-10-17 10:30");
        heatmaplocations[3] = new Location("59.858572", "17.638927", "2020-10-15", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-10-17 10:30");
        heatmaplocations[4] = new Location("59.858522", "17.638927", "2020-10-16", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-10-17 10:30");
        heatmaplocations[5] = new Location("59.858362", "17.638927", "2020-10-15", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-10-17 10:30");
        heatmaplocations[6] = new Location("59.858462", "17.638927", "2020-10-15", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-10-17 10:30");
        heatmaplocations[7] = new Location("59.858562", "17.638927", "2020-10-16", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-10-17 10:30");
        heatmaplocations[8] = new Location("59.858762", "17.638927", "2020-10-15", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-10-17 10:30");
        heatmaplocations[9] = new Location("59.858962", "17.638927", "2020-10-15", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-10-17 10:30");
        heatmaplocations[10] = new Location("59.856362", "17.638927", "2020-10-15", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-10-17 10:30");
        heatmaplocations[11] = new Location("59.857462", "17.638927", "2020-10-15", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-10-17 10:30");

    }



































    public void save(String textSave, Context ctx) {
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILE_NAME, ctx.MODE_PRIVATE);
            fos.write(textSave.getBytes());
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
