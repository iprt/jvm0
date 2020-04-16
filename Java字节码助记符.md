- `getstatic`
> 调用静态变量
- `putstatic`
> 为静态变量赋值
- `invokestatic`
> 调用静态方法
- `ldc`
> ldc 表示将int,float或是String类型常量从常量池中推送至栈顶
- `bipush`
> bipush 表示将单字节 ( -128 ~ 127 ) 的常量推送至栈顶
- `sipush`
> sipush 表示将一个短整型(-32768 ~ 32767) 推送至栈顶
- `iconst_1`
>iconst_1 表示将int型1推送至栈顶
><br> 同理 iconst_2
><br> iconst_m1 是-1
><br> 总共的 iconst_m1 ~ iconst_5
- `anewarray`
> 创建一个引用类型（类接口）的数组，并将其引用值压入栈顶
- `newarray`
> 创建一个指定的原始类型（如int,float,char）的数组，将其引用值压入栈顶