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

import com.wymarc.astrolabe.Location;
import com.wymarc.astrolabe.generator.AstrolabeGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GeneratorToolBar extends JToolBar implements MouseListener{
    private JTextField nameText = null;
    private JTextField locationNameText = null;
    private JTextField locationText = null;


    public GeneratorToolBar(GeneratorGui generator) {
        super();
        setFloatable(false);
        setRollover(true);

        JButton button = new JButton();
        //Look for the image.
        URL imageURL = AstrolabeGenerator.class.getResource("gui/images/icons/Save24.gif");
        button.setIcon(new ImageIcon(imageURL, "Save"));
        button.setActionCommand("Save to folder");
        button.setToolTipText("Save to Folder");
        button.addActionListener(generator);
        button.addMouseListener(this);
        add(button);

        button = new JButton();
        //Look for the image.
        imageURL = AstrolabeGenerator.class.getResource("gui/images/icons/SaveAsZip24.gif");
        button.setIcon(new ImageIcon(imageURL, "Save to Zip"));
        button.setActionCommand("Save to Zip");
        button.setToolTipText("Save to Zip");
        button.addActionListener(generator);
        button.addMouseListener(this);
        add(button);

        button = new JButton();
        //Look for the image.
        imageURL = AstrolabeGenerator.class.getResource("gui/images/icons/Print24.gif");
        button.setIcon(new ImageIcon(imageURL, "Print"));
        button.setActionCommand("Print all");
        button.setToolTipText("Print");
        button.addActionListener(generator);
        button.addMouseListener(this);
        button.setEnabled(GeneratorGui.HAS_PRINTER_SUPPORT);
        add(button);

        addSeparator();

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 0;

        JLabel nameLabel = new JLabel("Name:");
        nameText = getNameText();
        locationPanel.add(nameLabel,c);
        c.gridx = 1;
        locationPanel.add(nameText,c);

        JLabel locationNameLabel = new JLabel("Location name:");
        locationNameText = getLocationNameText();
        c.gridx = 2;
        locationPanel.add(locationNameLabel,c);
        c.gridx = 3;
        locationPanel.add(locationNameText,c);

        JLabel locationLabel = new JLabel("Location:");
        locationText = getLocationText();
        c.gridx = 4;
        locationPanel.add(locationLabel,c);
        c.gridx = 5;
        locationPanel.add(locationText,c);

        add(locationPanel);
    }

    public JTextField getNameText() {
        if (null == nameText){
            nameText = new JTextField("",10);
            nameText.setToolTipText("Name on astrolabe (optional)");
            nameText.addMouseListener(this);
        }
        return nameText;
    }

    public JTextField getLocationNameText() {
        if (null == locationNameText){
            locationNameText = new JTextField("",10);
            locationNameText.setToolTipText("Name of location (optional)");
            locationNameText.addMouseListener(this);
        }
        return locationNameText;
    }

    public JTextField getLocationText() {
        if (null == locationText){
            locationText = new JTextField("",12);
            locationText.setToolTipText("Location Coordinates (DDMMSSH DDDMMSSH)");
            locationText.addMouseListener(this);
            locationText.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    if (!e.isTemporary()) {
                        if (!Location.isValidLocation(locationText.getText())) {
                            locationText.setForeground(Color.RED);
                            SwingUtilities.invokeLater(new FocusGrabber(locationText));
                            JOptionPane.showMessageDialog(null, "Location must be in the format: ddmmssh dddmmssh. \n" +
                                    "for example, \n\n" +
                                    "45 degrees 30 minutes, 25 seconds North, \n" +
                                    "120 degrees, 40 minutes, 13 seconds West \n\n" +
                                    "would be: 453025N 1204013W","Location Error",JOptionPane.ERROR_MESSAGE);
                        }else{
                            locationText.setForeground(Color.BLACK);
                            GeneratorGui.MY_ASTROLABE.getLocation().setLocation(locationText.getText());
                        }
                    }
                }
            });
        }
        return locationText;
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
        if (obj instanceof JTextField){
            text = ((JTextField) obj).getToolTipText();
        }else if (obj instanceof JButton){
            text = ((JButton) obj).getToolTipText();
        }

        GeneratorGui.setStatusLabel(text);
    }

    public void mouseExited(MouseEvent e) {
        GeneratorGui.setStatusLabel("");
    }

}
