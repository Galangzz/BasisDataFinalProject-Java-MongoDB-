Preparation
1. MongoDB Compass
2. IDE (Visual Studio Code, IntelliJ, etc)
3. Source Code 

How to Make the Code Run Properly
1. Install MongoDB Compass and add the bin path to the environment variables
2. Make a new connection
3. Connect to your new connection
4. Make a new database with the name "BasisDataProject"
5. Make a new collection with the name "movie" and "User"
6. Restore the data by clicking the "Add Data" button then choose "Import JSON" and choose the JSON file (BasisDataProject.User.json >> User, and BasisDataProject.movie.json >> movie)

## "Add Data "Button
![Screenshot 2024-12-13 120616](https://github.com/user-attachments/assets/5b593b24-3df7-41e8-8b6d-255a6d99e8ad)

## JSON Data
![Screenshot 2024-12-13 120750](https://github.com/user-attachments/assets/ac775821-e565-4db0-8b50-f154d964911c)


8. Update the "filepicturelocation" field by changing it to the absolute path of the pictures // These pictures are already included in the source code

## Screenshots
![Screenshot 2024-12-13 115632](https://github.com/user-attachments/assets/a02d4ac9-3aa4-4245-a7d1-eace924bcf5a)


9. Lastly, you must change this part of every class code in the "View" folder. You'll need to change the absolute path of a picture named "Cinema_XXI.png"

## Part of Code in "View" Folder That Needs to be Changed
```
private void initializeFrame() {
...

ImageIcon originalIcon = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\2 Tugas Kuliah\\Basis Data\\TUGAS 16 (Final Task)\\Bioskop\\src\\Cinema_XXI.png");
Image originalImage = originalIcon.getImage();

...

ImageIcon iconTopLeft = new ImageIcon("G:"Change this part of path"\\src\\Cinema_XXI.png");
frame.setIconImage(iconTopLeft.getImage());

...
}

