package com.wymarc.astrolabe.generator.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.net.URL;

public class GeneratorImagePanel extends JPanel {
    // todo: needs to be a layered image
    private Image img;

    public GeneratorImagePanel(URL img) {
        this(new ImageIcon(img).getImage());
    }

    public GeneratorImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}