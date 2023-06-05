package com.sample.carmarket.app;

import com.sample.carmarket.entity.EngineType;
import com.sample.carmarket.entity.Manufacturer;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataComBean {

    @Autowired
    private DataManager dataManager;

    public int countElCars(Manufacturer manufacturer){
        List<KeyValueEntity> countCarsElectric = dataManager.loadValues("select e.model from Car e where e.model.manufacturer = :manufac and e.model.engineType = :engType")
                .parameter("manufac", manufacturer)
                .parameter("engType", EngineType.ELECTRIC)
                .list();
        return countCarsElectric.size();
    }

    public int countGasCars(Manufacturer manufacturer){
        List<KeyValueEntity> countCarsGasoline = dataManager.loadValues("select e.model from Car e where e.model.manufacturer = :manufac and e.model.engineType = :engType")
                .parameter("manufac", manufacturer)
                .parameter("engType", EngineType.GASOLINE)
                .list();
        return countCarsGasoline.size();
    }
}