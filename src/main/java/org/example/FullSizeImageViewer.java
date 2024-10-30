package org.example;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FullSizeImageViewer {
    public static void showImage(File imageFile) {
        JFrame frame = new JFrame("Full Size Image");
        frame.setSize(1280, 920);
        frame.setLocationRelativeTo(null);

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(imageFile));
            JLabel label = new JLabel(icon);
            frame.add(new JScrollPane(label));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}
