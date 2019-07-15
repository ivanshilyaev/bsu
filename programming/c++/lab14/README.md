﻿# Binary search tree
### **Бинарное поисковое дерево**
Создать класс «бинарное поисковое дерево», в котором будут храниться данные типа int. Должны быть реализованы следующие методы:
- конструктор без параметров, создающий пустое дерево;
- конструктор копирования;
- перегрузка оператора присваивания;
- деструктор;
- вставка нового элемента по значению с обеспечением уникальности хранимых элементов;
- удаление элемента по значению (если удаляемая вершина имеет двоих сыновей, переходить в правое поддерево, чтобы результат совпал с показанным в примере);
- поиск элемента по значению с подсчетом числа сравнений, выполненных в процессе поиска;
- концевой обход дерева с вызовом callback-функции, которая не изменяет хранящееся значение.
<br/>
Головная программа должна читать информацию о запрашиваемых операциях из файла INPUT.TXT и выводить протокол работы в файл OUTPUT.TXT в соответствии со следующими правилами:
<br/><br/>
Команда и ее описание и возможные записи в протоколе работы:
<br/>
- I значение
<br/>
Вставить новый элемент в дерево 
<br/>
значение: inserted 
<br/>
значение: not inserted 
<br/>
- D значение 
<br/>
Удалить элемент из дерева <br/>
значение: deleted <br/>
значение: not deleted <br/>
- F значение <br/>
Найти элемент <br/>
значение: found after чср comparison(s) <br/>
значение: not found after чср comparison(s)<br/>
- L <br/>
Вывести все элементы в порядке возрастания (по одному в строку) <br/>
Первая строка: List of elements <br/>
Последующие строки – элементы, хранящиеся в дереве.