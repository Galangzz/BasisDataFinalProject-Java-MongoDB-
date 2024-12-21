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
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class FilmDetails {
    private JFrame frame;

    public FilmDetails(List<String[]> films) {
        initializeFrame(films);
    }

    private void initializeFrame(List<String[]> films) {
        frame = new JFrame();
        frame.setTitle("Film Details Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\TUGAS 16 (Final Task)\\Bioskop\\src\\Cinema_XXI.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.lightGray);

        JLabel text = new JLabel("Film Details", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(-85, 35, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel(films);
        frame.add(rectanglePanel);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private JPanel getRectanglePanel(List<String[]> rooms) {
        JPanel rectanglePanel = getPanel();
        rectanglePanel.setLayout(new BorderLayout());

        JPanel roomsPanel = new JPanel(new GridBagLayout());
        roomsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);

        for (String[] room : rooms) {
            JButton roomButton = createRoomButton(room);
            roomsPanel.add(roomButton, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(roomsPanel);
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
        backButton.setBounds(330, 40, 90, 40);

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
        rectanglePanel.setBounds(40, 110, 400, 600);
        return rectanglePanel;
    }

    private JButton createRoomButton(String[] films) {
        JButton button = new JButton("<html><div style='margin: 5px'>" +
                "<b>" + films[0] + "</b>" + "<br>" +
                " " + "<br>" +
                "Rating: " + films[1] + "<br>" +
                "Director: " + films[2] + "<br>" +
                "Available: " + films[5] + "<br><br>" +
                "Summary: <br>" + films [3] + "<br>"+ 
                "</div></html>") {
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
                g2d.drawRoundRect(0, 1, getWidth() - 1, getHeight() - 2, 30, 30);
                g2d.dispose();
            }
        };

        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 375));
        
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setVerticalAlignment(SwingConstants.NORTH);

        if (films[4] != null && !films[4].isEmpty()) {
            ImageIcon filmImageIcon = new ImageIcon(films[4]);
            Image filmImage = filmImageIcon.getImage();
            Image resizedFilmImage = filmImage.getScaledInstance(110, 175, Image.SCALE_SMOOTH);
            
            button.setIcon(new ImageIcon(resizedFilmImage));
        } else {
            button.setIcon(new ImageIcon("path/to/default/image.png"));
        }
        
        button.addActionListener(e -> {
            frame.dispose();
            new ConfirmationBooking(Collections.singletonList(films));
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
