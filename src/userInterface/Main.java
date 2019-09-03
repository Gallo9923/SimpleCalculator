import java.util.Scanner;
import java.util.regex.*;

public class Main
{
	public static void main (String[] args)
	{
		//Atributes
		boolean menu = true;
		int optionMenu;
	
		//Initialization
		Scanner sc = new Scanner(System.in);
		
		
		while (menu)
		{
			System.out.println("Input the option you need");
			System.out.println("(1: One operation; 2: Flow of operations; 3: Exit)");
			optionMenu = sc.nextInt(); 
			
			switch (optionMenu)
			{
				case 1: 
					//One operation method 
					oneOperationStart();
					break;
				case 2:
					//Flow operations method
					break;
				case 3:
					System.out.println("The program has finished");
					menu = false;
					break;
				default:
					System.out.println("You need to choose a valid option");
				}
			}
		}
		
		
	public static void oneOperationStart()
	{	
		System.out.println("One Operation Mode has started");
		
		Scanner scanStr = new Scanner(System.in);
		
		String num1 = "";
		String operator = "";
		String num2 = "";
		
		num1 = scanStr.next();
		
		
		if (boolRegexChecker("[0-9]+", num1)) //Checks if the first input is an operation {1, ..., 5} 
		{
			System.out.println("Entro");
			
			//Creates the strings. Works for: +, -, *, /, % 
			for (int i=0; i<2; i++)
			{
				if (i==0)
				{
					operator = scanStr.next();
					
				} 
				else if (i==1)
				{
					num2 = scanStr.next();
				}
			}
			
			System.out.println("||");
			
			System.out.println(basicOperation(num1, operator, num2)); // Calcula el resultado
			
			
			
		} 
		else //Must be an operation {6, ..., 17)
		{ 
			System.out.println("Entro 2");
		}
	}
		
	public static boolean boolRegexChecker(String theRegex, String str2Check)
	{
		boolean result = false;
		
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(str2Check);
		
		result = regexMatcher.find();
		
		return result;
		
	}
	
	public static double basicOperation(String pNum1, String pOperator, String pNum2)
	{
		double result = 0; 
		
		switch(pOperator)
		{
			case "+":
				result = Integer.parseInt(pNum1) + Integer.parseInt(pNum2);
				break;
			case "-":
				result = Integer.parseInt(pNum1) - Integer.parseInt(pNum2);
				break;
			case "*":
				result = Integer.parseInt(pNum1) * Integer.parseInt(pNum2);
				break; 
			case "/":
				result = Integer.parseInt(pNum1) / Integer.parseInt(pNum2);
				break;
			case "%":
				result = Integer.parseInt(pNum1) % Integer.parseInt(pNum2);
				break; 
		}
		
		return result;
	}
	
	
}  // Close Main
	