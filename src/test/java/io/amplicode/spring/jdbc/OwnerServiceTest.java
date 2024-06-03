package io.amplicode.spring.jdbc;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import io.amplicode.spring.jdbc.model.owner.OwnerWithPets;
import io.amplicode.spring.jdbc.service.owner.OwnerFilter;
import io.amplicode.spring.jdbc.service.owner.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class OwnerServiceTest {

	protected abstract OwnerService getOwnerRepository();
	
	@Test
	void findAllQueryAndSort() {
		OwnerFilter filter = new OwnerFilter("ro", null, null);
		Sort sort = Sort.by(Sort.Order.by("city"));
		PageRequest pageable = PageRequest.of(0, 20, sort);
		List<OwnerBase> owners = getOwnerRepository().findAll(filter, pageable);

		assertEquals(3, owners.size());
		assertEquals("David", owners.get(0).getFirstName());
		assertEquals("Eduardo", owners.get(1).getFirstName());
		assertEquals("Harold", owners.get(2).getFirstName());
	}

	@Test
	void findAllWithPetsQueryAndSort() {
		OwnerFilter filter = new OwnerFilter("ro", null, null);
		Sort sort = Sort.by(Sort.Order.by("city"));
		PageRequest pageable = PageRequest.of(0, 20, sort);
		List<OwnerWithPets> owners = getOwnerRepository().findAllWithPets(filter, pageable);

		assertEquals(3, owners.size());
		OwnerWithPets ownerDavid = owners.get(0);
		assertEquals("David", ownerDavid.getFirstName());
		assertEquals(1, ownerDavid.getPets().size());
		assertEquals("Freddy", ownerDavid.getPets().get(0).getName());

		OwnerWithPets ownerEduardo = owners.get(1);
		assertEquals("Eduardo", ownerEduardo.getFirstName());
		assertEquals(2, ownerEduardo.getPets().size());
		assertEquals("Jewel", ownerEduardo.getPets().get(0).getName());
		assertEquals("Rosy", ownerEduardo.getPets().get(1).getName());

		OwnerWithPets ownerHarold = owners.get(2);
		assertEquals("Harold", ownerHarold.getFirstName());
		assertEquals(1, ownerHarold.getPets().size());
		assertEquals("Iggy", ownerHarold.getPets().get(0).getName());
	}

	@Test
	void findAllFilterByQueryAll() {
		OwnerFilter filter = new OwnerFilter("ro", "mad", "6085559435");
		PageRequest pageable = PageRequest.of(0, 20);
		List<OwnerBase> owners = getOwnerRepository().findAll(filter, pageable);

		assertEquals(1, owners.size());
		assertEquals("David", owners.get(0).getFirstName());
	}

	@Test
	void findAllSize5Page2SortName() {
		OwnerFilter filter = new OwnerFilter(null, null, null);
		PageRequest pageable = PageRequest.of(1, 5, Sort.by("firstName"));
		List<OwnerBase> owners = getOwnerRepository().findAll(filter, pageable);

		assertEquals(5, owners.size());
		assertEquals("Harold", owners.get(0).getFirstName());
		assertEquals("Jean", owners.get(1).getFirstName());
		assertEquals("Jeff", owners.get(2).getFirstName());
		assertEquals("Maria", owners.get(3).getFirstName());
		assertEquals("Peter", owners.get(4).getFirstName());
	}

	@Test
	void findAllById() {
		OwnerFilter filter = new OwnerFilter(null, null, null);
		PageRequest pageable = PageRequest.of(1, 5, Sort.by("firstName"));
		List<OwnerBase> owners = getOwnerRepository().findAll(filter, pageable);

		assertEquals(5, owners.size());
		assertEquals("Harold", owners.get(0).getFirstName());
		assertEquals("Jean", owners.get(1).getFirstName());
		assertEquals("Jeff", owners.get(2).getFirstName());
		assertEquals("Maria", owners.get(3).getFirstName());
		assertEquals("Peter", owners.get(4).getFirstName());
	}


	@Test
	void getOne() {
		OwnerBase owner = getOwnerRepository().findById(3).orElse(null);

		assertNotNull(owner);
		assertEquals("Eduardo", owner.getFirstName());
		assertEquals("Rodriquez", owner.getLastName());
		assertEquals("2693 Commerce St.", owner.getAddress());
		assertEquals("McFarland", owner.getCity());
		assertEquals("6085558763", owner.getTelephone());
	}

	@Test
	void getOneNotFound() {
		OwnerBase owner = getOwnerRepository().findById(Integer.MAX_VALUE).orElse(null);
		assertNull(owner);
	}

	@Test
	void getMany() {
		List<OwnerMinimal> owners = getOwnerRepository().findAllById(List.of(1, 3, 5, 7));
		assertEquals(4, owners.size());
		assertEquals("George", owners.get(0).getFirstName());
		assertEquals("Eduardo", owners.get(1).getFirstName());
		assertEquals("Peter", owners.get(2).getFirstName());
		assertEquals("Jeff", owners.get(3).getFirstName());
	}

	@Test
	public void createAndDelete() throws Exception {
		OwnerBase owner = new OwnerBase();
		owner.setFirstName("Ivan");
		owner.setLastName("Ivanov");
		owner.setAddress("Gastelo 43a");
		owner.setCity("Samara");
		owner.setTelephone("9271111111");

		OwnerBase savedOwner = getOwnerRepository().save(owner);
		assertNotNull(savedOwner);
		assertEquals("Ivan", savedOwner.getFirstName());
		assertEquals("Ivanov", savedOwner.getLastName());
		assertEquals("Gastelo 43a", savedOwner.getAddress());
		assertEquals("Samara", savedOwner.getCity());
		assertEquals("9271111111", savedOwner.getTelephone());

		getOwnerRepository().deleteById(savedOwner.getId());
	}

	@Test
	public void purePut() {
		OwnerBase owner = new OwnerBase();
		owner.setFirstName("Alex");
		owner.setLastName("Alexov");
		owner.setAddress("Gastelo");
		owner.setCity("Samara");
		owner.setTelephone("9271112233");

		OwnerBase savedOwner = getOwnerRepository().save(owner);

		savedOwner.setLastName("Ivanov");
		savedOwner.setAddress("Gastelo 43a");
		getOwnerRepository().save(savedOwner);

		getOwnerRepository().deleteById(savedOwner.getId());
	}

	@Test
	public void deleteMany() {
		OwnerBase alex = new OwnerBase();
		alex.setFirstName("Alex");
		alex.setLastName("Alexov");
		alex.setAddress("Gastelo");
		alex.setCity("Samara");
		alex.setTelephone("9271112233");
		OwnerBase savedAlex = getOwnerRepository().save(alex);

		OwnerBase ivan = new OwnerBase();
		ivan.setFirstName("Ivan");
		ivan.setLastName("Ivanov");
		ivan.setAddress("My Address");
		ivan.setCity("My City");
		ivan.setTelephone("0001112233");
		OwnerBase savedIvan = getOwnerRepository().save(ivan);

		List<Integer> toDeleteIds = List.of(savedAlex.getId(), savedIvan.getId());
		getOwnerRepository().deleteAllById(toDeleteIds);

		List<OwnerMinimal> owners = getOwnerRepository().findAllById(toDeleteIds);
		assertTrue(owners.isEmpty());
	}
}
