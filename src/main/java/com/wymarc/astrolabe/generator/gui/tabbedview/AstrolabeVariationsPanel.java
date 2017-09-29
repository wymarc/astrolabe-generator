package com.wymarc.astrolabe.generator.gui.tabbedview;

import com.wymarc.astrolabe.generator.AstrolabeGenerator;
import com.wymarc.astrolabe.generator.gui.GeneratorGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AstrolabeVariationsPanel extends JPanel implements ActionListener,MouseListener {
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

    private JCheckBox universalCheck = null;

    public AstrolabeVariationsPanel(){
        super();
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());

        JLabel optionsLabel = new JLabel("Variations on the basic astrolabe:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,10,5,5);
        c.gridx = 0;
        c.gridy = 0;
        optionsPanel.add(optionsLabel,c);
        c.gridy++;
        optionsPanel.add(getUniversalCheck(),c);
        leftPanel.add(optionsPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);

        ImageIcon icon = createImageIcon("gui/images/misc/universal.png");
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

    private JCheckBox getUniversalCheck(){
        if (null == universalCheck){
            universalCheck = new JCheckBox("Universal Astrolabe");
            universalCheck.setSelected(GeneratorGui.MY_ASTROLABE.getPrintUniversalAstrolabe());
            universalCheck.setToolTipText("Include the files for the Universal Astrolabe");
            universalCheck.setActionCommand("Universal");
            universalCheck.addActionListener(this);
            universalCheck.addMouseListener(this);
        }
        return universalCheck;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Universal")) {
            GeneratorGui.MY_ASTROLABE.setPrintUniversalAstrolabe(universalCheck.isSelected());
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
