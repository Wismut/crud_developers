package repository;

import model.Specialty;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {
    String TABLE_NAME = "specialties";
    String ID_ROW_NAME = "id";
    String NAME_ROW_NAME = "name";
    String DESCRIPTION_ROW_NAME = "description";
}
