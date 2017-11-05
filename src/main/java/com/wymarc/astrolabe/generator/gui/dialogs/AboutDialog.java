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
package com.wymarc.astrolabe.generator.gui.dialogs;

import com.wymarc.astrolabe.generator.AstrolabeGenerator;
import com.wymarc.astrolabe.generator.config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class AboutDialog extends JDialog {
    public AboutDialog(JFrame frame) {
        super(frame, true);
        init();
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void init() {
        setPreferredSize(new Dimension(600,480));
        setResizable(false);
        setLayout(new BorderLayout());
        setTitle("About");

        add(getIcon(), BorderLayout.WEST);
        add(getTextPanel(), BorderLayout.EAST);
        add(getBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel getTextPanel(){
        JPanel textPanel = new JPanel();
        textPanel.setBorder(new EmptyBorder(0,0,0,15));
        textPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,10,5,5);
        c.gridx = 0;
        c.gridy = 0;

        JLabel title = new JLabel("The Astrolabe Generator");
        title.setHorizontalAlignment(JLabel.RIGHT);
        title.setFont(new Font("Times New Roman", Font.BOLD+Font.ITALIC, 20));
        title.setForeground(Color.BLUE);
        textPanel.add(title,c);

        JLabel version = new JLabel("Version " + Config.version);
        version.setHorizontalAlignment(JLabel.RIGHT);
        version.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        c.gridy = 1;
        textPanel.add(version,c);

        JTextPane textArea = new JTextPane();
        textArea.setOpaque(false);
        textArea.setContentType("text/html");
        textArea.setEditable(false);
        // todo: set up text wrapping: http://stackoverflow.com/questions/4702891/toggling-text-wrap-in-a-jtextpane
        String text = "<i>Dedicated to the memory of James E. Morrison<br/>author of <b>The Astrolabe</b></i><br/><br/>";
        text += "The Astrolabe Generator is free software; you can<br/>";
        text += "redistribute it and/or modify it under the terms of the<br/>";
        text += "GNU General Public License as published by the Free<br/>";
        text += "Software Foundation; either version 2 of the License,<br/>";
        text += "or (at your option) any later version.<br/><br/>";
        text += "The Astrolabe Generator is distributed in the hope that<br/>";
        text += "it will be useful, but WITHOUT ANY WARRANTY; without<br/>";
        text += "even the implied warranty of MERCHANTABILITY or<br/>";
        text += "FITNESS FOR A PARTICULAR PURPOSE.  See the GNU<br/>";
        text += "General Public License for more details.";
        textArea.setText(text);
        c.gridy = 2;
        textPanel.add(textArea,c);

        JLabel copyRight = new JLabel("Copyright(c) 2014 - 2017 Timothy J . Mitchell");
        copyRight.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        c.gridy = 3;
        textPanel.add(copyRight,c);

        return textPanel;
    }

    private JPanel getBottomPanel(){
        JPanel bottom = new JPanel();
        bottom.setBorder(new EmptyBorder(10,0,10,0));
        bottom.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,20,0,20);
        c.gridx = 0;
        c.gridy = 0;

        JLabel contact1 = new JLabel("Project page: http://sourceforge.net/projects/astrolabegenera");
        contact1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        bottom.add(contact1,c);

        JLabel contact2 = new JLabel("Home page: http://astrolabeproject.com");
        contact2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        c.gridy = 1;
        bottom.add(contact2,c);

        c.gridx = 1;
        c.gridy = 1;
        JButton okayButton = new javax.swing.JButton("OK");
        okayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okayButtonActionPerformed(evt);
            }
        });
        bottom.add(okayButton,c);


        return bottom;
    }

    private JLabel getIcon(){
        java.net.URL imgURL = AstrolabeGenerator.class.getResource("gui/images/logo.png");
        if (imgURL != null) {
            JLabel icon = new JLabel((new ImageIcon(imgURL)));
            icon.setBorder(new EmptyBorder(0,15,0,0));
            return icon;
        }
        return new JLabel();
    }

    private void okayButtonActionPerformed(java.awt.event.ActionEvent evt){
        this.dispose();
    }

}

