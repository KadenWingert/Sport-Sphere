package coms309.PreferenceValue;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;
import coms309.Users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preference_value")
public class PreferenceValue {

    public PreferenceValue(String preferenceName, Boolean isEnabled, Integer intValue,
            String stringValue, Users user) {
        this.name = preferenceName;
        this.enabled = isEnabled;
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(name = "name")
    @NotFound(action = NotFoundAction.EXCEPTION)
    @NonNull
    private String name;

    @Column(name = "enabled")
    @NotFound(action = NotFoundAction.IGNORE)
    private Boolean enabled;

    @Column(name = "int_value")
    private int intValue;

    @Column(name = "string_value")
    private String stringValue;

    @OneToOne(mappedBy = "preferenceValue")
    private Users user;
}
