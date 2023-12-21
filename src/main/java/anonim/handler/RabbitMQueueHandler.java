package anonim.handler;

import anonim.config.RabbitMqConfig;
import anonim.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQueueHandler {

    private final MessageRepo messageRepo;

    @RabbitListener(queues = RabbitMqConfig.MEDIA_DOWNLOAD_QUEUE)
    public void downloadAndSaveFile() {

    }
}
