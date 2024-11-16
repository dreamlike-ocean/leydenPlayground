rm -rf spring-aot.aotconf spring.aot leyden-spring-0.0.1
source "/home/dreamlike/.sdkman/bin/sdkman-init.sh"
sdk use java graal23
export JAVA_TOOL_OPTIONS="--enable-preview"
mvn clean package -Djava.version=23 -am -pl leyden-spring
unset JAVA_TOOL_OPTIONS
java -Djarmode=tools -jar leyden-spring/target/leyden-spring-0.0.1.jar extract
export printLog=$1

#java -Dspring.aot.enabled=true -jar leyden-spring/target/leyden-spring-0.0.1.jar
# 切换到jdk24进行训练
sdk use java jdk-ea

echo "AOT Record"
java -Xlog:cds -XX:+AOTClassLinking -XX:AOTMode=record -XX:AOTConfiguration=spring-aot.aotconf -Dspring.aot.enabled=true -jar leyden-spring-0.0.1/leyden-spring-0.0.1.jar 
echo "AOT create!"
java -Xlog:cds -XX:+AOTClassLinking -XX:AOTMode=create -XX:AOTConfiguration=spring-aot.aotconf -XX:AOTCache=spring.aot -Dspring.aot.enabled=true -jar leyden-spring-0.0.1/leyden-spring-0.0.1.jar 

echo "Use aot and extract and Spring AOT"
time java -XX:+AOTClassLinking -XX:AOTCache=spring.aot -Dspring.aot.enabled=true -jar leyden-spring-0.0.1/leyden-spring-0.0.1.jar 

echo "Use extract and Spring AOT"
time java -Dspring.aot.enabled=true -jar leyden-spring-0.0.1/leyden-spring-0.0.1.jar 

echo "Use FatJar"
time java -jar leyden-spring/target/leyden-spring-0.0.1.jar 