# jpetstore-msa-items

> jpetstore item api
> 

## Production Mode
```
mvn clean install -DskipTests 
java -jar ./target/item-0.0.1-SNAPSHOT.jar
```

## Production Mode
```
mvn clean install -DskipTests
export JAVA_OPTS="-Dspring.profiles.active=prod -Dinventory.endpoint=https://localhost:8080/inventories -Ddb.url=jdbc:mysql://localhost:3306/jpetstore -Ddb.username=petstore -Ddb.password=test123# -Ddb.classname=com.mysql.cj.jdbc.Driver"
java -jar ${JAVA_OPTS} ./target/item-0.0.1-SNAPSHOT.jar
```

## Test
```
curl http://localhost:8080/items
curl http://localhost:8080/items/EST-2
```

## swagger
```
curl http://localhost:8080/swagger.html 
```