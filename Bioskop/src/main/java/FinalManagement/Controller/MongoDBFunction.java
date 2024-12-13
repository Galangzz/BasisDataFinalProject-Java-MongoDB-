package FinalManagement.Controller;

import FinalManagement.View.Menu;
import org.bson.Document;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static FinalManagement.View.LogInPage.frame;

public class MongoDBFunction extends MongoDBBase {
    private static String loggedEmail;
    private static String chosenFilm;
    private static String numberFilmBooked;

    public static void setLoggedEmail(String loggedEmail){
        MongoDBFunction.loggedEmail = loggedEmail;
    }

    public static void setChosenFilm(String chosenFilm) {
        MongoDBFunction.chosenFilm = chosenFilm;
    }

    public static void setNumberFilmBooked(String numberFilmBooked){
        MongoDBFunction.numberFilmBooked = numberFilmBooked;
    }

    public void userSignUp(String fullname, String gender, String id, String email, String password) {
        Document newUser = new Document("Name", fullname)
                .append("Gender", gender)
                .append("ID", id)
                .append("Email", email)
                .append("Password", password);
        insertDocument("User", newUser);
    }

    public void userLogIn(String email, String password) {
        Document filter = new Document("Email", email).append("Password", password);
        Document userFind = findFirstDocument("User", filter);
        setLoggedEmail(email);

        if (userFind != null) {
            JOptionPane.showMessageDialog(null, "Welcome, " + userFind.getString("Name") + "!");
            frame.dispose();
            Menu menu = new Menu();
            menu.showFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid email or password");
        }
    }

    public List<String[]> fetchFilmData(String filmName) {
        Document filter = new Document("title", new Document("$regex", ".*" + filmName + ".*").append("$options", "i"));

        List<Document> documents = findDocuments("movie", filter);

        List<String[]> films = new ArrayList<>();

        for (Document doc : documents) {
            String filmChosenName = doc.getString("title");
            String filmRating = doc.get("rating").toString();
            String filmDirector = doc.getString("director");
            String filmPlot = doc.getString("plot");
            String filmImagePath = doc.getString("filmpicturelocation");

            films.add(new String[]{filmChosenName, filmRating, filmDirector, filmPlot, filmImagePath});
        }
        return films;
    }

    public List<String[]> filmFetchRecommendationData() {
        Document filter = new Document("year", new Document("$gte", 2023))
                .append("rating", new Document("$gte", 8));

        List<Document> documents = findDocuments("movie", filter);
        List<String[]> movies = new ArrayList<>();

        for (Document doc : documents) {
            String title = doc.getString("title");
            String imagePath = doc.getString("filmpicturelocation");
            String rating = doc.get("rating") != null ? doc.get("rating").toString() : "N/A";
            movies.add(new String[]{title, imagePath, rating});
        }
        return movies;
    }

    public String pathFilmPicture(String filmName) {
        Document filter = new Document("title", filmName);
        Document document = findFirstDocument("movie", filter);

        if (document != null) {
            return document.getString("filmpicturelocation");
        } else {
            System.out.println("Path can't be found");
        }
        return null;
    }

    public List<String[]> fetchFilmDetails(String filmName) {
        Document filter = new Document("title", new Document("$regex", ".*" + filmName + ".*").append("$options", "i"));

        List<Document> documents = findDocuments("movie", filter);

        List<String[]> films = new ArrayList<>();

        for (Document doc : documents) {
            String filmChosenName = doc.getString("title");
            String filmRating = doc.get("rating").toString();
            String filmDirector = doc.getString("director");
            String filmPlot = doc.getString("plot");
            String filmImagePath = doc.getString("filmpicturelocation");
            String filmAvailableQuantity = doc.get("quantity").toString();
            String filmPrice = doc.get("price").toString();
            films.add(new String[]{filmChosenName, filmRating, filmDirector, filmPlot, filmImagePath, filmAvailableQuantity, filmPrice});
        }
        return films;
    }

    public boolean updateAvailability(List<String[]> films, String getBooked) {
        String[] filmsDetails = films.getFirst();

        try {
            setChosenFilm(filmsDetails[0]);
            setNumberFilmBooked(getBooked);
            int bookedQuantity = Integer.parseInt(getBooked);
            int remaining = Integer.parseInt(filmsDetails[5]) - bookedQuantity;

            if (remaining < 0) {
                System.err.println("Error: Booking quantity exceeds available rooms.");
                return false;
            }

            Document filter = new Document("title", filmsDetails[0]);
            Document updateOperation = new Document("$set", new Document("quantity", remaining));
            updateDocument("movie", filter, updateOperation);

            System.out.println("Database updated successfully.");
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input for booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: Database update failed.");
            e.printStackTrace();
        }
        return false;
    }

    public void addBookingToUser() {
        String timestamp = BookingTimestampHandler.getTimestamp();

        try {
            // Find the user
            Document userFilter = new Document("Email", loggedEmail);
            Document user = findFirstDocument("User", userFilter);

            if (user == null) {
                System.err.println("User not found.");
                return;
            }

            int newFilmBooking = Integer.parseInt(numberFilmBooked);

            List<Document> bookings = user.getList("bookings", Document.class, new ArrayList<>());

            boolean bookingExists = false;
            for (Document booking : bookings) {
                if (booking.getString("title").equals(chosenFilm)) {
                    int existingFilmBooked = booking.getInteger("quantity", 0);
                    int updatedFilmsBooked = existingFilmBooked + newFilmBooking;

                    Document updateFilter = new Document("Email", loggedEmail)
                            .append("bookings.title", chosenFilm);

                    Document updateOperation = new Document("$set",
                            new Document("bookings.$.quantity", updatedFilmsBooked));

                    updateDocument("User", updateFilter, updateOperation);
                    bookingExists = true;
                    System.out.println("Updated existing booking with additional quantity.");
                    break;
                }
            }

            if (!bookingExists) {
                Document newBooking = new Document()
                        .append("title", chosenFilm)
                        .append("quantity", newFilmBooking)
                        .append("booking_time", timestamp);

                Document updateOperation = new Document("$push", new Document("bookings", newBooking));
                updateDocument("User", userFilter, updateOperation);
                System.out.println("Added a new booking with timestamp: " + timestamp);
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for the booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Failed to add booking to user.");
            e.printStackTrace();
        }
    }


    public List<String[]> getOrderedRoomsByCustomer() {
        List<String[]> orderedFilms = new ArrayList<>();

        try {
            Document userFilter = new Document("Email", loggedEmail);
            Document user = findFirstDocument("User", userFilter);

            if (user == null) {
                System.err.println("User not found.");
                return orderedFilms;
            }

            List<Document> bookings = user.getList("bookings", Document.class, new ArrayList<>());

            for (Document booking : bookings) {
                String filmName = booking.getString("title");
                String roomsBooked = booking.get("quantity") != null ? booking.get("quantity").toString() : "N/A";
                String bookingTime = booking.getString("booking_time") != null ? booking.getString("booking_time") : "N/A";

                orderedFilms.add(new String[]{filmName, roomsBooked, bookingTime});
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to fetch ordered rooms.");
        }

        return orderedFilms;
    }

    public boolean updateAvailabilityCancel(List<String[]> films, String getBooked) {
        String[] filmsDetails = films.getFirst();

        try {
            setChosenFilm(filmsDetails[0]);
            setNumberFilmBooked(getBooked);
            int bookedQuantity = Integer.parseInt(getBooked);
            int remaining = Integer.parseInt(filmsDetails[5]) + bookedQuantity;

            if (remaining < 0) {
                System.err.println("Error: Booking quantity exceeds available rooms.");
                return false;
            }

            Document filter = new Document("title", filmsDetails[0]);
            Document updateOperation = new Document("$set", new Document("quantity", remaining));
            updateDocument("movie", filter, updateOperation);

            System.out.println("Database updated successfully.");
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input for booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: Database update failed.");
            e.printStackTrace();
        }
        return false;
    }

    public void cancelBookingFromUser(String numberFilmBookedCancel) {
        String timestamp = BookingTimestampHandler.getTimestamp();

        try {
            Document userFilter = new Document("Email", loggedEmail);
            Document user = findFirstDocument("User", userFilter);

            if (user == null) {
                System.err.println("User not found.");
                return;
            }

            int cancelQuantity = Integer.parseInt(numberFilmBookedCancel);
            List<Document> bookings = user.getList("bookings", Document.class, new ArrayList<>());

            boolean bookingExists = false;

            for (Document booking : bookings) {
                if (booking.getString("title").equals(chosenFilm)) {
                    int existingFilmBooked = booking.getInteger("quantity", 0);
                    int updatedFilmsBooked = existingFilmBooked - cancelQuantity;

                    if (updatedFilmsBooked < 0) {
                        System.err.println("Error: Cancel quantity exceeds the booked quantity.");
                        return;
                    }

                    if (updatedFilmsBooked == 0) {
                        Document pullOperation = new Document("$pull", new Document("bookings", new Document("title", chosenFilm)));
                        updateDocument("User", userFilter, pullOperation);

                        Document canceledBooking = new Document()
                                .append("title", chosenFilm)
                                .append("quantity", existingFilmBooked)
                                .append("cancel_time", timestamp);

                        Document pushCancelOperation = new Document("$push", new Document("cancelbooking", canceledBooking));
                        updateDocument("User", userFilter, pushCancelOperation);

                        System.out.println("Booking canceled and moved to cancelbooking.");
                    } else {
                        Document updateFilter = new Document("Email", loggedEmail)
                                .append("bookings.title", chosenFilm);

                        Document updateOperation = new Document("$set", new Document("bookings.$.quantity", updatedFilmsBooked));
                        updateDocument("User", updateFilter, updateOperation);

                        System.out.println("Updated booking with reduced quantity.");
                    }

                    bookingExists = true;
                    break;
                }
            }

            if (!bookingExists) {
                System.err.println("Booking not found for the specified title.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for the booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Failed to process booking cancellation.");
            e.printStackTrace();
        }
    }

}