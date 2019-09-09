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
			System.out.println("(1: One operation; 2: Flow of operations; 3: Convertion)");
			System.out.println("(4: Display Memory; 5: Scientific notation; 6: Exit Program)");
			
			optionMenu = Integer.parseInt(sc.next()); 
			
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
					System.out.println("--- Convertion ---");
					convertAnglesStart();
				case 4: 																			//For TESTING
					System.out.println("Memory will be printed");
					for(int i=0; i < memory.length; i++)
					{
						System.out.println(memory[i]);
					}
					break;
				case 5:
					System.out.println("--- Scientific Notation ---");
					System.out.println("Digit the number to convert");
					System.out.printf("=%n" + "%.3e%n", Double.parseDouble(sc.next()));
					break;
				case 6:
					System.out.println("The program has finished");
					menu = false;
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
					num1 = manageComplexOperations(input, pMemory);
					
					
				}else if (count == 1)
				{
					System.out.println("Have digit a wrong entry");
					etapa = false;
				} else
				{
					//RESOLVER EL NUMERO
					num2 = manageComplexOperations(input, pMemory);
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
		
		if (str.length() >= 3)
		{
			for(int i=0; i < 3; i++)			//Puede ser otro método
			{	
				str2 = str2 + String.valueOf(str.charAt(i));
			}
			
			
			switch (str2)
			{
				case "sqr":     		//Operation 11: Notación Cientifica
				case "pow":				//Operation 12: Convierte a radianes
				case "fac": 			//Operation 12: Convierte a grados
				case "sin":				//Operation 13
				case "cos": 			//Operation 14
				case "tan":				//Operation 15
				case "lnn":				//Operation 16 
				case "log":				//Operation 17
				case "rad":
				case "deg":
				case "mem":
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
	
	public static double power(int power, double num)            //Potenciación
	{
		double result = num;
		
		if (power == 0)
		{
			result = 1;
		} else
		{
			for(int i=0; i<power-1; i++)
			{	
				result = result * num; 	
			}
		}
		
		return result;
		
	}
	
	public static double factorial (int n)  			//Factorial
	{
		double result = 1; 
		
		for (int i=1; i<=n; i++)
		{
			result = i * result;
		}
		
		return result;
	}
	
	public static double bin2Dec(String bin)
	{
		String aux;
		double result = 0;
		
		for(int i=0; i<bin.length(); i++)
		{
			aux = Character.toString(bin.charAt(bin.length()-i-1));
			
			result += Integer.parseInt(aux) * power(i,2);
		
		}
		
		return result;
	}
	
	public static String dec2Hex(double dec) 	
	{
		
		String result = "";
		int remainder = 1;
		int aux = (int)dec;
		
		if (aux == 0)
		{
			result = "0";
		}
		
		while (aux != 0)
		{
			remainder = aux % 16;
			aux = aux / 16;
			
			if (remainder < 10)
			{
				result = Integer.toString(remainder) + result;
			} else
			{
				switch (remainder)
				{
					case 10:
						result = "A" + result;
						break;
					case 11:
						result = "B" + result;
						break;
					case 12:
						result = "C" + result;
						break;
					case 13: 
						result = "D" + result;
						break;
					case 14: 
						result = "E" + result;
						break;
					case 15:
						result = "F" + result;
						break;
				}
			}
		}
		
		return result;
	}
	
	
	public static double hex2Dec(String hex)
	{
		double result = 0.0; 
		String aux = "";
		
		for (int i=0; i < hex.length(); i++)
		{
			aux = Character.toString(hex.charAt(i));
			
			if (Character.isDigit(hex.charAt(i)))
			{
				
				result += power((hex.length()-1-i), 16) * Double.parseDouble(aux);
				
			} else
			{
				switch (aux)
				{
					case "A":
						result += 10 * power((hex.length()-1-i), 16);
						break;
					case "B":
						result += 11 * power((hex.length()-1-i), 16);
						break;
					case "C":
						result += 12 * power((hex.length()-1-i), 16);
						break;
					case "D":
						result += 13 * power((hex.length()-1-i), 16);
						break;
					case "E":
						result +=14 * power((hex.length()-1-i), 16);
						break;
					case "F":
						result += 15 * power((hex.length()-1-i), 16);
						break;
				}
				
			}
			
		}
		
		return result;
	}
	
	public static String dec2Bin(double dec)
	{
		String result = "";
		int remainder = 1;
		int aux = (int)dec;
		
		if (aux == 0)
		{
			result = "0";
		}
		
		while (aux != 0)
		{
			remainder = aux % 2;
			aux = aux / 2;
		
			result = remainder + result;
			
		}
	
		return result;	
	}
	
	public static double deg2Rad(double deg)
	{
		double result = Math.PI * deg / 180.0;
		return result;
	}
	
	public static double rad2Deg(double rad) 
	{
		double result = rad * 180.0/Math.PI;
		return result;
	}
	
	public static void convertAnglesStart()
	{
		Scanner sc = new Scanner(System.in);
		
		boolean convert = true;
		int caso = 0;
		double aux = 0.0;
		String str = "";
		
		while (convert)
		{
			System.out.println("1: Binary to decimal; 2: Decimal to hexadecimal");
			System.out.println("3: Hexadecimal to decimal; 4: Decimal to binary"); 
			System.out.println("5: Binary to hexadecimal; 6: Hexadecimal to  binary");
			System.out.println("7: Exit convertion");
			
			caso = sc.nextInt();
			
			switch (caso)
			{
				case 1:
					System.out.println("Digit the binary number");
					System.out.println(bin2Dec(sc.next()));
					break;
				case 2: 
					System.out.println("Digit the decimal number");
					System.out.println(dec2Hex(Integer.parseInt(sc.next())));
					break;
				case 3:
					System.out.println("Digit the hexadecimal number");
					System.out.println(hex2Dec(sc.next()));
					break;
				case 4: 
					System.out.println("Digit the decimal number");
					System.out.println(dec2Bin(Double.parseDouble(sc.next())));
					break;
				case 5: 
					System.out.println("Digit the hexadecimal number");
					aux = bin2Dec(sc.next());
					System.out.println(dec2Hex(aux));
					break;
				case 6:
					System.out.println("Digit the hexadecimal number");
					aux = hex2Dec(sc.next());
					System.out.println(dec2Bin(aux));
					break;
				case 7: 
					System.out.println("Convertion mode closed");
					convert = false;
				default:
					System.out.println("You need to choose a valid option");
			}
			
		} 
	}
	
	public static double manageComplexOperations(String str, double[] pMemory)
	{
		String expresion = "";
		int posN = 0;
		
		String num = "";
		String n = ""; 
		
		double result = 0.0;
		
		expresion = subString(str, 0, 3);
		
		switch (expresion)
			{
				case "sqr": 
					if(detectoComa(str))
					{
						posN = posComa(str); //Obtengo la posicion de la coma en el string STR
						n = subString(str, 4, posN); //Obtengo el número n
						num = subString(str, posN+1, str.length()-1); //Obtengo el num del String
						
						result = Math.exp((Math.log(Double.parseDouble(num)))/Double.parseDouble(n));
					} else
					{
						num = subString(str, 4, str.length()-1); //Obtengo el num del String
						result = squaredSqr(Double.parseDouble(num));
					}
					break;
				case "pow":	
					if(detectoComa(str))
					{
						
						posN = posComa(str); //Obtengo la posicion de la coma en el string STR
						
						
						
						n = subString(str, 4, posN); //Obtengo el número n
						num = subString(str, posN+1, str.length()-1); //Obtengo el num del String
				
						result = power(Integer.parseInt(n), Double.parseDouble(num));
						
					}	
					break;	
				case "fac": 
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = factorial(Integer.parseInt(num));
					break;
					
				case "sin":		
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = Math.sin(Double.parseDouble(num));
					break;
				case "cos": 	
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = Math.cos(Double.parseDouble(num));
					break;
				case "tan":	
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = Math.tan(Double.parseDouble(num));
					break;
				case "lnn":	
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = Math.log10(Double.parseDouble(num));
					break;
				case "log":
					posN = posComa(str); //Obtengo la posicion de la coma en el string STR
						
					n = subString(str, 4, posN); //Obtengo el número n
					num = subString(str, posN+1, str.length()-1); //Obtengo el num del String
				
					result = Math.log(Double.parseDouble(num))/Math.log(Double.parseDouble(n));	
					break;
				case "mem":
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = pMemory[Integer.parseInt(num)+1];
					break;
				case "rad":
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = deg2Rad(Double.parseDouble(num));
					break;
				case "deg":
					num = subString(str, 4, str.length()-1); //Obtengo el num del String
					result = rad2Deg(Double.parseDouble(num));
				default:
					System.out.println("Have digit a wrong entry");
					break;
			}
		
		return result;
			
	}
	
	
	public static boolean detectoComa(String str)    //Si detecta comma, retorna true
	{
		boolean result = false;
		
		for(int i=0; i<str.length();i++)
		{
			if (str.charAt(i) == ',')
			{
				result = true;
			}
		}	
		
		return result;
	}
	
	public static String subString(String str, int indexInicio, int indexFinal)
	{
		String result = "";
		
		for(int i=indexInicio; i < indexFinal; i++)			//Puede ser otro método
		{	
			result = result + String.valueOf(str.charAt(i));
		}
		
		return result;
	}
	
	public static int posComa(String str)
	{
		int result = 0;
		
		for (int i=0; i<str.length(); i++)
		{
			if(str.charAt(i) == ',')
			{
				result = i; 
			}
		}
		
		return result;
		
	}
	
	public static double squaredSqr(double num)
	{
		double result = 0.0;
		double num1 = 0.0;
		double num2 = 0.0;
		double aux1 = 0.0;
		double aux2 = 0.0;
		
		boolean test = true;
		int i=0;
		
		while (test)
		{
			num1 = i;
			aux1 = i * i;
			
			if ((i+1)*(i+1) > num)
			{
				test = false;
			}
			
			i++;
			
		}
		
		num2 = i; 
		aux2 = i * i; 
		
		double dif1 = 0.0;
		double dif2 = 0.0;
		
		dif1 = num - aux1;
		dif2 = aux2 - num;
		
		if (dif1 > dif2)
		{
			result = ((num2*num2)+num)/(num2*2);
		} else
		{
			result = ((num1*num1)+num)/(num1*2);
		}
		
		return result;
		
		
	}
	
}  // Close Main
	