package org.example;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImagePanel extends JPanel {
    public ImagePanel() {
        setLayout(new GridLayout(3, 3, 10, 10));
    }

    public void updateImages(List<File> images) {
        removeAll();
        for (File imageFile : images) {
            try {
                BufferedImage image = ImageIO.read(imageFile);
                ImageIcon thumbnail = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                JLabel imageLabel = new JLabel(thumbnail);
                imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        FullSizeImageViewer.showImage(imageFile);
                    }
                });
                add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        revalidate();
        repaint();
    }
}
