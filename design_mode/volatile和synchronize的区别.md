# volatile和synchronize的区别

### 原子性（atomicity）和可见性（visibility）

原子性意味着**一个时刻，只有一个线程能够执行一段代码**，这段代码通过一个monitor object保护。从而防止多个线程在更新状态时相互冲突。

可见性则更为微妙，它必须确保释放锁之前对共享数据作出的更改对于随后获得该锁的另一个线程是可见的。如果没有同步机制提供的这种可见性保证，线程看到的共享变量可能是修改前的值或不一致的值，这将引发许多严重问题。

### volatile

volatile 是一个类型修饰符。volatile 的作用是作为指令关键字，确保本条指令不会因编译器的优化而省略。

它所修饰的**变量不保留拷贝，直接访问内存中的**。

在Java内存模型中，有**main memory，每个线程也有自己的memory (例如寄存器)。为了性能，一个线程会在自己的memory中保持要访问的变量的副本。这样就会出现同一个变量在某个瞬间，在一个线程的memory中的值可能与另外一个线程memory中的值，或者main memory中的值不一致的情况。 一个变量声明为volatile，就意味着这个变量是随时会被其他线程修改的，因此不能将它cache在线程memory中**。

### 使用场景

在有限的情形下可以使用volatile代替锁。要想使volatile变量提供理想的线程安全，必须同时满足下面两个条件：

1.**对变量的写操作不依赖于当前值**。

2.**该变量没有包含在具有其他变量的不变式中**。

volatile最适用一个线程写，多个线程读的场景。

### synchronized

当它用来修饰一个方法或者一个代码块的时候，能够保证在同一时刻最多只有一个线程执行该段代码。

1.当两个并发线程访问同一个对象中的这个synchronized(this)代码块的时候，一个时间内只有一个线程能执行，另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。

2.然而，当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。

3.尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。

4.当一个线程访问object的一个synchronized(this)同步代码块时，它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。

### 区别

1.volatile是变量修饰符，而synchronized则是作用于一段代码或方法。

2.volatile只是在线程内存和“主”内存间同步某个变量的值；而synchronized通过锁定和解锁某个监视器同步所有变量的值, 显然synchronized要比volatile消耗更多资源。

3.volatile保证数据的可见性，但不能保证原子性；而synchronized可以保证原子性，也可以间接保证可见性，因为它会将私有内存中和公共内存中的数据做同步。

4.volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化。（及volatile可以防止指令重排序，而synchronized也不会）。



# 多线程之死锁

所谓死锁，是指两个或两个以上的线程，在执行过程中，由于竞争资源或彼此通信而造成的一种阻塞的现象。若无外力作用，它们都将无法执行推进下去。此时称线程处于死锁状态或系统产生了死锁，这些永远在相互等待的线程被称为死锁进程。

死锁产生的根本原因是：在申请锁时发生了交叉闭环申请。即线程在获得了锁A并且没有释放的情况下去申请锁B，这时另一个线程已经获得了锁B，在释放B之前又要先火速锁A，形成了闭环，陷入死锁循环。

#### 死锁产生的原因？

可以归结为以下两点：

1. 竞争资源

    系统中的资源可以分为两类：
    
    可剥夺资源，是指某进程在获得这类资源后，该资源可以再被其他进程或系统剥夺，CPU和主存均属于可剥夺性资源；
    
    另一类资源是不可剥夺资源，当系统把这类资源分配给某进程后，再不能强行收回，只能在进程用完后自行释放，如磁带机、打印机等。
    
    产生死锁中的竞争资源之一指的是竞争不可剥夺资源（例如：系统中只有一台打印机，可供进程P1使用，假定P1已占用了打印机，若P2继续要求打印机打印将阻塞）
    
    产生死锁中的竞争资源另外一种资源指的是竞争临时资源（临时资源包括硬件中断、信号、消息、缓冲区内的消息等），通常消息通信顺序进行不当，则会产生死锁

2. 进程间推进顺序非法

    若若P1保持了资源R1,P2保持了资源R2，系统处于不安全状态，因为这两个进程再向前推进，便可能发生死锁
    
    例如，当P1运行到P1：Request（R2）时，将因R2已被P2占用而阻塞；当P2运行到P2：Request（R1）时，也将因R1已被P1占用而阻塞，于是发生进程死锁
    
#### 产生死锁的4个必要条件

1. 互斥条件：进程要求对所分配的资源进行排他性控制，即在一段时间内某资源仅为一个进程所占用。

2. 请求和保持条件：当进程因请求资源而阻塞时，对已获得的资源保持不放。

3. 不剥夺条件：进程已获得的资源在未使用完之前，不能剥夺，只能在使用完时由自己释放。

4. 环路等待条件：在发生死锁时，必然存在一个进程--资源的环形链。


# synchronized和Lock的区别

synchronized和lock主要有以下区别：
1. synchronized是关键字，而Lock是个接口；
2. synchronized在执行完同步代码块或出现异常时，会自动释放锁，Lock必须手动释放锁；
3. synchronized会让线程一直等下去，而Lock可以让等待锁的线程响应中断；
4. 通过synchronized无法知道线程是否拿到锁，而Lock可以；
5. Lock能提高多个线程读写操作的效率；
6. synchronized可以用于类、方法和代码块，而Lock是块范围的；


#### 可重入锁
   
 如果锁具有可重入性，可称作为可重入锁。synchronized和ReentrantLock都是可重入锁。当一个线程执行到某个synchronized方法时，
 比如说method1，而在method1中会调用另外一个synchronized方法method2，此时线程不必重新去申请锁，而是可以直接执行方法method2。
 
 ```
class MyClass {
    public synchronized void method1() {
        method2();
    }

    public synchronized void method2() {

    }
}
```

上述代码中的两个方法method1和method2都用synchronized修饰了。假如某一时刻，线程A执行到了method1，此时线程A获取了这个对象的锁，
而由于method2也是synchronized方法，假如synchronized不具备可重入性，此时线程A需要重新申请锁。
但是，这就会造成死锁，因为线程A已经持有了该对象的锁，而又在申请获取该对象的锁，这样就会线程A一直等待永远不会获取到的锁。
而由于synchronized和Lock都具备可重入性，所以不会发生上述现象。
 
#### 可中断锁

顾名思义，就是可以相应中断的锁。在Java中synchronized不是可中断锁，而Lock是可中断锁。

如果线程A正在执行锁中的代码，线程B正在等待获取该锁，可能由于等待的时间过长，线程B不想等待了，想先去处理其他的事情，
我们可以让它中断自己或者在别的线程中中断它，这种就是可中断锁。

#### 公平锁

即尽量以请求锁的顺序来获取锁。synchronized不属于公平锁，不能保证等待的线程获取锁的顺序，
但是ReentrantLock和ReentrantReadWriteLock,默认情况下是非公平锁，但是可以设置为公平锁

# 悲观锁和乐观锁

### 悲观锁

总是假设最坏的情况，每次拿数据的时候都认为别人会修改，所以在每次拿数据时都要上锁。这样别人想拿这个数据就会阻塞直到它拿到锁
（共享资源每次只给一个线程使用，其它线程阻塞，用完后再把资源转让给其它线程）。Java中synchronized和ReentrantLock等独占锁就是悲观锁思想的实现。
一般多写的场景下用倍悲观锁就比较合适。

### 乐观锁

总是假设最好的情况，每次拿数据都不认为别人会修改，所以不会上锁，但是在更新的时候会判断一下在此期间有没有人去更改这个数据，可以使用版本号和CAS算法实现。
**乐观锁适用于多读的应用类型，这样可以提高吞吐量。**在Java中java.util.concurrent.atomic包下面的原子变量类就是使用了乐观锁的一种实现方式CAS实现的。

#### 乐观锁的两种常见实现方式

##### 版本号机制

一般是在数据表中加上一个数据版本号version字段，表示数据被修改的次数，当数据被修改时，version会+1。当线程A要更新数据时，在读取数据的同时也会读取version值，
在提交更新时，若刚才读到的version值与当前数据库中的version值相等时才更新，否则重试更新，知道更新成功。

比如说：

假设数据库中账号信息表有个version字段，当前值为1，当前余额为100。

1. A此时将其读出（version=1），并从其余额中扣除50；

2. 在A操作的过程中，B也进行了操作（version=1）,并从其余额中扣除20；

3. A完成了操作之后，提交更新之前先看version是否与自己读取的相等。如果相等，则version+1，变为2，连同账户扣除50，提交数据库更新。

4. B完成了操作，提交更新之前先看version是否与自己读取的相等。此时version=2，与自己读取的版本号不一致，所以B的操作被驳回。

这样就避免了B用基于version=1的旧数据修改的结果去覆盖A操作的结果。

##### CAS(Compare and Swap)

是一种有名的无锁算法。无锁编程，即不使用锁的情况下实现多线程之间的变量同步，也就是在没有线程阻塞的情况下实现变量的同步，所以也叫非阻塞同步。

CAS涉及到三个操作数：

1. 需要读写的内存值V；

2. 进行比较的值A；

3. 拟写入的新值B；

当且仅当V的值等于A时，CAS通过原子方式用新值B来更新V的值，否则不会执行任何操作（比较和替换是一个原子操作）。一般情况下是一个自旋操作，及不断的重试。

#### 乐观锁的缺点

##### ABA问题

如果一个变量V初次读取的时候是A值，并且在准备赋值的时候检查到它仍然是A值，那我们就能说明它的值没有被其他线程修改过了吗？很明显是不能的，
因为在这段时间它的值可能被改为其他值，然后又改回A，那CAS操作就会误认为它从来没有被修改过。这个问题被称为CAS操作的 "ABA"问题。

JDK 1.5 以后的 AtomicStampedReference 类就提供了此种能力，其中的 compareAndSet 方法就是首先检查当前引用是否等于预期引用，
并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。

##### 循环时间长开销大

##### 只能保证一个共享变量的原子操作

# 多线程的特性
多线程的三个特性：原子性、可见性、有序性。

原子性：是指一个操作是不可中断。即使是多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。比如说对于一个静态变量int a,线程A赋值为1，线程B赋值为-1。
两个线程同时对它进行赋值，那么不管这两个线程以何种方式、何种步调工作，a的值要么是1，要么是-1。这就是原子性的一个特定，不可被中断。

可见性：是指当一个线程修改了某一个共享变量的值，其他线程是否能立即这个这个修改。显然，对于串行来说，可见性问题是不存在的。

有序性：在并发时，程序的执行可能会出现乱序。给人的感觉就是：写在前面的代码，会在后面执行。有序性问题的存在是因为程序在运行时，可能会进行指令重排。

# ArrayList 源码分析

ArrayList实现了List接口，内部是一个可调整大小的数组实现的。提供了包括CRUD在内的多种方法对数据进行操作。ArrayList是线程不安全的，按照插入的顺序来保存数据。

#### 主要的成员变量

```
private static final int DEFAULT_CAPACITY = 10;//数组默认初始容量
 
private static final Object[] EMPTY_ELEMENTDATA = {};//定义一个空的数组实例以供其他需要用到空数组的地方调用 
 
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};//定义一个空数组，跟前面的区别就是这个空数组是用来判断ArrayList第一添加数据的时候要扩容多少。默认的构造器情况下返回这个空数组 
 
transient Object[] elementData;//数据存的地方它的容量就是这个数组的长度，同时只要是使用默认构造器（DEFAULTCAPACITY_EMPTY_ELEMENTDATA ）第一次添加数据的时候容量扩容为DEFAULT_CAPACITY = 10 
 
private int size;//当前数组的长度

```

#### ArrayList的构造方法有三种：

```
//构造一个初始容量为10的空列表
public ArrayList() 

//构造一个自定义容量的空列表
public ArrayList(int initialCapacity) 

//构造一个包含指定集合的元素的列表
public ArrayList(Collection<? extends E> c) 

```

#### 扩容机制
关于ArrayList的扩容核心方法是ensureCapacityInternal。
先前提到的成员变量：

**DEFAULTCAPACITY_EMPTY_ELEMENTDATA**：用来默认构造方法时返回的空数组。如果第一次添加数据的话那么数组扩容长度为DEFAULT_CAPACITY=10；

**EMPTY_ELEMENTDATA**：出现在需要用到空数组的地方，其中一处就是使用自定义容量构造方法的时候返回；


```
 public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            // any size if not default element table
            ? 0
            // larger than default for default empty table. It's already
            // supposed to be at default size.
            : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }
```
最终调用的是ensureExplicitCapacity方法，该方法的作用就是判断是否需要扩容。

```
  private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
```

其实我们其他方法都是过程方法，最终扩容还是在grow方法中完成的。

```
 private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //传入的容量为默认的10，则根据下面的判断我们可以知道
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```


