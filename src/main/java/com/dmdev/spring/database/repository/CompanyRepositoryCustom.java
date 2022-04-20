package com.dmdev.spring.database.repository;

import com.dmdev.spring.bpp.Auditing;
import com.dmdev.spring.bpp.Transaction;
import com.dmdev.spring.database.entity.Company;
import com.dmdev.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
@Transaction //custom annotation
@Auditing //custom annotation
@RequiredArgsConstructor
public class CompanyRepositoryCustom implements CrudRepository<Integer, Company> {

//    @InjectBean
//    @Resource(name = "pool1")
    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    @Value("${db.pool.size}")
    private final Integer poolSize;

//    public CompanyRepository(ConnectionPool pool1,
//                             List<ConnectionPool> pools,
//                             @Value("${db.pool.size}") Integer poolSize) {
//        this.pool1 = pool1;
//        this.pools = pools;
//        this.poolSize = poolSize;
//    }

    @PostConstruct
    private void init() {
        log.warn("Init company repository ...");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("Find by id method ... ");
        return Optional.of(new Company(id, null, Collections.emptyMap()));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("Delete method ... ");
    }

    //    public CompanyRepository(ConnectionPool connectionPool) {
//        this.connectionPool = connectionPool;
//    }
//
//    public static CompanyRepository of(ConnectionPool connectionPool) {
//        return new CompanyRepository(connectionPool);
//    }
}
