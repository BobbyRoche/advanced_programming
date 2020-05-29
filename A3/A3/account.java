/*
Robert Roche
CS265
Final Assignment
Creates account object
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class account
{
	private String name;
	private double balance;
	private int accountNum;
	private String history;

	//Account object constructor
	public account( String _name, double _balance, int _accountNum,
			String _history ) {
		name = _name;
		balance = _balance;
		accountNum = _accountNum;
		history = _history;
	}
	
	//Account object getter which returns its name 
	public String getName() {
		return this.name;
	}

	//Account object getter which returns its balancey
	public double getBalance(){
		return this.balance;
	}

	//Account object getter which returns its account number 
	public int getAccountNum() {
		return this.accountNum; 
	}

	//Account object getter which returns its history 
	public String getHistory() {
		return this.history;
	}

	//Account object setter which sets its balance property equal to the
	public void setBalance(double newBalance) {
		this.balance = newBalance + 0.00;
	}
	
	//Account object setter which appaends a parameter newHistory to its
	public void setHistory(String newHistory)
	{
		this.history += "_" + newHistory;
	}
}	
