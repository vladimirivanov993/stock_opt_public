package stock_opt;

public class Pair {
 int x;
 int y;
 
 public Pair(String spair1, String spair2) {
	 this.x = (int)Integer.parseInt(spair1);
	 this.y = (int)Integer.parseInt(spair2);
	 
 }
 
 public Pair(int x, int y){
	 this.x = x;
	 this.y = y;
 }
 
 public int getX() {
	 return x;
 }
 
 public int getY() {
	 return y;
 } 
 
}
