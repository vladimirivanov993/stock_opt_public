package stock_opt;

import java.util.Iterator;
import java.util.Vector;

public class SetFragment {
	Vector<Fragment> vf = new Vector<Fragment>();
	
	public SetFragment(Vector<Fragment> vf) {
		this.vf = vf;
	}

/** Преобразуем множество фрагментов к множеству точек */
	 public Vector<Pair> convertToVectorPair(){
		 Vector<Pair> vpair = new Vector<Pair>();
		 Iterator<Fragment> ii = vf.iterator();
		 while(ii.hasNext()){
			 vpair.addAll(ii.next().extractAllPair());//добавляем все промежуточные точки отрезка
		 }
		return vpair;
	 }
}
