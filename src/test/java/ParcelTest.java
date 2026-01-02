import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParcelTest {
    private static StandardParcel parcelStd;
    private static FragileParcel parcelFrag;
    private static PerishableParcel parcelPerish;

    @BeforeEach
    public void beforeEach() {
        parcelStd = new StandardParcel("Посылка #1", 10, "Москва", 1);
        parcelFrag = new FragileParcel("Посылка #2", 10, "Москва", 1);
        parcelPerish = new PerishableParcel("Посылка #3", 10, "Москва", 1, 4);
    }

    @Test
    public void shouldReturn2ForStandartParcelAndDaysIs1Day() {
        assertEquals(2, parcelStd.getCost());
    }

    @Test
    public void shouldReturn4ForFragileParcelAndDaysIs1Day() {
        assertEquals(4, parcelFrag.getCost());
    }

    @Test
    public void shouldReturn3ForPerishableParcelAndDaysIs1Day() {
        assertEquals(3, parcelPerish.getCost());
    }

    @Test
    public void shouldReturnFalseForPerishableParcelAndDaysIs4Days() {
        assertFalse(parcelPerish.isExpired(5));
    }

    @Test
    public void shouldReturnTrueForPerishableParcelAndDaysIs5Days() {
        assertTrue(parcelPerish.isExpired(6));
    }

}
