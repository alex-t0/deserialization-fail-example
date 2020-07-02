package deserialization.fail.example;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

public class MapperUtil {
    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        BasicPolymorphicTypeValidator.Builder builder = BasicPolymorphicTypeValidator.builder();

        BasicPolymorphicTypeValidator.TypeMatcher matcher = new BasicPolymorphicTypeValidator.TypeMatcher() {
            @Override
            public boolean match(MapperConfig<?> mapperConfig, Class<?> aClass) {
                return true;
            }
        };

        PolymorphicTypeValidator ptv = builder.allowIfSubType(matcher).allowIfBaseType(matcher).build();

        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        return mapper;
    }
}
