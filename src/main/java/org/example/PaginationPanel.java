package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginationPanel extends JPanel {
    private JButton prevButton;
    private JButton nextButton;
    private JLabel pageLabel;
    private int currentPage = 1;
    private int totalPages = 1;

    public PaginationPanel(Runnable onPrevPage, Runnable onNextPage) {
        setLayout(new FlowLayout());

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        pageLabel = new JLabel();

        prevButton.addActionListener(e -> onPrevPage.run());
        nextButton.addActionListener(e -> onNextPage.run());

        add(prevButton);
        add(pageLabel);
        add(nextButton);
    }

    public void updatePagination(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;

        pageLabel.setText("Page " + currentPage + " of " + totalPages);
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
    }
}