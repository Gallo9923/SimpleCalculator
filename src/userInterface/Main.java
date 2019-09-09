import java.util.Scanner;
import java.util.regex.*;

public class Main
{
	/**
	*	main method: Executes application and main menu
	*/
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
	* Manages all operations
	* <b> pos: </b> The result has been displayed and memory updated
	* @param sc Scanner to read String inputs
	* @param index Variable to detect in which part of the operation the process is. 0: num1 == empty; 1: num1 == with valueOf
	* @param oneOperation true if its one operation enable, false if flow operations enabled
	* @param pMemory matrix with memory of operations
	* @return double[] memory updated
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
	
	/**
	* Checks if a String contains one of the complex operations
	* <b> pre: </b> str must not be empty
	* @param str String that contains the complex operation sintax
	* @return boolean true if complex operation is present in the String
	*/
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
	*	Returns true if the string is an int or double. 
	* 	<b> pre: </b> string must not be empty
	*	<b> pre: </b> String is checked
	*	@param str string to be checked
	*  	@return boolean True if the string is an int or double 
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
	* <b> pos: </b> String is checked. 
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
	*	Checks if a given regex is present in a given String
	*	<b> pos: </b> theRegex is a regular expression and str2Check is not empty
	* 	@param theRegex regular expression to be checked
	*	@param str2Check String to be checks with regular expression
	*	@return boolean True if regular expression is present in str2Check
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
	*	<b> pos: </b> Operation has been calculated
	* 	@param pNum1 Number one
	* 	@param pOperator Mathematical operator (+, -. /, *, %)
	*	@param pNum2 number two
	* 	@return double result of operation betwwen pNum1 and pNum2
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
	*	<b> pos: </b> memory is updated
	*	@param newAns new data to be inserted to the memory
	*	@param array memory
	*	@return double[] memory updated
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
	
	/**
	*	calculates the nth power of a number
	* 	<b> pos: </b> the operation has been calculated
	* 	@param power nth power
	* 	@param num number
	* 	@return double result of the operation
	*/
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
	
	/**
	*	Calculates the factorial of a number n
	* 	<b> pos: </b> the operation has been calculated
	*	@param n number
	*	@return double result of the operation
	*/
	public static double factorial (int n)  			//Factorial
	{
		double result = 1; 
		
		for (int i=1; i<=n; i++)
		{
			result = i * result;
		}
		
		return result;
	}
	
	/**
	*	Converts a binary number to a decimal number
	* 	<b> pos: </b> convertion is done
	* 	@param bin String containing the binary number
	* 	@return double decimal number
	*/
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
	
	/**
	*	Converts a decimal number to a hexadecimal number
	* 	<b> pos: </b> convertion is done
	* 	@param dec String containing the decimal number
	* 	@return double hexadecimal number
	*/
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
	
	/**
	*	Converts a hexadecimal number to a decimal number
	* 	<b> pos: </b> convertion is done
	* 	@param hex String containing the hexadecimal number
	* 	@return double decimal number
	*/
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
	
	/**
	*	Converts a decimal number to a binary number
	* 	<b> pos: </b> convertion is done
	* 	@param dec String containing the decimal number
	* 	@return double binary number
	*/
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
	
	/**
	*	Converts from degrees to radians
	* 	<b> pos: </b> convertion is done
	* 	@param deg Double containing the degrees
	* 	@return double radians
	*/
	public static double deg2Rad(double deg)
	{
		double result = Math.PI * deg / 180.0;
		return result;
	}
	
	/**
	*	Converts from radians to degrees
	* 	<b> pos: </b> convertion is done
	* 	@param deg Double containing the radians
	* 	@return double degrees
	*/
	public static double rad2Deg(double rad) 
	{
		double result = rad * 180.0/Math.PI;
		return result;
	}
	
	/**
	* Manages the angle convertion menu
	*/
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
	
	/**
	*	Manages the calculation of complex operations
	*  	<b> pos: </b> Complex operations is calculated
	* 	@param str String containing the complex operation
	* 	@param pMemory Array containing the memory of the calculator
	* 	@return double result of the operation
	*/
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
	
	/**
	*	Detects if a comma is present in a String
	* 	<b> pos: </b> String is checked
	*	@param str String to be checked
	* 	@return boolean true if a comma is present in a String
	*/
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
	
	/**
	*	Generates a subString from a String
	* 	<b> pos: </b> subString is generated
	*	@param str String of which substring will be obtain
	*	@param indexInicio start index inclusive to form the substring from the String
	*	@param indexFinal final index non-inclusive to form the substring from the String
	*	@return String subString
	*/
	public static String subString(String str, int indexInicio, int indexFinal)
	{
		String result = "";
		
		for(int i=indexInicio; i < indexFinal; i++)			//Puede ser otro método
		{	
			result = result + String.valueOf(str.charAt(i));
		}
		
		return result;
	}
	
	/**
	*	Obtains the position of the last comma present in the String
	* 	<b> pos: </b> position obtained
	* 	@param str String to be checked
	* 	@return int index of the position of the last comma in the String
	*/
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
	
	/**
	*	Calculates the squared number 
	* 	<b> pos: </b> operation calculated
	*	@param num number
	*	@return double result
	*/
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
	