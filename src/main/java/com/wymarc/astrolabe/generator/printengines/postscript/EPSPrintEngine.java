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
package com.wymarc.astrolabe.generator.printengines.postscript;

import com.wymarc.astrolabe.generator.gui.GeneratorGui;
import com.wymarc.astrolabe.generator.printengines.postscript.extras.horary.AdvancedHoraryQuadrant;
import com.wymarc.astrolabe.generator.printengines.postscript.extras.horary.EqualHours;
import com.wymarc.astrolabe.generator.printengines.postscript.extras.horary.BasicHoraryQuadrant;
import com.wymarc.astrolabe.generator.printengines.postscript.extras.sine.VernierSineQuadrant;
import com.wymarc.astrolabe.generator.printengines.postscript.extras.sine.SineQuadrant;
import com.wymarc.astrolabe.generator.printengines.postscript.extras.universal.UniversalPrintEngine;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EPSPrintEngine {	

    private Boolean setFilePath(String filePathIn) {
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

    private Boolean save(String target, String fileData){

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
     * creates an Astrolabe object and sets its properties then
     * creates and runs the appropriate print engines
     */
    public void saveFiles(){

        JFileChooser chooser = new JFileChooser();
        if (null == GeneratorGui.MY_ASTROLABE.getFilePath()){
            chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
        }else{
            chooser.setCurrentDirectory(new java.io.File(GeneratorGui.MY_ASTROLABE.getFilePath()));
        }
        chooser.setDialogTitle("Select location to save files to");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setApproveButtonText("Select");
        // Set the mnemonic
        chooser.setApproveButtonMnemonic('s');
        // Set the tool tip
        chooser.setApproveButtonToolTipText("Save here");


        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath = chooser.getSelectedFile().getPath();
        GeneratorGui.MY_ASTROLABE.setFilePath(filePath);

        // verify the filepath and the Astrolabe Object
        Boolean noProblems = setFilePath(filePath) && (GeneratorGui.MY_ASTROLABE != null) ;
        String dataString;

		if (noProblems){			
			// create front side
	        FrontPrintEngine myAstrolabeFront = new FrontPrintEngine();
	        dataString = myAstrolabeFront.createFront(GeneratorGui.MY_ASTROLABE);
            save(filePath + "/AstrolabeFront.eps",dataString);

	        // create back side
	        BackPrintEngine myAstrolabeBack = new BackPrintEngine();
            dataString = myAstrolabeBack.createBack(GeneratorGui.MY_ASTROLABE);
            save(filePath + "/AstrolabeBack.eps",dataString);

	        // create Rete sheet
	        RetePrintEngine myAstrolabeRete = new RetePrintEngine();
            dataString = myAstrolabeRete.createRete(GeneratorGui.MY_ASTROLABE);
            save(filePath + "/AstrolabeRete.eps",dataString);

	        // create accessory sheet
            RulePrintEngine myAstrolabeRule = new RulePrintEngine();
            dataString = myAstrolabeRule.createCombinedSheet(GeneratorGui.MY_ASTROLABE, true);
            save(filePath + "/AstrolabeRules.eps",dataString);

            //print universal astrolabe
            if (GeneratorGui.MY_ASTROLABE.getPrintUniversalAstrolabe()){
                UniversalPrintEngine myUniversalAstrolabe = new UniversalPrintEngine();
                dataString = myUniversalAstrolabe.createPlate(GeneratorGui.MY_ASTROLABE);
                save(filePath + "/UniversalPlate.eps",dataString);
                dataString = myUniversalAstrolabe.createRete(GeneratorGui.MY_ASTROLABE);
                save(filePath + "/UniversalPlateRete.eps",dataString);
                dataString = myUniversalAstrolabe.createRegula(GeneratorGui.MY_ASTROLABE);
                save(filePath + "/UniversalPlateRegula.eps",dataString);
            }

            // create extras sheets
            if (GeneratorGui.MY_ASTROLABE.getPrintRuleSheet()){
                myAstrolabeRule = new RulePrintEngine();
                dataString = myAstrolabeRule.buildRulesSheet(GeneratorGui.MY_ASTROLABE,GeneratorGui.MY_ASTROLABE.isCounterChanged());
                save(filePath + "/AstrolabeRulesSheet.eps",dataString);
            }
            if (GeneratorGui.MY_ASTROLABE.getPrintAlidadeSheet()){
                myAstrolabeRule = new RulePrintEngine();
                dataString = myAstrolabeRule.buildAlidadeSheet(GeneratorGui.MY_ASTROLABE, GeneratorGui.MY_ASTROLABE.isCounterChanged());
                save(filePath + "/AstrolabeAlidadeSheet.eps",dataString);
            }
            // print climate plate sets, if any
//            for (JCheckBox chk : GeneratorGui.MY_ASTROLABE.getClimateSetCheckboxes()){
//                if (chk.isSelected()){
//                    //todo print the suckers
//                }
//            }

            // quadrants
            if (GeneratorGui.MY_ASTROLABE.getPrintBasicHoraryQuadrant()){
                BasicHoraryQuadrant basicHoraryQuad = new BasicHoraryQuadrant();
                dataString = basicHoraryQuad.printQuadrant();
                save(filePath + "/BasicHoraryQuadrant.eps",dataString);
            }
            if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedHoraryQuadrant()){
                AdvancedHoraryQuadrant advancedHoraryQuadrant = new AdvancedHoraryQuadrant();
                dataString = advancedHoraryQuadrant.printQuadrant(GeneratorGui.MY_ASTROLABE);
                save(filePath + "/AdvancedHoraryQuadrant.eps",dataString);
            }
            if (GeneratorGui.MY_ASTROLABE.getPrintSineQuadrant()){
                SineQuadrant sineQuadrant = new SineQuadrant();
                dataString = sineQuadrant.printQuadrant(false);
                save(filePath + "/SineQuadrant.eps",dataString);
            }
            if (GeneratorGui.MY_ASTROLABE.getPrintColorSineQuadrant()){
                SineQuadrant sineQuadrant = new SineQuadrant();
                dataString = sineQuadrant.printQuadrant(true);
                save(filePath + "/ColorSineQuadrant.eps",dataString);
            }
            if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedSineQuadrant()){
                VernierSineQuadrant vernierSineQuadrant = new VernierSineQuadrant();
                dataString = vernierSineQuadrant.printQuadrant();
                save(filePath + "/VernierSineQuadrant.eps",dataString);
            }
            if (GeneratorGui.MY_ASTROLABE.getPrintEqualHoursHoraryQuadrant()){
                EqualHours equalhoursQuadrantBack = new EqualHours(GeneratorGui.MY_ASTROLABE,false,false);
                dataString = equalhoursQuadrantBack.createQuadrantBack();
                save(filePath + "/EqualHoursQuadrantBack.eps",dataString);

                EqualHours equalhoursQuadrantFront = new EqualHours(GeneratorGui.MY_ASTROLABE,true,false);
                dataString = equalhoursQuadrantFront.createQuadrantFront();
                save(filePath + "/EqualHoursQuadrantFront.eps",dataString);

            }

            // for each climate set create a folder with the set name and print the climates to it
//            for (JCheckBox chk : GeneratorGui.MY_ASTROLABE.getClimateSetCheckboxes()){
//                if (chk.isSelected()){
//                    FrontPrintEngine climateEngine = new FrontPrintEngine();
//                    // make a local copy of the current settings
//                    Astrolabe workingAstrolabe = GeneratorGui.MY_ASTROLABE;
//                    //set it to hide the mater
//                    workingAstrolabe.setFrontPrintOption(1);
//                    workingAstrolabe.setShowHorizonPlate(false);
//                    // get the climate list
//                    List<ClimateSet> climateSets = GeneratorGui.MY_CONFIG.getClimateSets();
//                    for (ClimateSet set : climateSets){
//                        if (set.getName().equals(chk.getText())){
//                            String folder = set.getName();
//                            // test for folder and create it if needed
//                            File file = new File(filePath + "/" + folder);
//                            if (!file.exists()) {
//                                file.mkdir();
//                            }
//                            for(ClimatePlate plate :set.getClimates()){
//                                workingAstrolabe.getLocation().setLocation(plate.getLocation() + " 0000000E");
//                                String plateName = plate.getName();
//                                dataString = climateEngine.createFront(workingAstrolabe);
//                                save(filePath + "/" + folder + "/" + plateName + ".eps", dataString);
//                            }
//                            // Add horizons plate
//                            workingAstrolabe.setShowHorizonPlate(true);
//                            dataString = climateEngine.createFront(workingAstrolabe);
//                            save(filePath + "/" + folder + "/horizons.eps", dataString);
//                            workingAstrolabe.setShowHorizonPlate(false);
//                        }
//                    }
//                }
//            }


	        // todo
	        // quadrants, tools, instructions etc

//            Dastur myDastur = new Dastur();                     // todo tool
//            dataString = myDastur.createDastur();
//            save(filePath + "/myDastur.eps",dataString);
//
//            QuadrantTwo myAstrolabeQuad2 = new QuadrantTwo();  //todo small sine quadrant
//            dataString = myAstrolabeQuad2.printQuadrant();
//            save(filePath + "/QuadrantTwo.eps",dataString);
//
//            SineQuadrantOne mySineQuadrantOne = new SineQuadrantOne();  //todo colored for laser cutter
//            dataString = mySineQuadrantOne.printQuadrant();
//            save(filePath + "/SineQuadrantOne.eps",dataString);
//
//            DemoArms myDemoArms = new DemoArms();
//            dataString = myDemoArms.printArms();
//            save(filePath + "/DemoArms.eps",dataString);



            JOptionPane.showMessageDialog(null,"Files saved to:\n" + filePath);
	    } else{
            JOptionPane.showMessageDialog(null,"There was a problem saving the files","Save Error",JOptionPane.ERROR_MESSAGE);
        }
    }	    	
    

    /**
     * creates an Astrolabe object and sets its properties then
     * creates and runs the appropriate print engines
     */
    public void saveZip(){
    	//saving to zip: http://www.javaworld.com/community/node/8362

        JFileChooser chooser = new JFileChooser();
        if (null == GeneratorGui.MY_ASTROLABE.getFilePath()){
            chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
        }else{
            chooser.setCurrentDirectory(new java.io.File(GeneratorGui.MY_ASTROLABE.getFilePath()));
        }
        chooser.setDialogTitle("Select location to save zip file to");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setApproveButtonText("Select");
        // Set the mnemonic
        chooser.setApproveButtonMnemonic('s');
        // Set the tool tip
        chooser.setApproveButtonToolTipText("Save here");


        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        ZipOutputStream zos;
        String filePath = "";
        try {
            filePath = chooser.getSelectedFile() + "/astrolabe.zip";
            GeneratorGui.MY_ASTROLABE.setFilePath(filePath);

            zos = new ZipOutputStream(new FileOutputStream(filePath));

            //front
            FrontPrintEngine myAstrolabeFront = new FrontPrintEngine();
            String dataString = myAstrolabeFront.createFront(GeneratorGui.MY_ASTROLABE);
            updateZip("AstrolabeFront.eps", zos, dataString);

            //back
            BackPrintEngine myAstrolabeBack = new BackPrintEngine();
            dataString = myAstrolabeBack.createBack(GeneratorGui.MY_ASTROLABE);
            updateZip("AstrolabeBack.eps", zos, dataString);

            //rete
            RetePrintEngine myAstrolabeRete = new RetePrintEngine();
            dataString = myAstrolabeRete.createRete(GeneratorGui.MY_ASTROLABE);
            updateZip("AstrolabeRete.eps", zos, dataString);

            //rule
            RulePrintEngine myAstrolabeRule = new RulePrintEngine();
            dataString = myAstrolabeRule.createCombinedSheet(GeneratorGui.MY_ASTROLABE, true);
            updateZip("AstrolabeRule.eps", zos, dataString);

            //extras
            if (GeneratorGui.MY_ASTROLABE.getPrintRuleSheet()){
                dataString = myAstrolabeRule.buildRulesSheet(GeneratorGui.MY_ASTROLABE, GeneratorGui.MY_ASTROLABE.isCounterChanged());
                updateZip("AstrolabeRules.eps", zos, dataString);
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintAlidadeSheet()){
                dataString = myAstrolabeRule.buildAlidadeSheet(GeneratorGui.MY_ASTROLABE, GeneratorGui.MY_ASTROLABE.isCounterChanged());
                updateZip("AstrolabeAlidades.eps", zos, dataString);
            }

            // for each climate set create a folder with the set name and print the climates to it
//            for (JCheckBox chk : GeneratorGui.MY_ASTROLABE.getClimateSetCheckboxes()){
//                if (chk.isSelected()){
//                    FrontPrintEngine climateEngine = new FrontPrintEngine();
//                    // make a local copy of the current settings
//                    Astrolabe workingAstrolabe = GeneratorGui.MY_ASTROLABE;
//                    //set it to hide the mater
//                    workingAstrolabe.setFrontPrintOption(1);
//                    workingAstrolabe.setShowHorizonPlate(false);
//                    // get the climate list
//                    List<ClimateSet> climateSets = GeneratorGui.MY_CONFIG.getClimateSets();
//                    for (ClimateSet set : climateSets){
//                        if (set.getName().equals(chk.getText())){
//                            String folder = set.getName();
//                            updateZip(folder + "/", zos, null);
//                            for(ClimatePlate plate :set.getClimates()){
//                                workingAstrolabe.getLocation().setLocation(plate.getLocation() + " 0000000E");
//                                String plateName = plate.getName();
//                                dataString = climateEngine.createFront(workingAstrolabe);
//                                updateZip(folder + "/" + plateName + ".eps", zos, dataString);
//                            }
//                            // Add horizons plate
//                            workingAstrolabe.setShowHorizonPlate(true);
//                            dataString = climateEngine.createFront(workingAstrolabe);
//                            updateZip(folder + "/horizons.eps", zos, dataString);
//                            workingAstrolabe.setShowHorizonPlate(false);
//                        }
//                    }
//                }
//            }

            // quadrants
            if (GeneratorGui.MY_ASTROLABE.getPrintBasicHoraryQuadrant()){
                BasicHoraryQuadrant basicHoraryQuad = new BasicHoraryQuadrant();
                dataString = basicHoraryQuad.printQuadrant();
                updateZip("basicHoraryQuadrant.eps", zos, dataString);
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedHoraryQuadrant()){
                AdvancedHoraryQuadrant advancedHoraryQuadrant = new AdvancedHoraryQuadrant();
                dataString = advancedHoraryQuadrant.printQuadrant(GeneratorGui.MY_ASTROLABE);
                updateZip("AdvancedHoraryQuadrant.eps", zos, dataString);
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintSineQuadrant()){
                SineQuadrant sineQuadrant = new SineQuadrant();
                dataString = sineQuadrant.printQuadrant(false);
                updateZip("SineQuadrant.eps", zos, dataString);
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintColorSineQuadrant()){
                SineQuadrant sineQuadrant = new SineQuadrant();
                dataString = sineQuadrant.printQuadrant(true);
                updateZip("ColorSineQuadrant.eps", zos, dataString);
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedSineQuadrant()){
                VernierSineQuadrant vernierSineQuadrant = new VernierSineQuadrant();
                dataString = vernierSineQuadrant.printQuadrant();
                updateZip("VernierSineQuadrant.eps", zos, dataString);
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintEqualHoursHoraryQuadrant()){
                EqualHours equalhoursQuadrantBack = new EqualHours(GeneratorGui.MY_ASTROLABE,false,false);  //todo new quadrant
                dataString = equalhoursQuadrantBack.createQuadrantBack();
                updateZip("EqualHoursQuadrantBack.eps", zos, dataString);

                EqualHours equalhoursQuadrantFront = new EqualHours(GeneratorGui.MY_ASTROLABE,true,false);  //todo new quadrant
                dataString = equalhoursQuadrantFront.createQuadrantFront();
                updateZip("EqualHoursQuadrantFront.eps", zos, dataString);

            }


            // todo add other extras
            zos.close();
        }catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"There was a problem saving the ZIP archive","Save Error",JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null,"ZIP archive saved to: \n" + filePath);
    }

    private void updateZip(String fileName, ZipOutputStream zos, String dataString){
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

    public void printCurrent(){
       //todo  locate current selected tab and print that
        ArrayList<String> postScriptStrings = new ArrayList<String>();

        if (GeneratorGui.tabSelected.equals("Front")){
            FrontPrintEngine myAstrolabeFront = new FrontPrintEngine();
            postScriptStrings.add(myAstrolabeFront.createFront(GeneratorGui.MY_ASTROLABE));
        }else if (GeneratorGui.tabSelected.equals("Back")){
            BackPrintEngine myAstrolabeBack = new BackPrintEngine();
            postScriptStrings.add(myAstrolabeBack.createBack(GeneratorGui.MY_ASTROLABE));
        }else if (GeneratorGui.tabSelected.equals("Rete and Rules")){
            // create rete
            RetePrintEngine myAstrolabeRete = new RetePrintEngine();
            postScriptStrings.add(myAstrolabeRete.createRete(GeneratorGui.MY_ASTROLABE));
            // create rules
            RulePrintEngine myAstrolabeRules = new RulePrintEngine();
            postScriptStrings.add(myAstrolabeRules.createCombinedSheet(GeneratorGui.MY_ASTROLABE, true));
        }else if (GeneratorGui.tabSelected.equals("Quadrants")){
            //Quadrants
            if (GeneratorGui.MY_ASTROLABE.getPrintBasicHoraryQuadrant()){
                BasicHoraryQuadrant simpleHoraryQuadrant = new BasicHoraryQuadrant();
                postScriptStrings.add(simpleHoraryQuadrant.printQuadrant());
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedHoraryQuadrant()){
                AdvancedHoraryQuadrant advancedHoraryQuadrant = new AdvancedHoraryQuadrant();
                postScriptStrings.add(advancedHoraryQuadrant.printQuadrant(GeneratorGui.MY_ASTROLABE));
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintSineQuadrant()){
                SineQuadrant sineQuadrant = new SineQuadrant();
                postScriptStrings.add(sineQuadrant.printQuadrant(false));
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintColorSineQuadrant()){
                SineQuadrant sineQuadrant = new SineQuadrant();
                postScriptStrings.add(sineQuadrant.printQuadrant(true));
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedSineQuadrant()){
                VernierSineQuadrant vernierSineQuadrant = new VernierSineQuadrant();
                postScriptStrings.add(vernierSineQuadrant.printQuadrant());
            }
        }else if (GeneratorGui.tabSelected.equals("Extras")){
            //extras
            RulePrintEngine myAstrolabeRules = new RulePrintEngine();
            if (GeneratorGui.MY_ASTROLABE.getPrintRuleSheet()){
                postScriptStrings.add(myAstrolabeRules.buildRulesSheet(GeneratorGui.MY_ASTROLABE,
                        GeneratorGui.MY_ASTROLABE.isCounterChanged()));
            }

            if (GeneratorGui.MY_ASTROLABE.getPrintAlidadeSheet()){
                postScriptStrings.add(myAstrolabeRules.buildAlidadeSheet(GeneratorGui.MY_ASTROLABE,
                        GeneratorGui.MY_ASTROLABE.isCounterChanged()));
            }
        }else{
            return;
        }
        printPage(postScriptStrings);
    }

    public void printAll(){
        ArrayList<String> postScriptStrings = new ArrayList<String>();

        // create front side
        FrontPrintEngine myAstrolabeFront = new FrontPrintEngine();
        postScriptStrings.add(myAstrolabeFront.createFront(GeneratorGui.MY_ASTROLABE));

        // create back side
        BackPrintEngine myAstrolabeBack = new BackPrintEngine();
        postScriptStrings.add(myAstrolabeBack.createBack(GeneratorGui.MY_ASTROLABE));

        // create rete
        RetePrintEngine myAstrolabeRete = new RetePrintEngine();
        postScriptStrings.add(myAstrolabeRete.createRete(GeneratorGui.MY_ASTROLABE));

        // create rules
        RulePrintEngine myAstrolabeRules = new RulePrintEngine();
        postScriptStrings.add(myAstrolabeRules.createCombinedSheet(GeneratorGui.MY_ASTROLABE, true));

        //extras
        if (GeneratorGui.MY_ASTROLABE.getPrintRuleSheet()){
            postScriptStrings.add(myAstrolabeRules.buildRulesSheet(GeneratorGui.MY_ASTROLABE,
                    GeneratorGui.MY_ASTROLABE.isCounterChanged()));
        }

        if (GeneratorGui.MY_ASTROLABE.getPrintAlidadeSheet()){
            postScriptStrings.add(myAstrolabeRules.buildAlidadeSheet(GeneratorGui.MY_ASTROLABE,
                    GeneratorGui.MY_ASTROLABE.isCounterChanged()));
        }

        printPage(postScriptStrings);
    }

    private void printPage( ArrayList<String> postScriptStrings){
        if (null == postScriptStrings || postScriptStrings.isEmpty()){
            return;
        }

        DocFlavor postscript = DocFlavor.INPUT_STREAM.POSTSCRIPT;
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        attributeSet.add(MediaSizeName.NA_LETTER);    // todo: right now we support american letter. need to support A4

        // prompt user to see if they want the current view or all the parts printed.
        // possibly combine with printer selection

        //Look up available printers.
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(postscript, attributeSet);
        if (printService.length == 0){
            // No printers found. Inform user.
            JOptionPane.showMessageDialog(null, "No PostScript printers could be found on your system!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        PrintService defaultService = printService[0];

        PrintService service = ServiceUI.printDialog(null, 200, 200,
                printService, defaultService, postscript, attributeSet);

        if (null == service){
            return;
        }

        try {
            for (String postScriptString: postScriptStrings ){
                DocPrintJob pj = service.createPrintJob();
                InputStream is = new ByteArrayInputStream((postScriptString + "\f").getBytes());   // add \f to force new page
                Doc doc = new SimpleDoc(is, postscript, null);
                pj.print(doc, attributeSet);
            }
        } catch (PrintException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}