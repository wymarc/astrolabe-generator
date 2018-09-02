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
import com.wymarc.astrolabe.generator.io.FileHandler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class EPSPrintEngine {

    /**
     * Assemble a List of the selected components for output
     *
     * Data is returned in the form of a nested list:
     * 1. 1. Name of component 1
     *    2. EPS data for 1
     * 2. 1. Name of component 2
     *    2. EPS data for 2
     *
     * @return List  of components
     */
    private List<List<String>> getComponents(){
        List<List<String>> selectedComponents = new ArrayList<>();
        List<String> component;

        // create front side
        FrontPrintEngine myAstrolabeFront = new FrontPrintEngine();
        component = new ArrayList<>();
        component.add("AstrolabeFront.eps");
        component.add(myAstrolabeFront.createFront(GeneratorGui.MY_ASTROLABE));
        selectedComponents.add(component);

        // create back side
        BackPrintEngine myAstrolabeBack = new BackPrintEngine();
        component = new ArrayList<>();
        component.add("AstrolabeBack.eps");
        component.add(myAstrolabeBack.createBack(GeneratorGui.MY_ASTROLABE));
        selectedComponents.add(component);

        // create Rete sheet
        RetePrintEngine myAstrolabeRete = new RetePrintEngine();
        component = new ArrayList<>();
        component.add("AstrolabeRete.eps");
        component.add(myAstrolabeRete.createRete(GeneratorGui.MY_ASTROLABE));
        selectedComponents.add(component);

        // create accessory sheet
        RulePrintEngine myAstrolabeRule = new RulePrintEngine();
        component = new ArrayList<>();
        component.add("AstrolabeRules.eps");
        component.add(myAstrolabeRule.createCombinedSheet(GeneratorGui.MY_ASTROLABE, true));
        selectedComponents.add(component);

        //print universal astrolabe
        if (GeneratorGui.MY_ASTROLABE.getPrintUniversalAstrolabe()){
            UniversalPrintEngine myUniversalAstrolabe = new UniversalPrintEngine();
            component = new ArrayList<>();
            component.add("UniversalPlate.eps");
            component.add(myUniversalAstrolabe.createPlate(GeneratorGui.MY_ASTROLABE));
            selectedComponents.add(component);

            component = new ArrayList<>();
            component.add("UniversalPlateRete.eps");
            component.add(myUniversalAstrolabe.createRete(GeneratorGui.MY_ASTROLABE));
            selectedComponents.add(component);

            component = new ArrayList<>();
            component.add("UniversalPlateRegula.eps");
            component.add(myUniversalAstrolabe.createRegula(GeneratorGui.MY_ASTROLABE));
            selectedComponents.add(component);
        }

        // create extras sheets
        if (GeneratorGui.MY_ASTROLABE.getPrintRuleSheet()){
            myAstrolabeRule = new RulePrintEngine();
            component = new ArrayList<>();
            component.add("AstrolabeRulesSheet.eps");
            component.add(myAstrolabeRule.buildRulesSheet(GeneratorGui.MY_ASTROLABE, GeneratorGui.MY_ASTROLABE.isCounterChanged()));
            selectedComponents.add(component);
        }
        if (GeneratorGui.MY_ASTROLABE.getPrintAlidadeSheet()){
            myAstrolabeRule = new RulePrintEngine();
            component = new ArrayList<>();
            component.add("AstrolabeAlidadeSheet.eps");
            component.add(myAstrolabeRule.buildAlidadeSheet(GeneratorGui.MY_ASTROLABE, GeneratorGui.MY_ASTROLABE.isCounterChanged()));
            selectedComponents.add(component);
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
            component = new ArrayList<>();
            component.add("BasicHoraryQuadrant.eps");
            component.add(basicHoraryQuad.printQuadrant());
            selectedComponents.add(component);
        }
        if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedHoraryQuadrant()){
            AdvancedHoraryQuadrant advancedHoraryQuadrant = new AdvancedHoraryQuadrant();
            component = new ArrayList<>();
            component.add("AdvancedHoraryQuadrant.eps");
            component.add(advancedHoraryQuadrant.printQuadrant(GeneratorGui.MY_ASTROLABE));
            selectedComponents.add(component);
        }
        if (GeneratorGui.MY_ASTROLABE.getPrintSineQuadrant()){
            SineQuadrant sineQuadrant = new SineQuadrant();
            component = new ArrayList<>();
            component.add("SineQuadrant.eps");
            component.add(sineQuadrant.printQuadrant(false));
            selectedComponents.add(component);
        }
        if (GeneratorGui.MY_ASTROLABE.getPrintColorSineQuadrant()){
            SineQuadrant sineQuadrant = new SineQuadrant();
            component = new ArrayList<>();
            component.add("ColorSineQuadrant.eps");
            component.add(sineQuadrant.printQuadrant(true));
            selectedComponents.add(component);
        }
        if (GeneratorGui.MY_ASTROLABE.getPrintAdvancedSineQuadrant()){
            VernierSineQuadrant vernierSineQuadrant = new VernierSineQuadrant();
            component = new ArrayList<>();
            component.add("VernierSineQuadrant.eps");
            component.add(vernierSineQuadrant.printQuadrant());
            selectedComponents.add(component);
        }
        if (GeneratorGui.MY_ASTROLABE.getPrintEqualHoursHoraryQuadrant()){
            EqualHours equalhoursQuadrantBack = new EqualHours(GeneratorGui.MY_ASTROLABE,false,true);
            component = new ArrayList<>();
            component.add("EqualHoursQuadrantBack.eps");
            component.add(equalhoursQuadrantBack.createQuadrantBack());
            selectedComponents.add(component);

            EqualHours equalhoursQuadrantFront = new EqualHours(GeneratorGui.MY_ASTROLABE,true,true);
            component = new ArrayList<>();
            component.add("EqualHoursQuadrantFront.eps");
            component.add(equalhoursQuadrantFront.createQuadrantFront());
            selectedComponents.add(component);
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


        // Volvelle project, to be removed at some point
//        Lunar lunarVolvelle = new Lunar();
//
//        component = new ArrayList<>();
//        component.add("LunarVolvelleBasePage.eps");
//        component.add(lunarVolvelle.createBasePage(GeneratorGui.MY_ASTROLABE));
//        selectedComponents.add(component);
//
//        component = new ArrayList<>();
//        component.add("LunarVolvelleCalendar.eps");
//        component.add(lunarVolvelle.createVolvelleZodiacCalendarDisc(GeneratorGui.MY_ASTROLABE));
//        selectedComponents.add(component);
//
//        component = new ArrayList<>();
//        component.add("LunarVolvelleSun.eps");
//        component.add(lunarVolvelle.createVolvelleSunDisc(GeneratorGui.MY_ASTROLABE));
//        selectedComponents.add(component);
//
//        component = new ArrayList<>();
//        component.add("LunarVolvelleMoon.eps");
//        component.add(lunarVolvelle.createVolvelleMoonDisc(GeneratorGui.MY_ASTROLABE));
//        selectedComponents.add(component);

        // todo LASER CUTTER files
//        SineQuadrantLaser mySineQuadrantLaser = new SineQuadrantLaser();  //todo colored for laser cutter
//        component = new ArrayList<>();
//        component.add("SineQuadrantLaser.eps");
//        component.add(mySineQuadrantLaser.printQuadrant(true));
//        selectedComponents.add(component);


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
//            DemoArms myDemoArms = new DemoArms();
//            dataString = myDemoArms.printArms();
//            save(filePath + "/DemoArms.eps",dataString);



        return selectedComponents;
    }
    /**
     * creates an Astrolabe object and sets its properties then
     * creates and runs the appropriate print engines
     */
    public void saveFiles(){
        String filePath = FileHandler.getSavePath();

        // verify the filepath and the Astrolabe Object
        Boolean noProblems = filePath != null && GeneratorGui.MY_ASTROLABE != null ;
		if (noProblems){
            List<List<String>> components = getComponents();

            for (List<String> component : components){
                FileHandler.saveFile(filePath + "/" + component.get(0), component.get(1));
            }

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

        String filePath = FileHandler.getSavePath();

        // verify the filepath and the Astrolabe Object
        Boolean noProblems = filePath != null && GeneratorGui.MY_ASTROLABE != null ;
        if (noProblems){
            filePath = filePath + "/astrolabe.zip";
            List<List<String>> components = getComponents();
            try {
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(filePath));
                for (List<String> component : components){
                    FileHandler.updateZip(component.get(0), zos, component.get(1));
                }
                zos.close();
            }catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"There was a problem saving the ZIP archive","Save Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
        JOptionPane.showMessageDialog(null,"ZIP archive saved to: \n" + filePath);
    }

    /**
     * Print the component(s) on the currently selected tab
     */
    public void printCurrent(){
        ArrayList<String> postScriptStrings = new ArrayList<>();

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

    /**
     * Print all selected components
     */
    public void printAll(){
        ArrayList<String> postScriptStrings = new ArrayList<>();

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

    /**
     * This will attempt to locate PostScript compatable printers on the machine and
     * allow the user to print files directly.
     * @param postScriptStrings ArrayList<String> containing the PostScript data for the components
     */
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