from("kafka:{{consumer.topic}}?brokers={{kafka.host}}:{{kafka.port}}"
        + "&maxPollRecords={{consumer.maxPollRecords}}"
        + "&consumersCount={{consumer.consumersCount}}"
        + "&seekTo={{consumer.seekTo}}"
        + "&groupId={{consumer.group}}")
        .routeId("FromKafka")
        .to('log:info')
        .convertBodyTo(String.class, "UTF-8").unmarshal().json(org.apache.camel.model.dataformat.JsonLibrary.Jackson)
        .process {
            def body = it.in.body
            it.out.body = [success: true, message: body, "msg":"Validação ok"]
        }
        .to('log:info')