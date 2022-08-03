package stock_opt;

import java.util.Vector;

public class Way {
 String s_all;
 Vector<Pair> vp = new Vector<Pair>();// множество координат пути
 Pair start;
 Pair finish;
 Integer length;

 /**
  * строится объект пути
  * @param s_all
  * @param vp
  * @param start
  * @param finish
  * @param length
  */
 public Way(String s_all, Vector<Pair> vp,  Pair start, Pair finish, Integer length){
	 this.s_all = s_all;
	 this.vp = vp;
	 this.start = start;
	 this.finish = finish;
	 this.length = length;
 }
 
 /**
  * возвращается параметр строкового представления: 5:3-7;6-8:7
  * все координаты получается: 5,3 5,4 5,5 5,6 5,7 6,7 7,7 8,7
  * @return
  */
 public String getStringWay() {
	 return s_all;
 }
 
/**
 * возвращает общую длину пути
 * @return
 */
public Integer getLength() {
	return length;
}

}