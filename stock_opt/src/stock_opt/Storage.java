package stock_opt;

import java.util.Iterator;
import java.util.Vector;

public class Storage {

	String[][] mass;
	Way w;
	Vector<Integer> v_space = new Vector<Integer>();
	
	public Storage(String[][] s_mass) {
		// TODO Auto-generated constructor stub
		mass = s_mass;
		Boolean flag_space;
		v_space.clear();
		for(int i = 0; i < mass.length; i++) {
			flag_space = true;
			for(int j = 0; j < mass[i].length; j++) {
				if(mass[i][j] != " ") {
					flag_space = false;
				}
			}
			if(flag_space == true) {
				v_space.add(i);
			}
		}
		//теперь у нас есть вектор значений y, где все x - пусты
		//это технологические проходы
	}
	
	/***
	 * |вычисление расстояния между адресами
	 * A заход слева, B заход справа
	 * @param p1
	 * @param p2
	 * @return
	 */
	public SetFragment calc_way(Pair p1, Pair p2) {
		//тут по данным в mass строится объект пути
		String[] ss = mass[p1.getY()];
		//будем начинать строить маршрут от ближайшей свободной точки к ячейкам
		//и точка прибытия - тоже пустая рядом с ячейкой
                Pair point_start;
		int s_x = p1.getX();
		int s_y = p1.getY();
                String v = ss[s_x];
		if(v == "B") {
		  s_x += 1;			
		} else 
		if(v == "A"){
		  s_x -= 1;					
		}
		point_start = new Pair(s_x,s_y);
		s_x = 0;
		s_y = p2.getY();
		Pair point_end;
		int e_x = p2.getX();
		int e_y = p2.getY();
	        v = ss[e_x];
		if(v == "B") {
		  e_x += 1;			
		} else 
		if(v == "A"){
		  e_x -= 1;					
		}
		point_end = new Pair(e_x,e_y);
		//в v_space у нас y-координаты технологических проходов

		//надо найти ближайший технологический проход.
		//перед этим проверим, можно ли старт и конец напрямую соединить
		//если по всем позициями по этому пути будет пусто - значит, можем
		//критерий - одинаковые y-позиции у начальной и конечной точки, когда смещение только по x
		
			//найти ближайшие сечения к y-координатам обоих точет
			Integer y_pr_1st_upper = Static.getUpper(v_space, point_start.getY()); 
			Integer y_pr_1st_lower = Static.getLower(v_space, point_start.getY());
			Integer y_pr_2nd_upper = Static.getUpper(v_space, point_end.getY());
			Integer y_pr_2nd_lower = Static.getLower(v_space, point_end.getY());	
						
			SetFragment way = Static.typeDefine(point_start, point_end, y_pr_1st_upper, y_pr_1st_lower, y_pr_2nd_upper, y_pr_2nd_lower);
			return way;

			
			//идем до ближайшего технологического прохода
			//координата x берется у точки старта, а y - это ближайщий тех проход
			//путь идет прямо по технологическому проходу, покуда координата x не достигнет
			//конечной точки - горизонтальное перемещение 
			
			//в общем, получаем до 3х путей, по каждому их которых потом получим множество
			//всех его точек, а потом их соединим и будет длина маршрута

	}

	/** рисует маршрут поверх склада */
	public void draw(Vector<Pair> vpair) {
		// TODO Auto-generated method stub
		Iterator<Pair> ip = vpair.iterator();
		Pair curr_pair;
		while(ip.hasNext()){
			curr_pair = ip.next();
			mass[curr_pair.getY()][curr_pair.getX()] = ".";	
		}
		curr_pair = vpair.firstElement();
		mass[curr_pair.getY()][curr_pair.getX()] = "S"; //start	
		curr_pair = vpair.lastElement();
		mass[curr_pair.getY()][curr_pair.getX()] = "F";	//finish
	}

	public SetFragment calc_way(String s) {
		// TODO Auto-generated method stub
		String[] sm = s.split(";");
		String[] spair1 = sm[0].split(",");
		String[] spair2 = sm[1].split(",");
		//SetFragment sf = storage.calc_way(new Pair(0,6), new Pair(6,17)); //15,7 сначала x, затем y ; до 6,17 строит
	     SetFragment sf = calc_way(new Pair(spair1[0],spair1[1]), new Pair(spair2[0],spair2[1]));
		return sf;
	}

	
	
}
