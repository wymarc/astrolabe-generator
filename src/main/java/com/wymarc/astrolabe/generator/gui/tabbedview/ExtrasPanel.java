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

import com.wymarc.astrolabe.generator.AstrolabeGenerator;
import com.wymarc.astrolabe.generator.gui.GeneratorGui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExtrasPanel extends JPanel implements ActionListener,MouseListener {
    private JCheckBox tenAlidadeCheck = null;
    private JCheckBox tenRuleCheck = null;
    private JCheckBox counterChangedCheck = null;

    public ExtrasPanel() {
        super();
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());

        JLabel optionsLabel = new JLabel("Multi-piece sheets (handy for making a lot of astrolabes):");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,10,5,5);
        c.gridx = 0;
        c.gridy = 0;
        optionsPanel.add(optionsLabel,c);
        c.gridy++;
        optionsPanel.add(getTenAlidadeCheck(),c);
        c.gridy++;
        optionsPanel.add(getTenRuleCheck(),c);
        c.gridy++;
        optionsPanel.add(getCounterChangedCheck(),c);

        //todo
//        if (null != getClimateSetCheckboxes() && getClimateSetCheckboxes().size() > 0){
//            c.gridy++;
//            optionsPanel.add( new JSeparator(JSeparator.HORIZONTAL),c);
//            c.gridy++;
//            JLabel climateLabel = new JLabel("Climate plate sets (Interchangeable plates for major cities):");
//            optionsPanel.add(climateLabel,c);
//            c.gridy++;
//            JLabel climateLabel1 = new JLabel("(Plates will use the settings from the Front Tab)");
//            optionsPanel.add(climateLabel1,c);
//            for (JCheckBox ck : GeneratorGui.MY_ASTROLABE.getClimateSetCheckboxes()){
//                c.gridy++;
//                optionsPanel.add(ck,c);
//            }
//        }

        leftPanel.add(optionsPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);

        ImageIcon icon = createImageIcon("gui/images/misc/rulesthumb.png");
        JLabel iconLabel = new JLabel(icon);
        if (null != icon){
            iconLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        }
        add(iconLabel,BorderLayout.EAST);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = AstrolabeGenerator.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private JCheckBox getTenAlidadeCheck(){
        if (null == tenAlidadeCheck){
            tenAlidadeCheck = new JCheckBox("10 alidade sheet");
            tenAlidadeCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintAlidadeSheet());
            tenAlidadeCheck.setToolTipText("Include a ten alidade sheet");
            tenAlidadeCheck.setActionCommand("Alidade");
            tenAlidadeCheck.addActionListener(this);
            tenAlidadeCheck.addMouseListener(this);
        }
        return tenAlidadeCheck;
    }

    private JCheckBox getTenRuleCheck(){
        if (null == tenRuleCheck){
            tenRuleCheck = new JCheckBox("10 rule sheet");
            tenRuleCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintRuleSheet());
            tenRuleCheck.setToolTipText("Include a ten rule sheet");
            tenRuleCheck.setActionCommand("Rule");
            tenRuleCheck.addActionListener(this);
            tenRuleCheck.addMouseListener(this);
        }
        return tenRuleCheck;
    }

    private JCheckBox getCounterChangedCheck(){
        if (null == counterChangedCheck){
            counterChangedCheck = new JCheckBox("Counterchange alidades");
            counterChangedCheck.setSelected(GeneratorGui.MY_ASTROLABE.isCounterChanged());
            counterChangedCheck.setToolTipText("Counterchange the offset of the arms");
            counterChangedCheck.setActionCommand("Counterchange");
            counterChangedCheck.addActionListener(this);
            counterChangedCheck.addMouseListener(this);
        }
        return counterChangedCheck;
    }

// todo
//    private List<JCheckBox> getClimateSetCheckboxes(){
//        if (null == GeneratorGui.MY_ASTROLABE.getClimateSetCheckboxes()){
//            List<JCheckBox> climateSetCheckboxes = new ArrayList<JCheckBox>();
//            List<ClimateSet> climateSets = GeneratorGui.MY_CONFIG.getClimateSets();
//            for (ClimateSet set : climateSets){
//                JCheckBox temp = new JCheckBox(set.getName());
//                //temp.setSelected(GeneratorGui.MY_ASTROLABE.isCounterChanged());
//                temp.setToolTipText(set.getLocationNames());
//                climateSetCheckboxes.add(temp);
//            }
//            GeneratorGui.MY_ASTROLABE.setClimateSetCheckboxes(climateSetCheckboxes);
//        }
//        return GeneratorGui.MY_ASTROLABE.getClimateSetCheckboxes();
//    }


    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Alidade")) {
            GeneratorGui.MY_ASTROLABE.setPrintAlidadeSheet(tenAlidadeCheck.isSelected());
        }

        if (cmd.equals("Rule")) {
            GeneratorGui.MY_ASTROLABE.setPrintRuleSheet(tenRuleCheck.isSelected());
        }

        if (cmd.equals("Counterchange")) {
            GeneratorGui.MY_ASTROLABE.setCounterChanged(counterChangedCheck.isSelected());
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
