package third3task;

public class StepHistoryMaterialData {

	Integer step_num;
	Integer num_material;
	Integer import_m;
	Integer export_m;
	Integer amount_material;
//	Integer free_palletes_amount_before_step;//до текущего цикла прихода-ухода
//	Integer free_palletes_amount_after_step;//до текущего цикла прихода-ухода
	Integer free_palletes_amount_current; //с учетом занятости количеств пред.материалами по списку, к текущему материалу
	

//	all_amount_of_pallet_before_step  = all_capacity - free_palletes_amount_before_step;//всего занято до цикла-прихода-ухода
//	all_amount_of_pallet_after_step   = all_capacity - free_palletes_amount_after_step;//всего занято после цикла-прихода-ухода
//	all_amount_of_pallet_current      = all_capacity - free_palletes_amount_current; //всего занято к текущему моменту

	
	public StepHistoryMaterialData(Integer step_num, Integer num_material, Integer import_m,
			Integer export_m, Integer amount_material, Integer fpac) {
		this.step_num = step_num;
		this.num_material = num_material;
		this.import_m = import_m;
		this.export_m = export_m;
		this.amount_material = amount_material;
//		free_palletes_amount_before_step = fpabs;//до текущего цикла прихода-ухода
//		free_palletes_amount_after_step = fpaas;//до текущего цикла прихода-ухода
		free_palletes_amount_current = fpac; //с учетом занятости количеств пред.материалами по списку, к текущему материалу

	}

    /** вывод на печать записи материала(в текущей записи цикла) */
	public String toPrintString() {
		// TODO Auto-generated method stub
		String s = ";";
		return step_num + s + num_material + s + import_m + s + export_m + s + amount_material + s + free_palletes_amount_current;
	}

}
