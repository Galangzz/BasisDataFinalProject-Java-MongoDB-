package FinalManagement.View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import FinalManagement.Controller.Button;

public class SignUpPage {
    private static JFrame frame;

    public SignUpPage() {
        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setTitle("Sign Up Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\BasisDataFinalProject-Java-MongoDB-\\Bioskop\\src\\Cinema_XXI.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.lightGray);

        ImageIcon originalIcon = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\BasisDataFinalProject-Java-MongoDB-\\Bioskop\\src\\Cinema_XXI.png");
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

        JLabel text = new JLabel("Sign Up", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(40, 110 + desiredHeight + 10, 400, 50);
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
        int bigRectHeight = 830;

        int inputWidth = 320;
        int inputHeight = 40;
        int spacing = 30;

        int startX = (bigRectWidth - inputWidth) / 2;
        int startY = (bigRectHeight - (2 * inputHeight + spacing)) / 2;

        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        fullNameLabel.setBounds(startX + 30, startY - 150, inputWidth, 20);

        JTextField fullNameField = createRoundedInputField();
        fullNameField.setBounds(startX + 30, startY - 120, inputWidth, inputHeight);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        genderLabel.setBounds(startX + 30, startY + inputHeight + spacing - 140, inputWidth, 20);

        JTextField genderField = createRoundedInputField();
        genderField.setBounds(startX + 30, startY + inputHeight + spacing - 110, inputWidth, inputHeight);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setBounds(startX + 30, startY + inputHeight + spacing - 70, inputWidth, inputHeight);

        JTextField idField = createRoundedInputField();
        idField.setBounds(startX + 30, startY + inputHeight + spacing - 30, inputWidth, inputHeight);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(startX + 30, startY + inputHeight + spacing + 10, inputWidth, inputHeight);

        JTextField emailField = createRoundedInputField();
        emailField.setBounds(startX + 30, startY + inputHeight + spacing + 50, inputWidth, inputHeight);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setBounds(startX + 30, startY + inputHeight + spacing + 90, inputWidth, inputHeight);

        JPasswordField passwordField = createRoundedPasswordField();
        passwordField.setBounds(startX + 30, startY + inputHeight + spacing + 130, inputWidth, inputHeight);


        JButton signUpButton = createRoundedButton("Sign Up", () ->{
            if (Button.handleSignUp(fullNameField.getText(), genderField.getText(), idField.getText(), emailField.getText(), String.valueOf(passwordField.getPassword()))) {
                frame.dispose();
            }
        });
        signUpButton.setBounds(startX + 30, startY + inputHeight + spacing + 180, inputWidth, inputHeight);

        JLabel alreadyHaveAccountLabel = new JLabel("<HTML><U>Already have an account? Log in</U></HTML>");
        alreadyHaveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        alreadyHaveAccountLabel.setForeground(Color.lightGray);
        alreadyHaveAccountLabel.setBounds(startX + 75, startY + inputHeight + spacing + 220, inputWidth - 95, 20);
        alreadyHaveAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        alreadyHaveAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alreadyHaveAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Redirecting to Login Page...");
                LogInPage logInPage = new LogInPage();
                logInPage.showFrame();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                alreadyHaveAccountLabel.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                alreadyHaveAccountLabel.setForeground(Color.lightGray);
            }
        });

        rectanglePanel.add(fullNameLabel);
        rectanglePanel.add(fullNameField);
        rectanglePanel.add(genderLabel);
        rectanglePanel.add(genderField);
        rectanglePanel.add(idLabel);
        rectanglePanel.add(idField);
        rectanglePanel.add(emailLabel);
        rectanglePanel.add(emailField);
        rectanglePanel.add(passwordLabel);
        rectanglePanel.add(passwordField);
        rectanglePanel.add(signUpButton);
        rectanglePanel.add(alreadyHaveAccountLabel);

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

    private static JPasswordField createRoundedPasswordField() {
        JPasswordField passwordField = new JPasswordField("") {
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
        passwordField.setOpaque(false);
        passwordField.setBackground(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.setForeground(Color.GRAY);
        return passwordField;
    }
}