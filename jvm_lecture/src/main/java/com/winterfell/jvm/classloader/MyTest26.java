package com.winterfell.jvm.classloader;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 线程上下文类加载器的一般使用模式（获取-使用-还原）
 *
 * @author winterfell
 **/
public class MyTest26 {

    public static void main(String[] args) {

        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);

        Iterator<Driver> iterator = serviceLoader.iterator();

        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println("driver:" + driver.getClass() + ",loader:" + driver.getClass().getClassLoader());
        }

        System.out.println("当前线程上下文类加载器：" + Thread.currentThread().getContextClassLoader());

        System.out.println("ServiceLoader的类加载器" + ServiceLoader.class.getClassLoader());

    }
}
/*

 *  ClassLoader classLoader = Thread.currentThread().getContextContextClassLoader();
 *
 *  try{
 *      Thread.currentThread().setContextClassLoader(targetTccl);
 *      myMethod();
 *  } finally {
 *      Thread.currentThread().setContextClassLoader(classLoader);
 *  }
 *
 *  myMethod() 里面则调用了 Thread.currentThread().getContextContextClassLoader(); 获取当前线程的上下文类加载器做某些事情
 *
 *  如果一个类由类加载器A加载，这个类的依赖类也是由相同的类加载的（如果该依赖类之前没有被加载的话）
 *
 *  ContextClassLoader的作用就是为了破坏Java的类加载委托机制
 *
 *  当高层提供了同一的接口让低层去实现，同时又要在高层加载（或实例化），就必须要通过现成上下文类加载器来帮助高层的ClassLoader找到并加载类

 */

/*
ServiceLoader JavaDoc

A simple service-provider loading facility.
A service is a well-known set of interfaces and (usually abstract) classes. A service provider is a specific implementation of a service. The classes in a provider typically implement the interfaces and subclass the classes defined in the service itself. Service providers can be installed in an implementation of the Java platform in the form of extensions, that is, jar files placed into any of the usual extension directories. Providers can also be made available by adding them to the application's class path or by some other platform-specific means.
For the purpose of loading, a service is represented by a single type, that is, a single interface or abstract class. (A concrete class can be used, but this is not recommended.) A provider of a given service contains one or more concrete classes that extend this service type with data and code specific to the provider. The provider class is typically not the entire provider itself but rather a proxy which contains enough information to decide whether the provider is able to satisfy a particular request together with code that can create the actual provider on demand. The details of provider classes tend to be highly service-specific; no single class or interface could possibly unify them, so no such type is defined here. The only requirement enforced by this facility is that provider classes must have a zero-argument constructor so that they can be instantiated during loading.
A service provider is identified by placing a provider-configuration file in the resource directory META-INF/services. The file's name is the fully-qualified binary name of the service's type. The file contains a list of fully-qualified binary names of concrete provider classes, one per line. Space and tab characters surrounding each name, as well as blank lines, are ignored. The comment character is '#' ('\u0023', NUMBER SIGN); on each line all characters following the first comment character are ignored. The file must be encoded in UTF-8.
If a particular concrete provider class is named in more than one configuration file, or is named in the same configuration file more than once, then the duplicates are ignored. The configuration file naming a particular provider need not be in the same jar file or other distribution unit as the provider itself. The provider must be accessible from the same class loader that was initially queried to locate the configuration file; note that this is not necessarily the class loader from which the file was actually loaded.
Providers are located and instantiated lazily, that is, on demand. A service loader maintains a cache of the providers that have been loaded so far. Each invocation of the iterator method returns an iterator that first yields all of the elements of the cache, in instantiation order, and then lazily locates and instantiates any remaining providers, adding each one to the cache in turn. The cache can be cleared via the reload method.
Service loaders always execute in the security context of the caller. Trusted system code should typically invoke the methods in this class, and the methods of the iterators which they return, from within a privileged security context.
Instances of this class are not safe for use by multiple concurrent threads.
Unless otherwise specified, passing a null argument to any method in this class will cause a NullPointerException to be thrown.
Example Suppose we have a service type com.example.CodecSet which is intended to represent sets of encoder/decoder pairs for some protocol. In this case it is an abstract class with two abstract methods:
   public abstract Encoder getEncoder(String encodingName);
   public abstract Decoder getDecoder(String encodingName);
Each method returns an appropriate object or null if the provider does not support the given encoding. Typical providers support more than one encoding.
If com.example.impl.StandardCodecs is an implementation of the CodecSet service then its jar file also contains a file named
   META-INF/services/com.example.CodecSet
This file contains the single line:
   com.example.impl.StandardCodecs    # Standard codecs
The CodecSet class creates and saves a single service instance at initialization:
   private static ServiceLoader<CodecSet> codecSetLoader
       = ServiceLoader.load(CodecSet.class);
To locate an encoder for a given encoding name it defines a static factory method which iterates through the known and available providers, returning only when it has located a suitable encoder or has run out of providers.
   public static Encoder getEncoder(String encodingName) {
       for (CodecSet cp : codecSetLoader) {
           Encoder enc = cp.getEncoder(encodingName);
           if (enc != null)
               return enc;
       }
       return null;
   }
A getDecoder method is defined similarly.
Usage Note If the class path of a class loader that is used for provider loading includes remote network URLs then those URLs will be dereferenced in the process of searching for provider-configuration files.
This activity is normal, although it may cause puzzling entries to be created in web-server logs. If a web server is not configured correctly, however, then this activity may cause the provider-loading algorithm to fail spuriously.
A web server should return an HTTP 404 (Not Found) response when a requested resource does not exist. Sometimes, however, web servers are erroneously configured to return an HTTP 200 (OK) response along with a helpful HTML error page in such cases. This will cause a ServiceConfigurationError to be thrown when this class attempts to parse the HTML page as a provider-configuration file. The best solution to this problem is to fix the misconfigured web server to return the correct response code (HTTP 404) along with the HTML error page.


简单的服务提供商加载工具。
服务是一组著名(well-known)的接口和（通常是抽象的）类。服务提供者是服务的特定实现。提供程序中的类通常实现接口，并子类化服务本身中定义的类。服务提供程序可以扩展的形式安装在Java平台的实现中，也就是说，将jar文件放置在任何常用扩展目录中。也可以通过将提供者添加到应用程序的类路径或通过其他一些特定于平台的方式来使提供者可用。
出于加载的目的，服务由单一类型表示，即单一接口或抽象类。 （可以使用一个具体的类，但是不建议这样做。）给定服务的提供者包含一个或多个具体类，这些具体类使用该提供者特定的数据和代码来扩展此服务类型。提供者类通常不是整个提供者本身，而是包含足够信息以决定提供者是否能够满足特定请求以及可以按需创建实际提供者的代码的代理。提供程序类的细节往往是高度特定于服务的；没有单个类或接口可能会统一它们，因此此处未定义此类。此功能强制执行的唯一要求是，提供程序类必须具有零参数构造函数，以便可以在加载期间实例化它们。
通过将提供者配置文件放置在资源目录META-INF / services中来标识服务提供者。文件名是服务类型的标准二进制名称。该文件包含一个具体的提供程序类的标准二进制名称列表，每行一个。每个名称周围的空格和制表符以及空白行将被忽略。注释字符为“＃”（“ \ u0023”，数字符号）；在每一行中，第一个注释字符之后的所有字符都将被忽略。该文件必须使用UTF-8编码。
如果一个特定的具体提供程序类在多个配置文件中被命名，或者在同一配置文件中被多次命名，则重复项将被忽略。命名特定提供程序的配置文件不必与提供程序本身位于同一jar文件或其他分发单元中。该提供程序必须可以从最初查询定位配置文件的同一类加载程序进行访问；请注意，这不一定是实际从中加载文件的类加载器。
提供商的位置很懒，即按需实例化。服务加载器维护到目前为止已加载的提供者的缓存。每次迭代器方法的调用都会返回一个迭代器，该迭代器首先按实例化顺序生成高速缓存的所有元素，然后懒惰地定位和实例化任何剩余的提供程序，依次将每个提供程序添加到高速缓存中。可以通过reload方法清除缓存。
服务加载程序始终在调用方的安全上下文中执行。受信任的系统代码通常应从特权安全上下文中调用此类中的方法以及它们返回的迭代器的方法。
此类的实例不适用于多个并发线程。
除非另有说明，否则将null参数传递给此类中的任何方法都将引发NullPointerException。
示例假设我们有一个服务类型com.example.CodecSet，它旨在表示某种协议的编码器/解码器对的集合。在这种情况下，它是一个具有两个抽象方法的抽象类：
   public abstract Encoder getEncoder（String encodingName）;
   public abstract Decoder getDecoder（String encodingName）;
每个方法都返回一个适当的对象，如果提供者不支持给定的编码，则返回null。典型的提供程序支持多种编码。
如果com.example.impl.StandardCodecs是CodecSet服务的实现，则其jar文件还包含一个名为
   META-INF / services / com.example.CodecSet
此文件包含单行：
    com.example.impl.StandardCodecs    # Standard codecs
CodecSet类在初始化时创建并保存一个服务实例：
    private static ServiceLoader<CodecSet> codecSetLoader
       = ServiceLoader.load(CodecSet.class);
为了找到给定编码名称的编码器，它定义了一个静态工厂方法，该方法迭代已知和可用的提供者，仅在找到合适的编码器或用尽提供者时返回。
    public static Encoder getEncoder(String encodingName) {
       for (CodecSet cp : codecSetLoader) {
           Encoder enc = cp.getEncoder(encodingName);
           if (enc != null)
               return enc;
       }
       return null;
    }
类似地定义了getDecoder方法。
使用说明如果用于提供程序加载的类加载程序的类路径包括远程网络URL，则将在搜索提供程序配置文件的过程中取消引用这些URL。
此活动是正常的，尽管它可能会导致在Web服务器日志中创建令人费解的条目。但是，如果未正确配置Web服务器，则此活动可能导致提供商加载算法错误地失败。
当请求的资源不存在时，Web服务器应返回HTTP 404（未找到）响应。但是，在某些情况下，有时会错误地将Web服务器配置为返回HTTP 200（OK）响应以及有用的HTML错误页面。当此类尝试将HTML页面解析为提供程序配置文件时，这将引发ServiceConfigurationError。解决此问题的最佳方法是修复配置错误的Web服务器，以返回正确的响应代码（HTTP 404）和HTML错误页面。
 */