package com.auth_util.api.healthprobe.controller;

import com.auth_util.api.healthprobe.dao.HealthProbeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.net.UnknownHostException;


@RequestMapping("/healthprobe")
@RestController
public class HealthProbeController {

    private static final Logger log = LoggerFactory.getLogger(HealthProbeController.class);
    private static final String DELIMITER = " | ";

    @Value("${spring.application.name}")
    private String serviceName;

//    @Value("${apikey.health-probo-key}")
    private String healthprobeKey;

    private final HealthProbeDao healthProbeDao;

    private final String hostName;
    private final String hostAddress;

    public HealthProbeController(HealthProbeDao healthProbeDao) throws UnknownHostException {
        this.healthProbeDao = healthProbeDao;
        InetAddress localHost = InetAddress.getLocalHost();
        this.hostName = localHost.getHostName();
        this.hostAddress = localHost.getHostAddress();
    }

    @GetMapping
    public String getProboStatus() {
        return "Working";
    }

    @GetMapping("/probo/{key}")
    public String getAppProbo(@PathVariable String key) {
        validateKey(key);

        try {
            return buildResponse(null);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "App probo failed: " + e.getMessage());
        }
    }

    @GetMapping("/db-probo/{key}")
    public String getDatabaseProbo(@PathVariable String key) {
        validateKey(key);

        try {
            String dataValue = healthProbeDao.getRecordFromHealthCheck();
            if (dataValue == null)
                throw new NullPointerException("Database returned null value");

            return buildResponse(dataValue);
        }
        catch (NullPointerException e) {
            log.error("Null pointer in DB probo: {}", e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "DB probo failed: " + e.getMessage());
        }
        catch (Exception e) {
            log.error("Exceptionin DB probo: {}", e.getMessage(),e);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "DB probo failed: " + e.getMessage());
        }
    }


    private void validateKey(String key) {
        if (!healthprobeKey.equals(key)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid key");
        }
    }

    private String buildResponse(String dbData) {
        StringBuilder builder = new StringBuilder()
                .append(serviceName)
                .append(DELIMITER)
                .append(hostName)
                .append(DELIMITER)
                .append(hostAddress);
        if (dbData != null)
            builder.append(DELIMITER).append(dbData);

        return builder.toString();
    }

}
