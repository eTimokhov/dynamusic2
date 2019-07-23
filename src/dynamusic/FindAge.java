package dynamusic;

import atg.repository.RepositoryItemImpl;
import atg.repository.RepositoryPropertyDescriptor;

import java.util.Date;

public class FindAge extends RepositoryPropertyDescriptor {

    private static final String AGE_FORMAT_ATTR_NAME = "ageFormat";
    private static final String YEARS = "years";
    private static final String DAYS = "days";
    private static final String PROPERTY_DATE_OF_BIRTH = "dateOfBirth";

    private String ageFormat = "";

    @Override
    public void setValue(String pAttributeName, Object pValue) {
        if (pAttributeName.equals(AGE_FORMAT_ATTR_NAME)) {
            String value = pValue.toString();
            if (!value.equalsIgnoreCase(YEARS) && !value.equalsIgnoreCase(DAYS)) {
                throw new IllegalArgumentException("Incorrect value of " + AGE_FORMAT_ATTR_NAME + " attribute: " + value);
            }
            ageFormat = value;
        }
    }

    @Override
    public Object getPropertyValue(RepositoryItemImpl pItem, Object pValue) {
        Date dateOfBirth = (Date) pItem.getPropertyValue(PROPERTY_DATE_OF_BIRTH);
        if (dateOfBirth == null) return null; //ACC Error fix
        if (ageFormat.equals(DAYS)) {
            return AgeCalc.ageInDays(dateOfBirth);
        }
        return AgeCalc.ageInYears(dateOfBirth);
    }

    @Override
    public boolean isQueryable() {
        return false;
    }

    @Override
    public boolean isWritable() {
        return false;
    }
}
