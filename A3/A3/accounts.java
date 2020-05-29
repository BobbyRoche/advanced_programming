/*
Robert Roche
CS265
Final Assignment

This is the main program. This program purpose readsin a log file (cmd arg) and then translates the data 
read from the file into an ArrayList which holds the data.
4 possible user commands (info,history, insert transaction, display user message).
*/
import java.util.*;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;

public class accounts
{
	//random number generator
	static Random rand = new Random();
	
	//This function establishes the format for anytime balance is output
	public static String makeCurrency(double amt)
	{
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		return formatter.format(amt);
	}

	//Date formatter
	public static String makeDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	//This function writes new information to a temp file.
	public static void output(ArrayList<account> list)
	{
		FileOutputStream output = null;
		File f;
		try
		{	
			f = new File("accountsDB");
			//Check if an output file has already been created
			if (!f.exists())
				f.createNewFile();
			output = new FileOutputStream(f, false);
			//Loops through each accounts in the list
			for (int x = 0; x < list.size(); x++)
			{
				account temp = list.get(x);
				String history = temp.getHistory();
				String shortHistory = "";
				int index = 0;
				boolean check = false;
				if (history.length() < 1)
					check = true;
				while (!check)
				{
					//if history doesn't contain a _, there is only one history entry
					if (history.substring(index, history.length()).indexOf("_") == -1)
					{
						check = true;
						shortHistory = history.substring(index, history.length());
					}
					else
					{
						shortHistory = history.substring(index, history.length());
						shortHistory = shortHistory.substring(0, shortHistory.indexOf("_"));
						index += shortHistory.length() + 1;
					}
					String strOutput = temp.getAccountNum() + ":" + temp.getName() + ":" + shortHistory + "\n";
					byte[] bytesArray = strOutput.getBytes();
					output.write(bytesArray);
					output.flush();
				}
			}
		}
	  	catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try
			{
				if (output != null)
				{
					//Close the output stream after writing
					output.close();
				}
			}
			catch (IOException ioe)
			{
				System.out.println("Error in closing the stream!");
			}
		}
	}


	//USing the list parameter, this function creates a random 4 digit
	//integer and then checks the account account numbers of all other
	//account to ensure the generated number is unique
	public static int generateAccountNumber(ArrayList<account> list)
	{
		boolean check = false;
		int val = 0;
		while (!check)
		{
			check = true;
			//4 digits
			String num = String.format("%04d", rand.nextInt(10000));
			val = Integer.parseInt(num);
			for (int x = 0; x < list.size(); x++)
			{
				account temp = list.get(x);
				//Compares to other accountNums
				if (temp.getAccountNum() == val)
					check = false;
			}
			val++;
			if ((((val % 10) % 10) % 10) == 0)
				check = false;
			else
				val--;
		}	
		return val;
	}
	//Sorts the accounts by name, then by number
	public static ArrayList<account> sortList(ArrayList<account> list)
	{
		boolean check = false;
		while (!check)
		{
			check = true;
			for (int x = 0; x < list.size() - 1; x++)
			{
				account item1 = list.get(x);
				account item2 = list.get(x + 1);
				//Compare two accounts by accounts holder's name
				if (item2.getName().compareTo(item1.getName()) < 0)
				{
					list.set(x, item2);
					list.set(x+1, item1);
					check = false;
				}
				//If both account holders are the same person...
				else if (item2.getName().compareTo(item1.getName()) == 0)
				{
					//Sort accounts numerically
					if (item2.getAccountNum() < item1.getAccountNum())
					{	
						list.set(x, item2);
						list.set(x+1, item1);
						check = false;
					}
				}
			}
		}
		check = false;
		while (!check)
		{
			check = true;
			for (int x = 0; x < list.size() - 1; x++)
			{
				account item1 = list.get(x);
				for (int y = x+1; y < list.size(); y++)
				{
					account item2 = list.get(y);
					//If account item1 and item2 have the same account number
					if (item1.getAccountNum() == item2.getAccountNum())
					{
						check = false;
						item1.setHistory(item2.getHistory());
						list.remove(y);
					}
				}
			}
		}
		for (int x = 0; x < list.size(); x++)
		{
			check = false;
			account temp = list.get(x);
			double balance = 0;
			String line = temp.getHistory();
			String transaction = "";
			int index = 0; 
			if (line.length() < 1)
				check = true;
			while (!check)
			{
				//if history doesn't contain a _, there is only one history entry
				if (line.substring(index, line.length()).indexOf("_") == -1)
				{
					//Check is set to true so loop runs for the last time
					check = true;
					transaction = line.substring(index, line.length());
				}
				else
				{
					//Create a substring from new index to next underscore
					transaction = line.substring(index, line.length());
					transaction = transaction.substring(0, transaction.indexOf("_"));
					//update index
					index += transaction.length() + 1;
				}
				String type = transaction.substring(0, transaction.length());
				//Determine whether a deposit or withdrawal
				int modifier = 1; 
				if (type.indexOf("W") != -1 || type.indexOf("w") != -1)
					modifier = -1;
				//Extract the amount from history
				double amount = Double.parseDouble(transaction.substring(transaction.lastIndexOf(":")+1, transaction.length()));	
				balance += modifier * amount;
			}
			temp.setBalance(balance);
			list.set(x, temp);
		}
		//Return the newly sorted list
		return list;
	}

	/* 		
		Allow users to create accounts, make transactions, and get account
		info/transaction histor.
	*/
	public static ArrayList<account> userChoice(ArrayList<account> unsortedList, String choice)
	{
		//Sort parameter list
		ArrayList<account> list = new ArrayList<account>(sortList(unsortedList)); 
		Scanner sc = new Scanner(System.in);
	  	account temp = new account("", 0, 0, "");
		String input; 	
		//Run until "q" is entered
		do 
	  	{
			switch(choice)
			{
				case "-i":
					System.out.println("Info\n----");
				break;
				case "-h":
					System.out.println("History\n----");
				break;
				case "-t":
					System.out.println("Insert Transaction\n----");
				break;
			}
			for ( int x = 0; x < list.size(); x++)
			{
				temp = list.get(x);
				//x+1 so list starts at 1 instead of 0
				System.out.println((x + 1) + ") " + temp.getName() + " " + temp.getAccountNum());
			}
			if (choice.equals("-t"))
				System.out.println(list.size() + 1 + ") Create a new account");
			System.out.println("q)uit\n\n");
			System.out.print("Enter choice => ");
			input = sc.nextLine();
			System.out.print("\n");
			if (input.equals("q"))
			{
				sc.close();
			}
			else if (Integer.parseInt(input) == list.size() + 1 && choice.equals("-t"))
			{
				input = "q";
				int accountNum = 0;
				while (input.equals("q"))
				{
					System.out.print("Account holder's name: ");
					input = sc.nextLine();
					System.out.println();
					//Call function to generate a random number for new account
					accountNum = generateAccountNumber(list);
				}
				//Call account constructor to create new account object
				account userEntered = new account(input, 0.00, accountNum, "");
				System.out.println("Account created!\n\tName: " + input + "\n\tAccount #: " + accountNum);
				//add new account to list to be returned at end of function
				list.add(userEntered);
				//Sort the list with the new account
				list = sortList(list); 
				output(list);
			}
			//Conditional runs when user wants to make a withdrawal/deposit with exisitng account
			else if (Integer.parseInt(input) <= list.size() && Integer.parseInt(input) > 0)
			{
				if (choice.equals("-i"))
				{
					//input - 1 to account for x+! from before
					temp = list.get(Integer.parseInt(input) - 1);
					//Return all account info: numbe, name, and balance
					//The account's history is reserved for the history function
					System.out.println("\taccount #:  " + temp.getAccountNum());
					System.out.println("\t     name:  " + temp.getName());
					System.out.println("\t  balance:  $" + makeCurrency(temp.getBalance()));
				}
				else if (choice.equals("-h"))
				{
					boolean check = false;
					//input - 1 to account for x+1 from before
					temp = list.get(Integer.parseInt(input) - 1);
					String line = temp.getHistory();
					String transaction = "";
					int index = 0;
					if (line.length() < 1)
						check = true;
					while (!check)
					{
						//if history doesn't contain a _, there is only one history entry
						if (line.substring(index, line.length()).indexOf("_") == -1)
						{
							//Check is set to true so loop runs for the last time
							check = true;
							transaction = line.substring(index, line.length());
						}
						else
						{
							//Create a substring from new index to next underscore
							transaction = line.substring(index, line.length());
							transaction = transaction.substring(0, transaction.indexOf("_"));
							//update index
							index += transaction.length() + 1;
						}
						String date = transaction.substring(0,transaction.indexOf(":"));
						String type = transaction.substring(0, transaction.length());
						//Determine whether a deposit or withdrawal
						if (type.indexOf("W") != -1)
							type = "withdrawal";
						else
							type = "deposit";
						//Extract the amount from history
						double amount = Double.parseDouble(transaction.substring(transaction.lastIndexOf(":") + 1, transaction.length()));
						System.out.print("      " + date + " " + type + " $" + makeCurrency(amount));
						System.out.print("\n");
					}
				}
				else if (choice.equals("-t"))
				{
					//input - 1 to account for x+1 from before
					int index = Integer.parseInt(input) - 1;
					temp = list.get(index);
					input = ""; double amount;
					System.out.println("Current balance: $" + makeCurrency(temp.getBalance()));
					//Runs until user selects either w/d (ignores case)
					while (!input.equals("W") && !input.equals("D"))
					{
						System.out.print("Withdrawal or Deposit? (W/D) ==> ");
						input = sc.nextLine();
						input = input.toUpperCase();
					}
					int type = 1;
					if (input.equals("W"))
						type *= -1;
					amount = -1;
					String transType = input;
					while (amount < 0)
					{
						System.out.print("\nAmount? ==> ");
						input = sc.nextLine();
						amount = Integer.parseInt(input);
					}
					//Mulitply amount to add by -1 if transction type is a withdrawal, else amount doesn't change
					amount *= type;
					if (amount + temp.getBalance() < 0)
						System.out.println("Not enough money to withdraw!");
					else
					{
						temp.setBalance(temp.getBalance() + amount);
						temp.setHistory(makeDate() + ":" + transType + ":" + Math.abs(amount));
						list.set(index, temp);
						output(list);
					}
				}
			}		
			else
			{
				System.out.println("Not a valid command!");
			}
			System.out.print("\n");
		} while(!input.equals("q"));
		return list;
	}
	/*
		The main function of the JavaCalc class that will read inputs from the
		input.infix file provided and will print out the postfix version of it
		and the calculation of that postfix.
		@paran argv - String array of all command line arguments (none of
		which will be used)
		@return - none, the method is a void method
	*/
	public static void main(String[] argv)
	{
		File file = new File (argv[1]);
		if (!file.exists() && argv.length == 2)
			file = new File (argv[1]);
		Scanner sc = null;
		Scanner scanLine = null;
		ArrayList<account> list = new ArrayList<account>();
		try
		{
			//initialize new scanner with the file object created before.
			sc = new Scanner(file);
		}
		catch(FileNotFoundException fe)
		{
			System.out.println("Not Found!");
		}
		String line = "";
		//while not the end of the file being read.
		while (sc.hasNextLine())
		{
			//get next line and put it in previously created variable line.
			//Then make a new scanner with it to serperate out the tokens.
			line = sc.nextLine();
			int accountNum = Integer.parseInt(line.substring(0, line.indexOf(":")));
			line = line.substring(line.indexOf(":")+1, line.length());
			String name = line.substring(0, line.indexOf(":"));
			line = line.substring(line.indexOf(":")+1, line.length());
			String history = line.substring(0, line.length());
			list.add(new account(name, 0, accountNum, history));
		}
		String validParams = "-t-h-i-?";
		if (validParams.indexOf(argv[0]) == -1)
			System.out.println("Not a valid command! Try -? for more information");
		else if (!argv[0].equals("-?"))
			userChoice(list, argv[0] + "");
		else 
			System.out.println("Valid commands:\n\t-i ==> display account info\n\t-h ==> display account history\n\t-t ==> make transactions or create new accounts");
		sc.close();
	}
}



