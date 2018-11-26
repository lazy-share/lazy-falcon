该项目基于注解或JSON配置文件方式为Controller自动生成API，前后端分离后为减少接口沟通成本而开发和设计；
目前市面上有swagger rap等类似的框架；


安装好Maven Java Git等环境后

进入某个目录执行：
git clone https://github.com/lazy-share/lazy-falcon

用eclipse 或idea打开该项目,配置自己常用的Maven私服settings.xml配置文件，减少下载等待时间

执行lazy-falcon项目clean install

执行lazy-falcon-example项目clean package

找到主类：com.lazy.falcon.example.GendocMavenPluginExampleApplication并执行main方法以启动SpringBoot项目

启动后访问地址：http://127.0.0.1:8080/api_doc/index.html

如果访问失败，查看target/classes/static/下是否有api_doc目录，没有的话执行lazy-falcon-example项目clean package后再启动

具体使用方式请细看lazy-falcon-example项目的例子；



