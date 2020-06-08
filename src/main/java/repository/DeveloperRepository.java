package repository;

import model.Developer;

import java.util.List;

public interface DeveloperRepository extends GenericRepository<Developer, Long> {
    String TABLE_NAME = "developers";
    String ID_COLUMN_NAME = "id";
    String FIRSTNAME_COLUMN_NAME = "first_name";
    String LASTNAME_COLUMN_NAME = "last_name";
    String SPECIALTYID_COLUMN_NAME = "specialty_id";

    List<Developer> getAllBySpecialty(String specialityName);
}
