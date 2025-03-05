import java.util.*;

class HashMapCustom<K, V> {
    //Массив
    private Entry<K,V>[] table;
    //Начальная емкость HashMapCustom
    private int capacity= 4;
    //Все пары ключ-значения находящиеся в CustomHashMap
    LinkedList<Entry<K, V>> entrySetMap = new LinkedList<>();
    //Все ключи находящиеся в CustomHashMap
    Set<K> keySetMap = new HashSet<>();
    //Все значения находящиеся в CustomHashMap
    Collection<V> valuesMap = new HashSet<>();

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public K getKey()
        {
            return key;
        }

        public V getValue()
        {
            return value;
        }

        @Override
        public final String toString() { return key + "=" + value; }

    }
    //Возвращает все пары ключ значения находящиеся в CustomHashMap
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> res = Set.copyOf(entrySetMap);
        return res;
    }
    //Возвращает все key находящиеся в CustomHashMap
    public Set<K> keyset()
    {
        return keySetMap;
    }
    //Возвращает все value находящиеся в CustomHashMap
    public Collection<V> values()
    {
        return valuesMap;
    }

    //Конструктор CustomMap
    public HashMapCustom(){
        table = new Entry[capacity];
    }

    /*
     Метод помещает пару ключ-значение в HashMapCustom.
     Если Map уже содержит сопоставление для ключа, старое значение заменяется.
     1) Проверяем newKey не null
     2) Получаем hash newKey
     3) Создаем новый Entry
     4) Добавляем newEntry в entrySetMap, добавляем его key в keySetMap, добавляем его value в valuesMap
     5) Проверяем если в таблице нет записей, сохранить запись там.
     6) Если записи есть то создаем previous и приравниваем его к null,создаем current и приравниваем его к table[hash]
     7) Запускаем цикл пока не найдем пустую ячейку
     8) Проверяем если previous==null элемент должен быть вставлен в первый блок и выходим из функции
     9) В ином случае newEntry.next=current.next, previous.next=newEntry и выходим из функции
     10) В цикле приравниваем previous к current, а current приравниваем current.next
     */
    public void put(K newKey, V data){
        if(newKey==null)
            return;    //does not allow to store null.

        //calculate hash of key.
        int hash=hash(newKey);
        //create new entry.
        Entry<K,V> newEntry = new Entry<K,V>(newKey, data, null);
        keySetMap.add(newEntry.getKey());
        valuesMap.add(newEntry.getValue());
        entrySetMap.add(newEntry);
        if(table[hash] == null)
        {
            table[hash] = newEntry;

        }else
        {
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while(current != null)
            {
                if(current.key.equals(newKey))
                {
                    if(previous==null)
                    {
                        newEntry.next=current.next;
                        table[hash]=newEntry;
                        return;
                    }
                    else
                    {
                        newEntry.next=current.next;
                        previous.next=newEntry;
                        return;
                    }
                }
                previous=current;
                current = current.next;

            }
            previous.next = newEntry;
        }
    }

    /*
     Возвращеает value по переданному в него key
     */
    public V get(K key){
        int hash = hash(key);
        if(table[hash] == null){
            return null;
        }else{
            Entry<K,V> temp = table[hash];
            while(temp!= null){
                if(temp.key.equals(key))
                    return temp.value;
                temp = temp.next;
            }
            return null;
        }
    }



    public boolean remove(K deleteKey){

        int hash=hash(deleteKey);

        if(table[hash] == null){
            return false;
        }else{
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while(current != null){ //we have reached last entry node of bucket.
                if(current.key.equals(deleteKey)){
                    if(previous==null){  //delete first entry node.
                        table[hash]=table[hash].next;
                        return true;
                    }
                    else{
                        previous.next=current.next;
                        return true;
                    }
                }
                keySetMap.remove(deleteKey);
                V deleteValue = null;
                Entry deleteEntry = null;
                for(int j =0; j<=entrySetMap.size()-1;j++)
                {
                    if (entrySetMap.get(j).key.equals(deleteKey))
                    {
                        deleteValue = entrySetMap.get(j).value;
                        deleteEntry = entrySetMap.get(j);
                    }
                }

                valuesMap.remove(deleteValue);
                entrySetMap.remove(deleteEntry);
                previous=current;
                current = current.next;
            }
            return false;
        }

    }

    /*
     Функция при помощи ключа определяем hash
     */
    private int hash(K key){
        return Math.abs(key.hashCode()) % capacity;
    }
}