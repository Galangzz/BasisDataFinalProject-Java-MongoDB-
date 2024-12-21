package FinalManagement.Controller;

import javax.swing.JOptionPane;

import FinalManagement.View.ListFilmOrder;
import FinalManagement.View.LogInPage;
import static FinalManagement.View.Menu.frame;

public class Button {
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();

    public static boolean  handleSignUp(String fullName, String gender, String id, String email, String password) {
        if (fullName.isEmpty() || gender.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            mongoDBFunction.userSignUp(fullName, gender, id, email, password);
            JOptionPane.showMessageDialog(null, "Sign Up Successful! \nPlease login", "Success", JOptionPane.INFORMATION_MESSAGE);
            LogInPage logInPage = new LogInPage();
            logInPage.showFrame();
            return true;
        }
    }

    public static void handleLogin(String email, String password) {
        mongoDBFunction.userLogIn(email, password);
    }

    public static void handleMenuToListRoomOrder(){
        frame.dispose();
        ListFilmOrder listFilmOrder = new ListFilmOrder(mongoDBFunction.getOrderedRoomsByCustomer());
        listFilmOrder.showFrame();
    }
    public static void handleMenuToExit(){
        frame.dispose();
        JOptionPane.showMessageDialog(null, "Goodbye !!! \nHave a Nice Day", "See You Later", JOptionPane.INFORMATION_MESSAGE);
        LogInPage logInPage = new LogInPage();
        logInPage.showFrame();
    }
}
