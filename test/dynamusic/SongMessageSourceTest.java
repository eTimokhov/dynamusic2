package dynamusic;

import atg.dms.patchbay.MessageSourceContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SongMessageSourceTest {

    private SongMessageSource songMessageSource = new SongMessageSource();

    @Mock
    private ObjectMessage objectMessage;

    @Mock
    private MessageSourceContext context;

    @Before
    public void setUp() throws JMSException {
        MockitoAnnotations.initMocks(this);
        when(context.createObjectMessage()).thenReturn(objectMessage);
        songMessageSource.setMessageSourceContext(context);
        songMessageSource.stopMessageSource();
    }

    @Test
    public void testSendMessageCalledWhenMessageSourceIsRunning() throws JMSException {
        songMessageSource.startMessageSource();
        songMessageSource.fireMessage("123","genre","title");
        verify(context).sendMessage(objectMessage);
    }

    @Test
    public void testSendMessageNotCalledWhenMessageSourceIsNotRunning() throws JMSException {
        songMessageSource.fireMessage("123","genre","title");
        verify(context, never()).sendMessage(objectMessage);
    }
}