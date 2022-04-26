package com.example.brickscrapper.config;

import com.example.brickscrapper.model.TokopediaProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.lang.reflect.Field;
import java.util.Arrays;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final ScrapperConfiguration scrapperConfiguration;

    @Bean
    public FlatFileItemWriter<TokopediaProduct> tokopediaProductWriter() {
        return new FlatFileItemWriterBuilder<TokopediaProduct>()
                .name("tokopediaProductWriter")
                .resource(new FileSystemResource(scrapperConfiguration.getTokopedia().getProduct().getFileOutputPath()))
                .delimited()
                .fieldExtractor(new BeanWrapperFieldExtractor<TokopediaProduct>() {{
                    setNames(Arrays.stream(TokopediaProduct.class.getDeclaredFields())
                            .map(Field::getName).toArray(String[]::new));
                }})
                .build();
    }
}
