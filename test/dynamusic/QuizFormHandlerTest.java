package dynamusic;

import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;

public class QuizFormHandlerTest {

    private static final String VALIDATE_SUCCESS_URL = "success";
    private static final String VALIDATE_ERROR_URL = "error";

    @Mock
    private DynamoHttpServletRequest requestMock;

    @Mock
    private DynamoHttpServletResponse responseMock;

    private QuizFormHandler quizFormHandler = new QuizFormHandler();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        quizFormHandler.setValidateSuccessURL(VALIDATE_SUCCESS_URL);
        quizFormHandler.setValidateErrorURL(VALIDATE_ERROR_URL);
    }

    @After
    public void tearDown() {
        quizFormHandler.setUserAnswer(null);
        quizFormHandler.setAnswer(null);
        quizFormHandler.setCorrectAnswer(false);
    }

    @Test
    public void testHandleValidateWhenAnswerIsCorrect() throws IOException {
        quizFormHandler.setAnswer("ABC");
        quizFormHandler.setUserAnswer("ABC");
        quizFormHandler.handleValidate(requestMock, responseMock);
        assertTrue(quizFormHandler.isCorrectAnswer());
    }

    @Test
    public void testHandleValidateWhenAnswerIsIncorrect() throws IOException {
        quizFormHandler.setAnswer("ABC");
        quizFormHandler.setUserAnswer("BCD");
        quizFormHandler.handleValidate(requestMock, responseMock);
        assertFalse(quizFormHandler.isCorrectAnswer());
    }

}