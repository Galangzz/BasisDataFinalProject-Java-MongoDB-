package FinalManagement.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

import FinalManagement.Controller.Button;
import FinalManagement.Controller.MongoDBFunction;

public class Menu {
    public static JFrame frame;
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();

    public Menu() {
        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setTitle("Menu Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\TUGAS 16 (Final Task)\\Bioskop\\src\\Cinema_XXI.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.lightGray);

        ImageIcon originalIcon = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\TUGAS 16 (Final Task)\\Bioskop\\src\\Cinema_XXI.png");
        Image originalImage = originalIcon.getImage();

        int desiredWidth = 75;
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();
        int desiredHeight = (originalHeight * desiredWidth) / originalWidth;

        Image scaledImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        logoLabel.setBounds(200, 110, desiredWidth, desiredHeight);
        frame.add(logoLabel);

        JLabel text = new JLabel("Search", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(35, 110 + desiredHeight, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel();
        frame.add(rectanglePanel);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private static JPanel getRectanglePanel() {
        JPanel rectanglePanel = getPanel();

        int bigRectWidth = 400;
        int bigRectHeight = 600;

        int inputWidth = 320;
        int inputHeight = 40;
        int spacing = 30;

        int startX = (bigRectWidth - inputWidth) / 2;
        int startY = (bigRectHeight - (2 * inputHeight + spacing)) / 2;

        JButton yourBooking = createRoundedButton(Button::handleMenuToListRoomOrder
        );
        yourBooking.setBounds(startX + 30, startY + spacing * 12, inputWidth - 100, inputHeight);

        JTextField filmNameInputField = createRoundedInputField();
        filmNameInputField.setBounds(startX + 30, startY - 30, inputWidth, inputHeight - 5);

        JButton exitButton = createRoundedExit(Button::handleMenuToExit);
        exitButton.setBounds(startX + 255, startY + spacing * 12, inputWidth - 223, inputHeight);

        JButton findButton = createRoundedButton();
        findButton.setBounds(startX + 30, startY + inputHeight + spacing - 50, inputWidth, inputHeight);
        findButton.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                frame.dispose();
                List<String[]> films = mongoDBFunction.fetchFilmData(filmNameInputField.getText());
                FilmSearchResultPage filmSearchResultPage = new FilmSearchResultPage(films);
                filmSearchResultPage.showFrame();
            }
        });

        rectanglePanel.add(filmNameInputField);
        rectanglePanel.add(findButton);

        rectanglePanel.add(yourBooking);
        rectanglePanel.add(exitButton);
        displayFilmRecommendations();
        return rectanglePanel;
    }

    private static JPanel getPanel() {
        JPanel rectanglePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);

                int rectWidth = 400;
                int rectHeight = 600;
                int arcWidth = 30;
                int arcHeight = 30;

                int x = (getWidth() - rectWidth) / 2;
                int y = (getHeight() - rectHeight) / 2;

                g2d.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
            }
        };
        rectanglePanel.setBounds(40, 100, 400, 600);
        rectanglePanel.setOpaque(false);
        return rectanglePanel;
    }

    private static JButton createRoundedButton(Runnable action) {
        JButton button = new JButton("Your Booking") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.dispose();
            }
        };

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.addActionListener(e -> action.run());
        return button;
    }

    private static JButton createRoundedExit(Runnable action) {
        JButton button = new JButton("Exit") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.dispose();
            }
        };

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBackground(new Color(204, 51, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.addActionListener(e -> action.run());
        return button;
    }

    private static JTextField createRoundedInputField() {
        JTextField textField = new JTextField("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2d);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.GRAY);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        textField.setOpaque(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("");
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        textField.setForeground(Color.GRAY);
        return textField;
    }

    private static JButton createRoundedButton() {
        JButton button = new JButton("Find") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.dispose();
            }
        };

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        return button;
    }

    private static void displayFilmRecommendations() {
        List<String[]> recommendations = mongoDBFunction.filmFetchRecommendationData();

        JLabel recommendationLabel = new JLabel("Recommended Film", SwingConstants.CENTER);
        recommendationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        recommendationLabel.setForeground(Color.BLACK);
        recommendationLabel.setBounds(110, 320, 250, 30);

        JPanel recommendationsPanel = new JPanel();
        recommendationsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        recommendationsPanel.setBackground(Color.LIGHT_GRAY);
        recommendationsPanel.setOpaque(true);
        recommendationsPanel.setBackground(Color.WHITE);

        for (String[] movie : recommendations) {
            String title = movie[0];
            String imagePath = movie[1];
            String rating = movie[2];

            JButton filmButton = new JButton();
            filmButton.setLayout(new BorderLayout());
            filmButton.setBackground(Color.WHITE);
            filmButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            filmButton.setFocusPainted(false);

            JLabel imageLabel = new JLabel();
            try {
                ImageIcon filmIcon = new ImageIcon(imagePath);
                Image scaledImage = filmIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                imageLabel.setText("Image not available");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setPreferredSize(new Dimension(100, 150));
            }
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            filmButton.add(imageLabel, BorderLayout.NORTH);

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBackground(Color.WHITE);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel ratingLabel = new JLabel("Rating: " + rating);
            ratingLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            detailsPanel.add(titleLabel);
            detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            detailsPanel.add(ratingLabel);

            filmButton.add(detailsPanel, BorderLayout.CENTER);

            filmButton.addActionListener(e -> {
                frame.dispose();

                System.out.println("Film button clicked: " + title);
                List<String[]> filmDetailsData;
                frame.dispose();
                filmDetailsData = mongoDBFunction.fetchFilmDetails(title);
                FilmDetails filmDetails = new FilmDetails(filmDetailsData);
                filmDetails.showFrame();
            });
            recommendationsPanel.add(filmButton);
        }

        // Create a horizontal scroll pane
        JScrollPane scrollPane = new JScrollPane(recommendationsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(110, 350, 250, 220);

        JButton leftButton = new JButton("◀");
        JButton rightButton = new JButton("▶");

        leftButton.setBounds(60, 440, 50, 30);
        leftButton.setOpaque(true);
        leftButton.setBackground(Color.WHITE);
        leftButton.setBorderPainted(false);
        rightButton.setBounds(359, 440, 50, 30);
        rightButton.setOpaque(true);
        rightButton.setBackground(Color.WHITE);
        rightButton.setBorderPainted(false);

        leftButton.addActionListener(e -> {
            JViewport viewport = scrollPane.getViewport();
            Point currentPosition = viewport.getViewPosition();
            int scrollSpeed = 50;
            currentPosition.x = Math.max(currentPosition.x - scrollSpeed, 0);
            viewport.setViewPosition(currentPosition);
        });

        rightButton.addActionListener(e -> {
            JViewport viewport = scrollPane.getViewport();
            Point currentPosition = viewport.getViewPosition();
            int scrollSpeed = 50;
            int maxX = recommendationsPanel.getWidth() - viewport.getWidth();
            currentPosition.x = Math.min(currentPosition.x + scrollSpeed, maxX);
            viewport.setViewPosition(currentPosition);
        });

        frame.add(recommendationLabel);
        frame.add(leftButton);
        frame.add(rightButton);
        frame.add(scrollPane);
        frame.repaint();
        frame.revalidate();
    }
}