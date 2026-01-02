import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelBoxTest {
    private static StandardParcel parcelStdFirst;
    private static StandardParcel parcelStdSecond;
    private static ParcelBox<StandardParcel> littleBox;
    private static ParcelBox<StandardParcel> bigBox;

    @BeforeEach
    public void beforeEach() {
        parcelStdFirst = new StandardParcel("Посылка #1", 10, "Москва", 1);
        parcelStdSecond = new StandardParcel("Посылка #2", 10, "Москва", 1);

        littleBox = new ParcelBox<>(10, 1);
        bigBox = new ParcelBox<>(100, 2);
    }

    @Test
    public void shouldReturn1ForlittleBoxWhenWeightIs10kg() {
        littleBox.addParcel(parcelStdFirst);
        assertEquals(1, littleBox.getAllParcels().size());
    }

    @Test
    public void shouldReturn1ForLittleBoxWhenWeightIs20kg() {
        littleBox.addParcel(parcelStdFirst);
        littleBox.addParcel(parcelStdFirst);
        assertEquals(1, littleBox.getAllParcels().size());
    }

    @Test
    public void shouldReturn2ForBigBoxWhenWeightIs20kg() {
        bigBox.addParcel(parcelStdFirst);
        bigBox.addParcel(parcelStdSecond);
        assertEquals(2, bigBox.getAllParcels().size());
    }

}
