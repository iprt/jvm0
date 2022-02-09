# JVM 内存

- 虚拟机栈 Stack Frame 栈帧
    - 属于线程私有的内存空间
    - 当线程创建的时候，与之相对应的虚拟机栈创建
    - 当线程消亡的时候，虚拟机栈也消失了
- 程序计数器（Program Counter）
    - 用来标识程序需要执行哪一行
    - 也是线程私有的空间
- 本地方法栈
    - native
- 堆（heap）: Java操作对象都是基于引用操作的，JVM管理的最大一块内存空间
    - 与堆相关的一个重要的概念是 垃圾收集器。
    - 现代几乎所有的垃圾收集器都采用的分代收集算法
    - 堆空间也基于这一点进行了相应的空间划分。 新生代 与 老年代。 
        - Eden空间，From Survivor空间，To Survivor空间。
    - Java的堆空间在内存上可以是连续的也可以是不连续的
- 方法区（Method Area）：存储元信息  永久代（Permanent Generation） 从JDK 1.8 开始，已经彻底废弃了永久代，使用元空间（meta space）
    - 主要存放元数据
        - 例如 Class对象，常量
    - 引用（栈） -> 对象（堆） -> 元数据（方法区）
- 运行时常量池（编译期确定的）：方法区的一部分内容。
- 直接内存：Direct Memory
    - 与 Java NIO 密切相关。JVM是通过堆上的DirectByteBuffer来操作直接内存
    

**关于Java对象创建的过程**

- new关键字创建对象的3个步骤
    1. 在堆内存中创建对象的实例。
    2. 为对象的实例成员变量赋初值
    3. 将对象的引用返回

- 指针碰撞
    - ✔✔✔✔✔✔✔✔✖✖✖✖✖✖
    - 前提是堆中的空间通过一个指针进行分割，一侧是已经被占用的空间，另一侧是未被占用的空间
- 空闲列表
    - ✔✖✔✔✔✖✖✖✔✔✖✖✔✔
    - 堆内存空间中已被使用的与未被使用的空间是交织在一起的
    - 这是，虚拟机就需要通过一个列表来记录哪些空间是可以使用的，哪些空间是已被使用的
    - 接下来找出可以容纳下新创建对象的且未被使用的空间，在此空间存放该对象，同时还要修改列表上的记录

**对象在内存中的布局**

- 对象头
- 实例数据 （即我们在一个类中所声明的各项信息）
- 对齐填充（可选）


**引用指向**
- 引用指向句柄 
    - 引用指向了一个结构
    - 句柄指向堆内存对象
    - 句柄指向元空间数据
- 引用指向堆内存里的对象 （直接指针）
    - 引用指向了对象
    - 堆内存里的对象指向元空间的数据
    
## jvisualvm

- 堆溢出
    - jvm参数参考： `-Xms5m -Xmx5m -XX:+HeapDumpOnOutOfMemoryError`
- 栈溢出

## 元空间

JDK1.8 废弃了永久代，新增了元空间的概念

永久代原来和堆是一块连续的内存区域
- 回收麻烦
- 大小估计，不可定

元空间变成了一块本地的内存区域，使用指针碰撞的方式管理

元空间可以自动扩容，但是问题是自动扩容的时候会导致fullgc
- 所以初始值设置大一些来保证下一次的fullgc时间晚一些

一个类加载器对应一个元空间，所有的类加载器所包含的空间合并起来称之为元空间

元空间的问题
- 碎片化的问题
    - 产生的原因，元空间可能为一些自定义的类加载器分配比较小的块，而类加载器加载的类的大小并不小
    - 导致不能连续，进而导致碎片化产生

[参考：永久代去哪儿了](https://www.infoq.cn/article/Java-PERMGEN-Removed)

## jmap 与 jstat 工具实战分析

### jmap

获取java进程id
```
jps
```


打印类加载器数据
- `jmap -clstats PID`
    - `-clstats` 是 `-permstat`的替代方案

```
$ jps
10544 Jps
11168 MyTest5
13476 Main
8308 Launcher
15756
20476 RemoteMavenServer36

D:\
$ jmap -clstats 11168
Attaching to process ID 11168, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.251-b08
finding class loader instances ..done.
computing per loader stat ..done.
please wait.. computing liveness.liveness analysis may be inaccurate ...
class_loader    classes bytes   parent_loader   alive?  type

<bootstrap>     466     896211    null          live    <internal>
0x00000005c0c081b8      11      17867   0x00000005c0c08228      live    sun/misc/Launcher$AppClassLoader@0x00000007c000f958
0x00000005c0c08228      0       0         null          live    sun/misc/Launcher$ExtClassLoader@0x00000007c000fd00

```


打印Java堆的信息
```
$ jmap -heap 7844
Attaching to process ID 7844, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.251-b08

using thread-local object allocation.
Parallel GC with 10 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 0
   MaxHeapFreeRatio         = 100
   MaxHeapSize              = 8577351680 (8180.0MB)
   NewSize                  = 178782208 (170.5MB)
   MaxNewSize               = 2858942464 (2726.5MB)
   OldSize                  = 358088704 (341.5MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 125829120 (120.0MB)
   used     = 35128512 (33.50115966796875MB)
   free     = 90700608 (86.49884033203125MB)
   27.917633056640625% used
From Space:
   capacity = 524288 (0.5MB)
   used     = 0 (0.0MB)
   free     = 524288 (0.5MB)
   0.0% used
To Space:
   capacity = 1048576 (1.0MB)
   used     = 0 (0.0MB)
   free     = 1048576 (1.0MB)
   0.0% used
PS Old Generation
   capacity = 358088704 (341.5MB)
   used     = 692336 (0.6602630615234375MB)
   free     = 357396368 (340.83973693847656MB)
   0.1933420385134517% used

1726 interned Strings occupying 156600 bytes.
```

### jstat

**`jstat -gc 7844`打印元空间信息**
```
$ jstat -gc 7844
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
512.0  512.0   0.0    0.0   46080.0  32256.4   349696.0    676.1    4864.0 3094.7 512.0  323.5      79    0.038   0      0.000    0.038
```
- 元空间关注 `MC` `MU`

## `jcmd`

1. `jcmd pid VM.flag`
    - 查看JVM的启动参数
2. `jcmd pid help`
    - 列出当前的Java进程我们可以执行的操作是什么
3. `jcmd pid help JFR.dump`
    - 查看具体命令的选项有哪些
4. `jcmd pid PerfCounter.print`
    - 查看JVM性能相关的参数
5. `jcmd pid VM.uptime`
    - 查看JVM的启动时长
6. `jcmd pid GC.class_histogram`
    - 查看系统中类的统计信息
7. `jcmd pid Thread.print`
    - 打印当前线程的信息
    - 查看线程的堆栈信息
8. `jcmd pid GC.heap_dump filename`
    - 打印堆转储信息
    - `jcmd 123 GC.heap_dump /root/test.hprof`
    - 导出的文件可以jvisualvm查看
9. `jcmd pid VM.system`
    - 查看JVM的属性信息
10. `jcmd pid VM.version`
    - 查看JVM的版本号
11. `jcmd pid VM.command_line`
    - 查看java程序启动的命令行

### `jstack` 
> 可以查看或者导出Java应用程序的堆栈信息

`jstack pid`

死锁分析，利用jstack

### jmc: Java Mission Control

java飞行控制器

### jhat

可以使用 `OQL`





## 内存回收

当一个类加载器被垃圾回收器标记为不再存活，其对应的元空间会被回收

