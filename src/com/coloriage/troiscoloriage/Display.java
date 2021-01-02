/*
 * Display.java
 * Coloriage
 *
 * Created by Valérie Marissens Cueva on 2/1/2021.
 * Copyright (c) 2021 Valérie Marissens Cueva. All rights reserved.
 */

package com.coloriage.troiscoloriage;

import javax.swing.*;
import java.awt.*;

/**
 * Affiche le graphe dans une fenêtre.
 */
public class Display extends JFrame {
    private final JLabel jlabel ;
    boolean visible;

    public Display(String title) {
        super(title);       // Titre de la fenêtre
        setPreferredSize(new Dimension(500, 500));  // largeur, hauteur

        jlabel = new JLabel();
        visible = false;
        this.add(jlabel, BorderLayout.CENTER);
        this.pack();
    }

    public void setImage(Image blop) {
        if (!visible)
        {
            visible = true;
            this.setVisible(true);
        }

        jlabel.setIcon(new ImageIcon(blop));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /** La fenêtre n'est plus visible
     *
     */
    public void close() {
        this.dispose();
    }

}
