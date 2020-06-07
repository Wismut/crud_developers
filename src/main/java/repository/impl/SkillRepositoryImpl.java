package repository.impl;

import model.Skill;
import repository.SkillRepository;
import repository.connectionpool.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Optional<Skill> getById(Long id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SkillRepository.TABLE_NAME + " WHERE " + ID_ROW_NAME + " = ?");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(new Skill(resultSet.getLong(ID_ROW_NAME),
                    resultSet.getString(NAME_ROW_NAME)));
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
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + SkillRepository.TABLE_NAME);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                skills.add(new Skill(resultSet.getLong(ID_ROW_NAME),
                        resultSet.getString(NAME_ROW_NAME)));
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
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Skill update(Skill skill) {
        return null;
    }

    @Override
    public Optional<Skill> getByName(String name) {
        return Optional.empty();
    }
}
