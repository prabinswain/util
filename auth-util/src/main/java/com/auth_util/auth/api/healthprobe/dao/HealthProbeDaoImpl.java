package com.auth_util.auth.api.healthprobe.dao;

import org.springframework.stereotype.Repository;

@Repository
public class HealthProbeDaoImpl implements HealthProbeDao{

    @Override
    public String getRecordFromHealthCheck() {
        return "";
    }
}
