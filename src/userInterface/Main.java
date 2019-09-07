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
		Scanner sc = new Scanner(System.in); //Scanner for String
		
		double[] memory = new double[12];
		memory[0] = 1;
		
		while (menu)
		{
			System.out.println("Input the option you need");
			System.out.println("(1: One operation; 2: Flow of operations; 3: Exit)");
			optionMenu = sc.nextInt(); 
			
			switch (optionMenu)
			{
				case 1: 
					//One operation method 
					System.out.println("--- First Operation Started ---");
					memory = oneOperationStart(sc, 0, 0, true, memory);
					System.out.println("Back to menu");
					break;
				case 2:
					//Flow operations method
					System.out.println("--- Flow Operation Started ---");
					memory = oneOperationStart(sc, 0, 0, false, memory);
					System.out.println("Back to menu");
					break;
				case 3:
					System.out.println("The program has finished");
					menu = false;
					break;
				case 4: 																			//For TESTING
					System.out.println("Memory will be printed");
					for(int i=0; i < memory.length; i++)
					{
						System.out.println(memory[i]);
					}
					break;
				
				default:
					System.out.println("You need to choose a valid option");
				}
			}
		}
		
	/**
	* This verifies the inputs to make the correct operation
	* <b> pos: </b> The result has been displayed 
	*/
	public static double[] oneOperationStart(Scanner sc, int index, double ans, boolean oneOperation, double[] pMemory)
	{
	
		boolean etapa = true;
		int count = index;
		String input;
		
		String operator = "";
		double num1 = ans;
		double num2 = 0;
		double result = 0;
		
		
		while (etapa)
		{
			input = sc.next();
			
			
			if (containsBasicOperations(input)) 				 //OPERATOR
			{
				if (count == 0 || count == 2)
				{
					System.out.println("Have digit a wrong entry");
					etapa = false;
				} else
				{
					
					operator = input;
				}
			
			} else if (containsNumber(input))					//NUMBER
			{
				if (count == 0)
				{
					num1 = Double.parseDouble(input); 
					
				} else if (count == 1)
				{
					System.out.println("Have digit a wrong entry");
					etapa = false;
					
				} else
				{
					num2 = Double.parseDouble(input); 
				}	
			
			}else if (input.equalsIgnoreCase("PI"))			//CONSTANT PI
			{
				if (count == 0)
				{
					num1 = Math.PI;
					
				} else if (count == 1)
				{
					System.out.println("Have digit a wrong entry");
					etapa = false;
				} else
				{
					num2 = Math.PI;
				}
			}else if (containsComplex(input))					//COMPLEX
			{
				if (count == 0)
				{
					//RESOLVER EL NUMERO
				}else if (count == 1)
				{
					System.out.println("Have digit a wrong entry");
					etapa = false;
				} else
				{
					//RESOLVER EL NUMERO
				}
				
			}else 															//No reconocio ninguna entrada
			{
				System.out.println("Have digit a wrong entry");
				etapa = false;
			}
			
			if (etapa == false)
			{
				pMemory[0] = 0;
			}
			
			
			if (count == 2 && etapa == true)											//Se resuelve
			{
			
				result = basicOperation(num1, operator, num2);
				System.out.println("||");
				System.out.println(result); 
				
				pMemory = updateMem(result , pMemory);
				
				if (oneOperation == true)
				{
					etapa = false;
				} 
				
				
				if (oneOperation == false)  									//It means that we are in flow operations
				{
					pMemory = oneOperationStart(sc, 1, result, false, pMemory);
					if (pMemory[0] == 0)
					{
						etapa = false;
					}
				}
			}
			
			
			
			count++;
			
		}
		
		return pMemory;
	}
	
	public static boolean containsComplex(String str)
	{
		String str2 = "";
		boolean result = false; 
		
		if (str.length() == 3)
		{
			for(int i=0; i < 3; i++)			//Puede ser otro método
			{	
				str2 = str2 + String.valueOf(str.charAt(i));
			}
			
			
			switch (str2)
			{
				case "scn":     		//Operation 11: Notación Cientifica
				case "rad":				//Operation 12: Convierte a radianes
				case "deg": 			//Operation 12: Convierte a grados
				case "sin":				//Operation 13
				case "cos": 			//Operation 14
				case "tan":				//Operation 15
				case "log":				//Operation 16 
				case "sqr":				//Operation 17
					result = true;
					break; 
				default:
					System.out.println("Have digit a wrong entry");
					break;
			}
		}
		
		return result;
	}
	
	
	
	
	/**
	*	Returns true if the string is a int or double. 
	*	@param str string to be checked
	*  	@return boolean  
	*/
	public static boolean containsNumber(String str)
	{
		boolean result = true;
		
		for (int i = 0; i<str.length(); i++)
		{	
			if (!((Character.isDigit(str.charAt(i))) || (str.charAt(i) == '.')))
			{
				result = false; 
			}
		}
		
		return result;
	}
	
	/**
	* Checks if a string contains the basic of one of operations from 1 to 5
	* <b> pos: </b> returns true if basic operation is found
	* @param str String that will be checked
	* @return boolean True if basic operations is found 
	*/
	public static boolean containsBasicOperations(String str)
	{
		boolean result = false;
		
		if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%"))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	*	Deprecated
	*/	
	
	/*
	public static void DeprecatedoneOperationStart()
	{	
		System.out.println("One Operation Mode has started");
		
		Scanner scanStr = new Scanner(System.in);
		
		String num1 = "";
		String operator = "";
		String num2 = "";
	
		num1 = scanStr.next();
		
		if (num1 == "+" | "-" | "*" | "/" | "%")
		{
			
		}
		
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
	*/
	
	
	/**
	*	Checks if a given regex is present in a given String
	*/
	public static boolean boolRegexChecker(String theRegex, String str2Check)
	{
		boolean result = false;
		
		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(str2Check);
		
		result = regexMatcher.find();
		
		return result;
		
	}
	
	/**
	*	Calculates the operations 1 to 5 (+, -, /, *, %)
	* 	@param pNum1 
	* 	@param pOperator
	*	@param pNum2 
	*/
	public static double basicOperation(double pNum1, String pOperator, double pNum2)
	{
		double result = 0; 
		
		switch(pOperator)
		{
			case "+":
				result = pNum1 + pNum2;
				break;
			case "-":
				result = pNum1 - pNum2;
				break;
			case "*":
				result = pNum1 * pNum2;
				break; 
			case "/":
				result = pNum1 / pNum2;
				break;
			case "%":
				result = pNum1 % pNum2;
				break; 
		}
		
		return result;
	}
	
	/**
	*	Updates the memory of the calculator with a new answer
	*/
	public static double[] updateMem(double newAns, double[] array)
	{
		int pos;
		
		if (array[1] < 10)
		{
			pos = (int)array[1]+2;
			array[pos] = newAns;
		} else
		{
			for(int i=2; i < array.length-1; i++)
			{
				array[i] = array[i+1];
			}
			
			array[array.length-1] = newAns;
			
		}
		
		array[1]++;
		
		return array;
		
	}

	
}  // Close Main
	