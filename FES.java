import java.util.*;
import java.io.*;
/*Dev : Selim Harfouche
 *Code : File Encryption System
 *Codes and Decodes files in a Cypher manner with a twist
 */
public class FES {

	public static String userCase; //represents the user Case
	public static String staticFilePath; //represents the file path in input
	public static boolean sameKey = false; //if the key is the same for all lines
	public static boolean isKeyPresent = false; // if the key is present in the decrypted lines
	
	
	public static void main(String[] args) throws InterruptedException {
		printHeader();
		userCase = userCase();
		if(!(userCase.equals("login")))
		{
			List <textToRead> TTRList = getInput();
			switch(userCase)
			{
				case "encrypt":
					encrypt(TTRList);
					break;
				case "decrypt":
					decrypt(TTRList);
					break;
			}
			
		}
		
		else
		{	
			login();
		}	
		
		System.out.println();
		System.out.println("********************************************");
		System.out.println("		program ended");
		System.out.println("********************************************");
		
	}
	
	public static void printHeader()
	{
		//this method print the header
		System.out.println("****  ****  ****");
		System.out.println("*     *     *   ");
		System.out.println("****  ****  ****");
		System.out.println("*     *        *");
		System.out.println("*     ****  ****");
		System.out.println();
		System.out.println("File Encryption System");
		System.out.println("");
		System.out.println("1 - Encrypt");
		System.out.println("2 - Decrypt");
		System.out.println("3 - Login");
	}
	
	public static String userCase()
	{
		//this methods asks for the user case and returns it as a String
		// 1 to encrypt
		// 2 to decrypt
		// 3 to login
		
		Scanner scan = new Scanner(System.in);
		String userCase;	
		userCase = scan.nextLine();
		while(!(userCase.equals("1")||userCase.equals("2")||userCase.equals("3")))
		{
			System.out.println("Enter a valid number to continue");			
			userCase = scan.nextLine();			
		}
		
		if (userCase.equals("1"))
		{
			userCase = "encrypt";
		}
		
		if (userCase.equals("2"))
		{
			userCase = "decrypt";
		}
		
		if (userCase.equals("3"))
		{
			userCase = "login";
		}
		
	
		return userCase;
	}
		
	public static List <textToRead>  getInput()
	{
		//this method gets the input
		//returns it as a list of objects from textToRead
		
		String inputSourceType = getValidType();
		Scanner scan = new Scanner(System.in);
		if (inputSourceType.equals("1"))
		{	
			return fromConsole();
		}
		
		String filePath;
		if (inputSourceType.equals("2"))
		{
			filePath = getFilePath();
			return fromFile( filePath);
		}
		
		if (!(checkFilePath("src/default")))
		{
			System.out.println();
			System.out.println("It is supposed to be at src/default");
			filePath = getFilePath();
			return fromFile(filePath);
		}
		
		return fromFile("src/default");			
	}

	public static String getValidType()
	{
		//this methods returns the type of input(console, from textfile , from default)
		Scanner scan = new Scanner(System.in);	
		String inputSourceType;
		System.out.println();
		System.out.println("From where do you want to " + userCase);
		System.out.println("1 - Console");
		System.out.println("2 - a text file");
		System.out.println("3 - default.txt");
		inputSourceType = scan.nextLine();
		while(!(inputSourceType.equals("1")||inputSourceType.equals("2")||inputSourceType.equals("3")))
		{
			System.out.println("Enter a valid number to continue");			
			inputSourceType = scan.nextLine();			
		}
		
		return inputSourceType;
		
	}
	
	public static String getFilePath()
	{
		//this methods gets the path of a file in the case the user decided to input from a file
		String filePath;
		Scanner scan = new Scanner(System.in);
		do
		{
			System.out.println();
			do
			{
				System.out.println("Enter your source");
				filePath = scan.nextLine();
			}
			while(!repetition("confirm"));
			
		}
		
		while (!checkFilePath(filePath));	
		return filePath;
			
	}
	
	public static boolean checkFilePath(String s)
	{
		//this methods checks the existence of the file and returns a boolean accordingly
		File nf = new File(s);
		boolean b =  nf.exists();
		if (!b) {
			System.out.println("file does not exist for the given path");		
		}
		if(b) {
			System.out.println("file does exist for the given path");
		}
		return b;
	}
	
	public static List <textToRead> fromConsole()
	{
		//this methods gets input from the console(or command prompt or terminal)
		List <textToRead> TTRList = new ArrayList<>();
		Scanner scan= new Scanner(System.in);
		do
		{
			textToRead TTR = new textToRead();
			System.out.println();
			System.out.println("Enter your text");
			TTRList.add(TTR);		
			TTR.setLine(scan.nextLine());
		}
		
		while (repetition("Add more"));
		System.out.println();
		System.out.println("You entered : ");
		for (int i = 0 ; i<TTRList.size();i++)
		{
			System.out.println("Line " + (i+1)+": "+TTRList.get(i).getLine());
		}
		return TTRList;
	}
	
	public static List <textToRead> fromFile(String filePath)
	{
		//this methods adds every line of the file as a object from textToRead
		//the objects are grouped in a list that is called TTRList
		staticFilePath = filePath;
		List <textToRead> TTRList = new ArrayList<>();
		File nf = new File(filePath);
		if (nf.length()==0)
		{
			System.out.println("File length is 0!!!");
			System.out.println("Exiting ...");
			System.exit(1);
			
		}
		System.out.println();
		System.out.println("File Length : " + nf.length());
		try
		{
			Scanner fileScanner= new Scanner(nf);
			while(fileScanner.hasNextLine())
			{

				textToRead TTR = new textToRead();
				TTR.setLine(fileScanner.nextLine());
				TTRList.add(TTR);
				
			}
			
			fileScanner.close();
		}
		
		catch (IOException e) {
			System.out.print("IOEXCEPTIONERROR ");
		}
		
		return TTRList;
	}
	
	public static boolean repetition(String r)
	{
		//this method prints a statement
		//and only accepts y or n as valid inputs
		//it is most useful when used in a loop
		//(i.e. in the fromConsole method)
		System.out.println();
		boolean repetition = true;
		Scanner scan = new Scanner(System.in);
		String s;
		System.out.println( r +" ? y/n");
		do
		{
			
			System.out.println("y for yes n for no");
			s = scan.nextLine();
		}
		
		while (!(s.equals("n")||s.equals("y")));
		if(s.equals("n"))
		{
			repetition =false;
		}
		return repetition;
		
		
	}
	
	public static void encrypt(List <textToRead> TTRList)
	{
		//this methods marks the start of the encryption
		if (TTRList.size()==1)
		{
			encryptOneLine(TTRList);
		}
		
		else
		{
			 encryptMoreThanOneLine(TTRList);
		}
		output(TTRList);
		outputKeyChecker(TTRList);
		
	}
	
	public static void encryptOneLine(List <textToRead> TTRList)
	{
		//in the case the user gave only one line
		//this method encrypts this line
		sameKey = true;
		boolean b = keyCheck();
		TTRList.get(0).encryptLine(encryptionSettings(""));
		TTRList.get(0).addKey(b);
		
	}
	
	public static void encryptMoreThanOneLine(List <textToRead> TTRList)
	{
		//in the case the user gave more than one line
		//this method encrypts each and every line
		//in the case the user does decide to encrypt every line differently
		//the key is automatically added for every line
	
		if(repetition("Keep the same encryption for every line"))
		{
			boolean b = keyCheck();
			sameKey = true;
			String encryptionSettings = encryptionSettings("");
			
			for (int i = 0; i<TTRList.size();i++)
			{
				TTRList.get(i).encryptLine(encryptionSettings);
				TTRList.get(i).addKey(b);
			}
			
			
		}
		
		else
		{
			sameKey=false;
			for (int i = 0; i<TTRList.size();i++)
			{
				TTRList.get(i).encryptLine(encryptionSettings(" for line "+ (i+1)));
				TTRList.get(i).addKey(true);
				
			}
			
		}
		
	}
	
	public static String encryptionSettings(String s)
	{
		//this methods asks the user for the encryption settings
		//and returns it as a String that will be used by the textToRead class
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println("Chose your encryption method" + s);		
		String encryptionMethod;
		System.out.println("1 - Default Encrytion");
		System.out.println("2 - Addition");
		
		System.out.println();
		encryptionMethod = scan.nextLine();
		while(!(encryptionMethod.equals("1")||encryptionMethod.equals("2")))
		{
			System.out.println("Enter a valid number to continue//");			
			encryptionMethod = scan.nextLine();			
		}
		
		if (encryptionMethod.equals("1"))
		{
			encryptionMethod = "default";
		}
		
		if (encryptionMethod.equals("2"))
		{
			encryptionMethod = additionSettings();
		}
		
		return encryptionMethod;
	}
	
	public static String additionSettings()
	{
		//if the user went for addition
		//this method asks the user to chose the addition type
		//and to chose its settings accordingly
		Scanner scan = new Scanner(System.in);
		String s="";
		System.out.println("Chose your addition settings ");
		System.out.println("1 - Simple");
		System.out.println("2 - Advanced");
		s = scan.nextLine();	
		while(!(s.equals("1")||s.equals("2")))
		{
			System.out.println("Enter a valid number to continue");			
			s = scan.nextLine();			
		}
		
		if (s.equals("1"))
		{
			s="simpleaddition";
			s+=getInt("Space(s)",1);
			return s;
		}
		
		else
		{
			s = "addition";
			s+=getInt("Space(s)",1);
			s+=getInt("Random character upper limit",500);
			s+=getInt("Random character addition",15000);
			return s;
		}
			
	}
	
	public static String getInt(String ofWhat, int defaultSet)
	{
		//this method helps setting up the settings of the encryption
		//the user inputs a number to be set as an encryption settings
		//this checks the input and returns a string in the format
		// -int where - is the delimiter for the encryption method
		//it doesn't allow the user to return anything other than an int
		boolean b = true;
		int x =0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of "+ ofWhat + " default : "+defaultSet);
		do
		{
			try
			{
				x=Integer.parseInt(scan.nextLine());
				if (x<1)
				{
					x = Integer.parseInt(" ");
				}
				else
				{
					b =false;
				}
						
			}
			
			catch(NumberFormatException e)
			{

				System.out.println("Enter a valid number");
				b = true;
			}
			
		}
		
		while(b);
		
		return ("-"+String.valueOf(x));
		
	}
	
	public static boolean keyCheck()
	{
		//this method checks if the user wants to include the key
		//if the user decides no, a warning message will be displayed later on
		if(repetition("Include key in encrypted line"))
		{
			isKeyPresent = true;
			return true;
		}
		
		return false;
	}
	
	public static void output(List <textToRead> TTRList)
	{
		//this method let the user decide where he wants
		//the output
		if(repetition("print result"))
		{
			printResult(TTRList);
		}
		
		if(repetition("export to existing text file"))
		{
			exportToExisting(TTRList);		
		}
		
		if(repetition("export to new text file"))
		{
			exportToNew(TTRList);
		}
	
	}
	
	public static void printResult(List <textToRead> TTRList)
	{
		//this method prints the modified line in the console
		for(int i = 0; i<TTRList.size(); i++)
		{
			System.out.println("Line "+(i+1)+" :");
			System.out.println(TTRList.get(i).getModifiedLine());
			
		}
	}
	
	public static void exportToExisting(List <textToRead> TTRList)
	{
		//this method imports the result to an existing text file
		String filePath;
		try 
		{
			filePath = getFilePath();
			if(filePath.equals(staticFilePath))
			{
				filePath = filePathChecker(filePath);
			}
			
			FileWriter fw = new FileWriter(filePath);
			for(int i = 0; i<TTRList.size(); i++)
			{
				fw.write(TTRList.get(i).getModifiedLine());
				fw.write("\n");
				
			}
			fw.close();
		}
		
		catch (IOException e) {
			System.out.print("IOEXCEPTIONERROR ");
		}
	}
	
	public static void exportToNew(List <textToRead> TTRList)
	{
		//this method creates a new textfile
		//and imports the result to it.
		String filePath;
		filePath = createFilePath();
		File file = new File (filePath);
		System.out.println();
		System.out.println("New file : "+filePath);

		try {
			FileWriter fw = new FileWriter(file);
			for(int i = 0; i<TTRList.size(); i++)
			{
				fw.write(TTRList.get(i).getModifiedLine());
				fw.write("\n");
				
			}
			fw.close();
		}
		
		catch (IOException e) {
			System.out.print("IOEXCEPTIONERROR ");
		}
	}
	
	public static String filePathChecker(String filePath)
	{
	    // this method warns the user in case the writing is made 
		// to the same file from where the reading occured
		while (filePath.equals(staticFilePath))
		{
			System.out.println();
			System.out.println("Chosing the same file for input and output will result in input data loss");
			if(repetition("Confirm"))
			{
				break;
			}
			
			else filePath = getFilePath();
		}
		
		return filePath;
	}
	
	public static void outputKeyChecker (List <textToRead> TTRList)
	{
		//this method checks if the key is present in the output
		//regardless of the answer it asks the user about printing the key
		//in the case it is the same key for every line
		//otherwise the key is already included 
		if(!isKeyPresent) 
		{
			System.out.println();
			System.out.println("Note : very hard to decrypt if you don't have the key");
		}
		
		if(sameKey)
		{
			String key = TTRList.get(0).getKey();
			if(repetition("print your key"))
			{
				System.out.println(key);
			}	
		}	
	}
	
	public static String createFilePath()
	{
		//This method creates a filePath for a new file
		//Existing file path won't be accepted
		//the file will definitely show up in its location
		//but sometimes the ide will not show it in the package explorer tab
		String filePath;
		Scanner scan = new Scanner(System.in);
		do
		{
			System.out.println();
			do
			{
				System.out.println("Enter your source");
				filePath = scan.nextLine();
			}
			
			while(!repetition("confirm"));
			
		}
		
		while (checkFilePath(filePath));	
		return filePath;
		
	}
	
	public static void decrypt (List <textToRead> TTRList)
	{
		//this method marks the beginning of the description 
		if(TTRList.get(0).checkKey())
		{
			getInputKey(TTRList);
		}
		
		for (int i = 0;i<TTRList.size();i++)
		{
			TTRList.get(i).decrypt();
		}
		
		output(TTRList);
			
	}
	
	public static void getInputKey(List <textToRead> TTRList)
	{
		//in the case the key was not included in the text
		//this method gets the key from the user
		System.out.println();
		Scanner scan = new Scanner(System.in);
		String key;	
		do
		{
			System.out.println("Enter your key");
			key =scan.nextLine();
					
		}
		
		while(!repetition("confirm"));
		TTRList.get(0).addKey(key);
		if (TTRList.get(0).checkKey())
		{
			System.out.println("Invalid key!");
			System.out.println("Exiting ...");
			System.exit(1);
		}
		
		for (int i =1;i<TTRList.size();i++)
		{
			TTRList.get(i).addKey(key);
		}
		
	}
	
	public static void login()
	{
		//this is the login method that does what was asked 
		if(!(checkFilePath("src/login")))
		{
			System.out.println("Login file is not present");
			System.out.println("Exiting ...");
			System.exit(1);
		}
		
		List <textToRead> TTRList = fromFile("src/login");
		encryptLogin(TTRList);
		if(verifiedCredentials())
		{
			decryptLogin(TTRList);
		}
		
		else
		{
			System.out.println("Invalid credentials!");
			System.out.println("Exiting ...");
			System.exit(1);
		}
		
		logout();
			
	}
	
	public static void encryptLogin(List <textToRead> TTRList)
	{
		//this method encrypts the file 
		//situated at src/login
		//as soon as the user hits login
		for (int i = 0; i<TTRList.size();i++)
		{
			TTRList.get(i).encryptLine("default");
			TTRList.get(i).addKey(true);
		}
		
		try
		{
			
			FileWriter fw = new FileWriter("src/login");
			for(int i = 0; i<TTRList.size(); i++)
			{
				fw.write(TTRList.get(i).getModifiedLine());
				fw.write("\n");
				
			}
			
			fw.close();
		}
		
		catch (IOException e) {
			System.out.print("IOEXCEPTIONERROR ");
		}
		
		System.out.println();
		System.out.println("**************");
		System.out.println("FILE ENCRYPTED");
		System.out.println("**************");
		System.out.println();
	}
	
	public static void decryptLogin(List <textToRead> TTRList)
	{
		//N.B.: I could have decrypted the lines like I did in the other cases
		//I could have copied the values of modifiedLine and pasted them in Line
		//(for the objects of the class textToRead)
		//then proceeded for decryption just like the decryption by default
		//But I went for the "shorter" path that is to just get back the originals lines
		//Since they are already here, why go around
		
		try
		{
			
			FileWriter fw = new FileWriter("src/login");
			for(int i = 0; i<TTRList.size(); i++)
			{
				fw.write(TTRList.get(i).getLine());
				fw.write("\n");
				
			}
			
			fw.close();
		}
		
		catch (IOException e) {
			System.out.print("IOEXCEPTIONERROR ");
		}
		
		System.out.println();
		System.out.println("**************");
		System.out.println("FILE DECRYPTED");
		System.out.println("**************");
		System.out.println();
		
	}
	
	public static boolean verifiedCredentials()
	{
		//this method verifies the credentials
		Scanner scan =new Scanner(System.in);
		System.out.println("Enter admin account username");
		String username = scan.nextLine();
		System.out.println("Enter admin account password");
		String password = scan.nextLine();
		if(username.equals(password)&& username.equals("admin"))
		{
			return true;
		}
		
		else
		{
			return false;
		}
		
	}
	
	public static void logout()
	{
		//this method logout
		//and trolls the user if no is always given
		int i =1000;	
		while(!(repetition("Logout")))
		{
			i+=2000;
			System.out.println("You can only logout or break the computer ");
			try
			{
			    Thread.sleep(i);
			}
			catch(InterruptedException ex)
			{
				
			}
			
			System.out.println("or shutdown or close eclipse etc .. ");
			try
			{
			    Thread.sleep(i);
			}
			
			catch(InterruptedException ex)
			{
				
			}
			
		}
	}

}

