package deserialization.fail.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeserializationFromUntypedCollectionsTest {
    @Test
    public void mapDeserializationTastThatWorks() throws JsonProcessingException {
        List<Object> list = new ArrayList<>();

        User user = new User();
        user.setLogin("cool_man");
        user.setId(42L);

        UserContainer container = new UserContainer();
        container.setUser(user);

        list.add(user);
        list.add(container);

        ObjectMapper mapper = MapperUtil.getMapper();
        String serialized = mapper.writeValueAsString(list);

        Object deserialized = mapper.readValue(serialized, Object.class);

        Assertions.assertTrue(deserialized instanceof List);
        Assertions.assertTrue(((List)deserialized).get(0) instanceof User);
        Assertions.assertTrue(((List)deserialized).get(1) instanceof UserContainer);
    }

    @Test
    public void mapDeserializationTastThatNotWorks() throws JsonProcessingException {
        List<Object> list = new ArrayList<>();

        User user = new User();
        user.setLogin("cool_man");
        user.setId(42L);

        UserContainer container = new UserContainer();
        container.setUser(user);

        list.add(container);
        list.add(user);

        ObjectMapper mapper = MapperUtil.getMapper();
        String serialized = mapper.writeValueAsString(list);

        Object deserialized = mapper.readValue(serialized, Object.class);

        Assertions.assertTrue(deserialized instanceof List);
        Assertions.assertTrue(((List)deserialized).get(0) instanceof UserContainer);
        Assertions.assertTrue(((List)deserialized).get(1) instanceof User); // Boom!
    }
}
