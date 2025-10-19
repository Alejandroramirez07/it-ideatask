package com.solvd.it.dao.implementation;

import com.solvd.it.dao.SplunkMonitoringDAO;
import com.solvd.it.monitoring.SplunkMonitoring;
import com.solvd.it.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SplunkMonitoringDAOImpl implements SplunkMonitoringDAO {
    private static final Logger LOGGER = LogManager.getLogger(SplunkMonitoringDAOImpl.class);
    @Override
    public int save(SplunkMonitoring splunkMonitoring){
        String sql = "INSERT INTO splunk_monitoring (project_code, monitor_comments, number_incidents) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps= connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, splunkMonitoring.getProjectCode());
            ps.setString(2, splunkMonitoring.getMonitorComments());
            ps.setInt(3, splunkMonitoring.getNumberIncidents());
            ps.executeUpdate();

            try (ResultSet resultSet= ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId= resultSet.getInt(1);
                }
            }

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    @Override
    public Optional<SplunkMonitoring> findById(int projectCode) {
        String sql ="SELECT * FROM splunk_monitoring WHERE project_code =?";
        SplunkMonitoring splunkMonitoring=null;

        try (Connection connection =ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, projectCode);

            try (ResultSet resultSet =preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    splunkMonitoring=mapResultSetToSplunkMonitoring(resultSet);
                }
            }
        }catch (SQLException | InterruptedException e) {
            LOGGER.error("Error finding SplunkMonitoring record by project_code: " + e.getMessage());
        }
        return Optional.ofNullable(splunkMonitoring);
    }

    @Override
    public List<SplunkMonitoring> findAll() {
        List<SplunkMonitoring> monitors = new ArrayList<>();
        String sql = "SELECT * FROM splunk_monitoring";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                monitors.add(mapResultSetToSplunkMonitoring(rs));
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Error finding all SplunkMonitoring records: " + e.getMessage());
        }
        return monitors;
    }

    // --- U P D A T E ---
    @Override
    public boolean update(SplunkMonitoring monitor) {
        String sql = "UPDATE splunk_monitoring monitor_comments = ?, number_incidents = ? WHERE project_code = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(2, monitor.getMonitorComments());
            ps.setInt(3, monitor.getNumberIncidents());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Error updating SplunkMonitoring record: " + e.getMessage());
            return false;
        }
    }

    // --- D E L E T E ---
    @Override
    public boolean delete(int projectCode) {
        String sql = "DELETE FROM splunk_monitoring WHERE project_code = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, projectCode);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Error deleting SplunkMonitoring record: " + e.getMessage());
            return false;
        }
    }

    private SplunkMonitoring mapResultSetToSplunkMonitoring(ResultSet rs) throws SQLException {
        SplunkMonitoring monitor = new SplunkMonitoring();

        monitor.setProjectCode(rs.getInt("project_code"));
        monitor.setMonitorComments(rs.getString("monitor_comments"));
        monitor.setNumberIncidents(rs.getInt("number_incidents"));

        return monitor;
    }
}

