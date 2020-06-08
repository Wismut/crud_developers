package repository.impl;

import model.Skill;
import repository.SkillRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Optional<Skill> getById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                SkillRepository.TABLE_NAME +
                " WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Skill(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(NAME_COLUMN_NAME)));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Skill> getAll() {
        Connection connection = ConnectionUtil.getConnection();
        List<Skill> skills = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                SkillRepository.TABLE_NAME)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                skills.add(new Skill(resultSet.getLong(ID_COLUMN_NAME),
                        resultSet.getString(NAME_COLUMN_NAME)));
            }
            return skills;
        } catch (SQLException e) {
            e.printStackTrace();
            return skills;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Skill save(Skill skill) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                        SkillRepository.TABLE_NAME +
                        " VALUES(0, ?)"
                , Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, skill.getName());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long newId = generatedKeys.getLong(1);
            skill.setId(newId);
            return skill;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public void deleteBy(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " +
                SkillRepository.TABLE_NAME +
                " WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Skill update(Skill skill) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE " +
                SkillRepository.TABLE_NAME +
                " SET " +
                NAME_COLUMN_NAME +
                " = ? WHERE " +
                ID_COLUMN_NAME +
                " = ?")) {
            statement.setString(1, skill.getName());
            statement.setLong(2, skill.getId());
            statement.execute();
            return skill;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public Optional<Skill> getByName(String name) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                SkillRepository.TABLE_NAME +
                " WHERE " +
                NAME_COLUMN_NAME +
                " = ?")) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Skill(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(NAME_COLUMN_NAME)));
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Skill> getAllByNames(List<String> names) {
        Connection connection = ConnectionUtil.getConnection();
        List<Skill> skills = new ArrayList<>();
        String namesForQuery = names.stream()
                .map(n -> "'" + n + "'")
                .reduce((a, b) -> a + ',' + b)
                .map(s -> '(' + s + ')')
                .orElseThrow(RuntimeException::new);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                SkillRepository.TABLE_NAME +
                " WHERE " +
                NAME_COLUMN_NAME +
                " IN " +
                namesForQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                skills.add(new Skill(resultSet.getLong(ID_COLUMN_NAME),
                        resultSet.getString(NAME_COLUMN_NAME)));
            }
            return skills;
        } catch (SQLException e) {
            e.printStackTrace();
            return skills;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public List<Long> saveBatch(List<Skill> skills) {
        Connection connection = ConnectionUtil.getConnection();
        List<Long> generatedIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                        SkillRepository.TABLE_NAME +
                        " VALUES(0, ?)"
                , Statement.RETURN_GENERATED_KEYS)) {
            for (Skill skill : skills) {
                statement.setString(1, skill.getName());
                statement.addBatch();
                statement.clearParameters();
            }
            statement.executeBatch();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                generatedIds.add(generatedKeys.getLong(1));
            }
            return generatedIds;
        } catch (SQLException e) {
            return generatedIds;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }
}
