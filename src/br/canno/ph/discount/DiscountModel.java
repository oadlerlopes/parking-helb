package br.canno.ph.discount;

import br.canno.ph.car.type.Vehicle;
import br.canno.ph.car.type.VehicleType;
import br.canno.ph.discount.day.DiscountDay;
import java.util.Calendar;
import java.util.Date;

public class DiscountModel {

    public DiscountModel() {
    }

    public float discountByDay(Vehicle vehicle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case 2:
                return vehicle.getVehicleType().getPrice();
            case 3:
                if (vehicle.getVehicleType().equals(VehicleType.MOTO)) {
                    return (vehicle.getVehicleType().getPrice() / 2);
                } else {
                    return vehicle.getVehicleType().getPrice();
                }
            case 4:
                if (vehicle.getLicensePlate().substring(0, 1).equalsIgnoreCase("P")) {
                    float discount = vehicle.getVehicleType().getPrice() / 4;
                    return (vehicle.getVehicleType().getPrice() - discount);
                } else {
                    return vehicle.getVehicleType().getPrice();
                }
            case 5:
                break;
            case 6:
                if (vehicle.getVehicleType().equals(VehicleType.CAMIONETTE)) {
                    return (vehicle.getVehicleType().getPrice() / 2);
                } else {
                    return vehicle.getVehicleType().getPrice();
                }
            case 7:
                if ((calendar.get(Calendar.DAY_OF_MONTH) % 2) == 0) {
                    return (vehicle.getVehicleType().getPrice() / 2);
                } else {
                    return vehicle.getVehicleType().getPrice();
                }
            case 1:
                return vehicle.getVehicleType().getPrice();
            default:
                return vehicle.getVehicleType().getPrice();
        }

        return vehicle.getVehicleType().getPrice();
    }

    public DiscountDay checkDiscount(Vehicle vehicle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case 2:
                return DiscountDay.MONDAY;
            case 3:
                return DiscountDay.TUESDAY;
            case 4:
                return DiscountDay.WEDNESDAY;
            case 5:
                return DiscountDay.THURSDAY;
            case 6:
                return DiscountDay.FRIDAY;
            case 7:
                return DiscountDay.SATURDAY;
            case 1:
                return DiscountDay.SUNDAY;
            default:
                return DiscountDay.TUESDAY;
        }

    }

}
