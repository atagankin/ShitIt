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
        System.out.printf("Посылка %s обёрнута в защитную плёнку\n", this.description);
        super.packageItem();
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.printf("Хрупкая посылка %s изменила местоположение на %s\n", this.description, newLocation);
    }
}
