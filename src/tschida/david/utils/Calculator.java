package tschida.david.utils;

import android.util.Log;

public final class Calculator {
	
	public static final String TAG = "Calcultor";
	
	public static int calculate(int left, char operator, int right) throws IllegalArgumentException, ArithmeticException
	{
		int solution; 
		switch (operator)
		{
			case '+':
				solution = left + right;
				break;
			case '-':
				solution = left - right;
				break;
			case '*':
				solution = left * right;
				break;
			case '/':
				if (right == 0)
				{
					Log.e(TAG, "Attempt to divide by zero. Throwing exception...");
					throw new ArithmeticException("Cannot divide by 0");
				} else
					solution = left / right;
				break;
			default:
				Log.wtf(TAG, "An illegal operator (" + operator + ") was passed to the calculator...");
				throw new IllegalArgumentException("Error: ( " + operator + " ) is not a legal operator.");
		}
		return solution;
	}
	
	public static double calculate(double left, char operator, double right) throws IllegalArgumentException, ArithmeticException
	{
		double solution;
		switch (operator)
		{
			case '+':
				solution = left + right;
				break;
			case '-':
				solution = left - right;
				break;
			case '*':
				solution = left * right;
				break;
			case '/':
				if (right == 0)
				{
					Log.e(TAG, "Attempt to divide by zero. Throwing exception...");
					throw new ArithmeticException("Cannot divide by 0");
				} else
					solution = left / right;
				break;
			default:
				Log.wtf(TAG, "An illegal operator (" + operator + ") was passed to the calculator...");
				throw new IllegalArgumentException("Error: ( " + operator + " ) is not a legal operator.");
		}
		return solution;
	}
}


