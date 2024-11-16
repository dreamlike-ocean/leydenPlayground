echo "Maven Build"
mvn clean package -am -pl leyden-simple
echo "AOT Record"
java -Xlog:cds -XX:+AOTClassLinking -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf -jar leyden-simple/target/leyden-simple-fat.jar $1
echo "AOT create!"
java -Xlog:cds -XX:+AOTClassLinking -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf -XX:AOTCache=app.aot -jar leyden-simple/target/leyden-simple-fat.jar $1

echo "AOT load!"
java  -XX:+AOTClassLinking -XX:AOTCache=app.aot -jar leyden-simple/target/leyden-simple-fat.jar $1

echo "Without Aot"
java  -jar leyden-simple/target/leyden-simple-fat.jar $1