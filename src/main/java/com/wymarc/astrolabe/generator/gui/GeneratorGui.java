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
package com.wymarc.astrolabe.generator.gui;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.Location;
import com.wymarc.astrolabe.generator.config.AstrolabeExample;
import com.wymarc.astrolabe.generator.config.Config;
import com.wymarc.astrolabe.generator.gui.dialogs.AboutDialog;
import com.wymarc.astrolabe.generator.gui.dialogs.HelpDialog;
import com.wymarc.astrolabe.generator.gui.dialogs.ResourcesDialog;
import com.wymarc.astrolabe.generator.gui.tabbedview.*;
import com.wymarc.astrolabe.generator.io.FileHandler;
import com.wymarc.astrolabe.generator.printengines.postscript.EPSPrintEngine;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GeneratorGui extends JFrame implements ActionListener {
    public static Astrolabe MY_ASTROLABE = null;
    public static Config MY_CONFIG = null;
    public static boolean HAS_PRINTER_SUPPORT = false;
    public static String tabSelected = "Front";
    private static JLabel statusLabel = new JLabel("");

    private GeneratorMenuBar windowMenuBar = null;
    private JPanel statusPanel = null;
    private GeneratorToolBar toolBar = null;
    private GeneratorTabbedPane tabPane = null;

    public GeneratorGui(){
        super("Astrolabe Generator");
        MY_ASTROLABE = new Astrolabe();
        MY_CONFIG = new Config();
        initAstrolabe(0);
        createAndShowGUI();
    }

    private void initAstrolabe(int index){
        MY_ASTROLABE.getLocation().setLocationName(MY_CONFIG.getDefaultLocationName());
        MY_ASTROLABE.getLocation().setLocation(MY_CONFIG.getDefaultLocation());
        AstrolabeExample defaultAstrolabe = MY_CONFIG.getAstrolabeExamples().get(index);
        if (null != defaultAstrolabe){
            MY_ASTROLABE.setShowThrone(defaultAstrolabe.isShowThrone());
            MY_ASTROLABE.setShapeOption(defaultAstrolabe.getShapeOption());
            MY_ASTROLABE.setShowZodiacSymbols(defaultAstrolabe.isZodiacSymbols());
            MY_ASTROLABE.setShowRegistrationMarks(defaultAstrolabe.isShowRegistration());
            MY_ASTROLABE.setFrontPrintOption(defaultAstrolabe.getPrintOption());
            MY_ASTROLABE.setShowHorizonPlate(defaultAstrolabe.isShowHorizonPlate());
            MY_ASTROLABE.setShowTimeCorrection(defaultAstrolabe.isPrintTimeCorrection());
            MY_ASTROLABE.setHourMarkings(defaultAstrolabe.getHourMarkings());
            MY_ASTROLABE.setAlmucanterInterval(defaultAstrolabe.getAlmucantarInterval());
            MY_ASTROLABE.setShowAzimuthLines(defaultAstrolabe.isShowAzimuth());
            MY_ASTROLABE.setShowTwilightLines(defaultAstrolabe.isShowTwilight());
            MY_ASTROLABE.setShowAllTwilightLines(defaultAstrolabe.isShowAllTwilight());
            MY_ASTROLABE.setDegreeScaleType(defaultAstrolabe.getDegreeScaleType());
            MY_ASTROLABE.setShowUnequalHoursLines(defaultAstrolabe.isShowUnequalHours());
            MY_ASTROLABE.setShowHousesofHeavenLines(defaultAstrolabe.isShowHouses());
            MY_ASTROLABE.setShowLunarMansions(defaultAstrolabe.isShowMansions());
            MY_ASTROLABE.setShowCosine(defaultAstrolabe.isShowCosine());
            MY_ASTROLABE.setUse100(defaultAstrolabe.isUse100());
            MY_ASTROLABE.setGridPerDegree(defaultAstrolabe.isGridPerDegree());
            MY_ASTROLABE.setShowRadials(defaultAstrolabe.isShowRadials());
            MY_ASTROLABE.setShowArcs(defaultAstrolabe.isShowArcs());
            MY_ASTROLABE.setShowObliqityArc(defaultAstrolabe.isShowObliqityArc());
            MY_ASTROLABE.setTopLeft(defaultAstrolabe.getBackTopLeft());
            MY_ASTROLABE.setTopRight(defaultAstrolabe.getBackTopRight());
            MY_ASTROLABE.setBottomLeft(defaultAstrolabe.getBackBottomLeft());
            MY_ASTROLABE.setBottomRight(defaultAstrolabe.getBackBottomRight());
            MY_ASTROLABE.setReteType(defaultAstrolabe.getReteType());
            MY_ASTROLABE.setShowCotangentScale(defaultAstrolabe.isShowCotangent());
            MY_ASTROLABE.setShowConcentricCalendar(defaultAstrolabe.isShowConcentricCalendar());
            MY_ASTROLABE.setShowEquationOfTime(defaultAstrolabe.isShowEot());
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //first test for printer support we are only interested in PostScript printers
        DocFlavor postscript = DocFlavor.INPUT_STREAM.POSTSCRIPT;
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(postscript, attributeSet);
        if (printService.length > 0){
            HAS_PRINTER_SUPPORT = true;
        }

        //Create and set up the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 700));
        setLayout(new BorderLayout());

        setJMenuBar(getWindowMenuBar());
        add(getToolBar(), BorderLayout.NORTH);
        add(getStatusBar(), BorderLayout.SOUTH);
        add(getTabPane());

        //Display the window.
        pack();
        setLocationRelativeTo(null);

        updateGui();

        setVisible(true);
    }

    private void updateGui(){
        //update location
        GeneratorToolBar toolBar = getToolBar();
        toolBar.getNameText().setText(MY_ASTROLABE.getUserName());
        toolBar.getLocationNameText().setText(MY_ASTROLABE.getLocation().getLocationName());
        toolBar.getLocationText().setText(MY_ASTROLABE.getLocation().getDisplayString());
        getTabPane().updateControls();
    }

    private boolean updateAstrolabe(){
        GeneratorToolBar toolBar = getToolBar();
        JTextField locationText = toolBar.getLocationText();
        if (!Location.isValidLocation(locationText.getText())) {
            locationText.setForeground(Color.RED);
            SwingUtilities.invokeLater(new FocusGrabber(locationText));
            JOptionPane.showMessageDialog(null, "Location must be in the format: ddmmssh dddmmssh. \n" +
                    "for example, \n\n" +
                    "45 degrees 30 minutes, 25 seconds North, \n" +
                    "120 degrees, 40 minutes, 13 seconds West \n\n" +
                    "would be: 453025N 1204013W","Location Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            MY_ASTROLABE.getLocation().setLocation(locationText.getText());
        }

        MY_ASTROLABE.setUserName(toolBar.getNameText().getText());
        MY_ASTROLABE.getLocation().setLocationName(toolBar.getLocationNameText().getText());
        return true;
    }

    private JMenuBar getWindowMenuBar(){
        if(null == windowMenuBar){
            windowMenuBar = new GeneratorMenuBar(this);
        }
        return windowMenuBar;
    }

    private GeneratorToolBar getToolBar(){
        if (null == toolBar){
            toolBar = new GeneratorToolBar(this);
        }
        return toolBar;
    }

    private JPanel getStatusBar(){
        if(null == statusPanel){
            statusPanel = new JPanel();
            statusPanel.setBorder(new EmptyBorder(5,10,5,10));
            statusPanel.setLayout(new BorderLayout());

            //JLabel statusLabel = new JLabel("tooltip");
            statusPanel.add(statusLabel, BorderLayout.WEST);

            JLabel versionLabel = new JLabel("v" + Config.version + " astrolabeproject.com");
            statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            statusPanel.add(versionLabel, BorderLayout.EAST);
        }
        return statusPanel;
    }

    private GeneratorTabbedPane getTabPane(){
        if (null == tabPane){
            tabPane = new GeneratorTabbedPane();
            ChangeListener changeListener = new ChangeListener() {
                public void stateChanged(ChangeEvent changeEvent) {
                    GeneratorTabbedPane sourceTabbedPane = (GeneratorTabbedPane) changeEvent.getSource();
                    int index = sourceTabbedPane.getSelectedIndex();
                    tabSelected = sourceTabbedPane.getTitleAt(index);
                    if (index == 0){
                        sourceTabbedPane.getFrontPanel().getThumbNail().updateUI();
                    }else if (index == 1){
                        sourceTabbedPane.getBackPanel().getThumbNail().updateUI();
                    }else if (index == 2){
                        sourceTabbedPane.getRetePanel().getThumbNail().updateUI();
                    }
                }
            };
            tabPane.addChangeListener(changeListener);
        }
        return tabPane;
    }

    private void saveToFolder() {
        // make sure Astrolabe is updated
        if (!updateAstrolabe()){
            return;
        }
        // create Astrolabe files
        EPSPrintEngine epsAstrolabe = new EPSPrintEngine();
        epsAstrolabe.saveFiles();
    }

    private void saveToZip() {
        // make sure Astrolabe is updated
        if (!updateAstrolabe()){
            return;
        }

        // create Astrolabe files
        EPSPrintEngine epsAstrolabe = new EPSPrintEngine();
        epsAstrolabe.saveZip();
    }

    private void print(Boolean printAll) {
        // make sure Astrolabe is updated
        if (!updateAstrolabe()){
            return;
        }
        EPSPrintEngine epsAstrolabe = new EPSPrintEngine();
        if (printAll){
            epsAstrolabe.printAll();
        } else{
            epsAstrolabe.printCurrent();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Save to folder")){
            saveToFolder();
        } else if (action.equals("Save to Zip")){
            saveToZip();
        } else if (action.equals("Print all")){
            print(true);
        } else if (action.equals("Print current view")){
            print(false);
        } else if (action.equals("Program Help")){
            new HelpDialog(this,true);
        } else if (action.equals("Astrolabe Resources")){
            new ResourcesDialog(this,true);
        } else if (action.equals("About")){
            new AboutDialog(this,true);
        } else if (action.equals("Assembly Instructions")){
            FileHandler.saveInstructable();
        } else {
            // check list of astrolabe examples
            List<AstrolabeExample> astrolabeList = MY_CONFIG.getAstrolabeExamples();
            for (int i = 0; i < astrolabeList.size(); i++){
                if (astrolabeList.get(i).getName().equals(e.getActionCommand())){
                    // set up this astrolabe
                    initAstrolabe(i);
                    updateGui();
                }
            }
        }

    }

    public static void setStatusLabel(String text) {
        GeneratorGui.statusLabel.setText(text);
    }
}
