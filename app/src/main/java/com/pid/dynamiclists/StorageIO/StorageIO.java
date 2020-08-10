package com.pid.dynamiclists.StorageIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The type Storage io.
 * @author Ilya Mishin
 */
public class StorageIO {
    /**
     * Write file.
     *
     * @param filePath     the file path
     * @param fileName     the file name
     * @param yourJsonFile the your json file
     */
    public static void writeFile(File filePath, String fileName, String yourJsonFile){
        //Get FilePath and use it to create File
        String yourFilePath = filePath + "/" + fileName;
        File yourFile = new File(yourFilePath);
        //Create FileOutputStream, yourFile is part of the constructor
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(yourFile);

            //Convert JSON String to Bytes and write() it
            fileOutputStream.write(yourJsonFile.getBytes());
            //Finally flush and close FileOutputStream
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read file string.
     *
     * @param filePath the file path
     * @param fileName the file name
     * @return the string
     */
    public static String readFile(File filePath, String fileName) {
        String text = "";
        //Make sure to use a try-catch statement to catch any errors
        try {
            //Make your FilePath and File
            String yourFilePath = filePath + "/" + fileName;
            File yourFile = new File(yourFilePath);
            //Make an InputStream with your File in the constructor
            InputStream inputStream = new FileInputStream(yourFile);
            StringBuilder stringBuilder = new StringBuilder();
            //Check to see if your inputStream is null
            //If it isn't use the inputStream to make a InputStreamReader
            //Use that to make a BufferedReader
            //Also create an empty String
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                //Use a while loop to append the lines from the Buffered reader
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                //Close your InputStream and save stringBuilder as a String
                inputStream.close();
                text = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            //Log your error with Log.e
        }
        return text;
    }
}
