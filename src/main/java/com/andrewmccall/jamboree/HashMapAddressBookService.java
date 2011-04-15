package com.andrewmccall.jamboree;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@Repository
public class HashMapAddressBookService implements AddressBookService {

    private long id = 1;

    private HashMap<Long, Person> database = new HashMap<Long, Person>();

    @Override
    public void delete(long id) {
        System.out.println("Deelete. " + id);
        database.remove(database.get(id));
    }

    @Override
    public void add(Person person) {
        if (person.id == 0) {
            person.setId(id++);
            database.put(person.id, person);
        }
    }

    @Override
    public void update(Person person) {
        database.put(person.id, person);
    }

    @Override
    public Person get(long id) {
        return database.get(id);
    }

    @Override
    public Collection<Person> getAll() {
        return database.values();
    }
}
