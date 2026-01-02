// хрупкая посылка
public class FragileParcel extends Parcel implements Trackable {

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    protected int getCost() {
        return DELIVERY_COST_FRAGILE;
    }

    @Override
    public void packageItem() {
        this.keyState = ParcelStatus.PACKED;
        System.out.printf("Посылка %s обёрнута в защитную плёнку\n", this.description);
        System.out.printf("Посылка %s упакована\n", this.description);
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.printf("Хрупкая посылка %s изменила местоположение на %s\n", this.description, newLocation);
    }
}
