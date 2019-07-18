package dynamusic;

import atg.repository.RepositoryItemImpl;
import atg.repository.RepositoryPropertyDescriptor;

import java.util.Date;

public class FindAge extends RepositoryPropertyDescriptor {

    public static final String AGE_FORMAT_ATTR_NAME = "ageFormat";

    private String ageFormat = "";

    @Override
    public void setValue(String pAttributeName, Object pValue) {
        if (pAttributeName.equals(AGE_FORMAT_ATTR_NAME)) {
            String value = pValue.toString();
            if (!value.equalsIgnoreCase("years") && !value.equalsIgnoreCase("days")) {
                throw new IllegalArgumentException("Incorrect value of " + AGE_FORMAT_ATTR_NAME + " attribute: " + value);
            }
            ageFormat = value;
        }
    }

    @Override
    public Object getPropertyValue(RepositoryItemImpl pItem, Object pValue) {
        Date dateOfBirth = (Date) pItem.getPropertyValue("dateOfBirth");
        if (ageFormat.equals("days")) {
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
