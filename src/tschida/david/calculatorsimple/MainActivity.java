package tschida.david.calculatorsimple;

import tschida.david.utils.Calculator;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class contains the code for a simple calculator for Android. It was
 * built as a sample project for EPICS APPS.
 * 
 * Currently it is able to do basic commands (+*-/). Upon loading, the user sees
 * two EditText's where they can input numbers. Under those are a set of buttons
 * for the calculator operations. Answers, and errors, are displayed below the
 * buttons.
 * 
 * @author David Tschida (Vidia)
 * @version v0.0.2 (Beta)
 */
public class MainActivity extends Activity
{
	private static final String TAG = "MainActivity"; //Tag to be used in Log messages. 
	
	// Fields for the GUI items that are accessed by more than one method.
	private TextView outputBox; // Where the solution is displayed.
	private TextView txt_signPlaceholder; // Displays the selected operation
											// (sign) between the operands.
	
	private EditText lOperand; // Left operand
	private EditText rOperand; // Right...
	
	boolean signChosen; // A variable to state whether or not an operator button has been pressed
	
	/*
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_about:
	            openAbout();
	            return true;
	        case R.id.action_help:
	        	openHelp();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	


	/**
	 * Is run by onOptionsItemSelected() when the About icon is clicked. 
	 * It creates a new AlertDialog to display basic information about the app and its developer.
	 * It uses the AlertDialog.Builder class to change the settings for the dialog then the builder
	 * creates the dialog and it is shown with the show() method. 
	 */
	public void openAbout()
	{
		// Code taken from <http://developer.android.com/guide/topics/ui/dialogs.html>
		// with minor modifications.
		
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(R.string.about_message)
		       .setTitle(R.string.action_about)
		       .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int id) {
		    		   // User cancelled the dialog
		    		   Log.v(TAG, "Closing about dialog...");
		    	   }
		       })
		       .setIcon(R.drawable.action_about);
		
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		
		dialog.show();
	}
	
	/**
	 * Is run by onOptionsItemSelected() when the Help icon is clicked. 
	 */
	private void openHelp()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.help_message)
		       .setTitle(R.string.action_help)
		       .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int id) {
		    		   Log.v(TAG, "Closing help dialog...");
		    	   }
		       })
		       .setIcon(R.drawable.action_help);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	/**
	 * Inherited from Activity. Initializes the fields above.
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txt_signPlaceholder = (TextView) findViewById(R.id.txt_signPlaceholder);
		signChosen = false;
		
		lOperand = (EditText) findViewById(R.id.etxt_lOperand);
		rOperand = (EditText) findViewById(R.id.etxt_rOperand);
		outputBox = (TextView) findViewById(R.id.txt_solution);
		
		// Font source < www.fontspace.com/blue-vinyl/pocket-calculator >
		String fontPath = "fonts/calc_font/POCKC___.TTF";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		outputBox.setTypeface(tf);
	}
	
	/**
	 * Called by the ClickListener for the "add" button. Changes the
	 * txt_signPlaceholder to display the appropriate sign.
	 */
	public void click_btn_add(View view)
	{
		txt_signPlaceholder.setText("+");
		signChosen = true;
	}
	
	/**
	 * Called by the ClickListener for the "subtract" button. Changes the
	 * txt_signPlaceholder to display the appropriate sign.
	 */
	public void click_btn_subtract(View view)
	{
		txt_signPlaceholder.setText("-");
		signChosen = true;
	}
	
	/**
	 * Called by the ClickListener for the "multiply" button. Changes the
	 * txt_signPlaceholder to display the appropriate sign.
	 */
	public void click_btn_multiply(View view)
	{
		txt_signPlaceholder.setText("*");
		signChosen = true;
	}
	
	/**
	 * Called by the ClickListener for the "divide" button. Changes the
	 * txt_signPlaceholder to display the appropriate sign.
	 */
	public void click_btn_divide(View view)
	{
		txt_signPlaceholder.setText("/");
		signChosen = true;
	}
	
	/**
	 * Called by the ClickListener for the "add" button. Handles all of the
	 * error checking and calculating for the app. Checks that all of the values
	 * have been inputed and alerts the user if there are problems.
	 * 
	 * This method works well as it is, but I feel like it could be split into a
	 * few methods to simplify the code. - Calculation code will be moved into a
	 * separate "Calculator" class at a later time with exception handling.
	 */
	public void click_btn_finish(View view)
	{
		printErr(false, ""); // clears the error output box.
		double solution = 0;
		
		boolean error_occurred = false;
		
		String leftOpStr = lOperand.getText().toString();
		String rightOpStr = rOperand.getText().toString();
		
		char operator = ((String) txt_signPlaceholder.getText()).charAt(0);
		
		double leftOperand = 0, rightOperand = 0;
		
		/* Begin error handling */
		try
		{
			leftOperand = Double.parseDouble(leftOpStr);
		} catch (NumberFormatException e)
		{
			error_occurred = true;
			printErr(true, "Enter a valid number as the left operand.\n");
		}
		try
		{
			rightOperand = Double.parseDouble(rightOpStr);
		} catch (NumberFormatException e)
		{
			error_occurred = true;
			printErr(true, "Enter a valid number as the right operand.\n");
		}
		
		if (!error_occurred)
		{
			try
			{
				solution = Calculator.calculate(leftOperand, operator,
						rightOperand);
			} catch (ArithmeticException a)
			{
				printErr(true,
						"Cannot Divide by Zero!\n");
				error_occurred=true; 
			} catch (IllegalArgumentException i)
			{
				printErr(true, "Please select an operator.\n");
				error_occurred=true;
			}
		}
		
		if (!error_occurred)
			outputBox.setText("Answer: " + solution);
		else
			outputBox.setText("Answer: ");
	}
	
	/**
	 * A currently unused print method that takes its argument string and writes
	 * it to a TextView on the screen.
	 * 
	 * @param text
	 *            The text to be written to the screen.
	 */
	private void print(boolean append, String text)
	{
		TextView outputBox = (TextView) findViewById(R.id.txt_status);
		outputBox.setTextColor(Color.BLACK);
		if (append)
			outputBox.append(text);
		else
			outputBox.setText(text);
	}
	
	/**
	 * A print error method that prints red text to the screen to alert the user
	 * of errors in their input. It works mostly the same as print(String).
	 * 
	 * @param text
	 *            The text to be written to the screen.
	 */
	private void printErr(boolean append, String text)
	{
		TextView outputBox = (TextView) findViewById(R.id.txt_status);
		outputBox.setTextColor(Color.RED);
		if (append)
			outputBox.append(text);
		else
			outputBox.setText(text);
	}
	
	public void easter_egg(View view)
	{
		print(false, "Testing...");
	}
	
}
