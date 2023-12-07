package coms309.PreferenceValue;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import coms309.Users.UserRepository;
import coms309.Users.Users;

@Controller
public class PreferenceValueController {

    @Autowired
    private PreferenceValueRepository valueRepository;

    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public PreferenceValue getPreferenceValueById(@Argument int id) {
        return valueRepository.findById(id);
    }

    @QueryMapping
    public List<PreferenceValue> getAllPreferenceValues() {
        return valueRepository.findAll();
    }

    @MutationMapping
    public PreferenceValue addValue(@Argument PreferenceValue value) {
        return valueRepository.save(value);
    }

    @MutationMapping
    public PreferenceValue updateValue(@Argument int id, @Argument String name,
            @Argument Boolean enabled, @Argument Integer int_value, @Argument String string_value,
            @Argument int userID) {

        Users user = userRepository.findById(userID);
        PreferenceValue value = valueRepository.findById(id);
        value.setName(name);
        value.setEnabled(enabled);
        value.setIntValue(int_value);
        value.setStringValue(string_value);
        value.setUser(user);
        return valueRepository.save(value);
    }

    @MutationMapping
    public void deleteValue(@Argument int id) {
        valueRepository.deleteById(id);
    }

    @MutationMapping
    public PreferenceValue createPreferenceValue(@Argument String name, @Argument Boolean enabled,
            @Argument Integer int_value, @Argument String string_value, @Argument int userID) {

        Users user = userRepository.findById(userID);

        return valueRepository
                .save(new PreferenceValue(name, enabled, int_value, string_value, user));
    }
}
