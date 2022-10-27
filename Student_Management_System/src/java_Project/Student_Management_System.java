package java_Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Student_Management_System {

	public static void main(String[] args)throws IOException {
		 Scanner input = new Scanner(System.in);
	     char againYES_NO = 'y';
	        do{
	            System.out.println("\n\n********* Welcome To Student Management System *********\n");
	          
	                 StudentRecord.student_Main();
	            
	            System.out.println("\nSwitch off  [x] the student management system or Continue [y]: ");
	            againYES_NO = input.next().toLowerCase().charAt(0);// yes
	        }while (againYES_NO == 'y');
	        System.out.println("Signing off.....");

	}

}

//This class is the called student record because it performs the feature that are necessary for student record.Like add new student ,delete the existing student details from the user student list,update the student and find the student as per the user requirement 
class StudentRecord {
 public static void student_Main() throws IOException {
     Scanner input = new Scanner(System.in);
     String[] credentials = {"Login", "Sign-Up"};
     boolean loop = false;
     char userChoice;
     do {
         System.out.println("\n*********** [ Student - Record ] ***********");
         for (int i = 0; i < credentials.length; i++)
             System.out.printf("\n[%d] %s ", (i + 1), credentials[i]);
         System.out.print("\nSelect appropriate option (0 to exit): ");
         userChoice = input.next().charAt(0);

         switch (userChoice) {
             case '1' : loop = loginPage();break;
             case '2' : loop = signUpPage();break;
             case '0' : return;
             default: loop = true;
         }
     } while (loop);
 }
 // After a registered user passed out from the login panel, he will access this method for
 // further access to feature of student record.
 //The features includes (add, delete, read, search and update)
 public static void successfulLogin(String filename)throws IOException{
     Scanner input = new Scanner(System.in);
     String[] infoDetails = {"Student-Name: ","Rol / Contact # ","Address: "};
     String[] list = {"Add ","Read ","Find ","Delete Record / Update Data"};
     String choice;
     String data = "*************[Student-Record]*************";
     char continueOrExit = 'y';
     do {
         for (int i = 0; i < list.length; i++)
             System.out.printf("[%d] %s \n",(i+1),list[i]);
         System.out.print("Enter Choice: ");
         choice = input.next();
         try{
             if (!(Integer.parseInt(choice) > 0  & Integer.parseInt(choice)<=4)){
                 System.out.println("Please select Appropriate Option");
                 continue;
             }
         }
         catch (Exception e) {
             System.out.println("Please select integer Option");
             continue;
         }
         switch (choice){
             case "1": {
                 data +=  add(infoDetails);
                 saveData(data, filename);
             }break;
             case "2": readData(filename);break;
             case "3": findEntity(filename);break;
             case "4": updateStudent_record( filename,infoDetails);break;
         }
         System.out.println("\nPress [x] to exit\nPress [y] to continue: ");
         continueOrExit = input.next().charAt(0);
     }while (continueOrExit == 'y');
 }
 // Method for adding data to student record
 //File writer object is used for data appending and inserting
 public static String add(String[] info){
     String savedData = "\n";
     char choice;
     System.out.println("\nEnter student Details...");
     do {
         Scanner input = new Scanner(System.in);
         for (int i = 0; i < info.length; i++) {
             input.useDelimiter("\\r");
             System.out.print("\n" + info[i]);
             String data = input.nextLine();
             input.reset();
             savedData += info[i] + data +"\n";
         }
         savedData += "\n---------------\n";
         System.out.println("\nPress (y) to save another student record or (n) to exit");
         choice = input.next().toLowerCase().charAt(0);
     }while(choice == 'y');
     return savedData;
 }
 // Method for Storing data to student record
 //File writer object is used for data appending and inserting
 public static void saveData(String data , String filename) throws IOException{
     File student_record_text_file = new File(filename);
     if(!(student_record_text_file.exists())) {
         try {
             student_record_text_file.createNewFile();
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }
     FileWriter fileWriter = new FileWriter(student_record_text_file,true);
     fileWriter.write(data);
     fileWriter.close();
 }
 // Method for reading data to student record
 //Scanner object is used for data reading
 //The whole student record is read through hasNext() iterator
 // And Sequences are used to counter the contact being found
 public static void readData(String filename)throws IOException{
     File myFile = new File(filename);
     Scanner scan = new Scanner(myFile);
     try {
         scan.nextLine();
     }
     catch (Exception e){
         System.out.println("Student record exits but it is Empty");
         return;
     }
     Scanner scanner = new Scanner(myFile);
     System.out.println();
     while (scanner.hasNextLine())
         System.out.println(scanner.nextLine());
     scan.close();
 }
 // Method for finding data to student record
 //Scanner object is used for data reading from the student record
 //The whole student record is read through hasNext() iterator
 // And Sequences are used to counter the student being found
 public static void findEntity(String filename)throws IOException{
     File myFile = new File(filename);
     Scanner scan = new Scanner(myFile);
     try {
         scan.nextLine();
     }
     catch (Exception e){
         System.out.println("Student record exits but it is Empty");//0510111446699
         return;
     }
     System.out.print("Enter name to find: ");
     Scanner input = new Scanner(System.in);
     String name = input.nextLine().toLowerCase(Locale.ROOT);
     while (scan.hasNextLine()){
         String line = scan.nextLine();
         if (line.startsWith("Student-Name:") && line.toLowerCase().endsWith(name.toLowerCase())){
             System.out.println("Entity found...\n"+line);
             try{
                 System.out.println(scan.nextLine());
                 System.out.println(scan.nextLine());
             }catch (Exception e){
                 System.out.println("End Of Student record Reached");
             }
             scan.close();
             return;
         }
     }
     System.out.println("Student not found.");
 }
 // This is the login page whenever a user wants to access his/her student record,
 //he/she must go through this page and insert his/her credentials
 // If the credentials matched from the registration file then he will have access to his student record.
 public static boolean loginPage()throws IOException{
     Scanner input = new Scanner(System.in);
     System.out.println("\n\t\t**** [Welcome to Log-in Page] ****\n");
     System.out.print("Enter your username: ");
     String username = input.nextLine();
     String filename = "./"+username+".txt";
     File userFile = new File(filename);
     File registerationVerification = new File("./Registered_Area.txt");
     if (!(registerationVerification.exists())){
         System.out.println("Not registered.\nTry other username or sign-up");
         return true;//break
     }
     else if  (!(userFile.exists())){
         System.out.println("Login Failed\nNo Such Id found");
         return true;
     }
     else {
         Scanner userScan = new Scanner(registerationVerification);
         System.out.print("Enter password: ");
         String passcode = input.nextLine();
         while (userScan.hasNextLine())
             if (userScan.nextLine().toLowerCase(Locale.ROOT).endsWith(username.toLowerCase(Locale.ROOT))){
                 String s = userScan.nextLine();
                 if (passcode.equals(s.substring(s.indexOf(" ")+1))){
                     System.out.println("Password matched.... logging in...\nNow you can perform the following functions");
                     successfulLogin(filename);
                     return false;//return
                 }
             }
     }
     System.out.println("Wrong Password");
     return true;
 }
 // A new user can access any student record, unless he signs up
 //Sign up page fetched the new user's credentials and these credentials are secured in a file called Registration text file
 // When the user Sign's up, a student record will be generated according to his name
 // Now he/she can access his student record
 public static boolean signUpPage()throws IOException{
     Scanner input = new Scanner(System.in);
     System.out.println("\n\t\t**** [Welcome to Sign-Up Page] ****\n");
     boolean OK  = true;
     do{
         System.out.print("Enter User-name: ");
         String username = input.nextLine();
         File f = new File("./"+username+".txt");
         if (f.exists())
             System.out.println("User already Exits\nTry new username");
         else {
             System.out.print("Create Password: ");
             String password = input.nextLine();
             f.createNewFile();
             File registered = new File("./Registered_Area.txt");
             if (!(registered.exists()))
                 registered.createNewFile();
             FileWriter fileWriter = new FileWriter("./Registered_Area.txt",true);
             fileWriter.write("\nUsername: "+username+"\n");
             fileWriter.write("Password: "+password+"\n");
             fileWriter.close();
             System.out.println("User registered successfully\nNow you can Log-in to your student record\n");
             OK = false;
         }
     }while (OK);
     return true;
 }
 //This method hold the feature of deleting or updating.
 //According to user choice this method redirect user to the specific (deletion or updating ) method
 public static void updateStudent_record(String filename, String[] info)throws IOException {
     Scanner input = new Scanner(System.in);
     File file = new File(filename);
     Scanner scan = new Scanner(file);
     char choice;
     boolean found = false;
     System.out.println("\nEnter student name to update...");
     String studentName = input.nextLine();
     while (scan.hasNextLine()) {
         if (scan.nextLine().toLowerCase().endsWith(studentName.toLowerCase())) {
             System.out.println("student found");
             found = true;
             break;
         }
     }
     if (found) {
         String[] deleteUpdate = {"Delete", "Rename or Update"};
         boolean correct = true;
         do {
             for (int i = 0; i < deleteUpdate.length; i++)
                 System.out.printf("\n[%d] %s", (i + 1), deleteUpdate[i]);
             System.out.print("\nSelect choice: ");
             choice = input.next().charAt(0);
             switch (choice) {
                 case '1': delete(filename,studentName,info);correct = false;break;
                 case '2': updateExistingData(filename,studentName,info);correct = false;break;
                 default: System.out.println("Wrong selection:");continue;
             }
         } while (correct);
     } else {
         System.out.println("Student not found\nTry correct Student name");
         return;
     }
 }
 //Delete method requires the Student record name and the entity name for deletion purposes
 //Whenever the entity is encountered in student record. Then he/she can deleted using special technique here below
 public static void delete(String filename,String studentName,String[] info)throws IOException{
     File oldFile = new File(filename);
     Scanner scan = new Scanner(oldFile);
     String All_data = "";
     while (scan.hasNextLine()){
         String n = scan.nextLine();
         if (n.toLowerCase(Locale.ROOT).endsWith(studentName.toLowerCase(Locale.ROOT))){
             scan.nextLine();
             scan.nextLine();
         }
         else
             All_data += (n+"\n");
     }
     scan.close();
     boolean x = oldFile.delete();
     if(!x)
         System.out.println("Student Deleted Successfully");
     File newFile = new File(filename);
     FileWriter fileWriter = new FileWriter(newFile);
     fileWriter.write(All_data);
     fileWriter.close();
 }
 //Delete method requires the student record name and the entity name for updating purposes
 //Whenever the entity is encountered in student record. Then he/she can update data using special technique here below
 public static void updateExistingData(String filename,String studentName,String[] info)throws IOException{
     File oldFile = new File(filename);
     Scanner scan = new Scanner(oldFile);
     Scanner input = new Scanner(System.in);
     String All_data = "";
     while (scan.hasNextLine()){
         String n = scan.nextLine();
         if (n.toLowerCase(Locale.ROOT).endsWith(studentName.toLowerCase(Locale.ROOT))){
             scan.nextLine();
             scan.nextLine();
             System.out.println("Enter data to be updated: ");
             for (int i = 0; i < info.length; i++) {
                 input.useDelimiter("\\r");
                 System.out.print("\n" + info[i]);
                 String data = input.nextLine();
                 All_data += info[i] + data +"\n";
                 input.reset();
             }
         }
         else
             All_data += (n+"\n");
     }
     scan.close();
     boolean x = oldFile.delete();
     if(!x)
         System.out.println("Student details Updated Successfully");
     File newFile = new File(filename);
     FileWriter fileWriter = new FileWriter(newFile);
     fileWriter.write(All_data);
     fileWriter.close();
 }
}
