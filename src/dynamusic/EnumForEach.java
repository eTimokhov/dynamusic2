package dynamusic;

import atg.repository.Repository;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;

import javax.servlet.ServletException;
import java.io.IOException;

public class EnumForEach extends DynamoServlet {

    private static final String PARAM_REPOSITORY_NAME = "repositoryName";
    private static final String PARAM_ITEM_DESCRIPTOR_NAME = "itemDescriptorName";
    private static final String PARAM_PROPERTY_NAME = "propertyName";
    private static final String OUTPUT_PARAM_ENUM_VALUE = "element";
    private static final String OPARAM_OUTPUT = "output";
    private static final String OPARAM_ERROR = "error";

    @Override
    public void service(DynamoHttpServletRequest request, DynamoHttpServletResponse response) throws ServletException, IOException {
        String repositoryName = request.getParameter(PARAM_REPOSITORY_NAME);
        String itemDescriptorName = request.getParameter(PARAM_ITEM_DESCRIPTOR_NAME);
        String propertyName = request.getParameter(PARAM_PROPERTY_NAME);

        String[] enumValues;
        try {
            Repository repository = (Repository) resolveName(repositoryName);
            enumValues = EnumeratedProperties.getEnumeratedProperties(repository, itemDescriptorName, propertyName);
        } catch (Exception e) {
            if (isLoggingError()) logError(e);
            request.serviceParameter(OPARAM_ERROR, request, response);
            return;
        }
        if (enumValues.length == 0) {
            request.serviceParameter(OPARAM_ERROR, request, response);
            return;
        }

        for (String enumValue : enumValues) {
            request.setParameter(OUTPUT_PARAM_ENUM_VALUE, enumValue);
            request.serviceParameter(OPARAM_OUTPUT, request, response);
        }
    }
}
