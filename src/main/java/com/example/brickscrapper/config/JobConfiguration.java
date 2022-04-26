package com.example.brickscrapper.config;

import com.example.brickscrapper.tasklet.TokopediaScrapperTasklet;
import com.example.brickscrapper.tasklet.TokopediaWriterTasklet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final TokopediaScrapperTasklet tokopediaScrapperTasklet;
    private final TokopediaWriterTasklet tokopediaWriterTasklet;

    @Bean
    public Job listTokopediaTopProductsJob() {
        return jobBuilderFactory.get("listTokopediaTopProductsJob")
                .incrementer(new RunIdIncrementer())
                .start(scrappingTokopediaWebStep())
                .next(writingTokopediaProductStep())
                .build();
    }

    @Bean
    public Step scrappingTokopediaWebStep() {
        return stepBuilderFactory.get("scrappingTokopediaWebStep")
                .tasklet(tokopediaScrapperTasklet)
                .build();
    }

    @Bean Step writingTokopediaProductStep() {
        return stepBuilderFactory.get("writingTokopediaProductStep")
                .tasklet(tokopediaWriterTasklet)
                .build();
    }

}
