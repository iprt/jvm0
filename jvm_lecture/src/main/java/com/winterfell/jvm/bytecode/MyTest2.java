package com.winterfell.jvm.bytecode;

/**
 * 对于Java类中的每一个实例方法（非static方法），其在编译后所产生的字节码当中，方法参数总是会比源代码中方法参数的数量多一个（this）
 * 它位于方法的第一个参数位置处；这样，我们就可以在Java的实例方法中使用this来去访问当前对象的属性以及其他方法
 *
 * 这个操作是在编译期间完成的，即由 javac 编译器在编译的时候将this的访问转化为对一个普通实例方法参数的访问，
 * 接下来在运行期间，有JVM在调用实例方法是，自动向实例方法传入该this参数。所以，在实例方法的局部变量表中，知道会有一个指向当前对象的局部变量
 *
 *
 * @author winterfell
 **/
public class MyTest2 {

    String str = "Welcome";

    private int x = 5;

    public static Integer in = 10;

    public Object object = new Object();

    public static void main(String[] args) {
        MyTest2 myTest2 = new MyTest2();
        myTest2.setX(8);
        in = 20;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public void test() {
        synchronized (this) {
            System.out.println("Hello world");
        }
    }

    public void test2(String str) {
        synchronized (this.object) {
            System.out.println("hello world");
        }
    }

}
