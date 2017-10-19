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
package com.wymarc.astrolabe.generator.gui.tabbedview;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.gui.GeneratorGui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrontPanel extends JPanel implements ActionListener,MouseListener {
    private JComboBox showOptionsCombo = null;
    private JComboBox shapeCombo = null;
    private JComboBox hourCombo = null;
    private JComboBox degreeScaleCombo = null;
    private JComboBox altitudeIntervalCombo = null;
    private JCheckBox throneCheck = null;
    private JCheckBox registrationCheck = null;
    private JCheckBox azimuthLinesCheck = null;
    private JCheckBox unequalHoursCheck = null;
    private JCheckBox twilightLinesCheck = null;
    private JCheckBox allTwilightLinesCheck = null;
    private JCheckBox horizonPlateCheck = null;
    private JCheckBox housesOfHeavenCheck = null;
    private ThumbNail thumbNail = null;

    public FrontPanel() {
        super();
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Show: ");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,10,5,5);
        c.gridx = 0;
        c.gridy = 0;
        optionsPanel.add(label,c);
        c.gridx = 1;
        c.gridy = 0;
        optionsPanel.add(getShowOptionsCombo(),c);

        label = new JLabel("Limb shape: ");
        c.gridx = 0;
        c.gridy = 1;
        optionsPanel.add(label,c);
        c.gridx = 1;
        c.gridy = 1;
        optionsPanel.add(getShapeCombo(),c);

        JLabel hourLabel = new JLabel("Limb hour scale: ");
        c.gridx = 0;
        c.gridy = 2;
        optionsPanel.add(hourLabel,c);
        c.gridx = 1;
        c.gridy = 2;
        optionsPanel.add(getHourCombo(),c);

        JLabel degreeScaleLabel = new JLabel("Limb degree scale: ");
        c.gridx = 0;
        c.gridy = 3;
        optionsPanel.add(degreeScaleLabel,c);
        c.gridx = 1;
        c.gridy = 3;
        optionsPanel.add(getDegreeScaleCombo(),c);

        JLabel altitudeIntervalLabel = new JLabel("Altitude degree interval: ");
        c.gridx = 0;
        c.gridy = 4;
        optionsPanel.add(altitudeIntervalLabel,c);
        c.gridx = 1;
        c.gridy = 4;
        optionsPanel.add(getAltitudeIntervalCombo(),c);
        c.gridy++;
        optionsPanel.add(getThroneCheck(),c);
        c.gridy++;
        optionsPanel.add(getRegistrationCheck(),c);
        c.gridy++;
        optionsPanel.add( new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy++;
        optionsPanel.add(getAzimuthLinesCheck(),c);
        c.gridy++;
        optionsPanel.add(getUnequalHoursCheck(),c);
        c.gridy++;
        optionsPanel.add(getTwilightLinesCheck(),c);
        c.gridy++;
        optionsPanel.add(getAllTwilightLinesCheck(),c);
        c.gridy++;
        optionsPanel.add(getHousesOfHeavenCheck(),c);
        c.gridy++;
        optionsPanel.add( new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy++;
        optionsPanel.add(getHorizonPlateCheck(),c);

        leftPanel.add(optionsPanel,BorderLayout.NORTH);
        add(leftPanel,BorderLayout.WEST);

        add(getThumbNail(),BorderLayout.EAST);
    }

    public JPanel getThumbNail(){
        if (null == thumbNail){
            thumbNail = new ThumbNail();
        }
        return thumbNail.updateFrontThumbNail();
    }

    private JComboBox getShowOptionsCombo(){
        if (null == showOptionsCombo){
            showOptionsCombo = new JComboBox(Astrolabe.SHOWOPTIONS);
            showOptionsCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getFrontPrintOption());
            showOptionsCombo.setToolTipText("Select what parts to print");
            showOptionsCombo.setActionCommand("Show_Mater");
            showOptionsCombo.addActionListener(this);
            showOptionsCombo.addMouseListener(this);
        }
        return showOptionsCombo;
    }

    private JComboBox getShapeCombo(){
        if (null == shapeCombo){
            shapeCombo = new JComboBox(Astrolabe.SHAPEOPTIONS);
            shapeCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getShapeOption());
            shapeCombo.setToolTipText("Select shape of astrolabe");
            shapeCombo.setActionCommand("Shape");
            shapeCombo.addActionListener(this);
            shapeCombo.addMouseListener(this);
        }
        return shapeCombo;
    }

    private JComboBox getHourCombo(){
        if (null == hourCombo){
            hourCombo = new JComboBox(Astrolabe.HOUROPTIONS);
            hourCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getHourMarkings());
            hourCombo.setToolTipText("Select hour markings");
            hourCombo.setActionCommand("Hours");
            hourCombo.addActionListener(this);
            hourCombo.addMouseListener(this);
        }
        return hourCombo;
    }

    private JComboBox getDegreeScaleCombo(){
        if (null == degreeScaleCombo){
            degreeScaleCombo = new JComboBox(Astrolabe.DEGREESCALEOPTIONS);
            degreeScaleCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getDegreeScaleType());
            degreeScaleCombo.setToolTipText("Show degree scale");
            degreeScaleCombo.setActionCommand("Degrees");
            degreeScaleCombo.addActionListener(this);
            degreeScaleCombo.addMouseListener(this);
        }
        return degreeScaleCombo;
    }

    private JComboBox getAltitudeIntervalCombo(){
        if (null == altitudeIntervalCombo){
            altitudeIntervalCombo = new JComboBox(Astrolabe.ALTITUDEINTERVALOPTIONS);
            altitudeIntervalCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getDegreeInterval());
            altitudeIntervalCombo.setToolTipText("Set altitude angle interval");
            altitudeIntervalCombo.setActionCommand("Almucantars");
            altitudeIntervalCombo.addActionListener(this);
            altitudeIntervalCombo.addMouseListener(this);
        }
        return altitudeIntervalCombo;
    }

    private JCheckBox getThroneCheck(){
        if (null == throneCheck){
            throneCheck = new JCheckBox("Show throne");
            throneCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowThrone());
            throneCheck.setToolTipText("Show throne");
            throneCheck.setActionCommand("Show_Throne");
            throneCheck.addActionListener(this);
            throneCheck.addMouseListener(this);
        }
        return throneCheck;
    }

    private JCheckBox getRegistrationCheck(){
        if (null == registrationCheck){
            registrationCheck = new JCheckBox("Registration marks");
            registrationCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowRegistrationMarks());
            registrationCheck.setToolTipText("Show printing registration marks");
            registrationCheck.setActionCommand("Show_Reg_Marks");
            registrationCheck.addActionListener(this);
            registrationCheck.addMouseListener(this);
        }
        return registrationCheck;
    }

    private JCheckBox getAzimuthLinesCheck(){
        if (null == azimuthLinesCheck){
            azimuthLinesCheck = new JCheckBox("Show azimuth lines");
            azimuthLinesCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowAzimuthLines());
            azimuthLinesCheck.setToolTipText("Show azimuth lines");
            azimuthLinesCheck.setActionCommand("Show_Azimuth");
            azimuthLinesCheck.addActionListener(this);
            azimuthLinesCheck.addMouseListener(this);
        }
        return azimuthLinesCheck;
    }

    private JCheckBox getUnequalHoursCheck(){
        if (null == unequalHoursCheck){
            unequalHoursCheck = new JCheckBox("Show unequal hours");
            unequalHoursCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowUnequalHoursLines());
            unequalHoursCheck.setToolTipText("Show unequal hours");
            unequalHoursCheck.setActionCommand("Show_Unequal");
            unequalHoursCheck.addActionListener(this);
            unequalHoursCheck.addMouseListener(this);
        }
        return unequalHoursCheck;
    }

    private JCheckBox getTwilightLinesCheck(){
        if (null == twilightLinesCheck){
            twilightLinesCheck = new JCheckBox("Show twilight line");
            twilightLinesCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowTwilightLines());
            twilightLinesCheck.setToolTipText("Show twilight line");
            twilightLinesCheck.setActionCommand("Show_Twilight");
            twilightLinesCheck.addActionListener(this);
            twilightLinesCheck.addMouseListener(this);
        }
        return twilightLinesCheck;
    }

    private JCheckBox getAllTwilightLinesCheck(){
        if (null == allTwilightLinesCheck){
            allTwilightLinesCheck = new JCheckBox("Show all three twilight lines");
            allTwilightLinesCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowAllTwilightLines());
            allTwilightLinesCheck.setToolTipText("Show all three twilight lines");
            allTwilightLinesCheck.setActionCommand("Show_All_Twilight");
            allTwilightLinesCheck.addActionListener(this);
            allTwilightLinesCheck.addMouseListener(this);
        }
        return allTwilightLinesCheck;
    }

    private JCheckBox getHorizonPlateCheck(){
        if (null == horizonPlateCheck){
            horizonPlateCheck = new JCheckBox("Show horizons climate plate");
            horizonPlateCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowHorizonPlate());
            horizonPlateCheck.setToolTipText("Show horizons plate instead");
            horizonPlateCheck.setActionCommand("Show_Horizons");
            horizonPlateCheck.addActionListener(this);
            horizonPlateCheck.addMouseListener(this);
        }
        return horizonPlateCheck;
    }

    private JCheckBox getHousesOfHeavenCheck(){
        if (null == housesOfHeavenCheck){
            housesOfHeavenCheck = new JCheckBox("Show houses of heaven");
            housesOfHeavenCheck.setSelected(GeneratorGui.MY_ASTROLABE.getShowHousesofHeavenLines());
            housesOfHeavenCheck.setToolTipText("Show houses of heaven");
            housesOfHeavenCheck.setActionCommand("Show_Houses");
            housesOfHeavenCheck.addActionListener(this);
            housesOfHeavenCheck.addMouseListener(this);
        }
        return housesOfHeavenCheck;
    }

    public void updateControls(){
        getShowOptionsCombo().setSelectedIndex(GeneratorGui.MY_ASTROLABE.getFrontPrintOption());
        getShapeCombo().setSelectedIndex(GeneratorGui.MY_ASTROLABE.getShapeOption());
        getHourCombo().setSelectedIndex(GeneratorGui.MY_ASTROLABE.getHourMarkings());
        getDegreeScaleCombo().setSelectedIndex(GeneratorGui.MY_ASTROLABE.getDegreeScaleType());
        getAltitudeIntervalCombo().setSelectedIndex(GeneratorGui.MY_ASTROLABE.getAlmucanterInterval());
        getThroneCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowThrone());
        getRegistrationCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowRegistrationMarks());
        getAzimuthLinesCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowAzimuthLines());
        getUnequalHoursCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowUnequalHoursLines());
        getTwilightLinesCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowTwilightLines());
        getAllTwilightLinesCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowAllTwilightLines());
        getHorizonPlateCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowHorizonPlate());
        getHousesOfHeavenCheck().setSelected(GeneratorGui.MY_ASTROLABE.getShowHousesofHeavenLines());
        getThumbNail().updateUI();
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Show_Mater")) {
            int selectedView = getShowOptionsCombo().getSelectedIndex();
            GeneratorGui.MY_ASTROLABE.setFrontPrintOption(selectedView);
            if (selectedView == 1){
                getShapeCombo().setEnabled(false);
                getHourCombo().setEnabled(false);
                getDegreeScaleCombo().setEnabled(false);
            }else{
                getShapeCombo().setEnabled(true);
                getHourCombo().setEnabled(true);
                getDegreeScaleCombo().setEnabled(true);
            }
            if (selectedView == 2 || selectedView == 3){
                getAltitudeIntervalCombo().setEnabled(false);
                getAzimuthLinesCheck().setEnabled(false);
                getUnequalHoursCheck().setEnabled(false);
                getTwilightLinesCheck().setEnabled(false);
                getAllTwilightLinesCheck().setEnabled(false);
                getHousesOfHeavenCheck().setEnabled(false);
                getHorizonPlateCheck().setEnabled(false);
            }else{
                if (!getHorizonPlateCheck().isSelected()){
                    getAltitudeIntervalCombo().setEnabled(true);
                    getAzimuthLinesCheck().setEnabled(true);
                    getUnequalHoursCheck().setEnabled(true);
                    getTwilightLinesCheck().setEnabled(true);
                    allTwilightLinesCheck.setEnabled(getTwilightLinesCheck().isSelected());
                    getHousesOfHeavenCheck().setEnabled(true);
                }
                getHorizonPlateCheck().setEnabled(true);
            }
            getThumbNail().updateUI();
        }

        if (cmd.equals("Shape")) {
            GeneratorGui.MY_ASTROLABE.setShapeOption(getShapeCombo().getSelectedIndex());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Hours")) {
            GeneratorGui.MY_ASTROLABE.setHourMarkings(getHourCombo().getSelectedIndex());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Degrees")) {
            GeneratorGui.MY_ASTROLABE.setDegreeScaleType(getDegreeScaleCombo().getSelectedIndex());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Almucantars")) {
            GeneratorGui.MY_ASTROLABE.setDegreeInterval(getAltitudeIntervalCombo().getSelectedIndex());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Reg_Marks")) {
            GeneratorGui.MY_ASTROLABE.setShowRegistrationMarks(getRegistrationCheck().isSelected());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Throne")) {
            GeneratorGui.MY_ASTROLABE.setShowThrone(getThroneCheck().isSelected());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Azimuth")) {
            GeneratorGui.MY_ASTROLABE.setShowAzimuthLines(getAzimuthLinesCheck().isSelected());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Unequal")) {
            GeneratorGui.MY_ASTROLABE.setShowUnequalHoursLines(getUnequalHoursCheck().isSelected());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Twilight")) {
            GeneratorGui.MY_ASTROLABE.setShowTwilightLines(getTwilightLinesCheck().isSelected());
            allTwilightLinesCheck.setEnabled(getTwilightLinesCheck().isSelected());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_All_Twilight")) {
            GeneratorGui.MY_ASTROLABE.setShowAllTwilightLines(getAllTwilightLinesCheck().isSelected());
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Horizons")) {
            GeneratorGui.MY_ASTROLABE.setShowHorizonPlate(getHorizonPlateCheck().isSelected());
            if (getHorizonPlateCheck().isSelected()){
                getAltitudeIntervalCombo().setEnabled(false);
                getAzimuthLinesCheck().setEnabled(false);
                getUnequalHoursCheck().setEnabled(false);
                getTwilightLinesCheck().setEnabled(false);
                getAllTwilightLinesCheck().setEnabled(false);
                getHousesOfHeavenCheck().setEnabled(false);
            }else{
                getAltitudeIntervalCombo().setEnabled(true);
                getAzimuthLinesCheck().setEnabled(true);
                getUnequalHoursCheck().setEnabled(true);
                getTwilightLinesCheck().setEnabled(true);
                getAllTwilightLinesCheck().setEnabled(true);
                getHousesOfHeavenCheck().setEnabled(true);
            }
            getThumbNail().updateUI();
        }

        if (cmd.equals("Show_Houses")) {
            GeneratorGui.MY_ASTROLABE.setShowHousesofHeavenLines(getHousesOfHeavenCheck().isSelected());
            getThumbNail().updateUI();
        }

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        String text = "";
        Object obj = e.getSource();
        if (obj instanceof JComboBox){
            text = ((JComboBox) obj).getToolTipText();
        }else if (obj instanceof JCheckBox){
            text = ((JCheckBox) obj).getToolTipText();
        }

        GeneratorGui.setStatusLabel(text);
    }

    public void mouseExited(MouseEvent e) {
        GeneratorGui.setStatusLabel("");
    }
}
