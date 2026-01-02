// стандартная посылка
public class StandardParcel extends Parcel {
    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    protected int getCost() {
        return DELIVERY_COST_STANDART;
    }
}
