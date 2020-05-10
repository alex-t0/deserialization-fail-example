package deserialization.fail.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSerializationTest {
    @Test
    void testSerializationDeserialization() throws JsonProcessingException {
        User user = new User();

        user.setId(42L);
        user.setLogin("aaa");

        UserPair pair = new UserPair();

        pair.setUser1(user);
        pair.setUser2(user);

        ObjectMapper mapper = MapperUtil.getMapper();

        String serialized = mapper.writeValueAsString(pair);

        Object deserialized = mapper.readValue(serialized, Object.class);
    }
}
