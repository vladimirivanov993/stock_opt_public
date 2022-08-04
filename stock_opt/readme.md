Данная разработка решает задачи складской логистики.

1 и 2 задачи запускаются классом main в stock_opt StartOpt.java
Выходит примерно такой экран:

|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |

Введите адрес перемещения:

НУЖНО БУДЕТ: ВВЕСТИ АДРЕСА НАЧАЛЬНОЙ И КОНЕЧНОЙ ЯЧЕЕК В ФОРМАТЕ, ТИПА:  4,3;12,13
отступ по горизонтали 1й,отступ по вертикали 1й;отступ по горизонтали 1й,отступ по вертикали 1й;
левая нижняя граница - точка отсчета координат

НАПРИМЕР:

Введите адрес перемещения:
4,3;12,13
|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XF XX XX XX XX XX XX XX X|
|X XX XX XX X. XX XX XX XX XX XX XX X|
|X XX XX XX X. XX XX XX XX XX XX XX X|
|X XX XX XX X. XX XX XX XX XX XX XX X|
|X XX XX XX X. XX XX XX XX XX XX XX X|
|    .........                       |
|X XX.XX XX XX XX XX XX XX XX XX XX X|
|X XX.XX XX XX XX XX XX XX XX XX XX X|
|X XX.XX XX XX XX XX XX XX XX XX XX X|
|X XX.XX XX XX XX XX XX XX XX XX XX X|
|X XXSXX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|X XX XX XX XX XX XX XX XX XX XX XX X|
|                                    |

Длина маршрута: 19

ДАЛЕЕ СНОВА МОЖНО ВВЕСТИ КООРДИНАТЫ. КАЖДЫЙ РАЗ ПРОГРАММА БУДЕТ ПЕРЕСТРАИВАТЬ МАРШРУТ


По третьей задаче(пакет third3task): главный класс main запускается в StockStorage.java
Никаких данных этому классу передавать не нужно - он запускается автоматически
в Static.java указаны константы, позволяющие выбрать:

furacount = 6; //количество фур, которое приходит за один ход
multiplicator_pallet = 100; //множитель, который показывает, с какими объемами от паллета работаем. значение 100 - одна сотая.
palletes_of_fura = 20; // число паллетов в фуре
fura_amount = palletes_of_fura * multiplicator_pallet; //ёмкость одной фуры
count_cell = 2000; //количество ячеек на одном этаже
count_level = 1; //высота стелажей в этажах
palletes_volume = count_level * count_cell  * multiplicator_pallet; //ёмкость склада
product_amount = 80; //количество типов товара на складе
i = 20; //количество циклов прихода-ухода фур
stock_percentage = 80; //начальная загрузка склада в процентах
	
empiric_mo_std = 2 * furacount * fura_amount / product_amount;
String sinput = "0;"+empiric_mo_std+";0;"+empiric_mo_std;  // минимум и максимум матожидания и стд.откл соотв. Пример "1;15;1;15"

НА ВЫХОДЕ ПОЛУЧАЮТСЯ ФАЙЛЫ, КОТОРЫЕ СОДЕРЖАТ СЛЕДУЮЩИЕ ДАННЫЕ:
loop_before_start.txt
cвободная_паллетная_ёмкость:40000
шаг;материал;приход;уход;остаток
0;0;0;0;2000
(тут перечислены целые числа по всем материалам. файл характеризует начальное состояние склада)

loop0.txt здесь указан номер примененного шага(нумерация начинается с нуля).
файлов столько, сколько циклов прихода-ухода
там перечислены изменения по количеству материалов, с примером данных как в loop_before_start. 
Например, посмотрим первые 15 материалов на 19м шаге

cвободная_паллетная_ёмкость:65222
шаг;материал;приход;уход;остаток
19;0;0;67;3585
19;1;0;72;10300
19;2;0;0;0
19;3;0;87;4647
19;4;0;358;1583
19;5;0;0;0
19;6;0;333;1554
19;7;0;11;1887
19;8;0;0;0
19;9;0;34;1905
19;10;0;0;0
19;11;0;60;1538
19;12;2000;656;4335
19;13;0;165;3667
19;14;2000;25;6586
19;15;0;0;0
...

Также, у нас есть файл вероятностных характеристик, получаемый на выходе(они генерируются автоматичеки по заданным в параметре sinput класса Static.java в пакете third3task:
propability_parameters.txt
материал;матожидание;стандартное_отклонение
0;87.6813612057296;104.89163436968995
1;220.28163381324975;109.00816968007543
2;56.484404663294974;273.55324231045364
3;63.64066833908992;21.231473111463274
4;206.17107290087293;209.19487608861886
5;207.2011291310312;172.14639031117096

При перерапуске программы все выходные файлы перезаписываются новыми данными.
