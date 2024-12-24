package FinalManagement.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import FinalManagement.Controller.MongoDBFunction;

public class ConfirmationBooking {
    public static JFrame frame;
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();


    public ConfirmationBooking(List<String[]> films) {
        initializeFrame(films);
    }

    private void initializeFrame(List<String[]> films) {
        frame = new JFrame();
        frame.setTitle("Booking Confirmation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("D:\\VSC\\Visual Studio Cde\\Java\\JavaSE\\TUGAS 16 (Final Task)\\TUGAS 16 (Final Task)\\Bioskop\\src\\Cinema_XXI.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel text = new JLabel("Booking Confirmation", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(40, 120, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel(films);
        frame.add(rectanglePanel);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static JPanel getRectanglePanel(List<String[]> films) {
        JPanel rectanglePanel = getPanel();

        int bigRectWidth = 400;
        int bigRectHeight = 600;

        int inputWidth = 320;
        int spacing = 30;

        int startX = (bigRectWidth - inputWidth) / 2;
        int startY = 100;

        rectanglePanel.setLayout(null);


        String[] filmsDetails = films.getFirst();
        JLabel filmLabel = new JLabel(filmsDetails[0]);
        filmLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        filmLabel.setBounds(startX, startY + 5 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(filmLabel);

        JLabel ratingLabel = new JLabel("Rating: " + filmsDetails[1]);
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ratingLabel.setBounds(startX, startY + 6 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(ratingLabel);

        JLabel priceLabel = new JLabel("Price: $" + filmsDetails[6]);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setBounds(startX, startY + 7 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(priceLabel);

        JLabel availabilityLabel = new JLabel("Available: " + filmsDetails[5]);
        availabilityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        availabilityLabel.setBounds(startX, startY + 8 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(availabilityLabel);

        JLabel numberOfBooking = new JLabel("Quantity: ");
        numberOfBooking.setFont(new Font("Arial", Font.PLAIN, 16));
        numberOfBooking.setBounds(startX, startY + 9 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(numberOfBooking);

        JTextField numberOfBook = createRoundedInputField();
        numberOfBook.setBounds(startX + 70, startY + 9 * spacing + 28, inputWidth - 90, 25);
        rectanglePanel.add(numberOfBook);

        ImageIcon roomImageIcon = new ImageIcon(filmsDetails[4]);
        Image roomImage = roomImageIcon.getImage().getScaledInstance(125, 175, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(roomImage));
        imageLabel.setBounds(140, 80, 125, 175);
        rectanglePanel.add(imageLabel);

        JButton confirmButton = createRoundedButton("Confirm", () -> {
            int quantity = Integer.parseInt(numberOfBook.getText());
            int availibleFilm = Integer.parseInt(filmsDetails[5]);
            if(quantity <= 0 || quantity > availibleFilm){
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
                numberOfBook.setText("");
            }else{

                boolean updateSuccess = mongoDBFunction.updateAvailability(Collections.singletonList(filmsDetails), numberOfBook.getText());
                if (updateSuccess) {
                    JOptionPane.showMessageDialog(frame, "Booking confirmed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    mongoDBFunction.addBookingToUser();
                    frame.dispose();
                    Menu menu = new Menu();
                    menu.showFrame();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to confirm booking. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        confirmButton.setBounds(startX + 30, bigRectHeight - 80, 120, 40);
        rectanglePanel.add(confirmButton);

        confirmButton.setBounds(startX + 30, bigRectHeight - 80, 120, 40);
        rectanglePanel.add(confirmButton);

        JButton cancelButton = createRoundedButton("Cancel",()->{
            frame.dispose();
            Menu menu = new Menu();
            menu.showFrame();
        });
        cancelButton.setBounds(startX + 170, bigRectHeight - 80, 120, 40);
        rectanglePanel.add(cancelButton);

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

    private static JButton createRoundedButton(String text, Runnable action) {
        JButton button = new JButton(text) {
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
}
