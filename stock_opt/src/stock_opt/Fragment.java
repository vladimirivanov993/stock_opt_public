package stock_opt;

import java.util.Vector;

public class Fragment {
	
	Pair start;
	Pair end;
	public Fragment(Pair start, Pair end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end   = end;
	}
	
/**возвращает все промежуточные точки*/	
	public Vector<Pair> extractAllPair(){
		Vector<Pair> res_vpair = new Vector<Pair>();
		int x1 = start.getX();
		int x2 = end.getX();
		int y1 = start.getY();
		int y2 = end.getY();
		int increment_factor = 0;
		res_vpair.add(start);
		int y_new;
		int x_new;
		if(x2 - x1 == 0) {//у нас нет смещения по x, есть только по y
			increment_factor = y2 - y1;
			if(increment_factor > 0){
				y_new = y1;
				for(int i = 0; i < increment_factor; i++){
					y_new = y_new + 1; // y1, ... , y2
					res_vpair.add(new Pair(x1, y_new));
				} 				
			} else { // <= 0
				y_new = y1;
				for(int i = 0; i < Math.abs(increment_factor); i++){
					y_new = y_new - 1; // y2, ... , y1
					res_vpair.add(new Pair(x1, y_new));
				}
			}

		} else {
			if(y2 - y1 == 0) {//у нас нет смещения по x, есть только по y
				increment_factor = x2 - x1;
				if(increment_factor > 0){
					x_new = x1;
					for(int i = 0; i < increment_factor; i++){
						x_new = x_new + 1; // x1, ... , x2
						res_vpair.add(new Pair(x_new, y1));
					} 				
				} else { // <= 0
					x_new = x1;
					for(int i = 0; i < Math.abs(increment_factor); i++){
						x_new = x_new - 1; // x2, ... , x1
						res_vpair.add(new Pair(x_new, y1));
					}
				}

			}			
		}
		return res_vpair;
	}
	
}
