package FinalManagement.Controller;

import FinalManagement.View.FilmSearchResultPage;
import FinalManagement.View.ListFilmOrder;
import FinalManagement.View.LogInPage;

import javax.swing.*;
import static FinalManagement.View.Menu.frame;

public class Button {
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();

    public static void handleSignUp(String fullName, String gender, String id, String email, String password) {
        if (fullName.isEmpty() || gender.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            mongoDBFunction.userSignUp(fullName, gender, id, email, password);
            JOptionPane.showMessageDialog(null, "Sign Up Successful! \nPlease login", "Success", JOptionPane.INFORMATION_MESSAGE);
            LogInPage logInPage = new LogInPage();
            logInPage.showFrame();
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
}
