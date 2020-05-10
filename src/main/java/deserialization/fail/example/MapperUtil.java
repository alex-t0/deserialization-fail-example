package deserialization.fail.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class MapperUtil {
    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        BasicPolymorphicTypeValidator.Builder builder = BasicPolymorphicTypeValidator.builder();

        BasicPolymorphicTypeValidator.TypeMatcher matcher = new BasicPolymorphicTypeValidator.TypeMatcher() {
            @Override
            public boolean match(MapperConfig<?> mapperConfig, Class<?> aClass) {
                return true;
            }
        };

        PolymorphicTypeValidator ptv = builder.allowIfSubType(matcher).allowIfBaseType(matcher).build();

        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.EVERYTHING);

        Hibernate5Module hibernateModule = new Hibernate5Module();
        hibernateModule.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        hibernateModule.configure(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS, true);
        // hibernateModule.configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        mapper.registerModule(hibernateModule);

        return mapper;
    }
}
