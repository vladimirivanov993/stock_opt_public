package third3task;

import java.util.Random;

public class ProbabilityParameter {
	Double m_o; //матожидание 
	Double std; //среднеквадратичное отклонение

	/** конструктор автогенерации вероятностного параметра в заданных диапазонах */
	public ProbabilityParameter(double m_o_min, double m_o_max, double std_min, double std_max){
		m_o = m_o_min + Math.random()*( m_o_max - m_o_min);
		std = std_min + Math.random()*( std_max - std_min);
	}
	
	/**  генерирует заданное число в соответствии с матожиданием и среднеквадратичным отклонением*/
	 public Integer generate_value(Integer max_value){
		 Random random = new Random();
		 Integer temp_value = (int) (std * random.nextGaussian()+ m_o); 
		 temp_value = Math.abs(temp_value); //генерируем только положительные числа
		 return Math.min(temp_value, max_value);
	 }

	public String toPrint() {
		// TODO Auto-generated method stub
		return 	m_o + ";" + std; //матожидание материала и стандартное отклонения
	}
	 
}
