package io.amplicode.spring.jdbc;

import io.amplicode.spring.jdbc.service.owner.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JooqOwnerServiceTest extends OwnerServiceTest {

    @Autowired
    @Qualifier("jooqOwnerService")
    private OwnerService ownerService;

    @Override
    protected OwnerService getOwnerRepository() {
        return ownerService;
    }
}
