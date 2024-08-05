package io.e4i2.repository;

import io.e4i2.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
