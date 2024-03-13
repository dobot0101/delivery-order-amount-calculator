import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Scanner sc = new Scanner(System.in);
        System.out.print("배달비를 입력해주세요: ");
        int deliveryFee = sc.nextInt();

        System.out.print("할인금액을 입력해주세요: ");
        int discountAmount = sc.nextInt();

        System.out.print("사람 수를 입력해주세요: ");
        int numberOfPeople = sc.nextInt();

        sc.nextLine();

        List<Person> people = new ArrayList<>();
        for (int i = 0; i < numberOfPeople; i++) {
            System.out.print("사람 " + (i + 1) + " 이름을 입력해주세요: ");
            String name = sc.nextLine();

            System.out.print(name + "의 주문 금액을 입력해주세요: ");
            int orderAmount = sc.nextInt();
            sc.nextLine();  // Consume newline left-over

            people.add(new Person(name, orderAmount));
        }

        int finalFee = deliveryFee - discountAmount;
        for (Person person : people) {
            person.orderAmount += finalFee / numberOfPeople;
            System.out.println(person.name + "의 최종 금액: " + person.orderAmount);
        }
    }
}

class Person {
    String name;
    int orderAmount;

    public Person(String name, int orderAmount) {
        this.name = name;
        this.orderAmount = orderAmount;
    }
}