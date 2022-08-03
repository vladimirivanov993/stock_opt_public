package stock_opt;

import java.util.Iterator;
import java.util.Vector;

import third3task.StockStorage;

public class Static {

public static String viewToConsole(String[][] sm){
	sm = Static.reverse(sm);
	String s = "";
    String sep = "|";
     for(int i=0; i < sm.length; i++) {
    	 s = s.concat(sep);
    	 for(int j=0; j < sm[0].length; j++){
    		 s = s.concat(sm[i][j]);
    	 }
    	 s = s.concat(sep+"\n");
     }
    s=s.replaceAll("A", "🧰");
    s=s.replaceAll("B", "🧰");
    s=s.replaceAll("I", "🧰");
    s=s.replaceAll("E", "🧰");
	return s;
}

/** верхняя граница y*/
public static Integer getUpper(Vector<Integer> v_space, int y) {
	// TODO Auto-generated method stub
	Iterator<Integer> ii = v_space.iterator();
	while(ii.hasNext()){
		Integer current = ii.next(); 
		if(y < current ){
			return current;
		}
	}
	return -1;
}

/** нижняя граница y*/
public static Integer getLower(Vector<Integer> v_space, int y) {
	// TODO Auto-generated method stub
	for(int i = (v_space.size() - 1); i >= 0; i--){ //идем с конца, самого большого значения, пока не встретим меньшее, чем y
		if (v_space.elementAt(i) < y){
			return v_space.elementAt(i);
		}
	}
//	Iterator<Integer> ii = v_space.iterator();
//	while(ii.hasNext()){
//		Integer current = ii.next(); 
//		if(y > current ){
//			return current;
//		}
//	}
	return -1;
}

/**
 * 
 * @param point_start - точка начала маршрута
 * @param point_end   - точка конца маршрута
 * @param y_pr_1st_upper - верхнее ограничение первой точки маршрута
 * @param y_pr_1st_lower - нижнее  ограничение первой точки маршрута
 * @param y_pr_2nd_upper - верхнее ограничение второй точки маршрута
 * @param y_pr_2nd_lower - нижнее  ограничение второй точки маршрута
 * @return
 */
public static SetFragment typeDefine(Pair point_start, Pair point_end, 
								Integer y_pr_1st_upper, Integer y_pr_1st_lower,
								Integer y_pr_2nd_upper, Integer y_pr_2nd_lower) {
		
	Fragment fragment;
	Vector<Fragment> vf = new Vector<Fragment>();
	int x1 = point_start.getX();
	int y1 = point_start.getY();
	int x2 = point_end.getX();
	int y2 = point_end.getY();
	Fragment f1 = null;
	Fragment f2 = null;
	Fragment f3 = null;
   if( point_start.getX() == point_end.getX()) {
	   fragment = new Fragment(point_start, point_end); //линия просто прямая
	   vf.add(fragment);
   } else {
	   //здесь критерий U пути
	   if(y_pr_1st_upper == y_pr_2nd_upper && y_pr_1st_lower == y_pr_2nd_lower){
		   int value_upper_way = (2 * y_pr_2nd_upper) - y1 - y2;
		   int value_lower_way = y1 + y2 - (2 * y_pr_2nd_lower);
		   if( value_upper_way < value_lower_way ){
			   f1 = new Fragment(point_start, new Pair(x1, y_pr_2nd_upper - 1));              //перемещение по y
			   f2 = new Fragment(new Pair(x1, y_pr_2nd_upper), new Pair(x2, y_pr_2nd_upper)); //вбок по x
			   f3 = new Fragment(new Pair(x2, y_pr_2nd_upper - 1), point_end);         // перемещение по y
		   } else { //если путь у нижней границе короче или равен верхнему, идем по нему
			   f1 = new Fragment(point_start, new Pair(x1, y_pr_2nd_lower + 1));              //перемещение по y
			   f2 = new Fragment(new Pair(x1, y_pr_2nd_lower), new Pair(x2, y_pr_2nd_lower)); //вбок по x
			   f3 = new Fragment(new Pair(x2, y_pr_2nd_lower + 1), point_end);                //перемещение по y		   
		   }
//здесь критерий N пути  
	   } else {
		   if(y_pr_2nd_lower >=  y_pr_1st_upper ){//нижняя граница второй точки больше либо равна верхней границы первой - идем вверх
			   f1 = new Fragment(point_start, new Pair(x1, y_pr_1st_upper - 1));              //подъем вверх y
			   f2 = new Fragment(new Pair(x1, y_pr_1st_upper), new Pair(x2, y_pr_1st_upper)); //идем по оси x
			   f3 = new Fragment(new Pair(x2, y_pr_1st_upper + 1), point_end);
		   	  } else {
		   		  if(y_pr_1st_lower >= y_pr_2nd_upper){ //нижняя граница первой больше либо равна верхней границы второй - идем вниз
					   f1 = new Fragment(point_start, new Pair(x1, y_pr_1st_lower + 1));              //подъем вверх y
					   f2 = new Fragment(new Pair(x1, y_pr_1st_lower), new Pair(x2, y_pr_1st_lower)); //идем по оси x
					   f3 = new Fragment(new Pair(x2, y_pr_1st_lower - 1), point_end);		   			  
		   		  }
		   	  }
	   }
	   vf.add(f1);
	   vf.add(f2);
	   vf.add(f3);
   }
    SetFragment sf = new SetFragment(vf);
	return sf;

}
    //0 1 2 3
    //2 1 0
    //(l-1)-x, l=3
    //2-0
    //2-1
    //2-2
	public static String[][] reverse(String[][] sm){
		String[] temp;
		int l = sm.length;
		int index;
		if(l % 2 == 0){
			index = l / 2;
		} else {
			index = (int)((l / 2)+1); 
		}
		for(int i = l - 1; i >=index; i--){
			temp = sm[i];
			sm[i] = sm[(l-1)-i];
			sm[(l-1)-i] = temp;
		}
		return sm;
	}

	

	public static void applyWay(String[][] s_mass_large, String road) {
		// TODO Auto-generated method stub
		   //внешний массив - координаты по Y, внутренний по X
		   String[][] s_mass_reverse = Static.reverse(s_mass_large); 
		   Storage storage = new Storage(s_mass_reverse);
		   //SetFragment sf = storage.calc_way(new Pair(0,6), new Pair(6,17)); //15,7 сначала x, затем y ; до 6,17 строит
  	   //SetFragment sf = storage.calc_way("0,6;6,17"); //15,7 сначала x, затем y ; до 6,17 строит
		   SetFragment sf = storage.calc_way(road);
	       Vector<Pair> vpair = sf.convertToVectorPair();
	       storage.draw(vpair); //рисует маршрут поверх склада
		   System.out.println(Static.viewToConsole(s_mass_large));
		   System.out.println("Длина маршрута: " + vpair.size());
	}

	public static String[][] mass_initialize() {
		// TODO Auto-generated method stub
		  String[][] s_mass_large = {
				  {"E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E", "E", " ", "E"},
				  {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A","B", " ",  "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A","B", " ",  "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"},   
				  {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A","B", " ",  "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"},
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}, 
				  {"B", " ", "A", "B", " " ,"A", "B", " ", "A", "B", " " ,"A", "B", " ", "A", "B", " " ,"A", "B", " ", "A", "B", " " ,"A", "B", " ", "A", "B", " " ,"A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"}, 
				  {"B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A", "B", " ", "A"},  
				  {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},//y=1 
			       //y=0 
			   //x= 0    1    2    3    4    5    6    7    8
			};
		return s_mass_large;
	}

}
