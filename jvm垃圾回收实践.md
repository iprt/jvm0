# JVM 垃圾回收实践

## DEMO1 GC日志分析
- code
    ```java
    public class MyTest1 {
        public static void main(String[] args) {
            // 1M大小
            int size = 1024 * 1024;
            byte[] myAlloc1 = new byte[2 * size];
            byte[] myAlloc2 = new byte[2 * size];
            byte[] myAlloc3 = new byte[2 * size];
    //        byte[] myAlloc4 = new byte[2 * size];
            System.out.println("hello world");
        }
    }
    ```
    - 启动参数：
    - `-verbose:gc` : 输出详细的垃圾回收的日志
    - `-XX:+PrintGCDetails`: 打印垃圾回收详细信息
    - `-Xms20M` : 堆的初始大小
    - `-Xmx20M` : 堆的最大值
    - `-Xmn10M` : 新生代的容量是10M
    - `-XX:SurvivorRatio=8`
        - Eden空间和Survivor空间占据的大小比例 8:1
        - Eden:From:To = 8:1:1
        - 换言之：新生代大小为10M ---> Eden:8M From:1M To:1M
    - 总体: `-verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8`
- 控制台输出
    ```
    [GC (Allocation Failure) [PSYoungGen: 6664K->927K(9216K)] 6664K->5031K(19456K), 0.0021803 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
    hello world
    Heap
     PSYoungGen      total 9216K, used 3379K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
      eden space 8192K, 29% used [0x00000000ff600000,0x00000000ff864f68,0x00000000ffe00000)
      from space 1024K, 90% used [0x00000000ffe00000,0x00000000ffee7cb0,0x00000000fff00000)
      to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
      object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
     Metaspace       used 3069K, capacity 4556K, committed 4864K, reserved 1056768K
      class space    used 324K, capacity 392K, committed 512K, reserved 1048576K
    ```
    - `GC`代表触发了GC，原因是 `Allocation Failure`
        - 分配失败，换句话说 eden空间大小不够
    - `PSYoungGen`
        - `PS`: `Parallel Scavenge` 垃圾收集器
        - `YoungGen`: 新生代
    - `6664K->927K(9216K)`
        - `6664K` : 新生代在回收前存活的对象占据的空间
        - `927K` : 新生代在回收后还在存活的对象占据的空间
        - `(9216K)` : 新生代中总的空间容量9M
            - 解释：eden:from:to = 8M : 1M : 1M
            - survivor中有总有一个用来接收被复制过来的对象
            - 所以真正使用的时候是 8M + 1M = 9M = 9216K
    - `6664K->5031K(19456K)`
        - `6664K`: 当执行这次GC之前，总的堆的空间大小
        - `5031K`: 当执行完GC后，堆里存活对象占用的空间大小
        - `19456K`: 总的堆的**可用容量**
            - 因为使用的是复制算法，有一个survivor区的空间是被占用掉的，不纳入计算
    - `0.0021803 secs`:执行这次GC花费的时间
    - `[Times: user=0.00 sys=0.00, real=0.00 secs]`
        - `user=0.00` : 用户空间执行占用的时间
        - `sys=0.00` : 内核空间执行占用的时间
        - `real=0.00`: 真正执行的时间
    - `Heap`的详细信息
    - `from` 和 `to` 之间一定会有一个是浪费的
    - `ParOldGen total 10240K used 4104K`
        - `total` 通过两个参数计算出来：`20m-10m`
        - `used`：有新生代的对象 晋升 到老年代 占用的大小
        - **`used 4104K` 计算**
            ```
            1.执行GC，新生代释放空间容量
            6664K - 927K = 5737K
            
            2.执行GC，堆空间释放的空间容量
            6664K - 5031K = 1633K
            
            3.1 程序在刚执行的时候，老年代里还没有被使用
            3.2 新生代的对象 晋升到老年代 可以直接计算
            
            5737K - 1633K = 4104K
            ```
    - 关于新生代GC的一些理解
        - 新生代GC的时候对象并不一定真正被回收了（释放内存）
        - 也可能晋升到老年代（对象进入老年代，对应新生代的空间也是被释放了）
            - 从新生代的角度来看：进入老年代的对象对于新生代来说也是被回收掉了
        - 晋升到老年代
            - 新生代容纳不了新增的对象

## DEMO2 GC日志分析
- code
    ```java
    public class MyTest1 {
        public static void main(String[] args) {
            // 1M大小
            int size = 1024 * 1024;
    
            byte[] myAlloc1 = new byte[4 * size];
            byte[] myAlloc2 = new byte[4 * size];
            byte[] myAlloc3 = new byte[2 * size];
            byte[] myAlloc4 = new byte[2 * size];
    
            System.out.println("hello world");
    
        }
    }
    ```
- 控制台输出
    ```
    [GC (Allocation Failure) [PSYoungGen: 6664K->943K(9216K)] 10760K->9143K(19456K), 0.0020426 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
    [Full GC (Ergonomics) [PSYoungGen: 943K->0K(9216K)] [ParOldGen: 8200K->8904K(10240K)] 9143K->8904K(19456K), [Metaspace: 3061K->3061K(1056768K)], 0.0049524 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
    hello world
    Heap
     PSYoungGen      total 9216K, used 4506K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
      eden space 8192K, 55% used [0x00000000ff600000,0x00000000ffa66808,0x00000000ffe00000)
      from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
      to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     ParOldGen       total 10240K, used 8904K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
      object space 10240K, 86% used [0x00000000fec00000,0x00000000ff4b2300,0x00000000ff600000)
     Metaspace       used 3069K, capacity 4556K, committed 4864K, reserved 1056768K
      class space    used 324K, capacity 392K, committed 512K, reserved 1048576K
    ```
    - `Full GC`
        - 对新生代，老年代，原空间都进行了一次回收
        - 对原空间进行回收是性价比极低的行为
        - 会产生 `Stop The World`，导致业务线程暂停
    - `[PSYoungGen: 943K->0K(9216K)]`: 新生代直接回收完
    - `[ParOldGen: 8200K->8904K(10240K)]`:
        - 老年代回收的时候有可能存活的对象变少，也有可能变大
        - 这次GC老年代的内存变大 是因为 新生代的对象晋升到老年代
    - `9143K->8904K(19456K)` : 总堆大小
    
- 知识点
    - 当新生代无法容纳下一个新对象的创建，将直接在老年代诞生
        - 是否在老年代诞生也是取决于JVM的策略
        - 说明这个时候也是可能会进行一次 `Minor GC` 的
    - **Jdk1.8的新生代和老年代的默认垃圾收集器**
        - `PSYoungGen`: `Parallel Scavenge`
        - `ParOldGen`: `Parallel Old`
        
## 阈值和垃圾收集器对于对象分配的影响

**打印java执行的一些默认参数**

- 控制输入
    ```
    $ java -XX:+PrintCommandLineFlags -version
    -XX:InitialHeapSize=536071168 -XX:MaxHeapSize=8577138688 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC
    java version "1.8.0_251"
    Java(TM) SE Runtime Environment (build 1.8.0_251-b08)
    Java HotSpot(TM) 64-Bit Server VM (build 25.251-b08, mixed mode)
    ```
    - `-XX:InitialHeapSize`: 初始堆大小
        - 等价于 `-Xms`
    - `-XX:MaxHeapSize`: 堆的最大值
        - 等价与 `-Xmx`
    - `-XX:+PrintCommandLineFlags`: 打印启动参数
    - `-XX:+UseCompressedClassPointers`: 使用指针压缩，节省空间
    - `-XX:+UseCompressedOops`:
        - 32位的JVM程序在64位的JVM运行的时候，指针会膨胀，会占用更多的空间
        - 保证了 32位的JVM程序指针不会膨胀，也会压缩，从而节省空间
    - **`-XX:+UseParallelGC`**
        - 直接决定了新生代和老年代使用什么GC
        - 对于新生代: `Parallel Scavenge`
        - 对于老年代: `Parallel Old`
        
**demo示例**
- 代码
    ```
    public class MyTest2 {
        public static void main(String[] args) {
            // 1M大小
            int size = 1024 * 1024;
            byte[] myAlloc = new byte[5 * size];
        }
    }
    ```
- 第①次尝试：
    - 添加启动参数: `-verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=4194304`
    - `-XX:PretenureSizeThreshold=4194304` 详解
        - 以字节为单位，表示新生代创建的对象大小超过这个阈值的话，会直接诞生在老年代
        - 这里的阈值大小的 4M ，上面创建的对象大小是 5M
    - 运行结果
        ```
        Heap
         PSYoungGen      total 9216K, used 7852K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
          eden space 8192K, 95% used [0x00000000ff600000,0x00000000ffdab108,0x00000000ffe00000)
          from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
          to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
         ParOldGen       total 10240K, used 0K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
          object space 10240K, 0% used [0x00000000fec00000,0x00000000fec00000,0x00000000ff600000)
         Metaspace       used 3067K, capacity 4556K, committed 4864K, reserved 1056768K
          class space    used 324K, capacity 392K, committed 512K, reserved 1048576K
        ```
        - 虽然创建的对象的大小大于4M，但是对象并没有诞生在老年代
    - **使用了阈值参数但是对象并没有诞生在老年代的问题分析**
        - 因为JVM的参数在使用时有时候时交织在一起的
        - 在使用 `-XX:PretenureSizeThreshold` 这个参数的时候，需要指定串行的垃圾收集器
            - 即 `Serial垃圾收集器`
- 第②次尝试：
    - 添加启动参数: `-verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=4194304 -XX:+UseSerialGC`
        - 注意：在使用 `-XX:PretenureSizeThreshold` 指定了 垃圾收集器 `-XX:+UseSerialGC`
    - 运行结果
        ```
        Heap
         def new generation   total 9216K, used 2732K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
          eden space 8192K,  33% used [0x00000000fec00000, 0x00000000feeab0f8, 0x00000000ff400000)
          from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
          to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
         tenured generation   total 10240K, used 5120K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
           the space 10240K,  50% used [0x00000000ff600000, 0x00000000ffb00010, 0x00000000ffb00200, 0x0000000100000000)
         Metaspace       used 3067K, capacity 4556K, committed 4864K, reserved 1056768K
          class space    used 324K, capacity 392K, committed 512K, reserved 1048576K
        ```
        - 原来的 `PSYoungGen` ---> `def new generation`
        - 原来的 `ParOldGen` ---> `tenured generation`
    - 结果分析
        - 使用了 `-XX:+UseSerialGC`
            - 新生代使用的GC是 `Serial`
            - 老年代是用的GC是 `Serial Old`
        - 创建了一个5M的对象之后，发现并没有在新生代存储，而是直接存储在老年代
- 自我分析
    - `-XX:PretenureSizeThreshold` 要和 `-XX:+UseSerialGC` 搭配使用
    - 关于新生代对象到底在哪里创建，需要多次代码示例
        - 上述代码中，如果去掉了  `-XX:+UseSerialGC`
        - 但是创建一个 **8M** 大小的对象，同样会直接分配在老年代里面
        
- 关于GC的执行
    - 一般都是在新对象创建的时候进行GC，因为只有新对象创建的时候内存空间才会不够用

## MaxTenuringThreshold与阈值的动态调整详解

### 理论讲解
- MaxTenuringThreshold
    - 在可以自动调节对象晋升（Promote）到老年代阈值的GC中，设置改阈值的最大值
        - 个人理解：设置可以晋升到老年代的对象可以存活的**最大**年龄
    - 该参数的默认值是15
        - CMS中默认值为6
        - G1中默认值为15 （在JVM中，该数值由4个bit来表示的，所以最大值是1111，即15）
    - 经历了多次GC后，存活的对象（在新生代）会在`From Survivor` 和 `To Survivor` 之间来回存放
        - 前提：是这两个空间有足够的大小来存放这些数据
        - 来回存放就是来回复制：浪费性能
    - 在GC算法中，会计算每个对象年龄的大小，如果**达到某个年龄后**发现**总大小已经大于Survivor空间的50%**
        - 这时候就需要调整阈值，不能再继续等到默认的15次GC后才完成晋升
            - 自动调整，这里体现了自动调整
        - 为什么不能继续等到15次GC
            - 因为这样会导致Survivor空间不足，所以需要调整阈值让这些存活对象尽快完成晋升
            - `MaxTenuringThreshold` 这个参数就是设置阈值的最大值
            - 可能几次GC就完成晋升

- 代码示例 [MyTest4.java](jvm_lecture/src/main/java/com/winterfell/jvm/gc/MyTest4.java)