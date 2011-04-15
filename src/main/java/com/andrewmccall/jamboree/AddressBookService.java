package com.andrewmccall.jamboree;


import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AddressBookService {

    void delete (long id);

    void add(Person person);

    void update(Person person);

    Person get(long id);

    Collection<Person> getAll();

}
