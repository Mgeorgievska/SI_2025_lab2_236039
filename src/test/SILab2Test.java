import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class SILab2Test {

    // креирање листа со еден предмет
    private List<Item> createItemList(String name, int price, double discount, int quantity) {
        List<Item> list = new ArrayList<>();
        list.add(new Item(name, quantity, price, discount));
        return list;
    }

    @Test
    void testEveryStatement() {
        // 1. Пресметка без попуст и без исклучок
        List<Item> items1 = createItemList("Milk", 100, 0.0, 1);
        String card1 = "1234567890123456";
        double result1 = SILab2.checkCart(items1, card1);
        // јазли: 1,2 → 4 → 5.1 → 5.2 → 6 → 7 → 9 → 11 → 13 → 14 → 5.3 → 5.2 → 15 → 16 → 17 → 18.1 → 19 → 20 → 18.3 → 18.2 → 24 → 25
        assertEquals(100 * 1, result1, 0.001);

        // 2. Пресметка со попуст, sum -= 30;
        List<Item> items2 = createItemList("Milk", 500, 0.0, 1);
        String card2 = "1234567890123456";
        double result2 = SILab2.checkCart(items2, card2);
        // јазли: 1,2 → 4 → 5.1 → 5.2 → 6 → 7 → 9 → 10 → 11 → 12 → 5.3 → 5.2 → 15 → 16 → 17 → 18.1 → 19 → 20 → 18.3 → 24 → 25
        assertEquals((500 * 1) - 30, result2, 0.001);

        // 3. Исклучок - име е празно
        List<Item> items3 = createItemList("", 100, 0.0, 1);
        String card3 = "1234567890123456";
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items3, card3);
        });
        assertEquals("Invalid item!", ex.getMessage());
        // јазли: 1,2 → 4 → 5.1 → 5.2 → 6 → 7 → 8 → 25

        // 4. Исклучок - картичката содржи невалиден карактер
        List<Item> items4 = createItemList("Milk", 100, 0.0, 1);
        String card4 = "123a567890123456";
        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items4, card4);
        });
        assertEquals("Invalid character in card number!", ex2.getMessage());
        // јазли: 1,2 → 4 → 5.1 → 5.2 → 6 → 7 → 9 → 11 → 13 → 14 → 5.3 → 5.2 → 15 → 16 → 17 → 18.1 → 18.2 → 19 → 20 → 21 → 25
    }

    @Test
    void testMultipleCondition() {
        // 1. price <= 300 (False), discount > 0 (True), quantity > 10 (True)
        List<Item> items1 = createItemList("Milk", 100, 0.1, 11);
        String card1 = "1234567890123456";
        double result1 = SILab2.checkCart(items1, card1);
        // јазли: 1,2 → 4 → 5.2 → 6 → 7 → 9 → 10 → 11 → 12 → 5.3 → 5.2 → 15 → 16 → 17 → 18.2 → 19 → 20 → 18.3 → 18.2 → 24 → 25
        assertEquals(100 * (1 - 0.1) * 11 - 30, result1, 0.001);

        // 2. price > 300 (True), discount == 0 (False), quantity > 10 (True)
        List<Item> items2 = createItemList("Milk", 500, 0.0, 11);
        String card2 = "1234567890123456";
        double result2 = SILab2.checkCart(items2, card2);
        // јазли: 1,2 → 4 → 5.2 → 6 → 7 → 9 → 10 → 11 → 13 → 14 → 5.3 → 5.2 → 15 → 16 → 17 → 18.2 → 19 → 20 → 18.3 → 18.2 → 24 → 25
        assertEquals(500 * 11 - 30, result2, 0.001);

        // 3. price > 300 (True), discount > 0 (True), quantity <= 10 (False)
        List<Item> items3 = createItemList("Milk", 500, 0.1, 1);
        String card3 = "1234567890123456";
        double result3 = SILab2.checkCart(items3, card3);
        // јазли: 1,2 → 4 → 5.2 → 6 → 7 → 9 → 10 → 11 → 12 → 5.3 → 5.2  → 15 → 16 → 17 → 18.2 → 19 → 20 → 18.3 → 18.2 → 24 → 25
        assertEquals(500 * (1 - 0.1) * 1 - 30, result3, 0.001);

        // 4. price > 300 (True), discount > 0 (True), quantity > 10 (True)
        List<Item> items4 = createItemList("Milk", 500, 0.1, 11);
        String card4 = "1234567890123456";
        double result4 = SILab2.checkCart(items4, card4);
        // јазли: 1,2 → 4 → 5.2 → 6 → 7 → 9 → 10 → 11 → 12 → 5.3 → 5.2  → 15 → 16 → 17 → 18.2 → 19 → 20 → 18.3 → 18.2 → 24 → 25
        assertEquals(500 * (1 - 0.1) * 11 - 30, result4, 0.001);
    }
}
