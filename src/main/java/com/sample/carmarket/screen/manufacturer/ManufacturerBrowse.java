package com.sample.carmarket.screen.manufacturer;

import com.sample.carmarket.app.DataComBean;
import com.sample.carmarket.entity.Car;
import com.sample.carmarket.entity.EngineType;
import com.sample.carmarket.entity.Model;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.screen.*;
import com.sample.carmarket.entity.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static liquibase.repackaged.net.sf.jsqlparser.util.validation.metadata.NamedObject.table;

@UiController("Manufacturer.browse")
@UiDescriptor("manufacturer-browse.xml")
@LookupComponent("table")
public class ManufacturerBrowse extends MasterDetailScreen<Manufacturer> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataComBean dataComBean;

    @Subscribe("table.calculateCarsAction")
    public void onTableCalculateCarsAction(Action.ActionPerformedEvent event) {
        Manufacturer manufacturer = getTable().getSingleSelected();


        List<KeyValueEntity> allCars = dataComBean.countAllCars(manufacturer);
        String countElectricCars="0";
        String countGasolineCars="0";

        for (KeyValueEntity item: allCars){
            Object type = item.getValue("type");
            Object amount = item.getValue("amount");
            if(EngineType.fromId(type.toString()) == EngineType.ELECTRIC){
                countElectricCars= amount.toString();
            }
            else if(EngineType.fromId(type.toString()) == EngineType.GASOLINE){
                countGasolineCars= amount.toString();
            }
          }

        notifications.create().withCaption("Electric cars: " + countElectricCars
                                            + "; gasoline cars: " + countGasolineCars)
                                .show();

    }
}