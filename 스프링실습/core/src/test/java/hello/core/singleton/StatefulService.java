package hello.core.singleton;

public class StatefulService {
    private int price; // 상태 유지 10000->20000 이 되어버림

    public int order(String name, int price){
        System.out.println("name"+name+",price"+price);
        this.price = price;
        return price;
    }
    public int getPrice(){
        return price;
    }
}

