// package coms309.UserPreference;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import org.hibernate.annotations.NotFound;
// import org.hibernate.annotations.NotFoundAction;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
// import coms309.PreferenceValue.PreferenceValueRepository;
// import coms309.PreferenceValue.PreferenceValue;
// import coms309.Users.Users;
// import jakarta.persistence.CascadeType;
// // import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.NonNull;
// import lombok.RequiredArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// // @RequiredArgsConstructor
// @Entity
// @Table(name = "preference")
// public class UserPreference {


// // public UserPreference(String preferenceName, String preferenceValue, Boolean isEnabled) {
// // // this.preferenceOption = new PreferenceOption(preferenceName, isEnabled, preferenceValue);
// // }

// public UserPreference(PreferenceValue preferenceValue, Users user) {
// this.user = user;
// // this.values = new ArrayList<PreferenceValue>();
// this.values.add(preferenceValue);
// }

// public UserPreference(Users user) {
// this.user = user;
// }

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// @Column(name = "id")
// private int id;

// // @OneToOne
// // @JoinColumn(name = "userPreference", nullable = false)
// // private Users user;

// @OneToMany(mappedBy = "userPreferences", fetch = FetchType.LAZY)
// // @JoinColumn(name = "option_id")
// // @NotFound(action = NotFoundAction.IGNORE)
// private Set<PreferenceValue> values = new HashSet<>();

// public void setPreferenceValue(PreferenceValue preferenceValue) {
// this.values.add(preferenceValue);
// };

// // public void setValues(PreferenceValue preferenceValue) {
// // if (this.values == null) {
// // this.values = new ArrayList<PreferenceValue>();
// // }
// // values.add(preferenceValue);
// // }

// }
