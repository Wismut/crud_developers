package repository;

import model.Developer;

public interface DeveloperRepository extends GenericRepository<Developer, Long> {
    String TABLE_NAME = "developers";
    String ID_ROW_NAME = "id";
    String FIRSTNAME_ROW_NAME = "first_name";
    String LASTNAME_ROW_NAME = "last_name";
    String SPECIALTYID_ROW_NAME = "specialty_id";
}
