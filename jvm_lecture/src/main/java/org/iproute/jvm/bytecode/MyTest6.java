package org.iproute.jvm.bytecode;

/**
 * 方法的动态分派（多态）
 * <p>
 * 方法的动态分派涉及到一个重要概念，方法接收者。
 * <p>
 * invokevirtual 字节码指令的多态查找流程
 * 1. 到操作数栈顶找到对象的实际类型
 * 2. 如果在这个类型中找到了与常量池中描述符和名称相同的方法，并具备相应的访问权限
 * 3. 返回目标方法的直接引用
 * 4. 如果找不到，就从子类到父类，按层次再查找
 * <p>
 * 比较方法的重载（overload） 和 方法重写 (overwrite)
 * <p>
 * 方法重载是静态的，是编译期行为，方法重写是动态的，是运行期的行为
 *
 * @author winterfell
 **/
public class MyTest6 {

    public static void main(String[] args) {
        Fruit apple = new Apple();
        Fruit orange = new Orange();
        apple.test();
        orange.test();

        apple = new Orange();
        apple.test();
    }
}

class Fruit {

    public void test() {
        System.out.println("Fruit");
    }
}

class Apple extends Fruit {

    @Override
    public void test() {
        System.out.println("Apple");
    }
}

class Orange extends Fruit {

    @Override
    public void test() {
        System.out.println("orange");
    }
}