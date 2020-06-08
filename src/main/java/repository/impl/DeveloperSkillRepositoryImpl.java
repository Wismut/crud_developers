package repository.impl;

import model.DeveloperSkill;
import repository.DeveloperSkillRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperSkillRepositoryImpl implements DeveloperSkillRepository {
    public List<DeveloperSkill> getAllByDeveloperId(Long developerId) {
        Connection connection = ConnectionUtil.getConnection();
        List<DeveloperSkill> developerSkills = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                DeveloperSkillRepository.TABLE_NAME +
                " WHERE " +
                DEVELOPERID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, developerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                developerSkills.add(new DeveloperSkill(resultSet.getLong(DEVELOPERID_COLUMN_NAME),
                        resultSet.getLong(SKILLID_COLUMN_NAME)));
            }
            return developerSkills;
        } catch (SQLException e) {
            e.printStackTrace();
            return developerSkills;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    public List<DeveloperSkill> getAllBySkillId(Long skillId) {
        Connection connection = ConnectionUtil.getConnection();
        List<DeveloperSkill> developerSkills = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                DeveloperSkillRepository.TABLE_NAME +
                " WHERE " +
                SKILLID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, skillId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                developerSkills.add(new DeveloperSkill(resultSet.getLong(DEVELOPERID_COLUMN_NAME),
                        resultSet.getLong(SKILLID_COLUMN_NAME)));
            }
            return developerSkills;
        } catch (SQLException e) {
            e.printStackTrace();
            return developerSkills;
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    public boolean isDeveloperHasSkill(Long developerId, Long skillId) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " +
                DeveloperSkillRepository.TABLE_NAME +
                " WHERE " +
                DEVELOPERID_COLUMN_NAME +
                " = ? AND " +
                SKILLID_COLUMN_NAME +
                " = ?")) {
            statement.setLong(1, developerId);
            statement.setLong(2, skillId);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }

    @Override
    public DeveloperSkill save(DeveloperSkill developerSkill) {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " +
                DeveloperSkillRepository.TABLE_NAME +
                " VALUES(?, ?)")) {
            statement.setLong(1, developerSkill.getDeveloperId());
            statement.setLong(2, developerSkill.getSkillId());
            statement.execute();
            return developerSkill;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.releaseConnection(connection);
        }
    }
}
