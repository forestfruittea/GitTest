package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ImageGalleryApp extends JFrame {
    private JTextField searchField;
    private JButton loadButton;
    private ImagePanel imagePanel;
    private PaginationPanel paginationPanel;
    private ImageLoader imageLoader;
    private final File assetsFolder = new File("assets");
    private final File imageListFile = new File("assets/imageList.txt");

    private int currentPage = 1;

    public ImageGalleryApp() {
        setTitle("Image Gallery");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (!assetsFolder.exists()) {
            assetsFolder.mkdir();
        }
        loadSavedImages();

        createUI();
        updateGallery(currentPage);
    }

    private void createUI() {
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        searchField = new JTextField(20);
        loadButton = new JButton("Load Images");

        controlPanel.add(new JLabel("Search:"));
        controlPanel.add(searchField);
        controlPanel.add(loadButton);


        imagePanel = new ImagePanel();
        paginationPanel = new PaginationPanel(
                this::previousPage,
                this::nextPage
        );

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(imagePanel), BorderLayout.CENTER);
        add(paginationPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> openImageChooser());
        searchField.addActionListener(e -> filterImages(searchField.getText()));

        loadImages();
    }
    private void loadSavedImages() {
        try {
            if (imageListFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(imageListFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    File imageFile = new File(assetsFolder, line);
                    if (!imageFile.exists()) {
                        // Restore image if it doesn't exist in assets
                        Files.copy(Paths.get(line), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                reader.close();
            }
            imageLoader = new ImageLoader(assetsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        JFileChooser fileChooser = new JFileChooser("assets");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                try {
                    File destFile = new File("assets/" + file.getName());
                    if (!destFile.exists()) {
                        Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            imageLoader.loadImages();
            currentPage = 1;
            updateGallery(currentPage);

        }
    }

    private void saveImageList() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(imageListFile))) {
            for (File image : imageLoader.getAllImages()) {
                writer.println(image.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openImageChooser() {
        JFileChooser fileChooser = new JFileChooser("assets");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                try {
                    File destFile = new File(assetsFolder, file.getName());
                    if (!destFile.exists()) {
                        Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        imageLoader.addImage(destFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            saveImageList();
            currentPage = 1;
            updateGallery(currentPage);
        }
    }

    private void updateGallery(int page) {
        currentPage = page;
        List<File> imagesToShow = imageLoader.getImagesForPage(currentPage);
        imagePanel.updateImages(imagesToShow);
        paginationPanel.updatePagination(currentPage, imageLoader.getTotalPages());
    }

    private void filterImages(String query) {
        imageLoader.filterImages(query);
        currentPage = 1;
        updateGallery(currentPage);
    }

    private void previousPage() {
        if (currentPage > 1) {
            updateGallery(currentPage - 1);
        }
    }

    private void nextPage() {
        if (currentPage < imageLoader.getTotalPages()) {
            updateGallery(currentPage + 1);
        }
    }


    }