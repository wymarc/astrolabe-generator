/**
 * $Id: AstrolabeGenerator.java,v 3.1
 * <p/>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or(at your option) any later version.
 * <p/>
 * The Astrolabe Generator is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * <p/>
 * Copyright (c) 2017 Timothy J. Mitchell
 */
package com.wymarc.astrolabe.generator.io;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.gui.GeneratorGui;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileHandler {

    public static Astrolabe openAstrolabeFile(){
        Astrolabe savedAstrolabe = new Astrolabe();

        return savedAstrolabe;
    }

    public static boolean saveAstrolabe(Astrolabe myAstrolabe){
        boolean success = true;

        // get settings
        String userName = myAstrolabe.getUserName();
        String locationName = myAstrolabe.getLocation().getLocationName();
        double latitude = myAstrolabe.getLocation().getLatitude();
        double longitude = myAstrolabe.getLocation().getLongitude();
        int degreeInterval = myAstrolabe.getDegreeInterval();
        int azimuthInterval = myAstrolabe.getAzimuthInterval();
        int almucanterInterval = myAstrolabe.getAlmucanterInterval();
        int degreeScaleType = myAstrolabe.getDegreeScaleType();
        boolean showAzimuthLines = myAstrolabe.getShowAzimuthLines();
        boolean showTwilightLines = myAstrolabe.getShowTwilightLines();
        boolean showHousesofHeavenLines = myAstrolabe.getShowHousesofHeavenLines();
        boolean showUnequalHoursLines = myAstrolabe.getShowUnequalHoursLines();
        boolean showThrone = myAstrolabe.getShowThrone();
        int shapeOption = myAstrolabe.getShapeOption();
        boolean zodiacSymbols = myAstrolabe.getShowZodiacSymbols();
        int hourMarkings = myAstrolabe.getHourMarkings();
        boolean showRegistrationMarks = myAstrolabe.getShowRegistrationMarks();
        boolean showTimeCorrection = myAstrolabe.getShowTimeCorrection();
        boolean showCotangentScale = myAstrolabe.getShowCotangentScale();
        boolean showLunarMansions = myAstrolabe.getShowLunarMansions();
        int frontPrintOption = myAstrolabe.getFrontPrintOption();
        boolean showHorizonPlate = myAstrolabe.getShowHorizonPlate();
        int reteType = 0;
        int topLeft = myAstrolabe.getTopLeft();
        int topRight = myAstrolabe.getTopRight();
        int bottomLeft = myAstrolabe.getBottomLeft();
        int bottomRight = myAstrolabe.getBottomRight();
        boolean counterChanged = myAstrolabe.isCounterChanged();

        return success;
    }

    /**
     * Saves the Astrolabe_assembly.pdf to the local machine
     * @return success or failure
     */
    public static boolean saveInstructable(){
        boolean success;

        String filePath = getSavePath();
        GeneratorGui.MY_ASTROLABE.setFilePath(filePath);

        try{
            InputStream in = FileHandler.class.getResourceAsStream("/com/wymarc/astrolabe/generator/reference/Astrolabe_assembly.pdf");
            OutputStream out = new FileOutputStream(filePath + "/Astrolabe_assembly.pdf");
            byte[] buffer = new byte[1024];
            int len = in.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = in.read(buffer);
            }
            out.close();
            JOptionPane.showMessageDialog(null, "File saved to:\n" + filePath);
            success = true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"There was a problem saving the file","Save Error",JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        return success;
    }

    /**
     * Pops up a dialog to allow the user to choose where to save files
     * @return The path to the desired folder
     */
    public static String getSavePath(){
        JFileChooser chooser = new JFileChooser();
        if (null == GeneratorGui.MY_ASTROLABE.getFilePath()){
            chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
        }else{
            chooser.setCurrentDirectory(new java.io.File(GeneratorGui.MY_ASTROLABE.getFilePath()));
        }
        chooser.setDialogTitle("Select location to save to");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setApproveButtonText("Select");
        // Set the mnemonic
        chooser.setApproveButtonMnemonic('s');
        // Set the tool tip
        chooser.setApproveButtonToolTipText("Save here");


        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        String filePath = chooser.getSelectedFile().getPath();
        Boolean noProblems = setFilePath(filePath);

        if (noProblems){
            GeneratorGui.MY_ASTROLABE.setFilePath(filePath);
            return filePath;
        }

        return null;
    }

    /**
     * Tests for the existance of the traget folder, attempts to create it if it does not exist
     * @param filePathIn The path to the desired folder
     * @return True if a legal path, false otherwise
     */
    private static Boolean setFilePath(String filePathIn) {
        // first check to see if the path exists if not create it
        File path = new File(filePathIn);
        boolean exists = path.exists();
        if (!exists) {
            try{
                return path.mkdir();
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Error -- Folder path does not exist and cannot be created");
                exists = false;
            }
        }

        return exists;
    }

    /**
     * Save an individual datastring to a given file location
     * @param target Path and file name
     * @param fileData Data to be saved
     * @return Success or failure
     */
    public static Boolean saveFile(String target, String fileData){

        try {
            FileWriter outFile = new FileWriter(target);
            PrintWriter out = new PrintWriter(outFile);
            out.print(fileData);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * Updates an existing zipfile stream object with a new entry
     * @param fileName Name of the file to be added
     * @param zos ZipOutputStream to be updated
     * @param dataString Data to be saved in the file
     */
    public static void updateZip(String fileName, ZipOutputStream zos, String dataString){
        try {
            if (null == dataString && fileName.endsWith("/")){ //this is a folder
                ZipEntry ze1 = new ZipEntry(fileName);
                zos.putNextEntry(ze1);
                zos.closeEntry();
                zos.flush();
                return;
            }

            byte[] buf = new byte[1024];
            ZipEntry ze1 = new ZipEntry(fileName);
            zos.putNextEntry(ze1);
            InputStream is = new ByteArrayInputStream(dataString.getBytes(Charset.defaultCharset()));
            int len;
            while ((len = is.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            zos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
