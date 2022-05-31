package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args){
        GameProgress gameProgress1 = new GameProgress(123, 321, 99, 100);
        GameProgress gameProgress2 = new GameProgress(324, 523, 12, 200);
        GameProgress gameProgress3 = new GameProgress(564, 564, 55, 300);

        List<String> saveDats = new ArrayList<>();
        saveDats.add("c:\\Games\\savegames\\save1.dat");
        saveDats.add("c:\\Games\\savegames\\save2.dat");
        saveDats.add("c:\\Games\\savegames\\save3.dat");

        saveGame(saveDats.get(0), gameProgress1);
        saveGame(saveDats.get(1), gameProgress2);
        saveGame(saveDats.get(2), gameProgress3);

        zipFiles("c:\\Games\\savegames\\zip.zip", saveDats);

        clearFiles(saveDats);

    }

    public static void saveGame(String str, GameProgress gameProgress){
        try (FileOutputStream fos = new FileOutputStream(str);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zip, List<String> saveDats) {
        try (FileOutputStream fos = new FileOutputStream(zip);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (String saveDat : saveDats) {
                File fileToZip = new File(saveDat);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void clearFiles(List<String> saveDats){
        for (String saveDat : saveDats) {
            File tempFile = new File(saveDat);
            tempFile.delete();
        }
    }
}
