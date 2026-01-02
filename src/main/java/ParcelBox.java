import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {
    protected ArrayList<T> parcels = new ArrayList<>();
    private final int maxWeight;
    private int currentWeight = 0;
    private int boxNum;

    public ParcelBox(int maxWeight, int boxNum) {
        this.maxWeight = maxWeight;
        this.boxNum = boxNum;
    }

    public void addParcel(T parcel) {
        if (parcels.contains(parcel)) {
            System.out.printf("Посылка %s уже находится в коробке %d\n", parcel.getDescription(), boxNum);
            return;
        }

        if ((currentWeight + parcel.getWeight()) > maxWeight) {
            System.out.printf("Превышение максимального веса коробки %d. Текущий вес %d, доступный остаток: %d",
                    this.boxNum,
                    this.maxWeight,
                    (this.maxWeight - this.currentWeight)
            );
            return;
        }

        parcels.add(parcel);
        parcel.setKeyState(ParcelStatus.BOXED);
        currentWeight += parcel.getWeight();
        System.out.printf("Посылка '%s' добавлена в коробку %d\n", parcel.getDescription(), boxNum);
    }

    public ArrayList<T> getAllParcels() {
        return parcels;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Коробка " + boxNum +" (Общий вес : " + getCurrentWeight() + ")");
        for (int i = 0; i < parcels.size(); i++) {
            sb.append("\n" + i + ". " + parcels.get(i));
        }
        return sb.toString();
    }

    public void sendBox() {
        for (T p: parcels) {
            p.deliver();
        }
        // Чистим коробку после доставки, чтобы не отправлять посылки, которые уже доставлены
        // История скорее всего хранится в БД
        parcels.clear();
    }
}
