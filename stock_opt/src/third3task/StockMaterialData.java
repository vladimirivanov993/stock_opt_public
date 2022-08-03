package third3task;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;

/** этот класс описывает информацию по заданному материалу на складе
 * а также имеет параметры, позволяющие отслеживать изменения в случае прихода-ухода */
public class StockMaterialData {

	Integer number_product;//номер товара – целое число, индекс в векторе
	String name_position; //наименование –строка текста =  товар + номер товара
	String category = ""; //категория – строка текста, одна из множества {A, B, C}. Опр-ся по вер-и
	ProbabilityParameter pp_import; //вероятность разгрузки(= вероятность загрузки в тестовой конф-ции)
	ProbabilityParameter pp_export; //вероятность загрузки(0-1) 
	Integer palletsUse = 0; 	//ЗАПАС МАТЕРИАЛА НА СКЛАДЕ количество в паллетах(int), пусть будет дробным потом округлим до 1/5 паллета. 
	Integer delta_import = 0; //пришло на склад
	Integer delta_export = 0; //ушло со склада
	//Vector<StepHistoryMaterialData> vshmd = new Vector<StepHistoryMaterialData>();
	
	/** вывод в файл вероятностной характеристики */
	public String stock_material_data_print(){
		return pp_export.toPrint();
	}
	
	
	public StockMaterialData(Integer number, double m_o_min, double m_o_max, double std_min, double std_max, Integer init_amount){
		number_product = number;
		name_position = "product" + number.toString();
		pp_import = new ProbabilityParameter(m_o_min, m_o_max, std_min, std_max);
//		pp_export = new ProbabilityParameter(m_o_min, m_o_max, std_min, std_max);
		pp_export  = pp_import;
		palletsUse = init_amount;
	}

	public Integer minusRandomAmount() {
		Integer value = pp_export.generate_value(palletsUse);
		if(value <= palletsUse){
			minusAmount(value);
		} else {
			value = palletsUse; //выдаём всё, что есть
		}
		return value;
	}

	private void minusAmount(Integer value) {
		palletsUse -= value;
		delta_export = value;
	}

	public Integer addRandomAmount(Integer max_value) {
		Integer value = pp_export.generate_value(max_value);
		value = Math.min(Math.abs(value), max_value);
		addAmount(value);
		return value;
	}

	private void addAmount(Integer value) {
		palletsUse += value;
		delta_import = value; 
	}

	/** общее количество товара, которое имеется на складе*/
	public Integer getPalletesVolume() {
		return palletsUse;
	}

/**	Номер материала2, Приход, Уход, Остаток; - это известно на этом уровне */
	public String toPrintString() {
		// TODO Auto-generated method stub - вывести данные по материалу на печать
		String s = number_product + ";" + delta_import+ ";" + delta_export+ ";" + palletsUse;
		return s;
//		Integer number_product;//номер товара – целое число, индекс в векторе
//		String name_position; //наименование –строка текста =  товар + номер товара
//		String category = ""; //категория – строка текста, одна из множества {A, B, C}. Опр-ся по вер-и
//		ProbabilityParameter pp_import; //вероятность разгрузки(= вероятность загрузки в тестовой конф-ции)
//		ProbabilityParameter pp_export; //вероятность загрузки(0-1) 
//		Double palletsUse = 0.0; 	//количество в паллетах(int), пусть будет дробным потом округлим до 1/5 паллета
//		Double delta_import = 0.0; //пришло на склад
//		Double delta_export = 0.0; //ушло со склада
		
//		Шаг0; Номер материала2, 0, 0, Остаток; Свободная паллетная ёмкость;

	}

	/** моделирование прихода-ухода по одному товару, возврат итогового изменения*/
public Integer deltaRandomAmount(Integer free_palletes_amount_current, Integer step_num) {
	Integer value_im = pp_export.generate_value(free_palletes_amount_current);
	value_im = Math.min(Math.abs(value_im), free_palletes_amount_current);
	addAmount(value_im);
	Integer value_ex = pp_export.generate_value(palletsUse);
	if(value_ex <= palletsUse){
		minusAmount(value_ex);
	} else {
		value_ex = palletsUse; //выдаём всё, что есть
	}
	Integer delta = value_im - value_ex;
	StepHistoryMaterialData shmd = new StepHistoryMaterialData(step_num, this.number_product, value_im,
		 value_ex, this.palletsUse, free_palletes_amount_current);
//	vshmd.add(shmd);
	return delta;//будем возвращать разницу итогового прироста
}

/** моделирование прихода-ухода по фурам, возврат итогового изменения
 * @param i */
public Integer deltaRandomFura(Vector<Integer> v_fura_mats, Integer fura_amount, Integer free_palletes_amount_current, Integer step_num) {
	Integer value_im = 0;
	if(v_fura_mats.indexOf(number_product) >= 0){
		value_im = fura_amount;	
	} else {
		value_im = 0; //фура с данным материалом не пришла
	}
	//может быть так, что весь обьем фуры не поместится - тогда берем малую часть	
	value_im = Math.min(Math.abs(value_im), free_palletes_amount_current);
	addAmount(value_im);
	Integer value_ex = pp_export.generate_value(palletsUse);
	if(value_ex > palletsUse){
		value_ex = palletsUse; //выдаём всё, что есть
	}
	minusAmount(value_ex);
	Integer delta = value_im - value_ex;
	StepHistoryMaterialData shmd = new StepHistoryMaterialData(step_num, this.number_product, value_im,
		 value_ex, this.palletsUse, free_palletes_amount_current);
	//vshmd.add(shmd);
	return delta;//будем возвращать разницу итогового прироста
}

	/** вывод на печать истории материала */
//	public String toPrintHistory() {
//		Iterator<StepHistoryMaterialData> it = vshmd.iterator();
//		String res = "";
//		while(it.hasNext()){
//			res += it.next().toPrintString() + "\n"; //строка прихода и ухода товара в текущем цикле
//		}
//		return res;
//	}

	/** возвращение наименования товара */
	public String getNameMaterial() {
		// TODO Auto-generated method stub
		return name_position;
	}

	
	/** отправка данных истории прихода материала в одноимённый файл */
//	public void writeToFileMat() throws FileNotFoundException {
//		StockStorage.toWriteFile(getNameMaterial()+".txt", toPrintHistory());
//	}

	/** домножение на множитель нормализации */
	public void change_export_by_multiplier(Double im_ex_part) {
		// TODO Auto-generated method stub
		delta_export = (int) (delta_export * im_ex_part);
	}

	public Integer getDelta() {
		return delta_import - delta_export;
	}
	
}
