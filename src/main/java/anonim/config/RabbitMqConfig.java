package anonim.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    public static final String MEDIA_DOWNLOAD_ROUTING = "media_download.routing";
    public static final String MEDIA_DOWNLOAD_QUEUE = "media_download.queue";

    @Bean
    Queue watermarkQueue() {
        return new Queue(MEDIA_DOWNLOAD_QUEUE, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    Binding watermarkBinding(Queue watermarkQueue, DirectExchange exchange) {
        return BindingBuilder.bind(watermarkQueue)
            .to(exchange)
            .with(MEDIA_DOWNLOAD_ROUTING);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate template(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
