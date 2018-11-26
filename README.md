安装好Maven Java Git等环境后

进入某个目录执行：
git clone https://github.com/lazy-share/lazy-falcon

用eclipse 或idea打开该项目,配置自己常用的Maven私服settings.xml配置文件，减少下载等待时间

执行lazy-falcon项目clean install

执行lazy-falcon-example项目clean package

找到主类：com.lazy.falcon.example.GendocMavenPluginExampleApplication并执行main方法以启动SpringBoot项目
