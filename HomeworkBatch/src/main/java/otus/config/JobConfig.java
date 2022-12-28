package otus.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import otus.model.h2.AuthorH2;
import otus.model.h2.BookH2;
import otus.model.h2.CommentH2;
import otus.model.h2.GenreH2;
import otus.model.mongo.Author;
import otus.model.mongo.Book;
import otus.model.mongo.Comment;
import otus.model.mongo.Genre;
import otus.service.MigrationService;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 3;

    public static final String MIGRATE = "migrate";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @StepScope
    @Bean
    public MongoItemReader<Genre> genreReader() {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .template(mongoTemplate)
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .query(new Query())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> commentReader() {
        return new MongoItemReaderBuilder<Comment>()
                .name("commentItemReader")
                .template(mongoTemplate)
                .targetType(Comment.class)
                .sorts(new HashMap<>())
                .query(new Query())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Book> bookReader() {
        return new MongoItemReaderBuilder<Book>()
                .name("bookItemReader")
                .template(mongoTemplate)
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .query(new Query())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Author> authorReader() {
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReader")
                .template(mongoTemplate)
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .query(new Query())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreH2> genreProcessor(MigrationService ms) {
        return ms::genreProcessor;
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, CommentH2> commentProcessor(MigrationService ms) {
        return ms::commentProcessor;
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorH2> authorProcessor(MigrationService ms) {
        return ms::authorProcessor;
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookH2> bookProcessor(MigrationService ms) {
        return ms::bookProcessor;
    }

    @StepScope
    @Bean
    public JpaItemWriter<GenreH2> genreWriter(EntityManagerFactory entityManager) {
        return new JpaItemWriterBuilder<GenreH2>()
                .entityManagerFactory(entityManager)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<CommentH2> commentWriter(EntityManagerFactory entityManager) {
        return new JpaItemWriterBuilder<CommentH2>()
                .entityManagerFactory(entityManager)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<BookH2> bookWriter(EntityManagerFactory entityManager) {
        return new JpaItemWriterBuilder<BookH2>()
                .entityManagerFactory(entityManager)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<AuthorH2> authorWriter(EntityManagerFactory entityManager) {
        return new JpaItemWriterBuilder<AuthorH2>()
                .entityManagerFactory(entityManager)
                .build();
    }

    @Bean
    public Job migrateDbJob(Step transformGenreStep, Step transformAuthorStep,
                            Step transformCommentStep, Step transformBookStep) {
        return jobBuilderFactory.get(MIGRATE)
                .incrementer(new RunIdIncrementer())
                .flow(transformGenreStep)
                .next(transformAuthorStep)
                .next(transformBookStep)
                .next(transformCommentStep)
                .end()
                .build();
    }

    @Bean
    public Step transformGenreStep(ItemReader<Genre> reader, JpaItemWriter<GenreH2> writer,
                                   ItemProcessor<Genre, GenreH2> itemProcessor) {
        return stepBuilderFactory.get("migrate Genre")
                .<Genre, GenreH2>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformAuthorStep(ItemReader<Author> reader, JpaItemWriter<AuthorH2> writer,
                                    ItemProcessor<Author, AuthorH2> itemProcessor) {
        return stepBuilderFactory.get("migrate Author")
                .<Author, AuthorH2>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBookStep(ItemReader<Book> reader, JpaItemWriter<BookH2> writer,
                                  ItemProcessor<Book, BookH2> itemProcessor) {
        return stepBuilderFactory.get("migrate Book")
                .<Book, BookH2>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformCommentStep(ItemReader<Comment> reader, JpaItemWriter<CommentH2> writer,
                                     ItemProcessor<Comment, CommentH2> itemProcessor) {
        return stepBuilderFactory.get("migrate Comments")
                .<Comment, CommentH2>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}