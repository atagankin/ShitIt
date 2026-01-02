import java.util.Objects;

public abstract class Parcel {
    protected final static int DELIVERY_COST_STANDART = 2;
    protected final static int DELIVERY_COST_FRAGILE = 4;
    protected final static int DELIVERY_COST_PERISHABLE = 3;

    protected final String description;
    protected final int weight;
    protected final String deliveryAddress;
    protected int sendDay;
    protected ParcelStatus keyState;


    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
        this.keyState = ParcelStatus.NEW;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getSendDay() {
        return sendDay;
    }

    public void packageItem() {
        this.keyState = ParcelStatus.PACKED;
        System.out.printf("Посылка %s упакована\n", this.description);
    }

    public void deliver() {
        this.keyState = ParcelStatus.DELIVERED;
        System.out.printf("Посылка %s доставлена по адресу %s\n", this.description, this.deliveryAddress);
    }

    protected abstract int getCost();

    public int calculateDeliveryCost() {
        return this.weight * this.getCost();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        if (this == o) return true;
        Parcel target = (Parcel) o;
        return Objects.equals(this.description, target.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description);
    }

    @Override
    public String toString() {
        return this.description + " (вес: " + this.weight + ")";
    }

    public void setKeyState(ParcelStatus keyState) {
        this.keyState = keyState;
    }

    public ParcelStatus getKeyState() {
        return keyState;
    }
}
