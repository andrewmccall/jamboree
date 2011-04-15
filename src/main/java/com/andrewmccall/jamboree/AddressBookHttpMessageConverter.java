package com.andrewmccall.jamboree;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

public class AddressBookHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    Logger log = LoggerFactory.getLogger(this.getClass());

    public AddressBookHttpMessageConverter() {
        super(MediaType.ALL);

        if (log.isTraceEnabled())
            log.trace("Init.");
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (log.isTraceEnabled())
            log.trace("can write: " + clazz + " with media: " + mediaType.toString());
        return super.canWrite(clazz, mediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        if (log.isTraceEnabled())
            log.trace("supports: " + clazz.getName());
        if (Collection.class.isAssignableFrom(clazz) || Person.class.isAssignableFrom(clazz))
            return true;
        return false;
    }

    @Override
    protected Person readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        JSONObject json = null;
        try {
            json = new JSONObject(new JSONTokener(new InputStreamReader(inputMessage.getBody())));


            Person person = new Person();
            if (json.has("id"))
                person.setId(json.getLong("id"));
            if (json.has("firstname"))
                person.setFirstname(json.getString("firstname"));
            if (json.has("lastname"))
                person.setLastname(json.getString("lastname"));
            if (json.has("phone"))
                person.setPhone(json.getString("phone"));

            return person;
        } catch (JSONException e) {
            throw new IOException(e);
        }

    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        if (Person.class.isAssignableFrom(object.getClass())) {

            write((Person) object, new OutputStreamWriter(outputMessage.getBody()));

        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            if (log.isTraceEnabled())
                log.trace("Writing collection: " + ((Collection<Person>) object).size());

            write((Collection<Person>) object, new OutputStreamWriter(outputMessage.getBody()));
        }

    }

    private static void write(Collection<Person> people, Writer writer) throws IOException {

        writer.append("[");
        boolean first = true;
        for (Person p : people) {
            if (!first)
                writer.append(',');
            first = false;
            write(p, writer);
        }
        writer.append("]");
        writer.flush();
    }

    private static void write(Person person, Writer writer) throws IOException {
        try {
            new JSONObject()
                    .put("id", person.getId())
                    .put("firstname", person.getFirstname())
                    .put("lastname", person.getLastname())
                    .put("phone", person.getPhone()).write(writer);
            writer.flush();
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}
