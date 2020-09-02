import java.util.Random;

public class textToRead 
{
	//each object here constitute one line imported from either the console or a text file
	//N.B: if the file is empty it will not proceed

	private String line, modifiedLine, key;
	
	public void setLine(String s)
	{
		//this method sets the line to be encrypted or decrypted
		line = s;
	}
	
	public String getLine()
	{
		//this method returns the line to be encrypted or decrypted
		return line;
	}
	
	public String getModifiedLine()
	{
		//this method returns the encrypted or decrypted line
		return modifiedLine;
	}
	
	public String getKey()
	{
		//this method returns the key
		return key;
	}
	
	public void encryptLine(String encryptionSettings)
	{
		//this method analyses the encryption settings and launches the encryption accordingly
		if (encryptionSettings.equals("default"))
		{
			encryptDefault();	
			return;
		}
	
		if (encryptionSettings.substring(0,8).equals("addition"))
		{
			encryptionSettings =encryptionSettings.substring(9);
			encryptComplexAddition(encryptionSettings);	
			return;
		}

		if (encryptionSettings.substring(0,14).equals("simpleaddition"))
		{
			encryptionSettings =encryptionSettings.substring(15);
			encryptSimpleAddition(encryptionSettings);	
			return;			
		}
		
		
		
	}
	
	private void encryptDefault()
	{
		//this method encrypts the default way
		key = additionKey(1);
		modifiedLine = encryptAddition(1, line ,500,15000);
		
	}
	
	private void encryptSimpleAddition(String s)
	{
		//this method encrypts the simple addition 
		int space = Integer.valueOf(s);
		key = additionKey(space);
		modifiedLine = encryptAddition(space, line ,500,15000);

	}
	
	private void encryptComplexAddition(String s)
	{
		//this method encrypts the complex addition
		int space = Integer.valueOf(s.substring(0,s.indexOf('-')));
		s = s.substring(s.indexOf('-')+1);
		int upperLimit = Integer.valueOf(s.substring(0,s.indexOf('-')));
		int addition = Integer.valueOf(s.substring(s.indexOf('-')+1));
		key =additionKey(space);	
		modifiedLine = encryptAddition(space, line ,upperLimit,addition);
	}
	
	private String additionKey(int space)
	{
		//this method returns the addition key
		String additionKey= Character.toString((char)1000)+Character.toString((char)(9999+space));
		return additionKey;
	}
	
	private String encryptAddition(int space, String line, int upperLimit, int addition)
	{
		//this is the heart of the program
		//it takes the text and initializes a StringBuilder with the same value
		//the key is composed of two elements :
		//the first one tells that this is an addition(other method were planned)
		//the second tells how many characters in between 
		//by default for every character two characters will be generated in a way that
		//the first one is a random character and second one is the character corresponding to the sum
		// of the random character and the original one
		//if the user desires, a bunch of random characters can be added in between to confuses the reader
		//Originally it was supposed to generate a number for more than a million unicode characters
		//lets say the total number of possible values is TOTAL,
		//RANDOM is the random number 
		//ORIGINAL is the number of the char
		//ORIGINAL would become RANDOM+ORIGINAL, In the case that this sum was bigger than TOTAL 
		//then ORIGINAL = RANDOM+ORIGINAL - TOTAL ( 1 in 1,112,064 of getting the same character)
		//unfortunately this was impossible
		//it became clear than not all of them were assigned so I went for the first 100k according to earningcharts.net
		//problem is sometimes characters would "merge"
		//so while decrypting the computer would count them as one which messed up everything
		//I found the sweet spot to be in the range given by default give or take 500 which is a bunch of characters that won't merge (tested all of them in the range)
		//the range could have been way bigger but i decided to go for a safer option
		//that won't superimpose each others for most (if not all) of the characters that the audience will be using
		// (int) char_of_audience is less than 250
		//TL;DR a random sequence of characters is generated and the value of the original character is directly related to the first character
		//it proceeds for every character
		//in this way for ex ,the char 'a' is expressed differently for almost every occurrence
		StringBuilder linesb = new StringBuilder(line);
		int randomCharacterNumber;
		int newCharacterNumber;
		int length =linesb.length();
		String encryptedmessage = "";
	    //String encryptedmessage= additionKey(space);
		for(int i =0;i<length;i++) 
		{
			randomCharacterNumber = generateCharacterNumber(upperLimit, addition);
			newCharacterNumber=((int)linesb.charAt(0)+randomCharacterNumber);
			encryptedmessage+=Character.toString((char)randomCharacterNumber);
			if (space>1)
			{
				for (int ii=1;ii<space;ii++)
				{
					encryptedmessage+=Character.toString(generateCharacterNumber(upperLimit, addition));
				}
				
			}
			
			encryptedmessage+=Character.toString((char)newCharacterNumber);
			linesb.delete(0,1);
		}
		
		return encryptedmessage;
		
	}
	
	private int generateCharacterNumber(int upperLimit, int addition)
	{
		//this method generates a random character number within the boundary
		Random rand = new Random();
		int randomCharNumber;
		randomCharNumber = rand.nextInt(upperLimit)+addition;
		
		return randomCharNumber;
	}
	
	public void addKey(boolean b)
	{
		//this method "glues" the key to the modified line
		//used in encryption
		if(b)
		{
			modifiedLine = key+modifiedLine;

		}
	}
	
	public void addKey(String s)
	{
		//this method "glues" the key to the line
		//used in the decryption
		line = s +line;
	}
	
	public boolean decrypt()
	{
		//this is the begining of the decryption
		if (line.charAt(0)=='Ϩ')
		{
			decryptAddition();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkKey()
	{
		//this method checks the presence of the key
		if (line.charAt(0)=='Ϩ')
		{
			return false;
		}
		
		else
		{
			return true;
		}
		
	}
	
	private void decryptAddition()
	{
		//encrypt addition but reversed engineered 
		StringBuilder textToDecrypt = new StringBuilder(line);
		int characterNumber;
		textToDecrypt.deleteCharAt(0);
		int space = (int)(textToDecrypt.charAt(0))-9999;
		textToDecrypt.deleteCharAt(0);
		String decryptedText="";
		int currentCharacter=0;
		while(textToDecrypt.length()!=0)
		{
			characterNumber = (int)textToDecrypt.charAt(0);
			textToDecrypt.delete(0, space);
			currentCharacter = Math.abs(characterNumber-(int)textToDecrypt.charAt(0));
			decryptedText+=Character.toString((char)currentCharacter);

			textToDecrypt.delete(0, 1);
		}
		
		modifiedLine = decryptedText;
	}
	
}
