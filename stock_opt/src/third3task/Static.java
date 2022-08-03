package third3task;

public class Static {
	
	public static final Integer furacount = 6;

	public static int multiplicator_pallet = 100;
	public static int palletes_of_fura = 20;
	public static final Integer fura_amount = palletes_of_fura * multiplicator_pallet;

	public static final String loop_header = "шаг;материал;приход;уход;остаток";
	static int count_cell = 2000;
	static int count_level = 1;
	static int palletes_volume = count_level * count_cell  * multiplicator_pallet; //знаем заранее, 2000 паллетов, может быть до 5ти товаров на паллет *7 этажей, 1/100 паллет

	/** порядка 80 наименований - ожидаемое количество товаров на складе */
	static int product_amount = 80;

	//число циклов на складе
	static int i = 20; 
	
	//единица измерения - 1/5 паллета. При разделении на 5 получим в итоге число паллетов товаров
	static int stock_percentage = 80; //загрузка склада в процентах
	static double percentage_absolute = (double) stock_percentage / 100.0;
	
	static int empiric_mo_std = 2 * furacount * fura_amount / product_amount;
	static String sinput = "0;"+empiric_mo_std+";0;"+empiric_mo_std;  // минимум и максимум матожидания и стд.откл соотв. Пример "1;15;1;15"
	static String[] sm = sinput.split(";");

	static double m_o_min = (double) Double.parseDouble(sm[0]);
	static double m_o_max = (double) Double.parseDouble(sm[1]);
	static double std_min = (double) Double.parseDouble(sm[2]);
	static double std_max = (double) Double.parseDouble(sm[3]);

	public static String free_capacity = "cвободная_паллетная_ёмкость:";		
	
//	i
//	palletes_volume
//	stock_percentage
//	percentage_absolute
//	sinput
//	m_o_min
//	m_o_max
//	std_min
//	std_max
	
}
