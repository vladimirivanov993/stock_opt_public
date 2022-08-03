package third3task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

public class StockStorage {
	
	Vector<StockMaterialData> vsmd = new Vector<StockMaterialData>();
	/** всё количество импользуемых паллет. это начальная загрузка склада */	
	Integer all_amount_of_pallet = 0; //также, это общая ёмкость свободных паллет после шага 
	/** общая ёмкость склада. её не меняем */
	Integer all_capacity = 0; //вся ёмкость склада. не меняется при приходе-уходе
	Vector<String> vs = new Vector<String>();
	Integer all_amount_of_pallet_before_step = 0; //также, это общая ёмкость занятых паллет до шага  
	Integer all_amount_of_pallet_after_step = 0; //также, это общая ёмкость занятых паллет после шага  
	Integer all_amount_of_pallet_current = 0;     //занятые к текущему моменту шага(при обходе материалов)	
	
	public String get_string_mo_std_represent(){
		Iterator<StockMaterialData> i = vsmd.iterator();
		String s = "материал;матожидание;стандартное_отклонение\n";
		int counter =0;
		while(i.hasNext()){
			s = s+counter+";"+i.next().stock_material_data_print()+"\n";
			counter++;
		}
		return s;
	}
	
	public StockStorage(int product_amount, int palletes_volume, double percentage, double m_o_min, double m_o_max, double std_min, double std_max ) {
		StockMaterialData smd;
		all_capacity = palletes_volume;
		palletes_volume *= percentage;
		Integer init_amount = palletes_volume / product_amount; //всего частей 1/5ых паллетов на товар, целочисленно
		for(int i=0;i<product_amount;i++){
			smd = new StockMaterialData(i,  m_o_min,  m_o_max,  std_min,  std_max, init_amount);//пусть у нас будет матожидание от 1 паллеты до 150 
			vsmd.add(smd);
		}
		all_amount_of_pallet = palletes_volume;
		//начальная инициализация следующих трёх параметров одинакова. различия будут дальше
		all_amount_of_pallet_before_step = all_amount_of_pallet; //также, это общая ёмкость занятых паллет до шага  
		all_amount_of_pallet_after_step = all_amount_of_pallet; //также, это общая ёмкость занятых паллет после шага  
		all_amount_of_pallet_current = all_amount_of_pallet;     //занятые к текущему моменту шага(при обходе материалов)
	}

	/** расчет общего количества, занимаемого паллетами товаров на складе */
	public Integer calculateAllUseCapacity(){
		Integer capacity = 0;
		Iterator<StockMaterialData> i = vsmd.iterator();
		while(i.hasNext()){
			capacity += i.next().getPalletesVolume();
		}
		return capacity;
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
 
		int product_amount = Static.product_amount; //80 наименований
		int palletes_volume = Static.palletes_volume; //знаем заранее, 2000 паллетов, может быть до 5ти товаров на паллет *7 этажей, 1/100 паллет
 
		int stock_percentage = Static.stock_percentage;
		double percentage_absolute = (double) stock_percentage / 100.0;
		
		String sinput = Static.sinput;
		double m_o_min = Static.m_o_min; 
		double m_o_max = Static.m_o_max; 
		double std_min = Static.std_min; 
		double std_max = Static.std_max;
		String[] sm = Static.sm;
		
		StockStorage stock = new StockStorage(product_amount, palletes_volume, percentage_absolute, m_o_min, m_o_max, std_min, std_max);
		stock.stock_loop_init(); // по сути, работа этого метода описана выше. можно убрать
//		stock.toWriteInit(); //запись в файл множество матеариалов при первичной загрузке
		System.out.println(stock.getStringCondition());

		int loop_counter = Static.i; // можно изменить на запрос у пользователя
		
		System.out.println("шаг-номер_мат-приход-уход-остаток-свободно");	
//		stock.stock_loop(loop_counter); //применение заданного количество циклов к складу,
		stock.stock_loop_material(loop_counter); //применение заданного количество циклов к складу,
		System.out.println(stock.getStringCondition());
//		stock.toWriteAfterLoop(); 
//		stock.toWriteIntoFilesByMaterial(); //новый файл, печать всей истории изменений
		
		toWriteFile("propability_parameters.txt", stock.get_string_mo_std_represent());
		
		
	}

	/** запись истории материалов по файлам 
	 * @throws FileNotFoundException */
//	public void toWriteIntoFilesByMaterial() throws FileNotFoundException {
//			Iterator<StockMaterialData> it = vsmd.iterator();
//			while(it.hasNext()){
//				it.next().writeToFileMat();
//			}
//	}
	
	/** печать данных цикла приходов/уходов */
	private void toWriteAfterLoop() throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("AfterLoop.txt");
		PrintWriter pw = new PrintWriter(file);
		String s = getStringCondition();
		pw.write(s);
		pw.close();
	}
	/** склад на печать */


	/** основной цикл прихода-ухода товаров */
	//здесь будут представление в количествах в результате операций по складам.
	public Vector<String> stock_loop(int loop_counter) {
		// TODO Auto-generated method stub
		for(Integer i=0;i<loop_counter; i++){
			stock_input(); //загружаем на склад
			stock_output();//выгружаем из склада
			vs.add(PrintStockRepresent(i));
		}
		return vs;
		
	}
	

	
	/** цикл приходов-уходов и вывод на печать данных по циклам: в каждом данные по движению материалов 
	 * @param full_mats */
	public void stock_loop_material(int loop_counter) throws FileNotFoundException {
	Integer value = 0;
    Integer	free_palletes_amount_current = 0;
	String stemp = PrintStockRepresent(0);
	vs.add(stemp);
	toWriteFile("loop_before_start.txt", stemp); 
	for(Integer i=0;i<loop_counter; i++){
		Iterator<StockMaterialData> it = vsmd.iterator();
		Vector<Integer> full_mats = generate_set_materials(Static.furacount, 0, Static.product_amount);
		while(it.hasNext()){
			free_palletes_amount_current = all_capacity - all_amount_of_pallet_current;
			value = it.next().deltaRandomFura(full_mats, Static.fura_amount, free_palletes_amount_current, i);
			all_amount_of_pallet_current += value;
		}
//		normalize_vsmd();

		stemp = PrintStockRepresent(i);
		vs.add(stemp);
		toWriteFile("loop"+i+".txt", stemp); 
	}
}
	/** делаем экспортное количество примерно равное импортному */
	private void normalize_vsmd() {
		Integer export_amount = 1;
		Integer import_amount = 0;
		Iterator<StockMaterialData> it = vsmd.iterator();
		while(it.hasNext()){
			export_amount += it.next().delta_export; //считаем, сколько материалов ушло в первом приближении
		}
		it = vsmd.iterator();
		while(it.hasNext()){
			import_amount += it.next().delta_import; //считаем, сколько материалов пришло
		}
		Double d_e = (double) export_amount;
		Double d_i = (double) import_amount;
		//нам нужно, чтобы ушло примерно столько же, сколько и пришло.
		//export * import_ / export = import_
		Double im_ex_part = (double) (d_i / d_e) ;
		it = vsmd.iterator();
		while(it.hasNext()){
			it.next().change_export_by_multiplier(im_ex_part); //домножаем для баланса
		}
		
	}

	/** Integer count_of_integer, Integer low, Integer max */
	private Vector<Integer> generate_set_materials(Integer count_of_integer, Integer low, Integer max) {
		 Vector<Integer> vi = new Vector<Integer>();
		 Integer temp_int = 0;
		 max = max + 1; //чтобы правая граница тоже попадала
		for(int i =0; i < count_of_integer; i++){
			temp_int = (int) (low + Math.random()*( max - low));	
			vi.add(temp_int);
		}
		return vi;
	}

	/** вывод на печать цикла прихода-ухода товаров */
//	public String printStringVSMD(){
//		Iterator<StockMaterialData> it = vsmd.iterator();
//		String s_material_data;
//		String all_mats_data = "";
//		while(it.hasNext()){
//			s_material_data = it.next().toPrintHistory();
//			all_mats_data += s_material_data + "\n";
//		}
//		return all_mats_data; 
//	}
	
	
	/** основной цикл прихода-ухода товаров */
	//здесь будут представление в количествах в результате операций по складам.
	public Vector<String> stock_loop_init() {
    	vs.add(PrintStockRepresent(0));
		return vs;
	}

	
	private void stock_output() {//по каждому товару минус будет из его объема на складе
		Iterator<StockMaterialData> i = vsmd.iterator();
        Integer value = 0;
		while(i.hasNext()){
			value = i.next().minusRandomAmount();
			all_amount_of_pallet_current -= value;
		}
	}

	private void stock_input() {//максимум по материлалу не больше прихода по остаточной ёмкости склада
		Iterator<StockMaterialData> i = vsmd.iterator();
		Integer value = 0;
        Integer	free_palletes_amount_current = all_capacity - all_amount_of_pallet_current;
		while(i.hasNext()){
			value = i.next().addRandomAmount(free_palletes_amount_current);
			all_amount_of_pallet_current += value;
		}
	}
	
	/** печать начального состояния */
	public void toWriteInit() throws FileNotFoundException {
		toWriteFile("StockInit.txt");
	}

	/** печать состояния склада в файл*/
	public void toWriteFile(String name) throws FileNotFoundException {
		toWriteFile(name, getStringCondition());
	}
	
	/** печать состояния склада в файл*/
	public static void toWriteFile(String name, String data) throws FileNotFoundException {
		File file = new File(name);
		PrintWriter pw = new PrintWriter(file);
		pw.write(data);
		pw.close();
	}
	

	/** перевод представления множества строк: шаг-материал-количества  */
	public String getStringCondition(){
		String s = "";
		Iterator<String> i = vs.iterator();
		while(i.hasNext()){
			s=s.concat(i.next()+"\n");
		}
		return s;
	}

	/** Шаг1; StockMaterialData.toPrintString; Свободная паллетная ёмкость; */
	private String PrintStockRepresent(Integer step) {
		Integer capacity = calculateAllUseCapacity();
		Integer free_capacity = all_capacity - capacity;
		Iterator<StockMaterialData> i = vsmd.iterator();
		String s_part = Static.free_capacity+free_capacity + "\n";
		s_part = s_part + Static.loop_header +"\n";
		while(i.hasNext()){
			s_part += step + ";";
			s_part += i.next().toPrintString(); //Номер материала1;Приход;Уход;Остаток
			s_part = s_part + "\n";
		}
		return s_part;
	}

}
