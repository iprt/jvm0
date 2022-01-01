package org.iproute.jvm.classloader;

/**
 * 对于数组实例来说，其类型是由JVM在运行期间动态生成的，表示为 [Lcom.winterfell.jvm.classloader.MyParent4
 * 这种形式，动态生成的类型，其父类型就是Object
 *
 * 对于数组来说，JavaDoc经常将构成数组的元素称之为 component
 *
 * 助记符
 * anewarray：创建一个引用类型的数组
 * @author winterfell
 **/
public class MyTest4 {
    public static void main(String[] args) {
        // 会初始化
        // 不会 MyParent4 myParent4 = new MyParent4();
        // 不会初始化
        MyParent4[] myParent4s = new MyParent4[1];
        System.out.println(myParent4s.getClass());
        System.out.println(myParent4s.getClass().getSuperclass());

        int[] ints = new int[1];
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());

        char[] chars = new char[1];
        System.out.println(chars.getClass());
        System.out.println(chars.getClass().getSuperclass());

        short[] shorts = new short[1];
        System.out.println(shorts.getClass());
        System.out.println(shorts.getClass().getSuperclass());

        byte[] bytes = new byte[1];
        System.out.println(bytes.getClass());
        System.out.println(bytes.getClass().getSuperclass());
    }
}

class MyParent4 {
    static {
        System.out.println("MyParent4 static block");
    }
}
