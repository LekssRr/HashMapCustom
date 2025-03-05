import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        HashMapCustom<Integer, String> myMap = new HashMapCustom<>();

        for(int i = 0; i<= 5; i++)
        {
            myMap.put(i, String.valueOf(i+2));
        }

        System.out.println(myMap.entrySet());
    }

}