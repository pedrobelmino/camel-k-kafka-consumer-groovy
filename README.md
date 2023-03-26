LAB para uso do Kamel K - Kubernetes Kind + Groovy Script + Kafka + Strimzi(kafka) 
============================

**Pré-requisitos**
- Máquina EC2 AWS
- Docker Engine
- Kubernetes Kind - https://camel.apache.org/camel-k/1.12.x/installation/platform/kind.html
- Camel K Cli - https://camel.apache.org/camel-k/1.12.x/cli/cli.html
- Strimzi - Kafka Kubernetes - https://strimzi.io/quickstarts/

**Recuperação do ip referente ao cluster kafka instalado através do Strimzi**
```bash
kafkaip=`kubectl get svc/my-cluster-kafka-bootstrap -n kafka -ojsonpath="{.spec.clusterIP}"`; sed -i "/kafka\.host/s/<.*>/$kafkaip/g" application.properties
```

**Criação de configmap com configurações de acesso ao cluster kubernetes**
```bash
kubectl create configmap kafka.props  --from-file=src/main/resources/application.properties
```

**Execução da integração Camel K**
```bash
kamel run src/main/java/SampleKafkaConsumer.groovy --config=configmap:kafka.props --dev
```

**Execução do Kafka Producer**
```bash
kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.30.0-kafka-3.2.0 --rm --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic
```

**Frase motivacional: Estude e nunca pare! Você continuará bom!**


**Referências**
- https://camel.apache.org/camel-k/1.12.x/installation/platform/kind.html
- https://github.com/apache/camel-k-examples/tree/main/generic-examples/kafka
- https://camel.apache.org/camel-k/1.12.x/cli/cli.html
- https://strimzi.io/quickstarts/