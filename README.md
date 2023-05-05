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