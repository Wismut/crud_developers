package repository;

import model.Specialty;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {
    String TABLE_NAME = "specialties";
    String ID_COLUMN_NAME = "id";
    String NAME_COLUMN_NAME = "name";
    String DESCRIPTION_COLUMN_NAME = "description";
}
