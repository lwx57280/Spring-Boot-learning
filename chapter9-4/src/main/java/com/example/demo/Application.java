package com.example.demo;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.scheduling.PollerMetadata;

import java.io.File;
import java.io.IOException;

import static java.lang.System.getProperty;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    // 1、通过@Value注解自动获得ttps://spring.io/blog.atom的资源。
    @Value("https://spring.io/blog.atom")
    Resource resource;


    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {        // 2、使用Fluent API和Polles配置默认的轮询方式。
        return Pollers.fixedRate(500).get();
    }

    /**
     * 读取流程
     * 3、FeedEntryMessageSource实际为feed:inbound-channel-adapter，此处即构造feed的入站通道适配器作为数据输入。
     *
     * @return
     * @throws IOException
     */
    public FeedEntryMessageSource feedEntryMessageSource() throws IOException {
        FeedEntryMessageSource messageSource = new FeedEntryMessageSource(this.resource.getURL(), "news");
        return messageSource;
    }

    @Bean
    public IntegrationFlow myFlow() throws IOException {
        // 4、流程从From方法开始。
        return IntegrationFlows.from(feedEntryMessageSource())
                //5、通过路由方法route来选择路由，消息体（payload）的类型为SyndEntry,作为判断条件的类型String，判断的值是通过payload获得分类（Categroy）；
                .<SyndEntry, String>route(payload -> payload.getCategories().get(0).getName(),
                        mapping -> mapping.channelMapping("releases", "releasesChannel")    // 6、通过不同分类的值转向不同的消息通道，若分类为releases,则转向为releasesChannel;
                                // 若分类为engineering,则转向为engineeringChannel;若分类为news,则转向为newsChannel。
                                .channelMapping("engineering", "engineeringChannel")
                                .channelMapping("news", "newsChannel")).get();  // 7、通过get方法获得IntegrationFlow实体，配置为Spring的Bean。
    }

    /**
     * releases流程
     * @return
     */
    @Bean
    public IntegrationFlow releasesFlow() {
        // 1、从消息通道releasesChannel开始获取数据。
        return IntegrationFlows.from(MessageChannels.queue("releasesChannel",10))
                .<SyndEntry,String>transform(payload ->"《" + payload.getTitle() + "》" + payload.getLink() + getProperty("line.separator"))  // 2、使用transform方法进行数据转换。
                // payload类型为SyndEntry,将其转换为字符串类型,并自定义数据的格式。
                    .handle(Files.outboundAdapter(new File("D:/springblog"))    // 3、用户handle方法处理file的出站适配器。Files类是由Spring Integration Java DSL提供的Fluent API用来构造文件输出的适配器。
                            .fileExistsMode(FileExistsMode.APPEND)
                            .charset("UTF-8")
                            .fileNameGenerator(message -> "releases.txt")
                            .get()).get();
    }

    /**
     * engineering流程
     * @return
     */
    @Bean
    public IntegrationFlow engineeringFlow() {
        return IntegrationFlows.from(MessageChannels.queue("engineeringChannel",10))
                .<SyndEntry,String>transform(e -> "《" + e.getTitle() + "》" + e.getLink() + getProperty("line.separator"))
                    .handle(Files.outboundAdapter(new File("D:/springblog"))
                    .fileExistsMode(FileExistsMode.APPEND)
                    .charset("UTF-8")
                    .fileNameGenerator(message->"engineering.txt")
                .get()).get();
    }

    /**
     * news流程
     * @return
     */
    @Bean
    public IntegrationFlow newsFlow() {
        return IntegrationFlows.from(MessageChannels.queue("newsChannel", 10))
                .<SyndEntry,String>transform((payload) -> "《" + payload.getTitle() + "》" + payload.getLink() + getProperty("line.separator"))
                    .enrichHeaders( // 1、通过enrichHeaders来增加消息头的信息。
                            Mail.headers()
                            .subject("来自Spring的新闻")
                            .to(new String[]{"li_congzhi0528@163.com"}) // 2、邮件发送的相关信息通过Spring Integration Java DSL提供的Mail的headers方法来构造。
                            .from("li_congzhi0528@163.com"))
                .handle(Mail.outboundAdapter("smtp.163.com")        // 3、使用handle方法来定义邮件发送的出站适配器，使用Spring Integration Java DSL提供的Mail.outboundAdapter来构造，这里使用li_congzhi0528@163.com邮箱向自己发送邮件。
                .port(25)
                .protocol("smtp")
                .credentials("li_congzhi0528@163.com", "*******")
                .javaMailProperties(p -> p.put("mail.debug", "false")),e -> e.id("smtpOut"))
                .get();
    }
}
