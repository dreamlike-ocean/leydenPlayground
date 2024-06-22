打包和生成过程

mvn clean package && java -XX:CacheDataStore=simple-leyden.cds -jar leyden-simple/target/leyden-simple-fat.jar

测试结果：
```
➜  leydenPlayground java -jar leyden-simple/target/leyden-simple-fat.jar
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 0ms
load success 3077
load fail 43
class io.github.dreamlike.ClassforNameCase Time: 239ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 2025ms
HeavyClassInitCase init Time: 7876ms

➜  leydenPlayground java -XX:CacheDataStore=simple-leyden.cds -XX:+LoadCachedCode -jar leyden-simple/target/leyden-simple-fat.jar
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 0ms
load success 3077
load fail 43
class io.github.dreamlike.ClassforNameCase Time: 11ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 2241ms
HeavyClassInitCase init Time: 4059ms

➜  leydenPlayground java -XX:CacheDataStore=simple-leyden.cds -XX:-LoadCachedCode -jar leyden-simple/target/leyden-simple-fat.jar 
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 1ms
load success 3077
load fail 43
class io.github.dreamlike.ClassforNameCase Time: 14ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 1925ms
HeavyClassInitCase init Time: 4067ms


➜  leydenPlayground java -XX:CacheDataStore=simple-leyden.cds -XX:-LoadCachedCode -jar leyden-simple/target/leyden-simple-fat.jar
Hello world!
class io.github.dreamlike.HelloWorldCase Time: 0ms
load success 3077
load fail 43
class io.github.dreamlike.ClassforNameCase Time: 20ms
3.1416026534897203
class io.github.dreamlike.LeibnizCase Time: 2111ms
HeavyClassInitCase init Time: 4078ms

➜  leydenPlayground mvn -v                                                                                                        
Apache Maven 3.9.4 (dfbb324ad4a7c8fb0bf182e6d91b0ae20e3d2dd9)
Maven home: /home/dreamlike/.sdkman/candidates/maven/current
Java version: 24-leydenpremain, vendor: Oracle Corporation, runtime: /home/dreamlike/jdks/leyden-jdk-24
Default locale: zh_CN, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-31-generic", arch: "amd64", family: "unix"

```