package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

    static int ERROR_NUMBER;
    static int TOTAL_NUMBER;

    public static void main(String[] args){
        ensureList();
    }


    static void ensureList(){
        for (int i = 1000; i < 4000; i++) {
            File f = new File("C:\\Users\\lieniu\\Desktop\\bookurl\\" + i);
            File[] files = f.listFiles();
            if(files.length != 40){
                System.out.println(i + ",");
            }
        }
    }
    static void loadBook(){
        try {
            TOTAL_NUMBER++;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\user\\lieniu\\Desktop\\bookurl\\1000\\2.txt")));
            String url = reader.readLine();
            String name = reader.readLine();
            name = name.substring(0,name.length() - 4);
            if(true){
                ERROR_NUMBER++;
                return;
            }
            String index = reader.readLine();
            String img = reader.readLine();
            reader.readLine();
            String leibie = reader.readLine();
            reader.readLine();
            String author = reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            String status = reader.readLine();//0为连载
            reader.readLine();
            reader.readLine();
            reader.readLine();
            String textNumber = reader.readLine();
            reader.readLine();
            String last = reader.readLine().replaceAll("-","");
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                line = line.trim();
                line = line.replaceAll("<br>","");
                line = line.replaceAll("</br>","");
                line = line.replaceAll("<br/>","");
                if(line.indexOf("bxwx9.Org") != -1){
                    line = last.substring(line.indexOf("bxwx9.Org") + 9);
                }
                if(last.indexOf("wWw.BxWx9.Org") != -1){
                    line = last.substring(0,line.indexOf("wWw.BxWx9.Org"));
                }
                builder.append(line);
            }
            String neirong = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
