打包和生成过程

使用oracle graalvm 22进行编译和native-image 再使用leyden进行训练

mvn clean package -am -pl leyden-simple  -Pnative-release

测试结果：
```
➜  leydenPlayground git:(master) ✗ java -XX:CacheDataStore=simple-leyden.cds -XX:+LoadCachedCode -jar leyden-simple/target/leyden-simple-fat.jar
12123
12123
class io.github.dreamlike.DynamicEntryLeydenCase Time: 8187ms
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 0ms
load success 3099
load fail 21
class io.github.dreamlike.ClassforNameCase Time: 7ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 2163ms
HeavyClassInitCase init Time: 4069ms
class io.github.dreamlike.HeavyClassInitCase Time: 0ms
class io.github.dreamlike.JdkProxyLeydenCasae Time: 1ms
class io.github.dreamlike.JdkCaptureProxyLeydenCase Time: 0ms
class io.github.dreamlike.ALotLambdaLeydenCase Time: 1ms

➜  leydenPlayground git:(master) ✗ leyden-simple/target/leyden-simple 
12123
12123
class io.github.dreamlike.DynamicEntryLeydenCase Time: 17899ms
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 0ms
load success 321
load fail 2799
class io.github.dreamlike.ClassforNameCase Time: 1ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 2366ms
class io.github.dreamlike.HeavyClassInitCase Time: 0ms
class io.github.dreamlike.JdkProxyLeydenCasae Time: 0ms
class io.github.dreamlike.JdkCaptureProxyLeydenCase Time: 0ms
class io.github.dreamlike.ALotLambdaLeydenCase Time: 0ms

➜  leydenPlayground git:(master) ✗ java -jar leyden-simple/target/leyden-simple-fat.jar                                                         
12123
12123
class io.github.dreamlike.DynamicEntryLeydenCase Time: 16009ms
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 0ms
load success 3099
load fail 21
class io.github.dreamlike.ClassforNameCase Time: 254ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 2195ms
HeavyClassInitCase init Time: 7957ms
class io.github.dreamlike.HeavyClassInitCase Time: 0ms
class io.github.dreamlike.JdkProxyLeydenCasae Time: 6ms
class io.github.dreamlike.JdkCaptureProxyLeydenCase Time: 2ms
class io.github.dreamlike.ALotLambdaLeydenCase Time: 32ms

➜  leydenPlayground mvn -v                                                                                                        
Apache Maven 3.9.4 (dfbb324ad4a7c8fb0bf182e6d91b0ae20e3d2dd9)
Maven home: /home/dreamlike/.sdkman/candidates/maven/current
Java version: 24-leydenpremain, vendor: Oracle Corporation, runtime: /home/dreamlike/jdks/leyden-jdk-24
Default locale: zh_CN, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-31-generic", arch: "amd64", family: "unix"

```