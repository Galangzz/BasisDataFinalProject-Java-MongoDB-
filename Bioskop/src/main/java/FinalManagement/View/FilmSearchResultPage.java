package FinalManagement.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import FinalManagement.Controller.MongoDBFunction;

public class FilmSearchResultPage {
    private JFrame frame;
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();


    public FilmSearchResultPage(List<String[]> hotels) {
        initializeFrame(hotels);
    }

    private void initializeFrame(List<String[]> hotels) {
        frame = new JFrame();
        frame.setTitle("Film Search Result Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\BasisDataFinalProject-Java-MongoDB-\\Bioskop\\src\\Cinema_XXI.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.lightGray);

        JLabel text = new JLabel("Result", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(-110, 35, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel(hotels);
        frame.add(rectanglePanel);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private JPanel getRectanglePanel(List<String[]> films) {
        JPanel rectanglePanel = getPanel();
        rectanglePanel.setLayout(new BorderLayout());

        JPanel filmPanel = new JPanel(new GridBagLayout());
        filmPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);

        for (String[] film : films) {
            JButton hotelButton = createFilmButton(film);
            filmPanel.add(hotelButton, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(filmPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        rectanglePanel.add(scrollPane, BorderLayout.CENTER);

        return rectanglePanel;
    }

    private JPanel getPanel() {
        JPanel rectanglePanel = getJPanel();
        rectanglePanel.setOpaque(false);

        JButton backButton = createRoundedButton();
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                Menu menu = new Menu();
                menu.showFrame();
            }
        });
        backButton.setBounds(330,40, 90, 40);

        rectanglePanel.add(backButton);

        return rectanglePanel;
    }

    private static JPanel getJPanel() {
        JPanel rectanglePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                int rectWidth = 400;
                int rectHeight = 700;
                int arcWidth = 30;
                int arcHeight = 30;
                int x = (getWidth() - rectWidth) / 2;
                int y = (getHeight() - rectHeight) / 2;
                g2d.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
            }
        };
        rectanglePanel.setBounds(40, 100, 400, 600);
        return rectanglePanel;
    }

    private JButton createFilmButton(String[] film) {
        JButton button = new JButton("<html>" +
                "<b>" + film[0] + "</b><br>" +
                "Rating: " + film[1] + "<br>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.lightGray);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2d.dispose();
            }
        };

        button.setForeground(Color.BLACK);

        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setPreferredSize(new Dimension(355, 100));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setVerticalAlignment(SwingConstants.CENTER);
        ImageIcon pathHotelPicture = new ImageIcon(mongoDBFunction.pathFilmPicture(film[0]));
        Image hotelPicture = pathHotelPicture.getImage();
        Image resizedHotelPicture = hotelPicture.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(resizedHotelPicture));

        button.addActionListener(e -> {
            List<String[]> filmDetailsData;
            frame.dispose();
            filmDetailsData = mongoDBFunction.fetchFilmDetails(film[0]);
            FilmDetails filmDetails = new FilmDetails(filmDetailsData);
            filmDetails.showFrame();
        });

        return button;
    }

    private static JButton createRoundedButton() {
        JButton button = new JButton("Back") {
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
}
