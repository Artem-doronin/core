public class Main {
    public static void main(String[] args) {
        MyStringBuilder myStringBuilder = new MyStringBuilder();
        myStringBuilder.append("Hello");
        System.out.println(myStringBuilder);
        myStringBuilder.append(" World");
        System.out.println(myStringBuilder);
        myStringBuilder.undo();
        System.out.println(myStringBuilder);
    }
}