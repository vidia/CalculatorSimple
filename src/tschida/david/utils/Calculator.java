package tschida.david.utils;

public final class Calculator {
	
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
					throw new ArithmeticException("Cannot divide by 0");
				} else
					solution = left / right;
				break;
			default:
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
					throw new ArithmeticException("Cannot divide by 0");
				} else
					solution = left / right;
				break;
			default:
				throw new IllegalArgumentException("Error: ( " + operator + " ) is not a legal operator.");
		}
		return solution;
	}
}


