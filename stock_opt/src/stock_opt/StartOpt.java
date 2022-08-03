package stock_opt;

import java.util.Vector;


public class StartOpt {
	
	public static void main(String[] args) {
		 //System.out.println("I am sklad-optimizator");		
		  String[][] s_mass = { 									//y=
					 {" ", " ", " ", " ", " ", " ", " ", " ", " "}, //13
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //12
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //11
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //10
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //9
					 {" ", " ", " ", " ", " ", " ", " ", " ", " "},	//8
					 {"B", " ", "A", "B", " " ,"A", "B", " ", "A"}, //7
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //6
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //5
					 {" ", " ", " ", " ", " ", " ", " ", " ", " "},	//4
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //3
					 {"B", " ", "A", "B", " ", "A", "B", " ", "A"}, //2
					 {" ", " ", " ", " ", " ", " ", " ", " ", " "}, //1
					 {"E", " ", "E", "E", " ", "E", "E", " ", "E"}, //0
				  //x= 0    1    2    3    4    5    6    7    8	 
					 };
		  
		  
		 String[][] s_mass_large = Static.mass_initialize();
		 java.util.Scanner in = new java.util.Scanner(System.in);
		 String road;
		 

		 String[][] s_mass_reverse = Static.reverse(s_mass_large);
		 System.out.println(Static.viewToConsole(s_mass_reverse));
// Примеры маршрутов:		 
//		 0,6;15,17 
//		 0,6;6,17  
//		 0,6;6,5  
//		 0,6;6,4
//		 0,6;0,17
//		 16,29;1,1
//		 15,17;0,6
//		 6,17;0,6
//		 6,5;0,6
//		 6,4;0,6
//		 0,17;0,6
//		 1,1;16,29		 
		 while(true){
		   System.out.println("Введите адрес перемещения:");
		   road = in.next();
		   if(!road.toUpperCase().contains("EXIT")){
			   s_mass_large = Static.mass_initialize();
			   Static.applyWay(s_mass_large, road);
		   } else {
				 in.close();
		   }
		   
		 }
	}
}
