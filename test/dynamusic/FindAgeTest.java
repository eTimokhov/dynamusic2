package dynamusic;

import atg.repository.RepositoryItemImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindAgeTest {

    private static final String AGE_FORMAT_ATTR_NAME = "ageFormat";
    private static final String PROPERTY_DATE_OF_BIRTH = "dateOfBirth";
    private static final String DATE_OF_BIRTH = "01/01/2010";

    private Date date;

    @Mock
    private RepositoryItemImpl dateRI;


    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);
        date = new SimpleDateFormat("dd/MM/yyyy").parse(DATE_OF_BIRTH);
        when(dateRI.getPropertyValue(PROPERTY_DATE_OF_BIRTH)).thenReturn(date);
    }

    @Test
    public void testCallAgeInDays() {
        FindAge findAge = new FindAge();
        findAge.setValue(AGE_FORMAT_ATTR_NAME, "days");
        Integer expected = AgeCalc.ageInDays(date);
        Integer actual = (Integer) findAge.getPropertyValue(dateRI, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testCallAgeInYears() {
        FindAge findAge = new FindAge();
        findAge.setValue(AGE_FORMAT_ATTR_NAME, "years");
        Integer expected = AgeCalc.ageInYears(date);
        Integer actual = (Integer) findAge.getPropertyValue(dateRI, null);
        assertEquals(expected, actual);
    }
}