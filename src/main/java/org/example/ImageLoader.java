package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageLoader {
    private List<File> allImages;
    private List<File> filteredImages;
    private final int imagesPerPage = 9;

    public ImageLoader(File folder) {
        this.allImages = new ArrayList<>();
        loadImagesFromFolder(folder);
    }

    private void loadImagesFromFolder(File folder) {
        File[] files = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".jpg") ||
                        name.toLowerCase().endsWith(".jpeg") ||
                        name.toLowerCase().endsWith(".png"));
        if (files != null) {
            for (File file : files) {
                allImages.add(file);
            }
        }
        filteredImages = allImages;
    }
    public void addImage(File file) {
        if (!allImages.contains(file) && (file.getName().toLowerCase().endsWith(".jpg") ||
                file.getName().toLowerCase().endsWith(".jpeg") || file.getName().toLowerCase().endsWith(".png"))) {
            allImages.add(file);
            filteredImages = allImages;
        }
    }
    public List<File> getAllImages() {
        return allImages;
    }



    public void loadImages() {
        filteredImages = allImages;
    }

    public void filterImages(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredImages = allImages;
        } else {
            filteredImages = allImages.stream()
                    .filter(file -> file.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    public List<File> getImagesForPage(int page) {
        int start = (page - 1) * imagesPerPage;
        int end = Math.min(start + imagesPerPage, filteredImages.size());
        return filteredImages.subList(start, end);
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) filteredImages.size() / imagesPerPage);
    }
}
