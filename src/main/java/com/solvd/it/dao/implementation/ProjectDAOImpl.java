package com.solvd.it.dao.implementation;

import com.solvd.it.util.ConnectionPool;
import com.solvd.it.company.Project;
import java.sql.*;

public class ProjectDAOImpl {

    public void save(Project project) {
        String sql = "INSERT INTO projects (name, project_code) VALUES (?, ?)";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, project.getProjectName());
            ps.setInt(2, 205);
            ps.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
