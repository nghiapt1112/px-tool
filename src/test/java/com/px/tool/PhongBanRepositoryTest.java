package com.px.tool;

import com.px.tool.model.PhongBan;
import com.px.tool.repository.PhongBanRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.stream.IntStream;

public class PhongBanRepositoryTest extends PxApplicationTests {
    @Autowired
    private PhongBanRepository phongBanRepository;


    @Test
    public void create() {
        IntStream.range(0,12)
        .forEach(el -> {
            PhongBan phongBan = new PhongBan();
            phongBan.setLevel(1);
            phongBan.setName(UUID.randomUUID().toString() + el);
            this.phongBanRepository.save(phongBan);
        });
    }
}
