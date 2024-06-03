package io.amplicode.spring.jdbc;

import io.amplicode.spring.jdbc.service.owner.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RelationalCoreOwnerServiceTest extends OwnerServiceTest {

    @Autowired
    @Qualifier("relationalCoreOwnerService")
    private OwnerService ownerService;

    @Override
    protected OwnerService getOwnerRepository() {
        return ownerService;
    }
}
