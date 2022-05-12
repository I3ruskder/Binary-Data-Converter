
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class main {

	public static void main(String[] args) throws Exception{
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter Byte ordering: ");
        int byteOrdering = input.nextInt();
        System.out.println("Byte ordering entered = " + byteOrdering);
        
        System.out.print("Enter Floating point size: ");
        int floatingPointSize = input.nextInt();
        System.out.println("Floating point size entered = " + floatingPointSize);
        try {

        String path = "../SystemProject1\\input.txt";
        FileWriter fWriter = new FileWriter("../SystemProject1\\output.txt");
		File file = new File(path);
		             
		Scanner scan = new Scanner(file);
		//now we scan through each line by using this 
		
		while(scan.hasNextLine()) {
				fWriter.write(matchConvert(scan.nextLine(),byteOrdering,floatingPointSize)+"\n");
			
			}
		
		 fWriter.close();

        }
      
        // Catch block to handle if exception occurs
        catch (IOException e) {
 
            // Print the exception
            System.out.print(e.getMessage());
        }
        
        
	

	}
	public static String matchConvert(String x,int order,int pointSize) {
		if(x.contains("u")) {
			x = x.replaceAll("u", "");
			
			return convertUnsigned(Integer.parseInt(x),order);
		}
		else if(x.contains(".")) {
			return convertFloat(Float.parseFloat(x),order,pointSize);
		}
		else {
			return convertSigned(Integer.parseInt(x),order);
		}
	}
	
	
	public static String convertUnsigned(int  x,int order) {
		return setEndians(bintohex(unsignedtobinary(x)),order);
		
	}
	public static String convertSigned(int  x,int order) {
		return setEndians(bintohex(signedtobinary(x)),order);
		
	}
	public static String convertFloat(double x,int order,int pointSize) {
		return setEndians(bintohex(floattobinary(x,pointSize)),order);
	}
	
	public static String setEndians(String x,int k) {
		String hexBuilder = null;
		int number;
		if(k==1) {
			
			number = (16*gethexToDecimal(x.charAt(0)+""))+(1*gethexToDecimal(x.charAt(1)+""))+(4096*gethexToDecimal(x.charAt(2)+""))+(256*gethexToDecimal(x.charAt(3)+""));
			hexBuilder = bintohex(unsignedtobinary(number));
			
		}
		if(k==2) {
			
			number = (4096*gethexToDecimal(x.charAt(0)+""))+(256*gethexToDecimal(x.charAt(1)+""))+(16*gethexToDecimal(x.charAt(2)+""))+(1*gethexToDecimal(x.charAt(3)+""));
			hexBuilder = bintohex(unsignedtobinary(number));
		}
		
		return hexBuilder.toString();
	}

	
	public static String bintohex(String x) {
		StringBuilder hexBuilder = new StringBuilder();
		for(int i=0; i<x.length(); i+=4) {
			hexBuilder.append(getbinTohex(x.substring(i,i+4)));
		}
		return hexBuilder.toString();
	}
	
	public static String getbinTohex(String x) {
		
		if(x.equals("0000")) return "0";
		else if(x.equals("0001")) return "1";
		else if(x.equals("0010")) return "2";
		else if(x.equals("0011")) return "3";
		else if(x.equals("0100")) return "4";
		else if(x.equals("0101")) return "5";
		else if(x.equals("0110")) return "6";
		else if(x.equals("0111")) return "7";
		else if(x.equals("1000")) return "8";
		else if(x.equals("1001")) return "9";
		else if(x.equals("1010")) return "A";
		else if(x.equals("1011")) return "B";
		else if(x.equals("1100")) return "C";
		else if(x.equals("1101")) return "D";
		else if(x.equals("1110")) return "E";
		else 
			return "F";
		
	}
public static int gethexToDecimal(String x) {
		
		if(x.equals("0")) return 0;
		else if(x.equals("1")) return 1;
		else if(x.equals("2")) return 2;
		else if(x.equals("3")) return 3;
		else if(x.equals("4")) return 4;
		else if(x.equals("5")) return 5;
		else if(x.equals("6")) return 6;
		else if(x.equals("7")) return 7;
		else if(x.equals("8")) return 8;
		else if(x.equals("9")) return 9;
		else if(x.equals("A")) return 10;
		else if(x.equals("B")) return 11;
		else if(x.equals("C")) return 12;
		else if(x.equals("D")) return 13;
		else if(x.equals("E")) return 14;
		else 
			return 15;
		
	}
		
	public static String unsignedtobinary(int x) {
		
		StringBuilder unsignedBinary = new StringBuilder();
		while (x > 0) { unsignedBinary.append(x%2);  x /= 2;}
		unsignedBinary.reverse();
		while(unsignedBinary.length()<16){unsignedBinary.insert(0,0); }
		
		return unsignedBinary.toString();
	}
	public static String signedtobinary(int x) {
		
		StringBuilder unsignedBinary = new StringBuilder();
		StringBuilder signedBinary = new StringBuilder();
		int index =15;
		if(x>=0) {return unsignedtobinary(x);}

		else {
			unsignedBinary.append(unsignedtobinary(-x));
			while(index>0) {
				if(unsignedBinary.charAt(index)=='1') { signedBinary.append(1); break;}
				signedBinary.append(0);
				index--;
			}
			index--;
			while(index>=0) {
				if(unsignedBinary.charAt(index)=='0') signedBinary.append(1);
				else signedBinary.append(0);
				index--;
			}
			signedBinary.reverse();
			return signedBinary.toString();
		}
		
	}
	public static String floattobinary(double x,int size) {
		boolean isNegative = false;
		if(x<0) {isNegative=true; x*=-1;}
		
		StringBuilder binaryBuilder = new StringBuilder();
		int decimalPart = (int) x;
		double fractionalPart = x-decimalPart;
		int e,exp,k = 0;

		
		
		if(size==1) k=3;
		else if(size==2) k=8;
		else if(size==3) k=10;
		else if(size==4) k=12;
		
		while (decimalPart > 0) {
            binaryBuilder.append(decimalPart % 2);
            decimalPart /= 2;
		}
		
		binaryBuilder.reverse();
		int pointPosition = binaryBuilder.length();
	
		if (fractionalPart != 0) {
            while (fractionalPart != 0) {
            	fractionalPart *= 2;
                binaryBuilder.append((int) fractionalPart);
                fractionalPart = fractionalPart - (int) fractionalPart;
             
            }
        }
		
		while(binaryBuilder.length()<=k+1) {
			binaryBuilder.append(0);
		
		}

		roundTo(binaryBuilder, k-1);
		binaryBuilder.deleteCharAt(0);
		
	
		e = pointPosition-1;
		exp = (int) (e+(Math.pow(2, k-1)-1));
	
		while (exp > 0) {
            binaryBuilder.insert(0,exp % 2);
            exp /= 2;
		}
		
		if(!isNegative) {
			binaryBuilder.insert(0,"0");
		}
		else
			binaryBuilder.insert(0,"1");
	
		return binaryBuilder.toString();

	}
	public static void roundTo(StringBuilder x,int k) {
		if(x.substring(k,k+2).equals("01")&& x.substring(k+2,x.length()).contains("1")) {
			x.replace(k, x.length(), "1");
			
		}
		
		else if(x.substring(k,k+2).equals("00")){
			x.replace(k, x.length(), "0");
		}
		
		else if(x.substring(k,k+2).equals("11")) {
			x.replace(k, x.length(), "1");
			round(x,k);
		}
		
		else if(x.substring(k,k+2).equals("01")) {
			x.replace(k, x.length(), "1");
			round(x,k);
		}
		
	}
	public static void round(StringBuilder x,int k) {
		int i=k;
		while(i>=0) {
			if(x.charAt(i)=='0') {x.replace(i, i+1, "1"); break;}
			i--;
		}
	}
}

