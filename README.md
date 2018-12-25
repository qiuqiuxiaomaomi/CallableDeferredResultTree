#CallableDeferredResultTree
Spring异步技术研究

<pre>
背景：

         Tomcat等应用服务器的连接线程池实际上是有限制的；每一个连接请求都会耗掉线程池的一个连接数；如果某些耗时很长的操作，如
      对大量数据的查询操作，条用外部系统提供的服务区以及一些IO密集型操作等，会占用连接很长时间，这个时候这个连接就无法被释放而被
      其他请求重用。如果连接占用过多，服务器就很可能无法及时响应每个请求；极端情况下如果将线程池的所有链接耗尽，服务器将长时间
      无法向外提供服务。

         在常规场景中，客户端需要等待服务器处理完毕后才能继续其他操作，这个场景下每一步都是同步调用，如客户端调用Servlet后需要
      等待其处理返回，Servlet调用具体的Controller后也需要等待其返回。这种情况是在服务器端开发中最常见的场景，适合于服务器端处理
      时间不是很长的情况；默认情况下，Spring的Controller提供的就是这样的服务。

         当某项服务处理时间过长时，如邮件发送，需要调用外部接口，处理时间不受调用方控制，因此如果耗时过长会有两个比较严重的后果：
             1）一是如上文所说的会长时间占用请求连接数，严重时有可能导致服务器失去响应；
             2）二是客户端等待时间过长，导致前端应用的用户友好性下架，而且客户很有可能因为长时间得不到服务器响应而重复操作，从而
               家中服务器的负担，使得应用的崩溃的几率较大。

         为应对这种场景，一般会启用一个后台的线程池，处理请求的Controller会先提交一个好事长操作如发邮件发送到线程池中，然后立即
      返回到前端。因此处理响应的主线程耗时变短，客户端感受到的就是在点击某个发送按钮后很快就得到服务器反馈结果，然后就放心的继续处理
      其他操作。实际上邮件发送这种事情延迟几秒对于客户来说根本感受不到。当然应用需要保证提交到线程池中的任务执行成功，或者是执行
      失败后再前端某个地方能够看到失败的情况。
</pre>

###FutureTask/Future/Runnable区别与联系

![](https://i.imgur.com/m21X3xk.png)

<pre>
使用场景：

      加入有这样的场景，我们现在需要计算一个数据，而这个数据的计算比较耗时，而我们后面的程序也要用到这个数据结果，那么这个
      时Callable岂不是最好的选择？我们可以开设一个线程去执行计算，而主线程继续做其他事，而后面需要使用到这个数据时，我们再
      使用Future获取不就可以了吗？
</pre>

<pre>
Callable处理流程：

     客户端请求服务
        1）SpringMVC调用Controller，Controller返回一个Callback对象
        2）SpringMVC调用ruquest.startAsync并且将Callback提交到TaskExecutor中去执行
        3）DispatcherServlet以及Filters等从应用服务器线程中结束，但Response仍旧是打开状态，也就是说暂时还不返回给客户端
        5）TaskExecutor调用Callback返回一个结果，SpringMVC将请求发送给应用服务器继续处理
        6）DispatcherServlet再次被调用并且继续处理Callback返回的对象，最终将其返回给客户端
</pre>

<pre>
Future

      Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果等操作。必要时可以通过get方法获取
      执行结果，该方法会阻塞直到任务返回结果.
</pre>

<pre>
FutureTask

</pre>

<pre>
DeferredResult

         DeferredResult使用方式与Callable类似，但是在返回结果上不一样，它返回的时候实际结果可能没有生成，实际的结果可能会在另外
      的线程里面设置到DeferredResult中去。

      该类包含的一下日常使用相关的特性：
         1）超时配置
            通过构造函数可以传入超时时间，单位为ms，因为需要等待设置结果后才能继续处理并返回给客户端，如果一直等待会导致客户端
         一直无响应，因此必须有相同的超时机制来避免这个问题；实际上就算不设置这个超时时间，应用服务器或者Spring也会有一些默认
         的超时机制来处理这个问题。

         2）结果设置
            它的结果存储在一个名称为result的属性中，可以通过调用setResult的方法来设置属性，由于这个DeferredResult天生就是
        使用在多线程中的，因此对这个result属性的读写操作是有锁的。

      DeferredResult处理流程：

          1）客户端请求
          2）SpringMVC调用Controller， Controller返回一个DeferredResult对象。
          3）SpringMVC调用request.startAsync
          5）DispatchServlet以及Filter等从应用服务器结束，但response仍旧是结束，也就是说暂时还不返回给客户端。
          6）某些其他线程将结果设置到DeferredResult中，SpringMVC将请求发送给应用服务器继续处理。
          7）DispatchServlet再次被调用并且继续处理DeferredResult中的结果，最终将其返回给客户端。
</pre>

<pre>
SseEmitter
</pre>

<pre>
AsyncContext
</pre>