package com.re.rikkeistoreinventoryaop.service;

import com.re.rikkeistoreinventoryaop.dto.CreateKeeperRequest;
import com.re.rikkeistoreinventoryaop.entity.WarehouseKeeper;
import com.re.rikkeistoreinventoryaop.repository.WarehouseKeeperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WarehouseKeeperService {
    private static final Logger log = LoggerFactory.getLogger(WarehouseKeeperService.class);

    private final WarehouseKeeperRepository warehouseKeeperRepository;

    public WarehouseKeeperService(WarehouseKeeperRepository warehouseKeeperRepository) {
        this.warehouseKeeperRepository = warehouseKeeperRepository;
    }

    public WarehouseKeeper create(CreateKeeperRequest request) {
        WarehouseKeeper keeper =
                warehouseKeeperRepository.save(new WarehouseKeeper(request.fullName(), request.staffCode()));
        log.info("Đã tạo nhân viên kho staffCode={}", keeper.getStaffCode());
        return keeper;
    }
}
