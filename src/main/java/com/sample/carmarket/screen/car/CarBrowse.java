package com.sample.carmarket.screen.car;

import com.sample.carmarket.entity.Status;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.sample.carmarket.entity.Car;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

@UiController("Car.browse")
@UiDescriptor("car-browse.xml")
@LookupComponent("carsTable")
public class CarBrowse extends StandardLookup<Car> {
    @Autowired
    private GroupTable<Car> carsTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;

    @Subscribe("carsTable.markAsSold")
    public void onCarsTableMarkAsSold(Action.ActionPerformedEvent event) {

        if (carsTable.getSingleSelected().getStatus() == Status.SOLD){
            notifications.create().withCaption("Already Sold").show();
        }
        else {
            UUID carId = carsTable.getSingleSelected().getId();
            Car selectedCar = dataManager.load(Car.class).id(carId).one();
            selectedCar.setStatus(Status.SOLD);
            selectedCar.setDateOfSale(Calendar.getInstance().getTime());
            dataManager.save(selectedCar);
            getScreenData().loadAll();
            notifications.create().withCaption("Done").show();
        }
    }
}