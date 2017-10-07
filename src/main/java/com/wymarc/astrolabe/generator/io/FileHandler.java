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

    public static boolean saveInstructable(){
        boolean success = false;

        JFileChooser chooser = new JFileChooser();

        if (null == GeneratorGui.MY_ASTROLABE.getFilePath()){
            chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        }else{
            chooser.setCurrentDirectory(new java.io.File(GeneratorGui.MY_ASTROLABE.getFilePath()));
        }
        chooser.setDialogTitle("Select location to save file to");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setApproveButtonText("Select");
        // Set the mnemonic
        chooser.setApproveButtonMnemonic('s');
        // Set the tool tip
        chooser.setApproveButtonToolTipText("Save here");

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return success;
        }

        String filePath = chooser.getSelectedFile().getPath();
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
}
