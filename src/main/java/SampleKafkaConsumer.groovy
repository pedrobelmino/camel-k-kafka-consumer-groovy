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
            def idade = it.in.body["idade"]
            if(idade < 18){
                it.out.body = [success: true, message: body]
            }else{
                it.out.body = [success: false, message: body, msg:"É necessário ter pelo menos 18 anos"]
            }
        }
        .to('log:info')