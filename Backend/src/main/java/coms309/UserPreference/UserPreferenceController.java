// package coms309.UserPreference;

// import java.util.Optional;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
// import org.springframework.graphql.data.method.annotation.Argument;
// import org.springframework.graphql.data.method.annotation.MutationMapping;
// import org.springframework.graphql.data.method.annotation.QueryMapping;
// import org.springframework.stereotype.Controller;
// import coms309.PreferenceValue.PreferenceValueRepository;
// import coms309.Users.UserRepository;
// import coms309.Users.Users;
// import coms309.PreferenceValue.PreferenceValue;

// @Controller
// public class UserPreferenceController {

// @Autowired
// private UserPreferenceReprository userPreferenceReprository;

// @Autowired
// private PreferenceValueRepository valueRepository;

// @Autowired
// private UserRepository userRepository;

// @QueryMapping
// public Optional<UserPreference> getUserPreferenceById(@Argument int id) {
// return userPreferenceReprository.findById(id);
// }

// @QueryMapping
// public Iterable<UserPreference> getAllUserPreferences() {
// return userPreferenceReprository.findAll();
// }

// @QueryMapping
// public UserPreference getUserPreferenceByUserId(@Argument int userId, @Argument int valueId) {

// PreferenceValue value = valueRepository.findById(valueId);

// return userPreferenceReprository.findByUser(userRepository.findById(userId));
// }

// @MutationMapping
// public UserPreference addUserPreference(@Argument UserPreference userPreference) {
// return userPreferenceReprository.save(userPreference);
// }

// @MutationMapping
// public UserPreference updateUserPreference(@Argument UserPreference userPreference) {
// return userPreferenceReprository.save(userPreference);
// }

// @MutationMapping
// public void deleteUserPreference(@Argument int id) {
// userPreferenceReprository.deleteById(id);
// }

// @MutationMapping
// public UserPreference createPreference(@Argument String preferenceName,
// @Argument Integer intValue, @Argument String stringValue, @Argument Boolean isEnabled,
// @Argument int userId) {

// UserPreference userPreference = new UserPreference(userRepository.findById(userId));
// this.userPreferenceReprository.save(userPreference);

// // PreferenceValue preferenceValue = new PreferenceValue(preferenceName, isEnabled,
// // intValue,
// // stringValue, userPreference);

// // this.valueRepository.save(preferenceValue);

// // userPreference.setValues(preferenceValue);
// // userPreferenceReprository.save(userPreference);

// Optional<UserPreference> t =
// this.userPreferenceReprository.findById(userPreference.getId());
// return userPreference;
// }

// // @MutationMapping
// // public UserPreference createPreference2(@Argument PreferenceValue value, @Argument Users
// // user) {
// // this.valueRepository.save(value);
// // UserPreference userPreference = new UserPreference(value, user);
// // this.userPreferenceReprository.save(userPreference);
// // return userPreference;
// // }
// }
