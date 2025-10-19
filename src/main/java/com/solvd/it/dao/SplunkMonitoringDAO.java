package com.solvd.it.dao;

import com.solvd.it.monitoring.SplunkMonitoring;

import java.util.List;
import java.util.Optional;

public interface SplunkMonitoringDAO {
    /*@param monitor The SplunkMonitoring object to save.
    @return the ID of the new project
     */
    int save(SplunkMonitoring splunkMonitoring);
    /*@param the unique id.
    @return the splunk objetc or empty if nothing´s there
     */
    Optional<SplunkMonitoring> findById(int projectCode);

    /**
     * Retrieves all SplunkMonitoring records.
     *
     * @return A List of all SplunkMonitoring objects.
     */

    List<SplunkMonitoring> findAll();
    /**
     * Updates an existing SplunkMonitoring record in the database.
     * @param splunkMonitoring object
     * @return True if the update was successful, false otherwise.
     */

    boolean update(SplunkMonitoring splunkMonitoring);
    /**
     * Deletes a SplunkMonitoring record by its primary ID.
     *
     * @param projectCode The unique ID of the monitor record to delete.
     * @return True if the deletion was successful, false otherwise.
     */

    boolean delete(int projectCode);




}
