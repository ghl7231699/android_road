# UI渲染的几个关键概念

UI优化应该包含两个方面：一是效率的提升，我们可以非常高效的把UI的设计图转化成应用界面，并且保证UI在不同的尺寸和分辨率的手机上是一致的；
另一个方面是性能的提升，在准确实现复杂、酷炫的UI设计的同时，保证用户有流畅的体验。

### 1.屏幕与适配

对于屏幕碎片化的问题，Android 推荐使用 dp 作为尺寸单位来适配 UI，因此每个 Android 开发都应该很清楚 px、dp、dpi、ppi、density 这些概念。

![](https://note.youdao.com/yws/api/personal/file/WEB6d9bf868958bf88ca1a5b66e5002836a?method=download&shareKey=be63f33b856ba4b8f80dab2c15819463)

通过 dp 加上自适应布局可以基本解决屏幕碎片化的问题，也是 Android 推荐使用的屏幕兼容性适配方案。但是它会存在两个比较大的问题：

1. 不一致性。因为 dpi 与实际 ppi 的差异性，导致在相同分辨率的手机上，控件的实际大小会有所不同。
2. 效率。设计师的设计稿都是以 px 为单位的，开发人员为了 UI 适配，需要手动通过百分比估算出 dp 值。

除了直接 dp 适配之外，目前业界比较常用的 UI 适配方法主要有下面几种：
+ 限制符适配方案。主要有宽高限定符与 smallestWidth 限定符适配方案，具体可以参考[《Android 目前稳定高效的 UI 适配方案》](https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650826034&idx=1&sn=5e86768d7abc1850b057941cdd003927&chksm=80b7b1acb7c038ba8912b9a09f7e0d41eef13ec0cea19462e47c4e4fe6a08ab760fec864c777&scene=21#wechat_redirect)《smallestWidth 限定符适配方案》。
+ 今日头条适配方案。通过反射修正系统的 density 值，具体可以参考[《一种极低成本的 Android 屏幕适配方式》](https://mp.weixin.qq.com/s?__biz=MzI1MzYzMjE0MQ==&mid=2247484502&idx=2&sn=a60ea223de4171dd2022bc2c71e09351&scene=21#wechat_redirect)《今日头条适配方案》。

### 2.CPU和GPU

除了屏幕，UI渲染还依赖两个核心的硬件：CPU和GPU。UI 组件在绘制到屏幕之前，都需要经过 Rasterization（栅格化）操作，
而栅格化操作又是一个非常耗时的操作。GPU（Graphic Processing Unit ）也就是图形处理器，它主要用于处理图形运算，可以帮助我们加快栅格化操作。
![](https://note.youdao.com/ynoteshare1/index.html?id=746f458c7c2e12a93999cb15d71b9631&type=note)

你可以从图上看到，软件绘制使用的是 Skia 库，它是一款能在低端设备如手机上呈现高质量的 2D 跨平台图形框架，类似 Chrome、Flutter 内部使用的都是 Skia 库。

### 3.OpenGL 与 Vulkan

对于硬件绘制，我们通过调用 OpenGL ES 接口利用 GPU 完成绘制。OpenGL是一个跨平台的图形 API，它为 2D/3D 图形处理硬件指定了标准软件接口。而 OpenGL ES 是 OpenGL 的子集，专为嵌入式设备设计。
在官方[硬件加速的文档中](https://developer.android.com/guide/topics/graphics/hardware-accel)，可以看到很多API都有相应的Android API level限制。
![](https://note.youdao.com/yws/api/personal/file/WEB9a624c25b72448a1f54eaee720644b79?method=download&shareKey=8e03ff4ec334da3370db457e90e4aae1)

这是为什么呢？其实这主要是受OpenGL ES版本与系统支持的限制，直到最新的 Android P，有 3 个 API 是仍然没有支持。对于不支持的 API，我们需要使用软件绘制模式，渲染的性能将会大大降低。

![](https://note.youdao.com/yws/api/personal/file/WEBc067e1c95044d481dec6b0d701ecafde?method=download&shareKey=16e9199b0562ba2e925bd237d753d28f)

Android 7.0 把 OpenGL ES 升级到最新的 3.2 版本同时，还添加了对Vulkan的支持。[Vulkan](https://source.android.com/devices/graphics/arch-vulkan) 是用于高性能 3D 图形的低开销、跨平台 API。
相比 OpenGL ES，Vulkan 在改善功耗、多核优化提升绘图调用上有着非常明显的[优势](https://zhuanlan.zhihu.com/p/20712354)。


# 卡顿优化

导致卡顿的原因可能有三大类：

1）事件本身太耗时。

2）事件本身并不耗时，但需要等待别的地方返回耗时。

3）UI线程本身已经拿不到CPU资源来执行事件。

### 耗时事件

就是一些耗时业务逻辑直接写在了UI线程中，比如计算密集型的复杂计算，庞大的MD5计算，非对称RSA解密等。

### 耗时等待
#### 1.网络I/O 同步请求
#### 2.磁盘I/O 文件，数据库

一般的文件和数据库操作，大家可能都会自觉的在子线程中操作。但是值得一提的是SharedPreference的存储和读取，根据sp的设计，
创建的时候会开启子线程把整个文件全部加载进内存，加载完毕再通知主线程，如果读取尚未结束，此时想获取某个key的值，主线程就必须等待加载完毕为止。

因此，如果你的sp文件比较大，那么会带来几个严重问题：

a）第一次从sp中获取值的时候，有可能阻塞主线程，使界面卡顿、掉帧。

b）解析sp的时候会产生大量的临时对象，导致频繁GC，引起界面卡顿。

c）这些key和value会永远存在于内存之中，不会被释放，占用大量内存。

所以千万不要把庞大的key/value存在sp中，比如把复杂的json当value。

 

另外对于sp的存储，commit是同步操作，要在子线程中使用。而apply虽然是在子线程执行的，但是无节制地apply也会造成卡顿，原因是每次有系统消息发生的时候（handleStopActivity，handlePauseActivity）
都会去检查已经提交的apply写操作是否完成，如果没有完成则阻塞主线程。

#### 3.跨进程Binder同步等待返回数据

### CPU时间片

1）其他应用发生抢占CPU资源的情况，导致本应用无法获得CPU执行时间片。

2）线程间发生死锁，UI线程无法获取锁，导致无法继续执行。

3）频繁GC，内存抖动。GC的次数越多，消耗在GC上的时间就越长，CPU花在界面绘制上的时间相应就越短。


### 卡顿监控
#### 消息队列
通过替换Looper中的Printer实现。但是如果在其中拼接了大量的字符串的话，会导致性能损耗严重，引起帧率的降低。

优化方式：可以通过一个监控线程，每个1s向主线程消息队列的头部插入一条空消息。假设1s后这个消息并没有被主线程消费掉，说明阻塞消息运行的时间在0~1s之间。如果我们需要监控3s卡顿，那在第四次轮询中头部消息依然没有被消费的话，
就可以确定主线程出现了一次3s以上的卡顿。
这个方案也存在一定的误差，那就是发送空消息的间隔时间。但这个间隔时间也不能太小，因为监控线程和主线程处理空消息都会带来一些性能损耗，但基本影响不大。
#### 插桩

我们假设一个消息循环里面顺序执行了 A、B、C 三个函数，当整个消息执行超过 3 秒时，因为函数 A 和 B 已经执行完毕，我们只能得到的正在执行的函数 C 的堆栈，事实上它可能并不耗时。

答案是可以的，但是需要使用 Inline Hook 技术。我们可以实现类似 Nanoscope 先写内存的方案，但考虑到兼容性问题，这套方案并没有用到线上。对于大体量的应用，稳定性是第一考虑因素。那如果在编译过程插桩，兼容性问题肯定是 OK 的。
上一讲讲到 systrace 可以通过插桩自动生成 Trace Tag，我们一样也可以在函数入口和出口加入耗时监控的代码，但是需要考虑的细节有很多。避免方法数暴增。在函数的入口和出口应该插入相同的函数，
在编译时提前给代码中每个方法分配一个独立的 ID 作为参数。过滤简单的函数。过滤一些类似直接 return、i++ 这样的简单函数，并且支持黑名单配置。对一些调用非常频繁的函数，需要添加到黑名单中来降低整个方案对性能的损耗。

基于性能的考虑，线上只会监控主线程的耗时。微信的 Matrix 使用的就是这个方案，因为做了大量的优化，所以最终安装包体积只增大 1～2%，平均帧率下降也在 2 帧以内。虽然插桩方案对性能的影响总体还可以接受，但只会在灰度包使用。
插桩方案看起来美好，它也有自己的短板，那就是只能监控应用内自身的函数耗时，无法监控系统的函数调用，整个堆栈看起来好像“缺失了”一部分。
#### 帧率

业界都使用 Choreographer 来监控应用的帧率。跟卡顿不同的是，需要排除掉页面没有操作的情况，我们应该只在界面存在绘制的时候才做统计。那么如何监听界面是否存在绘制行为呢？可以通过 addOnDrawListener 实现。
```
getWindow().getDecorView().getViewTreeObserver().addOnDrawListener
```

我们经常用平均帧率来衡量界面流畅度，但事实上电影的帧率才 24 帧，用户对于应用的平均帧率是 40 帧还是 50 帧并不一定可以感受出来。对于用户来说，感觉最明显的是连续丢帧情况，
Android Vitals 将连续丢帧超过 700 毫秒定义为冻帧，也就是连续丢帧 42 帧以上。

因此，我们可以统计更有价值的冻帧率。冻帧率就是计算发生冻帧时间在所有时间的占比。出现丢帧的时候，我们可以获取当前的页面信息、View 信息和操作路径上报后台，降低二次排查的难度。

#### 生命周期监控
Activity、Service、Receiver 组件生命周期的耗时和调用次数也是我们重点关注的性能问题。例如 Activity 的 onCreate() 不应该超过 1 秒，不然会影响用户看到页面的时间。Service 和 Receiver 虽然是后台组件，
不过它们生命周期也是占用主线程的，也是我们需要关注的问题。


# UI渲染优化

在 Android 系统的 VSYNC 信号到达时，如果 UI 线程被某个耗时任务堵塞，长时间无法对 UI 进行渲染，这时就会出现卡顿。
但是这种情形并不是我们今天讨论的重点，UI 优化要解决的核心是由于渲染性能本身造成用户感知的卡顿，它可以认为是卡顿优化的一个子集。

### UI优化的常用手段
#### 1.尽量使用硬件加速

硬件加速绘制的性能是远远高于软件绘制的。所以说 UI 优化的第一个手段就是保证渲染尽量使用硬件加速。

有哪些情况我们不能使用硬件加速呢？之所以不能使用硬件加速，是因为硬件加速不能支持所有的 Canvas API，具体 API 兼容列表可以见drawing-support文档。如果使用了不支持的 API，系统就需要通过 CPU 软件模拟绘制，
这也是渐变、磨砂、圆角等效果渲染性能比较低的原因。

SVG 也是一个非常典型的例子，SVG 有很多指令硬件加速都不支持。但我们可以用一个取巧的方法，提前将这些 SVG 转换成 Bitmap 缓存起来，这样系统就可以更好地使用硬件加速绘制。
同理，对于其他圆角、渐变等场景，我们也可以改为 Bitmap 实现。

这种取巧方法实现的关键在于如何提前生成 Bitmap，以及 Bitmap 的内存需要如何管理。你可以参考一下市面常用的图片库实现。

#### 2.Create View 优化

请不要忘记，View 的创建也是在 UI 线程里，对于一些非常复杂的界面，这部分的耗时不容忽视。

在优化之前我们先来分解一下 View 创建的耗时，可能会包括各种 XML 的随机读的 I/O 时间、解析 XML 的时间、生成对象的时间（Framework 会大量使用到反射）。

##### 使用代码创建

使用xml进行UI编写是十分方便的，可以在AS中实现实时预览。如果我们要对一个界面进行极致优化，就可以使用代码进行编写界面。

但是这种方式对开发效率来说简直是灾难，因此我们可以使用一些开源的 XML 转换为 Java 代码的工具，例如[X2C](https://github.com/iReaderAndroid/X2C)。但坦白说，还是有不少情况是不支持直接转换的。

所以我们需要兼容性能与开发效率，建议只在对性能要求非常高，但修改又不非常频繁的场景才使用这个方式。

##### 异步创建
那我们能不能在线程提前创建 View，实现 UI 的预加载吗？尝试过的同学都会发现系统会抛出下面这个异常：
```
java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()      
  at android.os.Handler.<init>(Handler.java:121)
```

事实上，我们可以通过又一个非常取巧的方式来实现。在使用线程创建 UI 的时候，先把线程的 Looper 的 MessageQueue 替换成 UI 线程 Looper 的 Queue。

![](https://note.youdao.com/yws/api/personal/file/WEB0f09e1927a1a28f5591aa9b368bd64b6?method=download&shareKey=566800028a0bc6567b7597464e16a77f)

需要注意的是，在创建完View后，我们需要把线程的Looper恢复成原来的。

##### View的复用

#### 3. measure/layout 优化

渲染流程中 measure 和 layout 也是需要 CPU 在主线程执行的，对于这块内容网上有很多优化的文章，一般的常规方法有：

+ 减少 UI 布局层次。例如尽量扁平化，使用<ViewStub> <Merge> 等优化。
+ 优化 layout 的开销。尽量不使用 RelativeLayout 或者基于 weighted LinearLayout，它们 layout 的开销非常巨大。这里我推荐使用 ConstraintLayout 替代 RelativeLayout 或者 weighted LinearLayout。
+ 背景优化。尽量不要重复去设置背景，这里需要注意的是主题背景（theme)， theme 默认会是一个纯色背景，如果我们自定义了界面的背景，那么主题的背景我们来说是无用的。
但是由于主题背景是设置在 DecorView 中，所以这里会带来重复绘制，也会带来绘制性能损耗。

对于 measure 和 layout，我们能不能像 Create View 一样实现线程的预布局呢？这样可以大大地提升首次显示的性能。

Textview 是系统控件中非常强大也非常重要的一个控件，强大的背后就代表着需要做很多计算。在 2018 年的 Google I/O 大会，发布了PrecomputedText并已经集成在 Jetpack 中，
它给我们提供了接口，可以异步进行 measure 和 layout，不必在主线程中执行。

### UI优化的进阶手段


