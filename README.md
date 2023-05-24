# kafkaproj-study

kafkaproj 프로젝트의 카피 프로젝트로써 공부하기위해 만들어둠.  

## 실행방법

bitnami kafka 를 이용해서 테스트했으며 [이쪽](https://hub.docker.com/r/bitnami/kafka)을 클릭하여 참고하면됨.  
아래와 같이 `docker-compose.yml` 파일을 만들어주고나서 실행을 시켜준다.  
```yaml
version: "2"

services:
  zookeeper:
    image: docker.io/bitnami/zookeeper:3.8
    ports:
      - "2181:2181"
    volumes:
      - "zookeeper_data:/bitnami"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: docker.io/bitnami/kafka:3.1
    hostname: kafka
    ports:
      - "9092:9092"
    volumes:
      - "kafka_data:/bitnami"
    environment:
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092
    depends_on:
      - zookeeper

volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
```

컨테이너안에서 테스트할때는 쉘스크립트가 있는 위지는 아래와 같다.  
```shell
/opt/bitnami/kafka/bin/*
```

주로 테스트할때 사용했던 커맨드는 아래와 같다.  
```shell
# 메시지 생성 
kafka-console-producer.sh --topic simple-topic --bootstrap-server kafka:9092

# 메시지 처음부터 읽기 
kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic simple-topic --from-beginning
```

추가로 사용하고있는 `topic` 는 아래와 같이 미리 선언해둬야한다.  
```shell
kafka-topics.sh --bootstrap-server kafka:9092 --create --topic multipart-topic --partitions 3
```

## consumer

특정 파티션의 특정 offset 부터 읽고싶다고 할때 아래를 참고하면 된다.  
```kotlin
// 0번 파티션 리슨 
val topicPartition = TopicPartition(topicName, 0)
kafkaConsumer.assign(listOf(topicPartition))

// offset = 100 에 해당하는곳부터 읽는데 만약 그것보다 적은 데이터가 적재되었을경우 처음부터 읽는다 
kafkaConsumer.seek(topicPartition, 100L)
```

## 기타

1. [confluentinc/cp-docker-images](https://github.com/confluentinc/cp-docker-images/wiki/Getting-Started)
  * 일부 옵션들이 다르지만 해당 이미지를 사용하여 작업할수도있음.
