package com.andrewmccall.jamboree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/addressbook")
public class AddressBookController {

    @Resource
    private AddressBookService service;

    @RequestMapping(value = "/person/{id}/", method = RequestMethod.DELETE)
    public void  delete(@PathVariable long id) {
        service.delete(id);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public @ResponseBody Person add(@RequestBody Person person) {
        service.add(person);
        return person;
    }

    @RequestMapping(value = "/person/{id}/", method = RequestMethod.POST)
    public @ResponseBody Person update(@RequestBody Person person) {
        service.update(person);
        return person;
    }


    @RequestMapping(value = "/person/{id}/", method = GET)
    public @ResponseBody Person get(@PathVariable long id) {
        return service.get(id);
    }

    @RequestMapping(method = GET)
    public @ResponseBody Collection<Person> getAll() {
        return service.getAll();
    }



}
