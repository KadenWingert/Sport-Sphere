package coms309.PreferenceValue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceValueRepository extends JpaRepository<PreferenceValue, Integer> {

    coms309.PreferenceValue.PreferenceValue findById(int id);

    void deleteById(int id);

}
