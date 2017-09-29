package com.wymarc.astrolabe.generator.gui.tabbedview;

import com.wymarc.astrolabe.generator.gui.GeneratorGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class QuadrantPanel extends JPanel implements ActionListener,MouseListener {
    /**
     * $Id: AstrolabeGenerator,v 3.0
     * <p/>
     * The Astrolabe Generator is free software; you can redistribute it
     * and/or modify it under the terms of the GNU General Public License
     * as published by the Free Software Foundation; either version 2 of
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
     * Copyright (c) 2014, 2015, 2016, 2017 Timothy J. Mitchell
     */

    private JCheckBox simpleHoraryCheck = null;
    private JCheckBox advancedHoraryCheck = null;
    private JCheckBox equalHoursHoraryCheck = null;
    private JCheckBox sineCheck = null;
    private JCheckBox colorSineCheck = null;
    private JCheckBox advancedSineCheck = null;
    private ThumbNail thumbNail = null;

    public QuadrantPanel() {
        super();
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("A selection of quadrant types can be included:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,10,5,5);
        c.gridx = 0;
        c.gridy = 0;
        optionsPanel.add(label,c);
        c.gridy++;
        optionsPanel.add(getSimpleHoraryCheck(),c);
        c.gridy++;
        optionsPanel.add(getAdvancedHoraryCheck(),c);
        c.gridy++;
        optionsPanel.add( new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy++;
        optionsPanel.add(getEqualHoursHoraryCheck(),c);
        c.gridy++;
        optionsPanel.add( new JSeparator(JSeparator.HORIZONTAL),c);
        c.gridy++;
        optionsPanel.add(getSineCheck(),c);
        c.gridy++;
        optionsPanel.add(getColorSineCheck(),c);
        c.gridy++;
        optionsPanel.add(getAdvancedSineCheck(),c);

        leftPanel.add(optionsPanel, BorderLayout.NORTH);
        add(leftPanel,BorderLayout.WEST);

        add(getThumbNail("ColorSineQuadrant"),BorderLayout.EAST); // default to color Sine for start
    }

    private JPanel getThumbNail(String hoverObject){
        if (null == thumbNail){
            thumbNail = new ThumbNail();
        }
        return thumbNail.updateQuadrantThumbNail(hoverObject);
    }

    private JCheckBox getSimpleHoraryCheck(){
        if (null == simpleHoraryCheck){
            simpleHoraryCheck = new JCheckBox("Unequal hours horary quadrant (BASIC)");
            simpleHoraryCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintBasicHoraryQuadrant());
            simpleHoraryCheck.setToolTipText("Include an unequal hours horary quadrant");
            simpleHoraryCheck.setActionCommand("UnequalHorary");
            simpleHoraryCheck.addActionListener(this);
            simpleHoraryCheck.addMouseListener(this);
        }
        return simpleHoraryCheck;
    }

    private JCheckBox getAdvancedHoraryCheck(){
        if (null == advancedHoraryCheck){
            advancedHoraryCheck = new JCheckBox("Unequal hours horary quadrant (ADVANCED)");
            advancedHoraryCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintAdvancedHoraryQuadrant());
            advancedHoraryCheck.setToolTipText("Include an advanced unequal hours horary quadrant");
            advancedHoraryCheck.setActionCommand("AdvancedUnequalHorary");
            advancedHoraryCheck.addActionListener(this);
            advancedHoraryCheck.addMouseListener(this);
        }
        return advancedHoraryCheck;
    }

    private JCheckBox getEqualHoursHoraryCheck(){
        if (null == equalHoursHoraryCheck){
            equalHoursHoraryCheck = new JCheckBox("Equal hours horary quadrant (front and back)");
            equalHoursHoraryCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintEqualHoursHoraryQuadrant());
            equalHoursHoraryCheck.setToolTipText("Include an equal hours horary quadrant");
            equalHoursHoraryCheck.setActionCommand("EqualHoursHorary");
            equalHoursHoraryCheck.addActionListener(this);
            equalHoursHoraryCheck.addMouseListener(this);
        }
        return equalHoursHoraryCheck;
    }

    private JCheckBox getSineCheck(){
        if (null == sineCheck){
            sineCheck = new JCheckBox("Sine quadrant");
            sineCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintSineQuadrant());
            sineCheck.setToolTipText("Include a sine quadrant");
            sineCheck.setActionCommand("SineQuadrant");
            sineCheck.addActionListener(this);
            sineCheck.addMouseListener(this);
        }
        return sineCheck;
    }
    private JCheckBox getColorSineCheck(){
        if (null == colorSineCheck){
            colorSineCheck = new JCheckBox("Sine quadrant (COLOR)");
            colorSineCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintSineQuadrant());
            colorSineCheck.setToolTipText("Include a color sine quadrant");
            colorSineCheck.setActionCommand("ColorSineQuadrant");
            colorSineCheck.addActionListener(this);
            colorSineCheck.addMouseListener(this);
        }
        return colorSineCheck;
    }

    private JCheckBox getAdvancedSineCheck(){
        if (null == advancedSineCheck){
            advancedSineCheck = new JCheckBox("Sine quadrant with vernier scale");
            advancedSineCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintAdvancedSineQuadrant());
            advancedSineCheck.setToolTipText("Include a sine quadrant with vernier scale");
            advancedSineCheck.setActionCommand("AdvancedSine");
            advancedSineCheck.addActionListener(this);
            advancedSineCheck.addMouseListener(this);
        }
        return advancedSineCheck;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("UnequalHorary")) {
            GeneratorGui.MY_ASTROLABE.setPrintBasicHoraryQuadrant(getSimpleHoraryCheck().isSelected());
        }else if (cmd.equals("AdvancedUnequalHorary")) {
            // this quadrant is limited to as to latitude
            if (withinLimits()){
                GeneratorGui.MY_ASTROLABE.setPrintAdvancedHoraryQuadrant(getAdvancedHoraryCheck().isSelected());
            }else{
                getAdvancedHoraryCheck().setSelected(false);
            }
        }else if (cmd.equals("EqualHoursHorary")) {
            // this quadrant is limited to as to latitude
            if (withinLimits()){
                GeneratorGui.MY_ASTROLABE.setPrintEqualHoursHoraryQuadrant(getEqualHoursHoraryCheck().isSelected());
            }else{
                getEqualHoursHoraryCheck().setSelected(false);
            }

        }else if (cmd.equals("SineQuadrant")) {
            GeneratorGui.MY_ASTROLABE.setPrintSineQuadrant(getSineCheck().isSelected());
        }else if (cmd.equals("ColorSineQuadrant")) {
            GeneratorGui.MY_ASTROLABE.setPrintColorSineQuadrant(getColorSineCheck().isSelected());
        }else if (cmd.equals("AdvancedSine")) {
            GeneratorGui.MY_ASTROLABE.setPrintAdvancedSineQuadrant(getAdvancedSineCheck().isSelected());
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
        String action = "";
        Object obj = e.getSource();
        if (obj instanceof JCheckBox){
            text = ((JCheckBox) obj).getToolTipText();
            action = ((JCheckBox) obj).getActionCommand();
        }

        GeneratorGui.setStatusLabel(text);
        getThumbNail(action);
    }

    public void mouseExited(MouseEvent e) {
        GeneratorGui.setStatusLabel("");
    }

    private boolean withinLimits(){
        double UPPERLIMIT = 65.0;
        double LOWERLIMIT = 25.0;
        double latitude = GeneratorGui.MY_ASTROLABE.getLocation().getLatitude();
        String hemisphere = GeneratorGui.MY_ASTROLABE.getLocation().getLatDir().toUpperCase();

        if (hemisphere.equals("N") && latitude >= LOWERLIMIT && latitude <= UPPERLIMIT){
            return true;
        }

        JOptionPane.showMessageDialog(null,"This quadrant is limited to latitudes between 25 North and 65 North","Unavailable",JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
